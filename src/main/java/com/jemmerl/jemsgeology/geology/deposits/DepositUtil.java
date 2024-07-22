package com.jemmerl.jemsgeology.geology.deposits;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.data.enums.*;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.geology.GeoWrapper;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.blockinit.GeoRegistry;
import com.jemmerl.jemsgeology.init.depositinit.DepositRegistrar;
import com.jemmerl.jemsgeology.util.Pair;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.util.WeightedProbMap;
import com.jemmerl.jemsgeology.world.capability.chunk.ChunkGennedCapability;
import com.jemmerl.jemsgeology.world.capability.chunk.IChunkGennedCapability;
import com.jemmerl.jemsgeology.world.capability.deposit.DepositCapability;
import com.jemmerl.jemsgeology.world.capability.deposit.IDepositCapability;
import com.jemmerl.jemsgeology.geology.GeoMapBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

import java.util.*;

public class DepositUtil {

    private static IDepositCapability depCap;
    private static IChunkGennedCapability cgCap;

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
    public static Boolean isValidStone(Block blockIn, ArrayList<GeologyType> validStones) {
        return ((blockIn instanceof IGeoBlock) && validStones.contains(((IGeoBlock) blockIn).getGeologyType()));
        //return (blockIn instanceof IGeoBlock); // Debug Tool
    }

    // Check if a Geology Type is a valid stone for ore generation.
    public static Boolean isValidStone(GeologyType geologyType, ArrayList<GeologyType> validStones) {
        return validStones.contains(geologyType);
    }


    ////////////////////////
    // Registration Utils //
    ////////////////////////

    // Return an weighted probability map of OreTypes from a JsonArray
    public static WeightedProbMap<OreType> getOres(JsonArray oresArray, String name) {
        ArrayList<Pair<Integer, OreType>> elts = new ArrayList<>();
        try {
            int weightSum = 0;
            for (int i=0; i<oresArray.size(); i++) {
                JsonObject oreObj = oresArray.get(i).getAsJsonObject();
                OreType oreType = OreType.valueOf(oreObj.get("ore").getAsString().toUpperCase());
                int weight = oreObj.get("weight").getAsInt();
                weightSum += weight;
                if (weightSum > 100) {
                    throw new Exception("Ore weights sum to over 100 in deposit: " + name);
                }
                elts.add( new Pair<>(weight, oreType));
            }
        } catch (Exception e) {
            JemsGeology.getInstance().LOGGER.warn("Error in a deposit: {}, invalid ore entry.", name);

            // Debug
            if (JemsGeoConfig.SERVER.debug_deposit_reader.get()) {
                e.printStackTrace();
            }

            return null;
        }
        return new WeightedProbMap<>(elts);
    }

    // Return an array list of grade chances from a JsonObject
    public static WeightedProbMap<GradeType> getGrades(JsonObject jsonObject, String name) {
        int highGrade, midGrade, lowGrade;
        ArrayList<Pair<Integer, GradeType>> elts = new ArrayList<>();

        try {
            highGrade = Math.min(Math.max(jsonObject.get("high").getAsInt(), 0), 100);
            midGrade = Math.min(Math.max(jsonObject.get("mid").getAsInt(), 0), 100);
            lowGrade = Math.min(Math.max(jsonObject.get("low").getAsInt(), 0), 100);
            if ((highGrade + midGrade + lowGrade) > 100) {
                throw new Exception("Grade weights sum to over 100 in deposit: " + name);
            }

            elts.add( new Pair<>(highGrade, GradeType.HIGHGRADE));
            elts.add( new Pair<>(midGrade, GradeType.MIDGRADE));
            elts.add( new Pair<>(lowGrade, GradeType.LOWGRADE));

        } catch (Exception e) {
            JemsGeology.getInstance().LOGGER.warn("Error in a deposit: {}, invalid ore grades entry.", name);

            // Debug
            if (JemsGeoConfig.SERVER.debug_deposit_reader.get()) {
                e.printStackTrace();
            }

            return null;
        }

        return new WeightedProbMap<>(elts);
    }

