package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.geology.deposits.DepositUtil;
import com.jemmerl.jemsgeology.geology.deposits.instances.DiatremeMaarUtilDeposit;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.Pair;
import com.jemmerl.jemsgeology.util.ReplaceableStatus;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.util.WeightedProbMap;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobWarpNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MaarDiatremeFeature extends Feature<NoFeatureConfig> {

    public MaarDiatremeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private static final int MAX_TOP_RADIUS = 20;
    private static final int MIN_TOP_RADIUS = 12;
    private static final int MIN_BASE_RADIUS = 4;
    private static final int MAX_BASE_DECREMENT = 10;

    private static final int MIN_DIATREME_HEIGHT = 40; // Minimum height of the top of the diatreme
    private static final int MAX_DIATREME_HEIGHT = 65; // Maximum height of the top of the diatreme
    private static final int MAAR_THICKNESS = 20;


    private static final int MAX_RADIUS = 18; // Max radius of upper diatreme
    private static final int RADIUS_VARIATION = 7; // How much the diatreme radius can vary

    private static final int MAX_MAAR_RADIUS = 22; // Max radius of upper diatreme

    private static final int MAAR_HEIGHT = 10; // Height of the maar section
    private static final int MAAR_EJECTA_HEIGHT = 30; // Height of the maar and scattered debris above the maar

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        // Config disable option
        if (!JemsGeoConfig.SERVER.gen_maar_diatremes.get()) {
            return false;
        }

        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(reader.getSeed());
        }

        // Remove oceanic mantle pipes. Mantle pipes are a continental feature
        if (reader.getBiome(pos).getCategory() == Biome.Category.OCEAN) {
            return false;
        }

        // TODO
        // Add tuff and peridotite as random sprinkles
        // occasional partial beds of tuff


        //////////////////////////////////////
        /// COMPOSITION PROPERTY SELECTION ///
        //////////////////////////////////////

        DiatremeMaarUtilDeposit diaMaarDep = DiatremeMaarUtilDeposit.getDepositInstance();

        GeologyType mainIgnType = getMainIgnType(rand);
        BlockState mainIgnState = ModBlocks.GEOBLOCKS.get(mainIgnType).getBaseState();
        BlockState tuffState = ModBlocks.GEOBLOCKS.get(GeologyType.getTuff(mainIgnType)).getBaseState();

