package com.jemmerl.rekindleunderground.deposit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.OreType;
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

import javax.annotation.Nullable;
import java.util.ArrayList;

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

    // Check if a blockstate is a valid stone for ore generation
    // If not a StoneOreBlock, then the second condition (which
    // assumes that is is such) will never be reached.
    public static Boolean isValidStone(Block blockIn, ArrayList<StoneType> validStones) {
        //return ((blockIn instanceof StoneOreBlock) && validStones.contains(((StoneOreBlock) blockIn).getStoneType()));
        return (blockIn instanceof StoneOreBlock); // TODO DEBUG TOOL
    }


    //////////////////////
    // Generation Utils //
    //////////////////////

    // Process the enqueued blocks for a chunk
    public static boolean enqueueBlockPlacement(ISeedReader level, BlockPos qPos, OreType qType,
                                                String qName, BlockPos genPos, BlockState[][][] stateMap,
                                                IDepositCapability depCap, @Nullable IChunkGennedCapability cgCap) {

        // genPos and genChunk are the BlockPos and ChunkPos that the statemap is being generated for
        ChunkPos genChunk = new ChunkPos(genPos);
        ChunkPos qChunk = new ChunkPos(qPos);

        // If the enqueued chunk is the current generating chunk, attempt to place into the statemap
        if (qChunk.equals(genChunk)) {
            int xIndex = Math.abs(qPos.getX() - genPos.getX());
            int zIndex = Math.abs(qPos.getZ() - genPos.getZ());
            try {
                BlockState hostState = stateMap[xIndex][qPos.getY()][zIndex];
                if (isValidStone(hostState.getBlock(), DepositRegistrar.getDeposits().get(qName).getValid())) {
                    stateMap[xIndex][qPos.getY()][zIndex] = hostState.with(StoneOreBlock.ORE_TYPE, qType);
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
                BlockState state = level.getBlockState(qPos);
                if (isValidStone(state.getBlock(), DepositRegistrar.getDeposits().get(qName).getValid())) {
                    if (!level.setBlockState(qPos, state.with(StoneOreBlock.ORE_TYPE, qType), 2 | 16)) {
                        RekindleUnderground.getInstance().LOGGER.warn("Somehow {} could not be placed at {} even though chunk has generated",
                                state.getBlock().getRegistryName(), qPos);
                        return false;
                    }
                }
                return true;
            } else {
                depCap.putPendingOre(new BlockPos(qPos), qType, qName);
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
            RekindleUnderground.getInstance().LOGGER.warn("Error in valid deposit ore reading.");
            if (RKUndergroundConfig.COMMON.debug.get()) {
                e.printStackTrace();
            }
            return null;
        }
        return new WeightedProbMap<OreType>(elts);
    }

    // Return an array list of valid StoneTypes from a JsonArray
    public static ArrayList<StoneType> getStones(JsonArray jsonArray) {
        ArrayList<StoneType> stoneArray = new ArrayList<>();
            try {
                for (int i=0; i<jsonArray.size(); i++) {
                    stoneArray.add(StoneType.valueOf(jsonArray.get(i).getAsString().toUpperCase()));
                }
            } catch (Exception e) {
                RekindleUnderground.getInstance().LOGGER.warn("Error in deposit valid stone reading.");
                e.printStackTrace();
                return null;
            }
        return stoneArray;
    }


}