    // Return an array list of valid GeologyTypes from a JsonArray
    public static ArrayList<GeologyType> getOreStones(JsonArray jsonArray, String name) {
        HashSet<GeologyType> oreStoneSet = new HashSet<>(); // Using a set removes duplicate entries free of charge
            try {
                for (int i=0; i<jsonArray.size(); i++) {
                    String oreStoneStr = jsonArray.get(i).getAsString().toUpperCase();
                    switch (oreStoneStr) {
                        case "ALL":
                            // No need to do anything else, just return everything
                            return new ArrayList<>(EnumSet.allOf(GeologyType.class));
                        case "ALL_STONE":
                            // Return everything that is not a detritus, aka all stones
                            oreStoneSet.addAll(EnumSet.complementOf(GeologyType.getAllInGroup(StoneGroupType.DETRITUS)));
                            break;
                        case "ALL_SED":
                            oreStoneSet.addAll(GeologyType.getAllInGroup(StoneGroupType.SEDIMENTARY));
                            break;
                        case "ALL_IGN":
                            oreStoneSet.addAll(GeologyType.getAllInGroup(StoneGroupType.EXTRUSIVE));
                            oreStoneSet.addAll(GeologyType.getAllInGroup(StoneGroupType.INTRUSIVE));
                            break;
                        case "ALL_IGN_EXT":
                            oreStoneSet.addAll(GeologyType.getAllInGroup(StoneGroupType.EXTRUSIVE));
                            break;
                        case "ALL_IGN_INT":
                            oreStoneSet.addAll(GeologyType.getAllInGroup(StoneGroupType.INTRUSIVE));
                            break;
                        case "ALL_META":
                            oreStoneSet.addAll(GeologyType.getAllInGroup(StoneGroupType.METAMORPHIC));
                            break;
                        case "ALL_DETRITUS":
                            oreStoneSet.addAll(GeologyType.getAllInGroup(StoneGroupType.DETRITUS));
                            break;
                        default:
                            oreStoneSet.add(GeologyType.valueOf(oreStoneStr));
                    }

                }
            } catch (Exception e) {
                JemsGeology.getInstance().LOGGER.warn("Error in a deposit/feature: {}, invalid stones entry.", name);

                // Debug
                if (JemsGeoConfig.SERVER.debug_deposit_reader.get()) {
                    e.printStackTrace();
                }

                return null;
            }

        return new ArrayList<>(oreStoneSet);
    }

    // Return an array list of valid Biome Categories from a JsonArray
    public static ArrayList<Biome.Category> getBiomes(JsonArray jsonArray, String name) {
        ArrayList<Biome.Category> biomeArray = new ArrayList<>();
        try {
            for (int i=0; i<jsonArray.size(); i++) {
                String cat = jsonArray.get(i).getAsString().toLowerCase(Locale.ROOT);
                if (cat.equals("none")) {
                    // If you don't want a deposit to generate, you could just temporarily remove it... But fine,
                    // I'll handle it. Returns a list of just "none" and prevents the deposit from generating.
                    JemsGeology.getInstance().LOGGER.warn("Use of NONE in deposit/feature: {} registration detected. " +
                            "This prevents that generation despite any other listed biomes.", name);
                    return new ArrayList<>(Collections.singleton(Biome.Category.NONE));
                } else if (cat.equals("all")) {
                    // If "all" is ever specified, just return a list of every biome
                    return new ArrayList<>(EnumSet.allOf(Biome.Category.class));
                } else {
                    biomeArray.add(Biome.Category.byName(cat));
                }
            }
        } catch (Exception e) {
            JemsGeology.getInstance().LOGGER.warn("Error in a deposit/feature: {}, invalid biome entry.", name);

            // Debug
            if (JemsGeoConfig.SERVER.debug_deposit_reader.get()) {
                e.printStackTrace();
            }

            return null;
        }
        return biomeArray;
    }


    //////////////////////
    // Generation Utils //
    //////////////////////