//        boolean diamondBearing = isDiamondBearing(rand, mainIgnType);
//        float diamondPercent = diamondBearing ? ((0.10f * rand.nextFloat()) + 0.05f) : 0f; // Richness from 5% to 15%

        boolean diamondBearing = true;
        float diamondPercent = 0.3f;

        // Debug
        if (diamondBearing && JemsGeoConfig.SERVER.debug_diatreme_maar.get()) {
            JemsGeology.getInstance().LOGGER.info(("Diamondiferous diatreme generating at ({} ~ {}) with {} richness"),
                    pos.getX(), pos.getZ(), diamondPercent);
        }


        ///////////////////////////////
        /// SIZE PROPERTY SELECTION ///
        ///////////////////////////////

        // Note: feature is placed in the center of the chunk
        int topRadius = rand.nextInt(MAX_TOP_RADIUS - MIN_TOP_RADIUS) + MIN_TOP_RADIUS;
        int baseRadius = rand.nextInt(topRadius - MIN_BASE_RADIUS) + MIN_BASE_RADIUS;
        int baseDifference = topRadius - baseRadius;

        int diatremeHeight = rand.nextInt(MAX_DIATREME_HEIGHT - MIN_DIATREME_HEIGHT) + MIN_DIATREME_HEIGHT;
        if (reader.getBiome(pos).getCategory() == Biome.Category.EXTREME_HILLS) {
            diatremeHeight += 20; // Add height depending on certain biomes, due to the overall added terrain height
        }
        int maarHeight = diatremeHeight + MAAR_THICKNESS; // Main maar: 10 blocks, scattered debris: 20 blocks


        ////////////////
        // GENERATION //
        ////////////////

        ChunkPos genChunk = new ChunkPos(pos);

        HashMap<BlockState, Integer> brecciaMap = new HashMap<>(); // Count the relative amounts of replaced stones for breccia ratios
        int brecciaCount = 0; // Count the overall number of recorded replaced stones
        ArrayList<BlockPos> brecciaPosList = new ArrayList<>(); // Store locations to place breccia
        ArrayList<BlockPos> tuffPosList = new ArrayList<>(); // Store locations to place tuff

        // Pre-calculate the pure radius, which is the same for every location. Dither and warp added later
        float[] radius = new float[(maarHeight + 1)];
        for (int y = 0; y <= maarHeight; y++) {
            radius[y] = topRadius - (baseDifference * (1 - ((float) y / maarHeight)));
        }

        for (BlockPos areaPos : BlockPos.getAllInBoxMutable(pos.add(-MAX_TOP_RADIUS, 0, -MAX_TOP_RADIUS), pos.add(MAX_TOP_RADIUS, 0, MAX_TOP_RADIUS))) {

            int surfaceHeight = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, areaPos).getY() - 1;
            ISurfaceBuilderConfig surfaceConfig = reader.getBiome(areaPos).getGenerationSettings().getSurfaceBuilderConfig();
            float distance = (float) UtilMethods.getDistance2D(areaPos.getX(), areaPos.getZ(), pos.getX(), pos.getZ());

            for (int y = 0; y <= surfaceHeight; y++) {
                BlockPos placePos = areaPos.up(y);
                BlockState replaced = reader.getBlockState(placePos);

                ReplaceableStatus replaceStatus = UtilMethods.replaceableStatus(replaced);
                if (replaceStatus.equals(ReplaceableStatus.FAILED)) {
                    continue;
                }

                boolean isPlaced = false;
                boolean isRegolith = false;
                if (y > (surfaceHeight - 1 - rand.nextInt(4))) { // Surface weathering
                    if (distance > topRadius) continue;
                    isPlaced = true;
                } else if (y > maarHeight) { // Upper ejecta
                    if (distance > topRadius) continue;
                    isPlaced = true;
                } else if (y > diatremeHeight) { // Maar
                    if (distance > radius[y]) continue;
                    if (rand.nextFloat() < (-0.1f + (0.90f * (distance / radius[y])))) {
                        brecciaPosList.add(placePos); // Place a breccia block (later) here
                    } else if (rand.nextFloat() < (0.15f + (0.65f * (((float) y - diatremeHeight) / MAAR_THICKNESS)))) {
                        tuffPosList.add(placePos); // Place a tuff block (later) here
                    } else {
                        reader.setBlockState(placePos, mainIgnState, 2); // Place the primary stone here
                    }
                    isPlaced = true;
                } else { // Diatreme
                    if (distance > radius[y]) continue;
                    if (rand.nextFloat() < (-0.15f + (0.90f * (distance / radius[y])))) {
                        brecciaPosList.add(placePos); // Place a breccia block (later) here
                    } else if (rand.nextFloat() < 0.1f) {
                        tuffPosList.add(placePos); // Place a tuff block (later) here
                    } else {
                        reader.setBlockState(placePos, mainIgnState, 2); // Place the primary stone here
                    }
                    isPlaced = true;
                }

                if (isPlaced) {
                    // Record what block was replaced for later brecciation
                    brecciaCount += updateBrecciaMap(brecciaMap, replaced, replaceStatus);

                    reader.setBlockState(placePos, mainIgnState, 2); // this will not stay here, handle placement in IFs

                    if (diamondBearing && (rand.nextFloat() < diamondPercent)) {
                        System.out.println(placePos);
                        diaMaarDep.enqDiamondOre(reader, placePos);
                    }


                    // ORE HANDLE
                    // check if diamond?
                    // check if olivine?
                }

//                BlockState placed = mainIgnState;
//                if (isRegolith) {
//                    placed = ModBlocks.GEOBLOCKS.get(mainIgnType).getRegolith().getDefaultState();
//                }
            }
        }

        ///////////////////////
        // BUILD BRECCIA MAP //
        ///////////////////////

        ArrayList<Pair<Integer, BlockState>> elts = new ArrayList<>();
        if (brecciaCount > 0) {
            // Sort breccias into weighted map entries
            int weightSum = 0; // Used to catch (most likely impossible) 100-percent overflows
            for (BlockState key : brecciaMap.keySet()) {
                int weight = (brecciaMap.get(key) * 100) / brecciaCount;

                weightSum += weight;
                if (weightSum > 100) {
                    weight -= (weightSum - 100);
                }

                if (weight > 0) {
                    elts.add(new Pair<>(weight, key));
                }
            }
        }
        WeightedProbMap<BlockState> brecciaProbMap = new WeightedProbMap<>(elts);


        ////////////////////////
        /// BRECCIA HANDLING ///
        ////////////////////////

        if (!brecciaProbMap.isEmpty()) {
            for (BlockPos brecciaPos : brecciaPosList) {
                // 25% chance of tuff
                reader.setBlockState(brecciaPos, ((rand.nextFloat() < 0.75f) ? brecciaProbMap.nextElt() : tuffState), 2);
            }
        } else {
            // If no breccias were recorded, replace breccia positions with the main igneous rock (very unlikely)
            for (BlockPos brecciaPos : brecciaPosList) {
                // 50% chance of tuff
                reader.setBlockState(brecciaPos, ((rand.nextBoolean()) ? mainIgnState : tuffState), 2);
            }
        }


        /////////////////////
        /// TUFF HANDLING ///
        /////////////////////

        for (BlockPos tuffPos : tuffPosList) {
            reader.setBlockState(tuffPos, tuffState, 2);
        }









        return true;
    }

    //                // SURFACE WEATHERING GENERATION
    //                if (y > (surfaceHeight - 1 - rand.nextInt(4))) {
    //
    //                    // Get appropriate infill
    //                    BlockState fillState;
    //                    if (y == surfaceHeight) {
    //                        if (rand.nextFloat() > 0.10f) continue;
    //                        // TODO SET REGOLITH POS
    //                    } else {
    //                        if (rand.nextFloat() > 0.75f) continue;
    //                        if (rand.nextFloat() > 0.20f); // TODO SET REGOLITH POS
    //                        fillState = ModBlocks.GEOBLOCKS.get(GeologyType.ULTRAMAFIC_TUFF).getBaseState();
    //                    }
    //
    ////                    // Get appropriate infill
    ////                    if (y == surfaceHeight) {
    ////                        fillState = surfaceConfig.getTop();
    ////                    } else {
    ////                        fillState = (rand.nextFloat() > 0.20f) ? surfaceConfig.getUnder() : ModBlocks.GEOBLOCKS.get(GeologyType.ULTRAMAFIC_TUFF).getBaseState(); //TODO TEMP
    ////                    }
    //
    //
    //                    if (UtilMethods.convertVanillaToDetritus(fillState) instanceof IGeoBlock) {
    //                        // Places fill block with a fourth of the chance as the normal diatreme to have diamonds (if diamondiferous)
    //                        placeDiamondiferousBlock(rand, reader, currPos, genChunk, fillState, diamondiferous, (diamondPercent / 4f));
    //                    } else {
    //                        reader.setBlockState(currPos, fillState, 2);
    //                    }
    //
    //                    continue;
    //                }
    //
    //
    //
    //
    //
    //                //  GENERATION
    //                float radius = rand.nextInt(3);
    //                if (y > diatremeHeight) {
    //                    // Maximum possible radius for the top of maar is 22, which is 44/48 available blocks (3 chunks allocated to features)
    //                    radius += (MAX_MAAR_RADIUS - 7) - (baseDecrement * (1 - (y / (float) (diatremeHeight + MAAR_HEIGHT)))) +
    //                            (BlobWarpNoise.blobWarpRadiusNoise(areaPos.getX(), y, areaPos.getZ()) * 7);
    //                } else {
    //                    radius += (MAX_RADIUS - RADIUS_VARIATION)
    //                            - (baseDecrement * (1 - (y / (float) diatremeHeight)))
    //                            + (BlobWarpNoise.blobWarpRadiusNoise(areaPos.getX(), y, areaPos.getZ()) * RADIUS_VARIATION)
    //                }
    //
    //
    //                // DIATREME GENERATION
    //                if (y < diatremeHeight) {
    //
    //                    if (distance <= radius) {
    //                        BlockPos currPos = new BlockPos(areaPos.getX(), y, areaPos.getZ());
    //                        BlockState replacedBlock = reader.getBlockState(currPos);
    //
    //                        // Check if valid placement
    //                        if (UtilMethods.replaceableStatus(replacedBlock).equals(ReplaceableStatus.FAILED)) {
    //                            continue;
    //                        }
    //
    //                        // Record what block was replaced for later brecciation
    //                        brecciaCount += updateBrecciaMap(brecciaMap, replacedBlock);
    //
    //                        // A higher adjustment value means less breccia overall
    //                        if ((rand.nextFloat() + 0.18f) < (distance / radius)) {
    //                            brecciaPosList.add(currPos);
    //                        } else {
    //                            BlockState blockState;
    //                            if (diamondBearing && (rand.nextFloat() < diamondPercent)) {
    //                                blockState = ModBlocks.GEOBLOCKS.get(mainIgnType)
    //                                        .getStoneOre(OreType.DIAMOND, diaMaarDep.getGrades().nextElt())
    //                                        .getDefaultState();
    //                            } else {
    //                                blockState = ModBlocks.GEOBLOCKS.get(mainIgnType).getBaseStone().getDefaultState();
    //                            }
    //                            reader.setBlockState(currPos, blockState, 2);
    //                        }
    //                    }
    //                    continue;
    //                }




    //           float distance = (float) UtilMethods.getDistance2D(areaPos.getX(), areaPos.getZ(), pos.getX(), pos.getZ());
    //                    if (distance <= radius) {
    //                        BlockPos currPos = new BlockPos(areaPos.getX(), y, areaPos.getZ());
    //                        BlockState replacedBlock = reader.getBlockState(currPos);
    //
    //                        // Check if valid placement
    //                        if (UtilMethods.replaceableStatus(replacedBlock).equals(ReplaceableStatus.FAILED)) {
    //                            continue;
    //                        }
    //
    //                        if (distance > inner_radius) {
    //                            // Maar generation
    //                            {
    //                                int randInt = rand.nextInt(101);
    //                                if (randInt > 55) {
    //                                    // 45% Chance of tuff + 10% chance from brecca == 55% chance overall
    //                                    reader.setBlockState(currPos, ModBlocks.GEOBLOCKS.get(GeologyType.ULTRAMAFIC_TUFF).getBaseState(), 2); //TODO TEMP
    //                                } else if (randInt > 15) {
    //                                    // 40% Chance of regolith (which gives 10% chance of tuff itself, giving 30% chance of true regolith)
    //                                    regolithPosList.add(currPos);
    //                                } else {
    //                                    // 15% Chance of main igneous
    //                                    reader.setBlockState(currPos, ModBlocks.GEOBLOCKS.get(mainIgnType).getBaseState(), 2);
    //                                }
    //                            }
    //                        } else {
    //                            // Ejecta generation
    //                            generateEjecta(rand, reader, currPos, genChunk, diamondBearing, diamondPercent, regolithPosList);
    //                        }
    //
    //                        if (diamondBearing && (rand.nextFloat() < diamondPercent)) {
    //                            diaMaarDep.enqDiamondOre(reader, currPos);
    //                        }
    //                    }
    //                    continue;


