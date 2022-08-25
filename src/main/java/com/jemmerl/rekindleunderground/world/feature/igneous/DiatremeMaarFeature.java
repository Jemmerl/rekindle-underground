package com.jemmerl.rekindleunderground.world.feature.igneous;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.init.NoiseInit;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.util.Pair;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredBlobNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
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

public class DiatremeMaarFeature extends Feature<NoFeatureConfig> {

    public DiatremeMaarFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private static final int PIPE_MIN_RADIUS = 10; // Min radius of diatreme at the base
    private static final int PIPE_MAX_DEPTH = 30; // Maximum depth below the surface a pipe can spawn
    private static final int PIPE_DITHER_VARIATION = 3; // Randomization of edge "fuzziness"

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        // TODO TEMP, only allow generation if in debug mode
        if (!RKUndergroundConfig.COMMON.debug.get()) {
            return false;
        }

        // Remove oceanic mantle pipes. Mantle pipes are a continental feature
        if (reader.getBiome(pos).getCategory() == Biome.Category.OCEAN) {
            return false;
        }

        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(reader.getSeed());
        }

        // Add tuff and peridotite as random sprinkles
        // occasional partial beds of tuff


        ArrayList<BlockState> ejectaList = new ArrayList<>();
        ejectaList.add(StoneType.SCORIA.getStoneState());
        ejectaList.add(StoneType.TUFF.getStoneState());

        /* FEATURE PROPERTY SELECTION */





        //////////////////////////////////////
        /// COMPOSITION PROPERTY SELECTION ///
        //////////////////////////////////////

        // Determine the primary igneous matrix rock of the pipe
        BlockState replacingBlock;
        int randInt = rand.nextInt(101);
        if (randInt > 70) {
            replacingBlock = StoneType.KIMBERLITE.getStoneState(); // (30% Chance)
        } else if (randInt > 65) {
            replacingBlock = StoneType.LAMPROITE.getStoneState(); // (5% Chance)
        } else if (randInt > 40) {
            replacingBlock = StoneType.BASALT.getStoneState(); // (25% Chance)
        } else if (randInt > 25) {
            replacingBlock = StoneType.DACITE.getStoneState(); // (15% Chance)
        } else if (randInt > 15) {
            replacingBlock = StoneType.ANDESITE.getStoneState(); // (10% Chance)
        } else {
            replacingBlock = StoneType.RHYOLITE.getStoneState(); // (15% Chance)
        }

        // Determine if the pipe is diamond bearing
        boolean diamondiferous = false;
        if (replacingBlock == StoneType.KIMBERLITE.getStoneState() && (rand.nextFloat() < 0.25f)) {
            diamondiferous = true;
        } else if (replacingBlock == StoneType.LAMPROITE.getStoneState() && (rand.nextFloat() < 0.05f)) {
            diamondiferous = true;
        }

        // How rich the pipe is from 1% to 15%
        float diamondPercent = 0f;
        if (diamondiferous) {
            diamondPercent = (0.14f * rand.nextFloat()) + 0.01f;
        }

        BlockPos posShift = pos.add(8,0,8); // Center the feature
        int baseDecrement = rand.nextInt(6); // How much smaller the base radius will be

        // TEMP
        int height = 100;
        // END TEMP

        HashMap<BlockState, Integer> xenolithMap = new HashMap<>(); // Count replaced stones gen xenoliths
        ArrayList<BlockPos> xenolithPosList = new ArrayList<>(); // Store locations to place xenoliths
        BlockState replacedBlock;

        int range = 25;
        for (int y = 0; y < height; y++) {
            if (y > 0) {
                for (BlockPos blockPos : BlockPos.getAllInBoxMutable(posShift.add(-range, 0, -range), posShift.add(range, 0, range))) {

                    float radius = (range - 10) - (baseDecrement * (1-(y/(float)height))) + (ConfiguredBlobNoise.blobRadiusNoise(blockPos.getX(), y, blockPos.getZ()) * 10);

                    float distance;
                    if ((distance = (float)UtilMethods.getHypotenuse(blockPos.getX(), blockPos.getZ(), posShift.getX(), posShift.getZ())) <= radius) {
                        BlockPos currPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());

                        if ((rand.nextFloat() + 0.15f) < (distance / radius)) {
                            xenolithPosList.add(currPos);
                        } else {
                            replacedBlock = reader.getBlockState(currPos);
                            updateXenolithMap(xenolithMap, replacedBlock);

                            // Set the block
                            if (diamondiferous && (rand.nextFloat() < diamondPercent)) {
                                // Generate the block with diamond ore
                                reader.setBlockState(currPos, replacingBlock.with(StoneOreBlock.ORE_TYPE, OreType.DIAMOND), 2);
                            } else {
                                reader.setBlockState(currPos, replacingBlock, 2);
                            }

                        }
                    }
                }
            }
        }