    // Process the Geo-Map enqueued ore blocks for a chunk
    public static boolean processGeoMapEnqueue(ISeedReader reader, BlockPos qPos, OreType qType, GradeType qGrade,
                                               String qName, BlockPos cornerPos, GeoMapBuilder geoMapBuilder) {

        if (depCap == null) {
            depCap = reader.getWorld().getCapability(DepositCapability.JEMGEO_DEPOSIT_CAPABILITY)
                    .orElseThrow(() -> new RuntimeException("JemsGeo deposit capability is null..."));
        }

        if (cgCap == null) {
            cgCap = reader.getWorld().getCapability(ChunkGennedCapability.JEMGEO_CHUNK_GEN_CAPABILITY)
                    .orElseThrow(() -> new RuntimeException("JemsGeo chunk gen capability is null..."));
        }

        GeoWrapper[][][] geoWrapperMap = geoMapBuilder.getGeoWrapperArray();

        ChunkPos genChunk = new ChunkPos(cornerPos); // Current generating corner and respective chunk
        ChunkPos qChunk = new ChunkPos(qPos); // Enqueued block and chunk positions
        IDeposit qDeposit = DepositRegistrar.getEnqOreDeposits()
                .getOrDefault(qName, DepositRegistrar.getUtilDeposits().get(qName)); // Get the enqueued deposit

        if (qDeposit == null) {
            // Debug
            if (JemsGeoConfig.SERVER.debug_block_enqueuer.get()){
                JemsGeology.getInstance().LOGGER.warn(("Block enqueuer unable to load deposit with name: {}"), qName);
            }
            return false;
        }

        // If the enqueued chunk is the current generating chunk, attempt to place into the statemap
        if (qChunk.equals(genChunk)) {
            // Check if the block is placing in a valid deposit biome
            if (!qDeposit.getBiomes().contains(reader.getBiome(qPos).getCategory())) {
                return false;
            }

            int xIndex = Math.abs(qPos.getX() - cornerPos.getX());
            int zIndex = Math.abs(qPos.getZ() - cornerPos.getZ());
            try {
                GeologyType geologyType = geoWrapperMap[xIndex][qPos.getY()][zIndex].getGeologyType();
                if (isValidStone(geologyType, qDeposit.getValid())) {
                    geoWrapperMap[xIndex][qPos.getY()][zIndex] = new GeoWrapper(geologyType, qType, qGrade);
                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Debug
                if (JemsGeoConfig.SERVER.debug_block_enqueuer.get()){
                    JemsGeology.getInstance().LOGGER.warn(
                            "Enq block at {} was out of bounds with values {} {} {}",
                            qPos, xIndex, qPos.getY(), zIndex);
                }
                return false;
            }
            // If this statement reaches here, the block was not a valid placement stone or something has gone very wrong...
            return false;
        }

        // Otherwise, process already or not yet generated chunks
        // Note: all ores that use this method are inherently not placed delayed
        return processOreEnqueue(reader, qPos, qType, qGrade, false, qDeposit);
    }


    // Overload for use in lambda methods that only provide the string deposit name
    public static boolean processOreEnqueue(ISeedReader reader, BlockPos qPos, OreType qType, GradeType qGrade, boolean delayed, String qName) {
        IDeposit qDeposit = DepositRegistrar.getEnqOreDeposits()
                .getOrDefault(qName, DepositRegistrar.getUtilDeposits().get(qName)); // Get the enqueued deposit
        return processOreEnqueue(reader, qPos, qType, qGrade, delayed, qDeposit);
    }


    // Process an enqueued ore block for an already or not yet generated chunk
    public static boolean processOreEnqueue(ISeedReader reader, BlockPos qPos, OreType qType, GradeType qGrade, boolean delayed, IDeposit qDeposit) {

        if (depCap == null) {
            depCap = reader.getWorld().getCapability(DepositCapability.JEMGEO_DEPOSIT_CAPABILITY)
                    .orElseThrow(() -> new RuntimeException("JemsGeo deposit capability is null..."));
        }

        if (cgCap == null) {
            cgCap = reader.getWorld().getCapability(ChunkGennedCapability.JEMGEO_CHUNK_GEN_CAPABILITY)
                    .orElseThrow(() -> new RuntimeException("JemsGeo chunk gen capability is null..."));
        }

        // If not in the currently generating chunk, and the enqueued chunk has generated, try to force placement
        ChunkPos qChunk = new ChunkPos(qPos);
        if (cgCap.hasChunkGenerated(qChunk)) {
            // Check if the block is placing in a valid deposit biome
            if (!qDeposit.getBiomes().contains(reader.getBiome(qPos).getCategory())) {
                return false;
            }

            BlockState state = reader.getBlockState(qPos);
            state = UtilMethods.convertVanillaToDetritus(state); // Convert vanilla detritus to respective OreBlocks for comparison
            Block hostBlock = state.getBlock();

            if (isValidStone(hostBlock, qDeposit.getValid())) {
                GeoRegistry geoRegistry = ModBlocks.GEOBLOCKS.get(((IGeoBlock) hostBlock).getGeologyType());
                Block placeBlock = (UtilMethods.isGeoBlockRegolith(hostBlock)) ?
                        geoRegistry.getRegolithOre(qType, qGrade) : geoRegistry.getStoneOre(qType, qGrade);

                if (!reader.setBlockState(qPos, placeBlock.getDefaultState(), 2 | 16)) {
                    // Debug
                    if (JemsGeoConfig.SERVER.debug_block_enqueuer.get()){
                        JemsGeology.getInstance().LOGGER.warn("Somehow {} could not be placed at {} even though chunk has generated",
                                state.getBlock().getRegistryName(), qPos);
                    }
                    return false;
                }
            }
            return true;
        } else {
            if (delayed) {
                depCap.putDelayedPendingOre(new BlockPos(qPos), qType, qGrade, qDeposit.getName());
            } else {
                depCap.putImmediatePendingOre(new BlockPos(qPos), qType, qGrade, qDeposit.getName());
            }
            return false;
        }
    }


// have generate function determine based on size what function to use (if small, not even MC code is good)
// change from accepting range to accepting average value
// IF USING MINECRAFT METHOD, ALWAYS PASS TO STANDARD UNLESS AVG = 1


    // Gets the placement area for the Minecraft ore cubes
    public static boolean genMinecraftOre(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, IScatterDeposit scatterDeposit) {
        float angle = rand.nextFloat() * (float)Math.PI;
        float radius = (float)scatterDeposit.getSize() / 8.0F; // Determines the longest radius in any direction
        int extension = MathHelper.ceil((radius + 1.0F) / 2.0F); // Determines how much further out to check to ensure all spots evaluated (cubes will overhang from placement area)
        double xMax = (double)pos.getX() + Math.sin(angle) * (double)radius; // V Determines horizontal geometry (narrow or blob)
        double xMin = (double)pos.getX() - Math.sin(angle) * (double)radius; // |
        double zMax = (double)pos.getZ() + Math.cos(angle) * (double)radius; // |
        double zMin = (double)pos.getZ() - Math.cos(angle) * (double)radius; // ^
        double y1 = (pos.getY() + rand.nextInt(3) - 2); // V Determines vertical geometry (thick or thin)
        double y2 = (pos.getY() + rand.nextInt(3) - 2); // ^
        int startX = pos.getX() - MathHelper.ceil(radius) - extension; // Starting X to check (increases from here)
        int startY = pos.getY() - 2 - extension; // Starting Y to check (increases from here)
        int startZ = pos.getZ() - MathHelper.ceil(radius) - extension; // Starting Z to check (increases from here)
        int horizDiameter = 2 * (MathHelper.ceil(radius) + extension); // Horizontal diameter of deposit placement area
        int vertDiameter = 2 * (2 + extension); // Vertical diameter of deposit placement area

        // Iterate over the potential placement area and try to confirm some part of it is underground
        for(int checkX = startX; checkX <= startX + horizDiameter; ++checkX) {
            for(int checkZ = startZ; checkZ <= startZ + horizDiameter; ++checkZ) {
                // If any Y value is underground, then:
                if (startY <= reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, checkX, checkZ)) {
                    // Try to place ore deposit
                    return placeOreCubes(reader, rand, scatterDeposit, xMax, xMin, zMax, zMin, y1, y2, startX, startY, startZ, horizDiameter, vertDiameter);
                }
            }
        }
        return false; // If no blocks placed, then return false
    }

