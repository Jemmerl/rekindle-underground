package com.jemmerl.rekindleunderground.deposit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneGroupType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.util.Pair;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import com.jemmerl.rekindleunderground.world.capability.chunk.IChunkGennedCapability;
import com.jemmerl.rekindleunderground.world.capability.deposit.IDepositCapability;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.*;

public class DepositUtil {


    ///////////////////
    // General Utils //
    ///////////////////

    // Only works in the StoneGenFeature feature, which
    // generates on a specific corner of every chunk
    // Really should just use isInChunk, kept if needed
    @Deprecated
    public static Boolean isInsideChunk(BlockPos chunkCorner, BlockPos point) {
        return (((chunkCorner.getX() <= point.getX()) && (point.getX() <= (chunkCorner.getX() + 15)))
                && ((chunkCorner.getZ() <= point.getZ()) && (point.getZ() <= (chunkCorner.getZ() + 15))));
    }

    // Check if a point lies in the chunk
    public static boolean isInChunk(BlockPos pos, ChunkPos chunkPos) {
        return new ChunkPos(pos).equals(chunkPos);
    }

    // Check if a blockstate is a valid stone for ore generation.
    // If not a StoneOreBlock, then the second condition (which assumes that it is such) will never be reached.
    public static Boolean isValidStone(Block blockIn, ArrayList<StoneType> validStones) {
        return ((blockIn instanceof StoneOreBlock) && validStones.contains(((StoneOreBlock) blockIn).getStoneType()));
        //return (blockIn instanceof StoneOreBlock); // TODO DEBUG TOOL
    }


    //////////////////////
    // Generation Utils //
    //////////////////////