//
//
//
//        ////////////////////////////////////
//        /// GEOMETRIC PROPERTY SELECTION ///
//        ////////////////////////////////////
//
//        BlockPos posShift = pos.add(8,0,8);
//        System.out.println(posShift.getX() + ", " + posShift.getZ());
//        int maxYHeight = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX(), pos.getZ());
////        int yShift = rand.nextInt(PIPE_MAX_DEPTH);
//        int yShift = -rand.nextInt(PIPE_MAX_DEPTH) - 30; // TEMP FOR TESTING
////        System.out.println("Max Height: " + maxYHeight);
//        int startY = 1 - yShift;
//        int endY = maxYHeight - yShift;
////        System.out.println("Set endY: " + endY);
//

//        int range = (int)(maarMaxRadius * 1.5f) + (PIPE_DITHER_VARIATION * 2);
//        for (int y = startY; y < endY; y++) {
//            if (y > 0) {

//
        int totalXenoliths = xenolithMap.values().stream().reduce(0, Integer::sum);
        //System.out.println(totalXenoliths);

        if (!xenolithPosList.isEmpty()) {
            if (totalXenoliths > 0) {
                ArrayList<Pair<Integer, BlockState>> elts = new ArrayList<>();
                int weight;
                int weightSum = 0; // Used to catch (most likely impossible) 100-percent overflows

                for (BlockState key : xenolithMap.keySet()) {
                    weight = (xenolithMap.get(key) * 100) / totalXenoliths;

                    weightSum += weight;
                    if (weightSum > 100) { weight -= (weightSum - 100); }

                    if (weight > 0) {
                        elts.add(new Pair<>(weight, key));
                    }
                }
                WeightedProbMap<BlockState> wpm = new WeightedProbMap<>(elts);

                for (BlockPos xenolithPos : xenolithPosList) {
                    reader.setBlockState(xenolithPos, wpm.nextElt(), 2);
                }
            } else {
                for (BlockPos xenolithPos : xenolithPosList) {
                    reader.setBlockState(xenolithPos, replacingBlock, 2);
                }
            }
        }

        return true;
    }


    // Angle of the main "subterranean" component (diatreme)
    private static float getDiatremeAngle(Random rand) {
        if (rand.nextBoolean()) {
            return 0f; // Cylinder-shaped
        } else {
            return 0.05f + (0.15f * rand.nextFloat()); // Carrot-shaped
        }
    }

    // Angle of the upper "exposed" component (maar)
    private static float getMaarAngle(Random rand, float diaAngle) {
        if (rand.nextBoolean()) {
            return diaAngle; // Continue diatreme shape
        } else {
            return diaAngle + (0.3f * rand.nextFloat()); // Expand diatreme shape
        }
    }

    // Get the current radius of the diatreme section
    // Will be slightly different every time it is called, causing a dithering effect
    private static int getPipeRadius(Random rand, int baseRadius, float angle, BlockPos pos) {
        float noise = ConfiguredBlobNoise.blobRadiusNoise(pos.getX(), pos.getY(), pos.getZ());
        return (int)(baseRadius + (pos.getY() * angle) // Radius based on height and shape
                + (PIPE_MIN_RADIUS * (1f + (0.5f * noise)))); // Warp pipe shape
                //+ rand.nextInt(PIPE_DITHER_VARIATION)); // Add random dither
    }


    private static void updateXenolithMap (HashMap<BlockState, Integer> map, BlockState replaced) {
        if (StoneType.isInStones(replaced.getBlock().getRegistryName().getPath())) {
            if (map.containsKey(replaced)) {
                int prevVal = map.get(replaced);
                map.replace(replaced, prevVal + 1);
            } else {
                map.put(replaced, 1);
            }
        }
    }


}