    // Place Minecraft ore cubes over the placement area
    private static boolean placeOreCubes(IWorld reader, Random rand, IScatterDeposit scatterDeposit, double xMax, double xMin, double zMax, double zMin, double y1, double y2, int startX, int startY, int startZ, int horizDiameter, int vertDiameter) {
        // Get the weighted random grade of the specific placement try
        GradeType grade = scatterDeposit.getGrades().nextElt();

        int blocksPlaced = 0; // Counts number of placed blocks
        BitSet bitset = new BitSet(horizDiameter * vertDiameter * horizDiameter); // Make empty bitset with size length*width*height to track all placements
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int size = scatterDeposit.getSize(); // How many cubes of diameter (size/4) aka radius (size/8) are placed
        double[] cube = new double[size * 4]; // Holds coords and random factor for every cube

        for(int k = 0; k < size; ++k) {
            float f = (float)k / (float)size;
            double cubeCenterX = MathHelper.lerp(f, xMax, xMin);
            double cubeCenterY = MathHelper.lerp(f, y1, y2);
            double cubeCenterZ = MathHelper.lerp(f, zMax, zMin);
            double halfRadiusVariation = rand.nextDouble() * (double)size / 16.0D; // random amount by a half-radius
            double d7 = ((double)(MathHelper.sin((float)Math.PI * f) + 1.0F) * halfRadiusVariation + 1.0D) / 2.0D;
            cube[k * 4 + 0] = cubeCenterX;
            cube[k * 4 + 1] = cubeCenterY;
            cube[k * 4 + 2] = cubeCenterZ;
            cube[k * 4 + 3] = d7; //some kind of radius fluxation, based on its later usage
        }

        //check if two cubes overlap?, cancel one of them if so
        for(int Ci = 0; Ci < size - 1; ++Ci) {
            if (!(cube[Ci * 4 + 3] <= 0.0D)) {
                for(int Cj = Ci + 1; Cj < size; ++Cj) {
                    if (!(cube[Cj * 4 + 3] <= 0.0D)) {
                        double d12 = cube[Ci * 4 + 0] - cube[Cj * 4 + 0];
                        double d13 = cube[Ci * 4 + 1] - cube[Cj * 4 + 1];
                        double d14 = cube[Ci * 4 + 2] - cube[Cj * 4 + 2];
                        double d15 = cube[Ci * 4 + 3] - cube[Cj * 4 + 3];
                        if (d15 * d15 > d12 * d12 + d13 * d13 + d14 * d14) {
                            if (d15 > 0.0D) {
                                cube[Cj * 4 + 3] = -1.0D;
                            } else {
                                cube[Ci * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        for(int Ci = 0; Ci < size; ++Ci) {
            double d11 = cube[Ci * 4 + 3];
            if (!(d11 < 0.0D)) {
                double d1 = cube[Ci * 4 + 0];
                double d3 = cube[Ci * 4 + 1];
                double d5 = cube[Ci * 4 + 2];
                int cubeStartX = Math.max(MathHelper.floor(d1 - d11), startX); // V ensure coord outside max checked region
                int cubeStartY = Math.max(MathHelper.floor(d3 - d11), startY); // |
                int cubeStartZ = Math.max(MathHelper.floor(d5 - d11), startZ); // ^
                int cubeEndX = Math.max(MathHelper.floor(d1 + d11), cubeStartX); // V same for the ending coords
                int cubeEndY = Math.max(MathHelper.floor(d3 + d11), cubeStartY); // |
                int cubeEndZ = Math.max(MathHelper.floor(d5 + d11), cubeStartZ); // ^

                for(int placeX = cubeStartX; placeX <= cubeEndX; ++placeX) {
                    double d8 = ((double)placeX + 0.5D - d1) / d11;
                    if (d8 * d8 < 1.0D) {
                        for(int placeY = cubeStartY; placeY <= cubeEndY; ++placeY) {
                            double d9 = ((double)placeY + 0.5D - d3) / d11;
                            if (d8 * d8 + d9 * d9 < 1.0D) {
                                for(int placeZ = cubeStartZ; placeZ <= cubeEndZ; ++placeZ) {
                                    double d10 = ((double)placeZ + 0.5D - d5) / d11;
                                    if (d8 * d8 + d9 * d9 + d10 * d10 < 1.0D) {
                                        // Convert placePos to its position in the bitset
                                        int bitPos = placeX - startX + (placeY - startY) * horizDiameter + (placeZ - startZ) * horizDiameter * vertDiameter;
                                        // If that position in the bitset is unfilled:
                                        if (!bitset.get(bitPos)) {
                                            bitset.set(bitPos); // Fill it so it is not placed over twice
                                            blockpos$mutable.setPos(placeX, placeY, placeZ); // Position to try to place at
                                            // If the position passes the configured placement requirement:
                                            if (placeDepositOre(reader, scatterDeposit, grade, blockpos$mutable)) {
                                                ++blocksPlaced; // If place successful, increment
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return blocksPlaced > 0; // Return true if any number of blocks were placed
    }


    // Place an ore block from a deposit
    public static boolean placeDepositOre(IWorld reader, IDeposit deposit, GradeType grade, BlockPos placePos) {
        Block hostBlock = UtilMethods.convertVanillaToDetritus(reader.getBlockState(placePos)).getBlock();
        if (!DepositUtil.isValidStone(hostBlock, deposit.getValid())) {
            return false;
        }

        GeoRegistry geoRegistry = ModBlocks.GEOBLOCKS.get(((IGeoBlock) hostBlock).getGeologyType());
        Block placeBlock = (UtilMethods.isGeoBlockRegolith(hostBlock)) ?
                geoRegistry.getRegolithOre(deposit.getOres().nextElt(), grade) :
                geoRegistry.getStoneOre(deposit.getOres().nextElt(), grade);

        return reader.setBlockState(placePos, placeBlock.getDefaultState(), 2);
    }
}