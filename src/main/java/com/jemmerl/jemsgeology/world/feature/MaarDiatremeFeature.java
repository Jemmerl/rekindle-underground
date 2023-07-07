package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.blocks.IOreBlock;
import com.jemmerl.jemsgeology.blocks.StoneOreBlock;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.Pair;
import com.jemmerl.jemsgeology.util.ReplaceableStatus;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.util.WeightedProbMap;
import com.jemmerl.jemsgeology.util.lists.ModBlockLists;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

    private static final int MAX_DIATREME_RADIUS = 19; // Max radius of upper diatreme
    private static final int MAX_MAAR_RADIUS = 22; // Max radius of upper diatreme
    private static final int MIN_DIATREME_HEIGHT = 50; // Minimum height of the top of the diatreme
    private static final int MAX_DIATREME_HEIGHT = 60; // Maximum height of the top of the diatreme
    private static final int MAAR_HEIGHT = 10; // Height of the maar section
    private static final int MAAR_EJECTA_HEIGHT = 30; // Height of the maar and scattered debris above the maar

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        // Config disable option
        if (!JemsGeoConfig.COMMON.gen_maar_diatremes.get()) {
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

        // Determine the primary igneous matrix rock of the pipe
        BlockState mainIgnBlock;
        {
            int randInt = rand.nextInt(101);
            if (randInt > 65) {
                mainIgnBlock = ModBlocks.KIMBERLITE_STONE.get().getDefaultState(); // (35% Chance)
            } else if (randInt > 60) {
                mainIgnBlock = ModBlocks.LAMPROITE_STONE.get().getDefaultState(); // (5% Chance)
            } else if (randInt > 35) {
                mainIgnBlock = ModBlocks.BASALT_STONE.get().getDefaultState(); // (25% Chance)
            } else if (randInt > 30) {
                mainIgnBlock = ModBlocks.DACITE_STONE.get().getDefaultState(); // (5% Chance)
            } else if (randInt > 20) {
                mainIgnBlock = ModBlocks.ANDESITE_STONE.get().getDefaultState(); // (10% Chance)
            } else {
                mainIgnBlock = ModBlocks.RHYOLITE_STONE.get().getDefaultState(); // (20% Chance)
            }
        }

        // Determine if the pipe is diamond bearing
        boolean diamondiferous = false;
        if (mainIgnBlock == ModBlocks.KIMBERLITE_STONE.get().getDefaultState() && (rand.nextFloat() < 0.25f)) {
            diamondiferous = true;
        } else if (mainIgnBlock == ModBlocks.LAMPROITE_STONE.get().getDefaultState() && (rand.nextFloat() < 0.05f)) {
            diamondiferous = true;
        }

        // How rich the pipe is from 1% to 15%
        float diamondPercent = diamondiferous ? ((0.10f * rand.nextFloat()) + 0.05f) : 0f;

        // Debug
        if (diamondiferous && JemsGeoConfig.COMMON.debug_diatreme_maar.get()) {
            JemsGeology.getInstance().LOGGER.info("Diamondiferous diatreme generating at {} with {} richness", pos, diamondPercent);
        }


        ///////////////////////////////
        /// SIZE PROPERTY SELECTION ///
        ///////////////////////////////

        // How much smaller the base radius will be in blocks
        int baseDecrement = rand.nextInt(8);

        // Top of the diatreme
        int diatreme_height = rand.nextInt(MAX_DIATREME_HEIGHT - MIN_DIATREME_HEIGHT) + MIN_DIATREME_HEIGHT;

        // Add height depending on certain biomes, due to the overall added terrain height
        if (reader.getBiome(pos).getCategory() == Biome.Category.EXTREME_HILLS) {
            diatreme_height += 25;
        }

        // Top of the maar (the main maar is 10 blocks, the extra 20 accounts for scattered debris generation above
        int max_height = diatreme_height + 30;


        /////////////////////////
        // DIATREME GENERATION //
        /////////////////////////

        HashMap<BlockState, Integer> brecciaMap = new HashMap<>(); // Count the relative amounts of replaced stones for breccia ratios
        int brecciaCount = 0; // Count the overall number of recorded replaced stones
        ArrayList<BlockPos> brecciaPosList = new ArrayList<>(); // Store locations to place breccia
        ArrayList<BlockPos> regolithPosList = new ArrayList<>(); // Store locations to place regolith

        for (int y = 1; y < diatreme_height; y++) {
            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-MAX_DIATREME_RADIUS, 0, -MAX_DIATREME_RADIUS), pos.add(MAX_DIATREME_RADIUS, 0, MAX_DIATREME_RADIUS))) {
                // Maximum possible radius for the top of diatreme is 18, which is 36/48 available blocks (3 chunks allocated to features)

                float radius = (MAX_DIATREME_RADIUS - 7) - (baseDecrement * (1 - (y / (float) diatreme_height))) +
                        (BlobNoise.blobRadiusNoise(blockPos.getX(), y, blockPos.getZ()) * 7) + rand.nextInt(3);

                float distance = (float) UtilMethods.getHypotenuse(blockPos.getX(), blockPos.getZ(), pos.getX(), pos.getZ());
                if (distance <= radius) {
                    BlockPos currPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
                    BlockState replacedBlock = reader.getBlockState(currPos);

                    // Check if valid placement
                    if (UtilMethods.replaceableStatus(replacedBlock).equals(ReplaceableStatus.FAILED)) {
                        continue;
                    }

                    // Record what block was replaced for later brecciation
                    brecciaCount += updateBrecciaMap(brecciaMap, replacedBlock);

                    // A higher adjustment value means less breccia overall
                    if ((rand.nextFloat() + 0.18f) < (distance / radius)) {
                        brecciaPosList.add(currPos);
                    } else {
                        placeDiamondiferousBlock(rand, reader, currPos, mainIgnBlock, diamondiferous, diamondPercent);
                    }
                }
            }
        }


        /////////////////
        // BRECCIA MAP //
        /////////////////

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


        /////////////////////
        // MAAR GENERATION //
        /////////////////////

        for (int y = diatreme_height; y < (diatreme_height + MAAR_HEIGHT); y++) {
            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-MAX_DIATREME_RADIUS, 0, -MAX_DIATREME_RADIUS), pos.add(MAX_DIATREME_RADIUS, 0, MAX_DIATREME_RADIUS))) {
                // Maximum possible radius for the top of maar is 22, which is 44/48 available blocks (3 chunks allocated to features)
                float outer_radius = (MAX_MAAR_RADIUS - 7) - (baseDecrement * (1 - (y / (float) (diatreme_height + MAAR_HEIGHT)))) +
                        (BlobNoise.blobRadiusNoise(blockPos.getX(), y, blockPos.getZ()) * 7);
                float inner_radius = MAX_MAAR_RADIUS - (MAX_MAAR_RADIUS * (1 - ((y - diatreme_height) / (float) MAAR_HEIGHT)));

                float distance = (float) UtilMethods.getHypotenuse(blockPos.getX(), blockPos.getZ(), pos.getX(), pos.getZ());
                if (distance <= outer_radius) {
                    BlockPos currPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
                    BlockState replacedBlock = reader.getBlockState(currPos);

                    // Check if valid placement
                    if (UtilMethods.replaceableStatus(replacedBlock).equals(ReplaceableStatus.FAILED)) {
                        continue;
                    }

                    if (distance > inner_radius) {
                        // Maar generation
                        {
                            int randInt = rand.nextInt(101);
                            if (randInt > 55) {
                                // 45% Chance of tuff + 10% chance from brecca == 55% chance overall
                                reader.setBlockState(currPos, ModBlocks.ULTRAMAFIC_TUFF_STONE.get().getDefaultState(), 2); //TODO TEMP
                            } else if (randInt > 15) {
                                // 40% Chance of regolith (which gives 10% chance of tuff itself, giving 30% chance of true regolith)
                                regolithPosList.add(currPos);
                            } else {
                                // 15% Chance of main igneous
                                reader.setBlockState(currPos, mainIgnBlock, 2);
                            }
                        }
                    } else {
                        // Ejecta generation
                        generateEjecta(rand, reader, currPos, mainIgnBlock, diamondiferous, diamondPercent, regolithPosList);
                    }
                }
            }
        }


        ///////////////////////
        // EJECTA GENERATION //
        ///////////////////////

        for (int y = (diatreme_height + MAAR_HEIGHT); y < (diatreme_height + MAAR_EJECTA_HEIGHT); y++) {
            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-MAX_DIATREME_RADIUS, 0, -MAX_DIATREME_RADIUS), pos.add(MAX_DIATREME_RADIUS, 0, MAX_DIATREME_RADIUS))) {
                float radius = (MAX_MAAR_RADIUS - 7) + (BlobNoise.blobRadiusNoise(blockPos.getX(), y, blockPos.getZ()) * 7);

                float distance = (float) UtilMethods.getHypotenuse(blockPos.getX(), blockPos.getZ(), pos.getX(), pos.getZ());
                if (distance <= radius) {
                    BlockPos currPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
                    BlockState replacedBlock = reader.getBlockState(currPos);

                    // Check if valid placement
                    if (UtilMethods.replaceableStatus(replacedBlock).equals(ReplaceableStatus.FAILED)) {
                        continue;
                    }

                    // Ejecta generation
                    generateEjecta(rand, reader, currPos, mainIgnBlock, diamondiferous, diamondPercent, regolithPosList);
                }
            }
        }


        ////////////////////////
        /// BRECCIA HANDLING ///
        ////////////////////////

        if (!brecciaPosList.isEmpty()) {
            if (!brecciaProbMap.isEmpty()) {
                for (BlockPos brecciaPos : brecciaPosList) {
                    // 25% chance of tuff
                    if (rand.nextFloat() < 0.75f) {
                        reader.setBlockState(brecciaPos, brecciaProbMap.nextElt(), 2);
                    } else {
                        reader.setBlockState(brecciaPos, ModBlocks.ULTRAMAFIC_TUFF_STONE.get().getDefaultState(), 2); //TODO TEMP
                    }
                }

            } else {
                // If no breccias were recorded, replace breccia positions with the main igneous rock (very unlikely)
                for (BlockPos brecciaPos : brecciaPosList) {
                    // 50% chance of tuff
                    if (rand.nextBoolean()) {
                        reader.setBlockState(brecciaPos, mainIgnBlock, 2);
                    } else {
                        reader.setBlockState(brecciaPos, ModBlocks.ULTRAMAFIC_TUFF_STONE.get().getDefaultState(), 2); //TODO TEMP
                    }
                }
            }
        }


        /////////////////////////
        /// REGOLITH HANDLING ///
        /////////////////////////

        if (!regolithPosList.isEmpty()) {
            boolean emptyProbMap = brecciaProbMap.isEmpty();
            for (BlockPos regolithPos : regolithPosList) {
                GeologyType regolithType = ((StoneOreBlock) (((!emptyProbMap && (rand.nextFloat() > 0.35f)) ? brecciaProbMap.nextElt() : mainIgnBlock).getBlock())).getGeologyType();
                BlockState regolithState = (ModBlockLists.GEO_LIST.get(regolithType).getRegolithBlock().getDefaultState());
                placeDiamondiferousBlock(rand, reader, regolithPos, regolithState, diamondiferous, diamondPercent);
            }
        }


        // Debug
        if (JemsGeoConfig.COMMON.debug_diatreme_maar.get()) {
            for (int i = 0; i < 10; i++) {
                reader.setBlockState(new BlockPos(pos.getX(), (max_height + 10 + i), pos.getZ()), Blocks.GOLD_BLOCK.getDefaultState(), 2);
            }
        }

        // Finish maar-diatreme generation
        return true;
    }


    /////////////////////
    // UTILITY METHODS //
    /////////////////////

    // Add a replaced block to the breccia counter map
    private static int updateBrecciaMap(HashMap<BlockState, Integer> brecciaMap, BlockState replaced) {
        Block replacedBlock = replaced.getBlock();
        if (replacedBlock instanceof IOreBlock && !(((IOreBlock) replacedBlock).getStoneGroupType().equals(StoneGroupType.DETRITUS))) {
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


    // Place an ejecta block
    private static void generateEjecta(Random rand, ISeedReader reader, BlockPos currPos, BlockState mainIgnBlock,
                                       Boolean diamondiferous, float diamondPercent, ArrayList<BlockPos> regolithPosList) {
        if (rand.nextFloat() > 0.65f) {
            regolithPosList.add(currPos);
        } else {
            setFillBlock(rand, reader, currPos, diamondiferous, diamondPercent);
        }
    }


    // Place a filler detritus for the upper maar
    private static void setFillBlock(Random rand, ISeedReader reader, BlockPos currPos, Boolean diamondiferous, float diamondPercent) {
        BlockState fillState;
        ISurfaceBuilderConfig surfaceConfig = reader.getBiome(currPos).getGenerationSettings().getSurfaceBuilderConfig();
        int height = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, currPos).getY() - 1;

        // Get appropriate infill
        if (currPos.getY() == height) {
            fillState = surfaceConfig.getTop();
        } else {
            fillState = (rand.nextFloat() > 0.20f) ? surfaceConfig.getUnder() : ModBlocks.ULTRAMAFIC_TUFF_STONE.get().getDefaultState(); //TODO TEMP

            // Debug
            if (JemsGeoConfig.COMMON.debug_diatreme_maar.get() && (currPos.getY() > height)) {
                JemsGeology.getInstance().LOGGER.info("Placed an unexpected maar-fill block at {}", currPos);
            }
        }
        //fillState = surfaceConfig.getUnderWaterMaterial(); need to cast to (SurfaceBuilderConfig)

        if (UtilMethods.convertVanillaToDetritus(fillState) instanceof IOreBlock) {
            // Places fill block with a fourth of the chance as the normal diatreme to have diamonds (if diamondiferous)
            placeDiamondiferousBlock(rand, reader, currPos, fillState, diamondiferous, (diamondPercent / 4f));
        } else {
            reader.setBlockState(currPos, fillState, 2);
        }
    }


    // Place potentially diamondiferous block
    private static void placeDiamondiferousBlock(Random rand, ISeedReader reader, BlockPos currPos,
                                                 BlockState blockState, Boolean diamondiferous, float diamondPercent) {
        if (diamondiferous && (rand.nextFloat() < diamondPercent)) {
            blockState = UtilMethods.convertVanillaToDetritus(blockState);

            // Select grade of individual ore and generate the block with diamond ore
            reader.setBlockState(currPos, blockState
                    .with(StoneOreBlock.ORE_TYPE, OreType.DIAMOND)
                    .with(StoneOreBlock.GRADE_TYPE, getDiamondGrade(rand)), 2);
        } else {
            reader.setBlockState(currPos, blockState, 2);
        }
    }


    // Get the grade type for diamond ore
    private static GradeType getDiamondGrade(Random rand) {
        int randInt = rand.nextInt(101);
        if (randInt > 10) {
            return GradeType.LOWGRADE; // (90% Chance)
        } else if (randInt > 1) {
            return GradeType.MIDGRADE; // (9% Chance)
        } else {
            return GradeType.HIGHGRADE; // (1% Chance)
        }
    }

}