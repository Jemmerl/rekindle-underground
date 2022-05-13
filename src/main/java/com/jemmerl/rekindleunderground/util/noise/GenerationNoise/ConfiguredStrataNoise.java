package com.jemmerl.rekindleunderground.util.noise.GenerationNoise;

import com.jemmerl.rekindleunderground.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.util.noise.FastNoiseLite;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class ConfiguredStrataNoise {

    private static FastNoiseLite strataNoise; // Deprecated
    private static FastNoiseLite insertedStrataNoise; // Used to determine the bounds of an inserted independent, wavy strata layer
    private static FastNoiseLite floodBasaltNoise; // Generates the flood basalt layers (not diabase)

    private static long WORLD_SEED;

    ////////////////////////////////////////////////
    /////            Region Handler            /////
    ////////////////////////////////////////////////

    public static BlockState getStoneStrataBlock(int x, int y, int z) {
        //int regionVal = (int)(ConfiguredRegionNoise.stoneRegionNoise(x, y, z) * 5); //TODO 5 gives 11 region types possible rn (-5 to 5)
        int regionVal;
        if (ConfiguredRegionNoise.stoneRegionNoise(x, y, z) < 0) { // TEMP
            regionVal = -5;
        } else {
            regionVal = 0;
        }

        BlockState blockState;
        switch (regionVal) {
            case -5: // Flood Basalt
                blockState = floodBasaltGen(x, y, z);
                break;

            default: blockState = Blocks.IRON_BLOCK.getDefaultState();
                break;
        }
        return blockState;
    }


    //////////////////////////////////////////////////
    /////            Block Generators            /////
    //////////////////////////////////////////////////

    // Gets the block state for a Flood Basalt region at the specific location
    private static BlockState floodBasaltGen(int xPos, int yPos, int zPos) {
        BlockState state;
        float noiseVal;
        if (yPos <= (11 + (int)(4 * insertedStrataNoise.GetNoise((xPos * 2), (yPos), (zPos * 2))))) { // Adds Diabase layer smoothly below y 10-16
            state = ModBlocks.DIABASE.get().getDefaultState();
        } else {
            noiseVal = floodBasaltNoise.GetNoise((xPos / 5f), (yPos * 2f), (zPos / 5f));
            if (noiseVal < -0.1f) {
                state = ModBlocks.GABBRO.get().getDefaultState();
            } else {
                state = ModBlocks.BASALT.get().getDefaultState();
            }
        }
        return state;
    }





    //////////////////////////////////////////////////
    /////            Noise Generators            /////
    //////////////////////////////////////////////////

    /*
    *  Only needed if excessive dynamic manipulation of input or output is required.
    *  Else, operations will be done in args or where called.
    */


    // Returns the strata layer noise value for determining rock type NOTE: USE AS TEMPLATE, IS OUTDATED
/*    public static float stoneStrataNoise(int x, int y, int z) {
        int faultShiftedY = y + (int)((50 * FAULT_VARIATION) * stoneFaultNoise(x, y, z));
        int shiftedSeed = (int)(WORLD_SEED + (stoneRegionNoise(x, y, z) * (10000 * REGIONAL_VARIATION)));
        strataNoise.SetSeed(shiftedSeed);
        return strataNoise.GetNoise(x * (15f * 1), faultShiftedY * (400.0f * (1f - (float)STRATA_DEPTH)), z * (15f * 1)); // include a strata size factor?
    }*/


    ///////////////////////////////////////////////////////
    /////             Noise Configurations            /////
    ///////////////////////////////////////////////////////

    // FastNoiseLite configuration for strata generation
    public static void configNoise(long worldSeed) {
        WORLD_SEED = worldSeed; // Sets the world seed for access in noise generation

        // FastNoiseLite configuration for strata - TEMPLATE
        if (strataNoise == null) {
            strataNoise = new FastNoiseLite((int)(WORLD_SEED)+22493);
            strataNoise.SetNoiseType(FastNoiseLite.NoiseType.Value);
            strataNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            strataNoise.SetFractalType(FastNoiseLite.FractalType.None);
            strataNoise.SetFrequency(0.0001f);
        }

        // FastNoiseLite configuration for inserted strata
        if (insertedStrataNoise == null) {
            insertedStrataNoise = new FastNoiseLite((int)(WORLD_SEED)-22493);
            insertedStrataNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            insertedStrataNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            insertedStrataNoise.SetFrequency(0.04f);
        }

        // FastNoiseLite configuration for flood basalts
        if (floodBasaltNoise == null) {
            floodBasaltNoise = new FastNoiseLite((int)(WORLD_SEED)-22493);
            floodBasaltNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            floodBasaltNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            floodBasaltNoise.SetFrequency(0.03f);
        }
    }

}