    // Process the enqueued blocks for a chunk
    public static boolean enqueueBlockPlacement(ISeedReader level, BlockPos qPos, OreType qType, GradeType qGrade,
                                                String qName, BlockPos genPos, BlockState[][][] stateMap,
                                                IDepositCapability depCap, @Nullable IChunkGennedCapability cgCap) {

        // genPos and genChunk are the corner BlockPos and ChunkPos that the statemap is being generated for
        ChunkPos genChunk = new ChunkPos(genPos);

        // qPos and qChuck are the specific block position and respective chunk of the enqueued placement
        ChunkPos qChunk = new ChunkPos(qPos);

        // qName is the name of the deposit type being enqueued from, with qDeposit being that deposit instance
        IDeposit qDeposit = DepositRegistrar.getDeposits().get(qName);

        // If the enqueued chunk is the current generating chunk, attempt to place into the statemap
        if (qChunk.equals(genChunk)) {
            // Check if the block is placing in a valid deposit biome
            if (!qDeposit.getBiomes().contains(level.getBiome(qPos).getCategory())) {
                return false;
            }

            int xIndex = Math.abs(qPos.getX() - genPos.getX());
            int zIndex = Math.abs(qPos.getZ() - genPos.getZ());
            try {
                BlockState hostState = stateMap[xIndex][qPos.getY()][zIndex];
                if (isValidStone(hostState.getBlock(), qDeposit.getValid())) {
                    stateMap[xIndex][qPos.getY()][zIndex] = hostState.with(StoneOreBlock.ORE_TYPE, qType)
                            .with(StoneOreBlock.GRADE_TYPE, qGrade);
                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                if (RKUndergroundConfig.COMMON.debug.get()){
                    RekindleUnderground.getInstance().LOGGER.warn(
                            "Enq block at {} was out of bounds with values {} {} {}",
                            qPos, xIndex, qPos.getY(), zIndex);
                }
                return false;
            }

            // If this statement reaches here, the StoneOreBlock was not
            // a valid placement stone or something has gone very wrong...
            return false;
        }

        // If not in the currently generating chunk, and the enqueued chunk has generated, try to force placement
        if (cgCap != null) {
            if (cgCap.hasChunkGenerated(qChunk)) {
                // Check if the block is placing in a valid deposit biome
                if (!qDeposit.getBiomes().contains(level.getBiome(qPos).getCategory())) {
                    return false;
                }

                BlockState state = level.getBlockState(qPos);
                if (isValidStone(state.getBlock(), qDeposit.getValid())) {
                    if (!level.setBlockState(qPos, state.with(StoneOreBlock.ORE_TYPE, qType).with(StoneOreBlock.GRADE_TYPE, qGrade), 2 | 16)) {
                        RekindleUnderground.getInstance().LOGGER.warn("Somehow {} could not be placed at {} even though chunk has generated",
                                state.getBlock().getRegistryName(), qPos);
                        return false;
                    }
                }
                return true;
            } else {
                depCap.putPendingOre(new BlockPos(qPos), qType, qGrade, qName);
                return false;
            }
        }

        return false;
    }



    ////////////////////////
    // Registration Utils //
    ////////////////////////

    // Return an weighted probability map of OreTypes from a JsonArray
    public static WeightedProbMap<OreType> getOres(JsonArray oresArray) {
        ArrayList<Pair<Integer, OreType>> elts = new ArrayList<>();
        try {
            int weightSum = 0;
            for (int i=0; i<oresArray.size(); i++) {
                JsonObject oreObj = oresArray.get(i).getAsJsonObject();
                OreType oreType = OreType.valueOf(oreObj.get("ore").getAsString().toUpperCase());
                int weight = oreObj.get("weight").getAsInt();
                weightSum += weight;
                if (weightSum > 100) {
                    throw new Exception("Ore weights sum to over 100 in deposit");
                }
                elts.add( new Pair<>(weight, oreType));
            }
        } catch (Exception e) {
            RekindleUnderground.getInstance().LOGGER.warn("Error in a deposit valid ore reading.");
            if (RKUndergroundConfig.COMMON.debug.get()) {
                e.printStackTrace();
            }
            return null;
        }
        return new WeightedProbMap<>(elts);
    }

    // Return an array list of grade chances from a JsonObject
    public static WeightedProbMap<GradeType> getGrades(JsonObject jsonObject) {
        int highGrade, midGrade, lowGrade;
        ArrayList<Pair<Integer, GradeType>> elts = new ArrayList<>();

        try {
            highGrade = Math.min(Math.max(jsonObject.get("high").getAsInt(), 0), 100);
            midGrade = Math.min(Math.max(jsonObject.get("mid").getAsInt(), 0), 100);
            lowGrade = Math.min(Math.max(jsonObject.get("low").getAsInt(), 0), 100);
            if ((highGrade + midGrade + lowGrade) > 100) {
                throw new Exception("Grade weights sum to over 100 in deposit");
            }

            elts.add( new Pair<>(highGrade, GradeType.HIGHGRADE));
            elts.add( new Pair<>(midGrade, GradeType.MIDGRADE));
            elts.add( new Pair<>(lowGrade, GradeType.LOWGRADE));

        } catch (Exception e) {
            RekindleUnderground.getInstance().LOGGER.warn("Error in a deposit ore grades reading.");
            if (RKUndergroundConfig.COMMON.debug.get()) {
                e.printStackTrace();
            }
            return null;
        }

        return new WeightedProbMap<>(elts);
    }

    // Return an array list of valid StoneTypes from a JsonArray
    public static ArrayList<StoneType> getOreStones(JsonArray jsonArray) {
        HashSet<StoneType> stoneSet = new HashSet<>(); // Using a set removes duplicate entries free of charge
            try {
                for (int i=0; i<jsonArray.size(); i++) {
                    String oreStoneStr = jsonArray.get(i).getAsString().toUpperCase();
                    switch (oreStoneStr) {
                        case "all":
                            // No need to do anything else, just return everything
                            return new ArrayList<>(EnumSet.allOf(StoneType.class));
                        case "all_stone":
                            // Return everything that is not a detritus, aka all stones
                            stoneSet.addAll(EnumSet.complementOf(StoneType.getAllInGroup(StoneGroupType.DETRITUS)));
                            break;
                        case "all_sed":
                            stoneSet.addAll(StoneType.getAllInGroup(StoneGroupType.SEDIMENTARY));
                            break;
                        case "all_ign":
                            stoneSet.addAll(StoneType.getAllInGroup(StoneGroupType.EXTRUSIVE));
                            stoneSet.addAll(StoneType.getAllInGroup(StoneGroupType.INTRUSIVE));
                            break;
                        case "all_ign_ext":
                            stoneSet.addAll(StoneType.getAllInGroup(StoneGroupType.EXTRUSIVE));
                            break;
                        case "all_ign_int":
                            stoneSet.addAll(StoneType.getAllInGroup(StoneGroupType.INTRUSIVE));
                            break;
                        case "all_meta":
                            stoneSet.addAll(StoneType.getAllInGroup(StoneGroupType.METAMORPHIC));
                            break;
                        case "all_detritus":
                            stoneSet.addAll(StoneType.getAllInGroup(StoneGroupType.DETRITUS));
                            break;
                        default:
                            stoneSet.add(StoneType.valueOf(oreStoneStr));
                    }

                }
            } catch (Exception e) {
                RekindleUnderground.getInstance().LOGGER.warn("Error in a deposit valid orestone reading.");
                if (RKUndergroundConfig.COMMON.debug.get()) {
                    e.printStackTrace();
                }
                return null;
            }

        return new ArrayList<>(stoneSet);
    }

    // Return an array list of valid Biome Categeories from a JsonArray
    public static ArrayList<Biome.Category> getBiomes(JsonArray jsonArray) {
        ArrayList<Biome.Category> biomeArray = new ArrayList<>();
        try {
            for (int i=0; i<jsonArray.size(); i++) {
                String cat = jsonArray.get(i).getAsString().toLowerCase(Locale.ROOT);
                if (cat.equals("none")) {
                    // If you don't want a deposit to generate, you could just temporarily remove it... But fine,
                    // I'll handle it. Returns a list of just "none" and prevents the deposit from generating.
                    RekindleUnderground.getInstance().LOGGER.warn("Use of NONE in deposit registration detected. " +
                            "This prevents that generation despite any other listed biomes.");
                    return new ArrayList<>(Collections.singleton(Biome.Category.NONE));
                } else if (cat.equals("all")) {
                    // If "all" is ever specified, just return a list of every biome
                    return new ArrayList<>(EnumSet.allOf(Biome.Category.class));
                } else {
                    biomeArray.add(Biome.Category.byName(cat));
                }
            }
        } catch (Exception e) {
            RekindleUnderground.getInstance().LOGGER.warn("Error in a deposit valid biome reading.");
            if (RKUndergroundConfig.COMMON.debug.get()) {
                e.printStackTrace();
            }
            return null;
        }
        return biomeArray;
    }


}
