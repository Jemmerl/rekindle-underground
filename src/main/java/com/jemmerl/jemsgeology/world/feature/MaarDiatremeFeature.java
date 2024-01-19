package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.geology.deposits.instances.DiatremeDiaUtilDeposit;
import com.jemmerl.jemsgeology.geology.deposits.instances.DiatremeOliveUtilDeposit;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.Pair;
import com.jemmerl.jemsgeology.util.ReplaceableStatus;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.util.WeightedProbMap;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.RegionNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
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

    private static final int MAX_TOP_RADIUS = 20; // (actual is one less)
    private static final int MIN_TOP_RADIUS = 12;
    private static final int MIN_BASE_RADIUS = 4;

    private static final int MIN_DIATREME_HEIGHT = 40; // Minimum height of the top of the diatreme
    private static final int MAX_DIATREME_HEIGHT = 65; // Maximum height of the top of the diatreme (actual is one less)
    private static final int MAAR_THICKNESS = 20;

    private static final int RADIUS_VARIATION = 7; // How much the diatreme radius can vary
    
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


        ////////////////////////////////////
        // COMPOSITION PROPERTY SELECTION //
        ////////////////////////////////////

        DiatremeDiaUtilDeposit diaMaarDep = DiatremeDiaUtilDeposit.getDepositInstance();
        DiatremeOliveUtilDeposit oliveMaarDep = DiatremeOliveUtilDeposit.getDepositInstance();

        final GeologyType mainIgnType = getMainIgnType(rand);
        final BlockState mainIgnState = ModBlocks.GEOBLOCKS.get(mainIgnType).getBaseState();
        final BlockState mainRegState = ModBlocks.GEOBLOCKS.get(mainIgnType).getRegolith().getDefaultState();
        final BlockState tuffState = ModBlocks.GEOBLOCKS.get(GeologyType.getTuff(mainIgnType)).getBaseState();

        // temp dev
        final BlockState testState2 = ModBlocks.GEOBLOCKS.get(GeologyType.SANDSTONE).getBaseState();

        final boolean diamondBearing = isDiamondBearing(pos, mainIgnType);
        final float diamondPercent = diamondBearing ? ((0.10f * rand.nextFloat()) + 0.05f) : 0f; // Richness from 5% to 15%
        final float olivinePercent = isOlivineBearing(mainIgnType) ? ((0.25f * rand.nextFloat()) + 0.15f) : 0f; // Richness from 5% to 15%
        final float regTopPercent = rand.nextFloat() * 0.20f + 0.05f; // Surface building: percent regolith (for variability)

