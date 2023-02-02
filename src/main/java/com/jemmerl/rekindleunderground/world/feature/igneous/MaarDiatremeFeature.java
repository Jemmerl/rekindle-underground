package com.jemmerl.rekindleunderground.world.feature.igneous;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.blocks.IOreBlock;
import com.jemmerl.rekindleunderground.blocks.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.init.ModBlocks;
import com.jemmerl.rekindleunderground.init.NoiseInit;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.util.Pair;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredBlobNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// Not always circular, can be bumpy or eliptical
// More small breccia towards outside (and much less host rock), fewer and larger towards inside

public class MaarDiatremeFeature extends Feature<NoFeatureConfig> {

    public MaarDiatremeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private static final int MAX_RADIUS = 22; // Min radius of diatreme at the base
    private static final int MIN_DIATREME_HEIGHT = 50; // Minimum height of the top of the diatreme
    private static final int MAX_DIATREME_HEIGHT = 65; // Maximum height of the top of the diatreme
    private static final int MAAR_HEIGHT = 10; // Height of the maar section
    private static final int MAAR_EJECTA_HEIGHT = 30; // Height of the maar and scattered debris above the maar

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        // Config disable option
        if (!RKUndergroundConfig.COMMON.gen_maar_diatremes.get()) {
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

        // Add tuff and peridotite as random sprinkles
        // occasional partial beds of tuff


        ArrayList<BlockState> ejectaList = new ArrayList<>();
        ejectaList.add(ModBlocks.SCORIA_STONE.get().getDefaultState());
        ejectaList.add(ModBlocks.TUFF_STONE.get().getDefaultState());







        //////////////////////////////////////
        /// COMPOSITION PROPERTY SELECTION ///
        //////////////////////////////////////

        // Determine the primary igneous matrix rock of the pipe
        BlockState replacingBlock;
        int randInt = rand.nextInt(101);
        if (randInt > 65) {
            replacingBlock = ModBlocks.KIMBERLITE_STONE.get().getDefaultState(); // (35% Chance)
        } else if (randInt > 60) {
            replacingBlock = ModBlocks.LAMPROITE_STONE.get().getDefaultState(); // (5% Chance)
        } else if (randInt > 35) {
            replacingBlock = ModBlocks.BASALT_STONE.get().getDefaultState(); // (25% Chance)
        } else if (randInt > 30) {
            replacingBlock = ModBlocks.DACITE_STONE.get().getDefaultState(); // (5% Chance)
        } else if (randInt > 20) {
            replacingBlock = ModBlocks.ANDESITE_STONE.get().getDefaultState(); // (10% Chance)
        } else {
            replacingBlock = ModBlocks.RHYOLITE_STONE.get().getDefaultState(); // (20% Chance)
        }

        // Determine if the pipe is diamond bearing
        boolean diamondiferous = false;
        if (replacingBlock == ModBlocks.KIMBERLITE_STONE.get().getDefaultState() && (rand.nextFloat() < 0.25f)) {
            diamondiferous = true;
        } else if (replacingBlock == ModBlocks.LAMPROITE_STONE.get().getDefaultState() && (rand.nextFloat() < 0.05f)) {
            diamondiferous = true;
        }

        // How rich the pipe is from 1% to 15%
        float diamondPercent = diamondiferous ? ((0.14f * rand.nextFloat()) + 0.01f) : 0f;

        // Debug
        if (diamondiferous && RKUndergroundConfig.COMMON.debug_diatreme_maar.get()) {
            RekindleUnderground.getInstance().LOGGER.info("Diamondiferous diatreme generating at {} with {} richness", pos, diamondPercent);
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

        HashMap<BlockState, Integer> brecciaMap = new HashMap<>(); // Count replaced stones gen breccia
        ArrayList<BlockPos> brecciaPosList = new ArrayList<>(); // Store locations to place breccia

        for (int y = 0; y < diatreme_height; y++) {
            if (y > 0) {
                for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-MAX_RADIUS, 0, -MAX_RADIUS), pos.add(MAX_RADIUS, 0, MAX_RADIUS))) {

                    // Maximum possible radius for the top of diatreme is 18, which is 36/48 available blocks (3 chunks allocated to features)
                    float radius = (MAX_RADIUS - 14) - (baseDecrement * (1-(y/(float)diatreme_height))) + (ConfiguredBlobNoise.blobRadiusNoise(blockPos.getX(), y, blockPos.getZ()) * 10);

                    float distance;
                    if ((distance = (float)UtilMethods.getHypotenuse(blockPos.getX(), blockPos.getZ(), pos.getX(), pos.getZ())) <= radius) {
                        BlockPos currPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());

                        // A higher adjustment value means less breccia overall
                        if ((rand.nextFloat() + 0.18f) < (distance / radius)) {
                            brecciaPosList.add(currPos);
                        } else {
                            BlockState replacedBlock = reader.getBlockState(currPos);

                            // Check if valid placement
                            if (!UtilMethods.igneousReplaceable(replacedBlock)) {
                                continue;
                            }

                            // Record what block was replaced for later brecciation
                            updateBrecciaMap(brecciaMap, replacedBlock);

                            // Set the block
                            if (diamondiferous && (rand.nextFloat() < diamondPercent)) {
                                // Select grade of individual ore and generate the block with diamond ore
                                GradeType grade;
                                randInt = rand.nextInt(101);
                                if (randInt > 10) {
                                    grade = GradeType.LOWGRADE; // (90% Chance)
                                } else if (randInt > 1) {
                                    grade = GradeType.MIDGRADE; // (9% Chance)
                                }  else {
                                    grade = GradeType.HIGHGRADE; // (1% Chance)
                                }
                                reader.setBlockState(currPos, replacingBlock
                                        .with(StoneOreBlock.ORE_TYPE, OreType.DIAMOND)
                                        .with(StoneOreBlock.GRADE_TYPE, grade), 2);
                            } else {
                                reader.setBlockState(currPos, replacingBlock, 2);
                            }

                        }
                    }
                }
            }
        }


        /////////////////////
        // MAAR GENERATION //
        /////////////////////

        for (int y = diatreme_height; y < (diatreme_height + MAAR_HEIGHT); y++) {

        }


        ///////////////////////
        // EJECTA GENERATION //
        ///////////////////////

        for (int y = (diatreme_height + MAAR_HEIGHT); y < (diatreme_height + MAAR_EJECTA_HEIGHT); y++) {

        }


        ////////////////////////
        /// BRECCIA HANDLING ///
        ////////////////////////

        // Sum the total number of counted breccia
        int totalBreccia = brecciaMap.values().stream().reduce(0, Integer::sum);

        // Place breccias into diatreme
        if (!brecciaPosList.isEmpty()) {
            if (totalBreccia > 0) {
                ArrayList<Pair<Integer, BlockState>> elts = new ArrayList<>();

                // Sort breccias into weighted map entries
                int weightSum = 0; // Used to catch (most likely impossible) 100-percent overflows
                for (BlockState key : brecciaMap.keySet()) {
                    int weight = (brecciaMap.get(key) * 100) / totalBreccia;

                    weightSum += weight;
                    if (weightSum > 100) { weight -= (weightSum - 100); }

                    if (weight > 0) {
                        elts.add(new Pair<>(weight, key));
                    }
                }

                // Build weighted map and fill each designated breccia position with weighted random breccia
                WeightedProbMap<BlockState> wpm = new WeightedProbMap<>(elts);
                for (BlockPos brecciaPos : brecciaPosList) {
                    reader.setBlockState(brecciaPos, wpm.nextElt(), 2);
                }

            } else {
                // If no breccias were recorded, replace breccia positions with the main igneous rock (very unlikely)
                for (BlockPos brecciaPos : brecciaPosList) {
                    reader.setBlockState(brecciaPos, replacingBlock, 2);
                }
            }
        }

        // Debug
        if (RKUndergroundConfig.COMMON.debug_diatreme_maar.get()) {
            for (int i = 0; i < 10; i++) {
                reader.setBlockState(new BlockPos(pos.getX(), (max_height + 10 + i), pos.getZ()), Blocks.GOLD_BLOCK.getDefaultState(), 2);
            }
        }

        return true;
    }

    // TODO REWRITE?
    // Add a replaced block to the breccia counter map
    private static void updateBrecciaMap(HashMap<BlockState, Integer> brecciaMap, BlockState replaced) {
        if (replaced.getBlock() instanceof IOreBlock) {
            // If map entry exists, add to it. Else, start a new entry
            if (brecciaMap.containsKey(replaced)) {
                int prevVal = brecciaMap.get(replaced);
                brecciaMap.replace(replaced, prevVal + 1);
            } else {
                brecciaMap.put(replaced, 1);
            }
        }
    }

}

//    // Get the current radius of the diatreme section
//    // Will be slightly different every time it is called, causing a dithering effect
//    private static int getPipeRadius(Random rand, int baseRadius, float angle, BlockPos pos) {
//        float noise = ConfiguredBlobNoise.blobRadiusNoise(pos.getX(), pos.getY(), pos.getZ());
//        return (int)(baseRadius + (pos.getY() * angle) // Radius based on height and shape
//                + (PIPE_MIN_RADIUS * (1f + (0.5f * noise)))); // Warp pipe shape
//                //+ rand.nextInt(PIPE_DITHER_VARIATION)); // Add random dither
//    }