//        /////////////////////////
//        // DIATREME GENERATION //
//        /////////////////////////
//
//
//
//        for (int y = 1; y < diatremeHeight; y++) {
//            for (BlockPos areaPos : BlockPos.getAllInBoxMutable(pos.add(-MAX_DIATREME_RADIUS, 0, -MAX_DIATREME_RADIUS), pos.add(MAX_DIATREME_RADIUS, 0, MAX_DIATREME_RADIUS))) {
//                // Maximum possible radius for the top of diatreme is 18, which is 36/48 available blocks (3 chunks allocated to features)
//
//                float radius = (MAX_DIATREME_RADIUS - 7) - (baseDecrement * (1 - (y / (float) diatremeHeight))) +
//                        (BlobWarpNoise.blobWarpRadiusNoise(areaPos.getX(), y, areaPos.getZ()) * 7) + rand.nextInt(3);
//
//                float distance = (float) UtilMethods.getDistance2D(areaPos.getX(), areaPos.getZ(), pos.getX(), pos.getZ());
//                if (distance <= radius) {
//                    BlockPos currPos = new BlockPos(areaPos.getX(), y, areaPos.getZ());
//                    BlockState replacedBlock = reader.getBlockState(currPos);
//
//                    // Check if valid placement
//                    if (UtilMethods.replaceableStatus(replacedBlock).equals(ReplaceableStatus.FAILED)) {
//                        continue;
//                    }
//
//                    // Record what block was replaced for later brecciation
//                    brecciaCount += updateBrecciaMap(brecciaMap, replacedBlock);
//
//                    // A higher adjustment value means less breccia overall
//                    if ((rand.nextFloat() + 0.18f) < (distance / radius)) {
//                        brecciaPosList.add(currPos);
//                    } else {
//                        placeDiamondiferousBlock(rand, reader, currPos, genChunk,
//                                ModBlocks.GEOBLOCKS.get(mainIgnType).getBaseState(), diamondBearing, diamondPercent);
//                    }
//
//                    if (diamondBearing && (rand.nextFloat() < diamondPercent)) {
//                        diaMaarDep.enqDiamondOre(reader, currPos);
//                    }
//                }
//            }
//        }
//
//
//        /////////////////
//        // BRECCIA MAP //
//        /////////////////
//
//        ArrayList<Pair<Integer, BlockState>> elts = new ArrayList<>();
//        if (brecciaCount > 0) {
//            // Sort breccias into weighted map entries
//            int weightSum = 0; // Used to catch (most likely impossible) 100-percent overflows
//            for (BlockState key : brecciaMap.keySet()) {
//                int weight = (brecciaMap.get(key) * 100) / brecciaCount;
//
//                weightSum += weight;
//                if (weightSum > 100) {
//                    weight -= (weightSum - 100);
//                }
//
//                if (weight > 0) {
//                    elts.add(new Pair<>(weight, key));
//                }
//            }
//        }
//        WeightedProbMap<BlockState> brecciaProbMap = new WeightedProbMap<>(elts);
//
//
//        /////////////////////
//        // MAAR GENERATION //
//        /////////////////////
//
//        for (int y = diatremeHeight; y < (diatremeHeight + MAAR_HEIGHT); y++) {
//            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-MAX_DIATREME_RADIUS, 0, -MAX_DIATREME_RADIUS), pos.add(MAX_DIATREME_RADIUS, 0, MAX_DIATREME_RADIUS))) {
//                // Maximum possible radius for the top of maar is 22, which is 44/48 available blocks (3 chunks allocated to features)
//                float outer_radius = (MAX_MAAR_RADIUS - 7) - (baseDecrement * (1 - (y / (float) (diatremeHeight + MAAR_HEIGHT)))) +
//                        (BlobWarpNoise.blobWarpRadiusNoise(blockPos.getX(), y, blockPos.getZ()) * 7);
//                float inner_radius = MAX_MAAR_RADIUS - (MAX_MAAR_RADIUS * (1 - ((y - diatremeHeight) / (float) MAAR_HEIGHT)));
//
//                float distance = (float) UtilMethods.getDistance2D(blockPos.getX(), blockPos.getZ(), pos.getX(), pos.getZ());
//                if (distance <= outer_radius) {
//                    BlockPos currPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
//                    BlockState replacedBlock = reader.getBlockState(currPos);
//
//                    // Check if valid placement
//                    if (UtilMethods.replaceableStatus(replacedBlock).equals(ReplaceableStatus.FAILED)) {
//                        continue;
//                    }
//
//                    if (distance > inner_radius) {
//                        // Maar generation
//                        {
//                            int randInt = rand.nextInt(101);
//                            if (randInt > 55) {
//                                // 45% Chance of tuff + 10% chance from brecca == 55% chance overall
//                                reader.setBlockState(currPos, ModBlocks.GEOBLOCKS.get(GeologyType.ULTRAMAFIC_TUFF).getBaseState(), 2); //TODO TEMP
//                            } else if (randInt > 15) {
//                                // 40% Chance of regolith (which gives 10% chance of tuff itself, giving 30% chance of true regolith)
//                                regolithPosList.add(currPos);
//                            } else {
//                                // 15% Chance of main igneous
//                                reader.setBlockState(currPos, ModBlocks.GEOBLOCKS.get(mainIgnType).getBaseState(), 2);
//                            }
//                        }
//                    } else {
//                        // Ejecta generation
//                        generateEjecta(rand, reader, currPos, genChunk, diamondBearing, diamondPercent, regolithPosList);
//                    }
//
//                    if (diamondBearing && (rand.nextFloat() < diamondPercent)) {
//                        diaMaarDep.enqDiamondOre(reader, currPos);
//                    }
//                }
//            }
//
//
//            // is surface or 3-5 below then regolith
//
//        }
//
//
//        ///////////////////////
//        // EJECTA GENERATION //
//        ///////////////////////
//
//        for (int y = (diatremeHeight + MAAR_HEIGHT); y < (diatremeHeight + MAAR_EJECTA_HEIGHT); y++) {
//            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-MAX_DIATREME_RADIUS, 0, -MAX_DIATREME_RADIUS), pos.add(MAX_DIATREME_RADIUS, 0, MAX_DIATREME_RADIUS))) {
//                float radius = (MAX_MAAR_RADIUS - 7) + (BlobWarpNoise.blobWarpRadiusNoise(blockPos.getX(), y, blockPos.getZ()) * 7);
//
//                float distance = (float) UtilMethods.getDistance2D(blockPos.getX(), blockPos.getZ(), pos.getX(), pos.getZ());
//                if (distance <= radius) {
//                    BlockPos currPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
//                    BlockState replacedBlock = reader.getBlockState(currPos);
//
//                    // Check if valid placement
//                    if (UtilMethods.replaceableStatus(replacedBlock).equals(ReplaceableStatus.FAILED)) {
//                        continue;
//                    }
//
//                    // Ejecta generation
//                    generateEjecta(rand, reader, currPos, genChunk, diamondBearing, diamondPercent, regolithPosList);
//
//                    if (diamondBearing && (rand.nextFloat() < diamondPercent)) {
//                        diaMaarDep.enqDiamondOre(reader, currPos);
//                    }
//                }
//            }
//        }
//
//        ////////////////////////
//        /// BRECCIA HANDLING ///
//        ////////////////////////
//
//        if (!brecciaPosList.isEmpty()) {
//            if (!brecciaProbMap.isEmpty()) {
//                for (BlockPos brecciaPos : brecciaPosList) {
//                    // 25% chance of tuff
//                    if (rand.nextFloat() < 0.75f) {
//                        reader.setBlockState(brecciaPos, brecciaProbMap.nextElt(), 2);
//                    } else {
//                        reader.setBlockState(brecciaPos, ModBlocks.GEOBLOCKS.get(GeologyType.ULTRAMAFIC_TUFF).getBaseState(), 2); //TODO TEMP
//                    }
//                }
//
//            } else {
//                // If no breccias were recorded, replace breccia positions with the main igneous rock (very unlikely)
//                for (BlockPos brecciaPos : brecciaPosList) {
//                    // 50% chance of tuff
//                    if (rand.nextBoolean()) {
//                        reader.setBlockState(brecciaPos, ModBlocks.GEOBLOCKS.get(mainIgnType).getBaseState(), 2);
//                    } else {
//                        reader.setBlockState(brecciaPos, ModBlocks.GEOBLOCKS.get(GeologyType.ULTRAMAFIC_TUFF).getBaseState(), 2); //TODO TEMP
//                    }
//                }
//            }
//        }
//
//
//        /////////////////////////
//        /// REGOLITH HANDLING ///
//        /////////////////////////
//
//        if (!regolithPosList.isEmpty()) {
//            boolean emptyProbMap = brecciaProbMap.isEmpty();
//            for (BlockPos regolithPos : regolithPosList) {
//                GeologyType regolithType = (!emptyProbMap && (rand.nextFloat() > 0.35f)) ?
//                        ((IGeoBlock) brecciaProbMap.nextElt().getBlock()).getGeologyType() : mainIgnType;
//                BlockState regolithState = ModBlocks.GEOBLOCKS.get(regolithType).getRegolith().getDefaultState();
//                placeDiamondiferousBlock(rand, reader, regolithPos, genChunk, regolithState, diamondBearing, diamondPercent);
//                if (diamondBearing && (rand.nextFloat() < diamondPercent)) {
//                    diaMaarDep.enqDiamondOre(reader, regolithPos);
//                }
//            }
//        }
//
//
//        // Debug
//        if (JemsGeoConfig.SERVER.debug_diatreme_maar.get()) {
//            for (int i = 0; i < 10; i++) {
//                reader.setBlockState(new BlockPos(pos.getX(), (maarHeight + 10 + i), pos.getZ()), Blocks.GOLD_BLOCK.getDefaultState(), 2);
//            }
//        }
//
//        // Finish maar-diatreme generation
//        return true;
//    }
//
//
//    /////////////////////
//    // UTILITY METHODS //
//    /////////////////////
//
//    // Place an ejecta block
//    private static void generateEjecta(Random rand, ISeedReader reader, BlockPos currPos, ChunkPos genChunk,
//                                       boolean diamondiferous, float diamondPercent, ArrayList<BlockPos> regolithPosList) {
//        if (rand.nextFloat() > 0.65f) {
//            regolithPosList.add(currPos);
//        } else {
//            setFillBlock(rand, reader, currPos, genChunk, diamondiferous, diamondPercent);
//        }
//    }
//
//
//    // Place a filler detritus for the upper maar
//    private static void setFillBlock(Random rand, ISeedReader reader, BlockPos currPos, ChunkPos genChunk, boolean diamondiferous, float diamondPercent) {
//        BlockState fillState;
//        ISurfaceBuilderConfig surfaceConfig = reader.getBiome(currPos).getGenerationSettings().getSurfaceBuilderConfig();
//        int height = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, currPos).getY() - 1;
//
//        // Get appropriate infill
//        if (currPos.getY() == height) {
//            fillState = surfaceConfig.getTop();
//        } else {
//            fillState = (rand.nextFloat() > 0.20f) ? surfaceConfig.getUnder() : ModBlocks.GEOBLOCKS.get(GeologyType.ULTRAMAFIC_TUFF).getBaseState(); //TODO TEMP
//        }
//        //fillState = surfaceConfig.getUnderWaterMaterial(); need to cast to (SurfaceBuilderConfig)
//        //currPos.getY() > (height - rand.nextInt(4) - 2)
//
//        if (UtilMethods.convertVanillaToDetritus(fillState) instanceof IGeoBlock) {
//            // Places fill block with a fourth of the chance as the normal diatreme to have diamonds (if diamondiferous)
//            placeDiamondiferousBlock(rand, reader, currPos, genChunk, fillState, diamondiferous, (diamondPercent / 4f));
//        } else {
//            reader.setBlockState(currPos, fillState, 2);
//        }
//    }
//
//
//    // Place potentially diamondiferous block
//    private static void placeDiamondiferousBlock(Random rand, ISeedReader reader, BlockPos currPos, ChunkPos genChunk,
//                                                 BlockState blockState, boolean diamondiferous, float diamondPercent) {
//
//        // Enqueue block placement if not in current chunk -- even side chunks may not be fully generated.
//        ChunkPos currChunk = new ChunkPos(currPos);
//        if (currChunk.equals(genChunk)) {
//            Block block = UtilMethods.convertVanillaToDetritus(blockState).getBlock();
//            if (block instanceof IGeoBlock) {
//                IGeoBlock geoBlock = (IGeoBlock) block;
//                // Select grade of individual ore and generate the block with diamond ore
//                if (UtilMethods.isRegolith(geoBlock)) {
//                    block = ModBlocks.GEOBLOCKS.get(geoBlock.getGeologyType()).getRegolith();
//                } else {
//                    block = ModBlocks.GEOBLOCKS.get(geoBlock.getGeologyType()).getBaseStone();
//                }
//
//                reader.setBlockState(currPos, block.getDefaultState(), 2);
//                return;
//            }
//        }
//        reader.setBlockState(currPos, blockState, 2);
//    }


    //////////////////////////////////////////////////////////////////////////////////////

    /////////////////////
    // UTILITY METHODS //
    /////////////////////

    // Determine the primary igneous matrix rock of the pipe
    private static GeologyType getMainIgnType (Random rand){
        int randInt = rand.nextInt(101);
        if (randInt > 65) {
            return GeologyType.KIMBERLITE; // (35% Chance)
        } else if (randInt > 60) {
            return GeologyType.LAMPROITE; // (5% Chance)
        } else if (randInt > 35) {
            return GeologyType.BASALT; // (25% Chance)
        } else if (randInt > 30) {
            return GeologyType.DACITE; // (5% Chance)
        } else if (randInt > 20) {
            return GeologyType.ANDESITE; // (10% Chance)
        } else {
            return GeologyType.RHYOLITE; // (20% Chance)
        }
    }

    // Only Kimberlite and Lamproite pipes can contain diamonds
    private static boolean isDiamondBearing (Random rand, GeologyType mainIgnType){
        switch (mainIgnType) {
            case KIMBERLITE:
                return (rand.nextFloat() < 0.25f); // 25% chance if Kimberlite
            case LAMPROITE:
                return (rand.nextFloat() < 0.10f); // 10% chance if Lamproite
            default:
                return false;
        }
    }

    // Add a replaced block to the breccia counter map
    private static int updateBrecciaMap (HashMap<BlockState, Integer> brecciaMap, BlockState replaced, ReplaceableStatus replaceStatus){
        if (replaceStatus == ReplaceableStatus.GEOBLOCK_STONE) {
            // If map entry exists, add to it. Else, start a new entry
            if (brecciaMap.containsKey(replaced)) {
                int prevVal = brecciaMap.get(replaced);
                brecciaMap.replace(replaced, prevVal + 1);
            } else {
                brecciaMap.put(replaced, 1);
            }
            return 1;
        }
        return 0;
    }
}