//        boolean diamondBearing = true;
//        float diamondPercent = 0.10f;

        // Debug
        if (diamondBearing && JemsGeoConfig.SERVER.debug_diatreme_maar.get()) {
            JemsGeology.getInstance().LOGGER.info(("Diamondiferous diatreme generating at ({} ~ {}) with {} richness"),
                    pos.getX(), pos.getZ(), diamondPercent);
        }


        /////////////////////////////
        // SIZE PROPERTY SELECTION //
        /////////////////////////////

        // Note: diatreme feature is placed in the center of the chunk

        // Diamondiferous diatremes are on average, bigger than non-diamond bearing pipes due to the higher
        // volume of erupted magma provided by the longer eruptions diamonds need to reach the surface from deep down.
        // The +1 to max ensures that with conversion to integer, the maximum value is possible.
        final int topRadius = (diamondBearing ? triDist((MIN_TOP_RADIUS+4), (MAX_TOP_RADIUS+1), (MAX_TOP_RADIUS+1), rand)
                : triDist(MIN_TOP_RADIUS, (MAX_TOP_RADIUS+1), MIN_TOP_RADIUS, rand));

        final int baseRadius = rand.nextInt(topRadius - MIN_BASE_RADIUS) + MIN_BASE_RADIUS;
        final int baseDifference = topRadius - baseRadius;

        int diatremeHeight = rand.nextInt(MAX_DIATREME_HEIGHT + 1 - MIN_DIATREME_HEIGHT) + MIN_DIATREME_HEIGHT;
        if (reader.getBiome(pos).getCategory() == Biome.Category.EXTREME_HILLS) {
            diatremeHeight += 20; // Add height depending on certain biomes, due to the overall added terrain height
        }
        final int maarHeight = diatremeHeight + MAAR_THICKNESS; // Main maar: 10 blocks, scattered debris: 20 blocks

        //System.out.println("Dia Height: " + diatremeHeight + ", Maar Height: " + maarHeight + ", " + reader.getChunk(pos).getPos());


        ////////////////
        // GENERATION //
        ////////////////

        HashMap<BlockState, Integer> brecciaMap = new HashMap<>(); // Count the relative amounts of replaced stones for breccia ratios
        int brecciaCount = 0; // Count the overall number of recorded replaced stones
        HashMap<BlockPos, Boolean> brecciaPosMap = new HashMap<>(); // Store breccia locations and if regolith
        ArrayList<BlockPos> diaPosList = new ArrayList<>(); // Store diamond ore locations
        ArrayList<BlockPos> olivePosList = new ArrayList<>(); // Store olvine ore locations

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

                // TODO config toggle to remove diamonds from the surface (to make players need to check for them?)
                boolean brecciaSpot = false;
                if (y == surfaceHeight) { // Surface weathering
                    if (distance > topRadius) continue;

                    if (rand.nextFloat() < regTopPercent) {
                        if (rand.nextFloat() < 0.33f) {
                            brecciaPosMap.put(placePos, true); // Place a breccia block (later) here
                            brecciaSpot = true;
                        } else {
                            reader.setBlockState(placePos, mainRegState, 2);
                        }
                    } else {
                        reader.setBlockState(placePos, surfaceConfig.getTop(), 2);
                    }


                } else if (y > maarHeight) { // Upper ejecta
                    if (distance > topRadius) continue;
                    reader.setBlockState(placePos, testState2, 2); // this will not stay here, handle placement in IFs


                } else if (y > diatremeHeight) { // Maar
                    if (distance > radius[y]) continue;

                    // Weathering if near-surface
                    boolean regoWeather = (y > (surfaceHeight - 2 - rand.nextInt(4)));

                    if (rand.nextFloat() < (-0.10f + (0.90f * (distance / radius[y])))) {
                        brecciaPosMap.put(placePos, true); // Place a breccia block (later) here
                        brecciaSpot = true;
                    } else if (rand.nextFloat() < (0.15f + (0.65f * (((float) y - diatremeHeight) / MAAR_THICKNESS)))) {
                        reader.setBlockState(placePos, tuffState, 2); // Place a tuff block here
                    } else {
                        reader.setBlockState(placePos,
                                (regoWeather ? mainRegState : mainIgnState), 2); // Place the primary stone here
                    }


                } else { // Diatreme
                    if (distance > radius[y]) continue;

                    // Weathering if near-surface
                    boolean regoWeather = (y > (surfaceHeight - 2 - rand.nextInt(4)));

                    if (rand.nextFloat() < (-0.15f + (0.90f * (distance / radius[y])))) {
                        brecciaPosMap.put(placePos, regoWeather); // Place a breccia block (later) here
                        brecciaSpot = true;
                    } else if (rand.nextFloat() < 0.1f) {
                        reader.setBlockState(placePos, tuffState, 2); // Place a tuff block here
                    } else {
                        reader.setBlockState(placePos,
                                (regoWeather ? mainRegState : mainIgnState), 2); // Place the primary stone here
                    }
                }

                // Record what block was replaced for later brecciation
                brecciaCount += updateBrecciaMap(brecciaMap, replaced, replaceStatus);

                // Mark a location for ore
                if (diamondBearing && (rand.nextFloat() < diamondPercent)) {
                    diaPosList.add(placePos);
                } else if (!brecciaSpot && (rand.nextFloat() < olivinePercent)) {
                    // Can appear in the igneous rock and tuff, not breccia
                    // TODO Note, currently does not consider breccia spots later marked as tuff. May not change.
                    olivePosList.add(placePos);
                }


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
            brecciaPosMap.forEach((bPos, isReg) -> {
                // 20% chance of tuff
                BlockState state = isReg ?
                        UtilMethods.convertRegolith(brecciaProbMap.nextElt(), true) : brecciaProbMap.nextElt();
                reader.setBlockState(bPos, ((rand.nextFloat() < 0.80f) ? state : tuffState), 2);
            });
        } else {
            // If no breccias were recorded, replace breccia positions with the main igneous rock (very unlikely)
            brecciaPosMap.forEach((bPos, isReg) -> {
                // 50% chance of tuff
                BlockState state = isReg ? mainRegState : mainIgnState;
                reader.setBlockState(bPos, ((rand.nextBoolean()) ? state : tuffState), 2);
            });
        }


        //////////////////
        // ORE HANDLING //
        //////////////////

        // Place/enqueue diamond ores
        for (BlockPos placePos : diaPosList) {
            diaMaarDep.enqDiamondOre(reader, placePos);
        }

        // Place/enqueue olivine ores
        for (BlockPos placePos : olivePosList) {
            oliveMaarDep.enqOlivineOre(reader, placePos, diamondBearing);
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
        if (randInt > 60) {
            return GeologyType.KIMBERLITE; // (40% Chance)
        } else if (randInt > 55) {
            return GeologyType.LAMPROITE; // (5% Chance)
        } else if (randInt > 30) {
            return GeologyType.BASALT; // (25% Chance)
        } else if (randInt > 25) {
            return GeologyType.DACITE; // (5% Chance)
        } else if (randInt > 20) {
            return GeologyType.ANDESITE; // (5% Chance)
        } else {
            return GeologyType.RHYOLITE; // (20% Chance)
        }
    }

    // Only Kimberlite and Lamproite pipes can contain diamonds
    // Note: diamond pipes in reality tend to cluster over a "good diamond conditions" area, simulated using regions
    private static boolean isDiamondBearing (BlockPos pos, GeologyType mainIgnType) {
        Random rand = new Random((long) (RegionNoise.volcanicRegionNoise(pos.getX(), pos.getZ(), true) * 100000));

        switch (mainIgnType) {
            case KIMBERLITE:
                return (rand.nextFloat() < 0.35f); // 35% region chance if Kimberlite
            case LAMPROITE:
                return (rand.nextFloat() < 0.15f); // 15% region chance if Lamproite
            default:
                return false;
        }
    }

    // Only the (ultra)mafic lavas contain peridotite (olivine) xenoliths in diatremes. As far as I can tell, anyway.
    private static boolean isOlivineBearing(GeologyType mainIgnType) {
        switch (mainIgnType) {
            case KIMBERLITE:
            case LAMPROITE:
            case BASALT:
                return true;
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

    // Triangular value probability distribution with configurable peak between the min and max values
    public static int triDist(int min, int max, int peak, Random rand) {
        float F = (peak - min) / (float) (max - min);
        float r = rand.nextFloat();
        if (r < F) {
            return (int) (min + Math.sqrt(r * (max - min) * (peak - min)));
        } else {
            return (int) (max - Math.sqrt((1 - r) * (max - min) * (max - peak)));
        }
    }
}