package com.jemmerl.rekindleunderground.util.noise.GenerationNoise;

import com.jemmerl.rekindleunderground.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.util.noise.FastNoiseLite;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import static com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredRegionNoise.stoneFaultNoise;

public class ConfiguredStrataNoise {

    private static FastNoiseLite strataNoise; // Used for general adjustable strata generation
    private static FastNoiseLite yWarpNoise; // Used to add warping to adjustable strata
    private static FastNoiseLite yTiltNoise; // Used to add slant to adjustable strata
    private static FastNoiseLite insertedStrataNoise; // Used to determine the bounds of an inserted independent, wavy strata layer
    private static FastNoiseLite floodBasaltNoise; // Used to generate basalt/gabbro layers for flood basalts

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

        // TODO re-implement fault shifts

        BlockState blockState;
        switch (regionVal) {
            case -5: // Flood Basalt
                blockState = floodBasaltGen(x, y, z, false);
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
    private static BlockState floodBasaltGen(int xPos, int yPos, int zPos, boolean faults) {
        BlockState state;
        float noiseVal;
        if (faults) {
            yPos += (int)(10f * stoneFaultNoise(xPos, yPos, zPos));
        }
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

    private static float genAdjustableLayers(int x, int y, int z, float curvature, int warp, int tilt, int fault, boolean orogeny) {
        // Warp determines how "buckled" the layers are, tilt determines what angle the strata form at.
        // Orogeny is if the region is in (or what was once) a mountain biome; means warp is
        // an increasing function of y (more y, more warp)

        // Apply regional seed shift
        int shiftedSeed = ConfiguredRegionNoise.getRegionShiftedSeed(x, y, z);
        yWarpNoise.SetSeed(shiftedSeed + 1340);
        yTiltNoise.SetSeed(shiftedSeed - 15605);
        strataNoise.SetSeed(shiftedSeed);

        // Bound input variables
        // curvature:(0,5]; all others:[0-100]
        curvature = ((curvature <= 0) || (curvature > 5f)) ? 1f : curvature;
        warp = ((warp < 0) || (warp > 100)) ? 10 : warp;
        tilt = ((tilt < 0) || (tilt > 100)) ? 10 : tilt;
        fault = ((fault < 0) || (fault > 100)) ? 20 : fault;

        // Applies tile and then warp to the layers
        // If an orogeny region (mountainous or once was), then increase by y value
        int layerShiftedY = y + (int)((tilt * tilt * tilt / 30) * yTiltNoise.GetNoise(x / 30f, z / 30f));
        if (orogeny) {
            float mappedY = UtilMethods.remap((float)y, new float[]{0,256}, new float[]{0.5f,3f});
            layerShiftedY += (int)(mappedY * warp * 0.3f * yWarpNoise.GetNoise(x * 15, z * 15));
        } else {
            layerShiftedY += (int)(warp * 0.3f * yWarpNoise.GetNoise(x * 15, z * 15));
        }

        // Applies fault vertical shift
        int faultShiftedY = layerShiftedY + (int)((fault / 2f) * stoneFaultNoise(x, y, z));

        // Tilting the layers shrinks layer height, tilt value is used here to counteract this effect
        return strataNoise.GetNoise(x * 15f * curvature, faultShiftedY * ((400.0f - (tilt * 3f)) * 0.75f), z * 15f * curvature);
    }


    // Returns the strata layer noise value for determining rock type NOTE: USE AS TEMPLATE/EXAMPLE -- DEPRECATED
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

        // FastNoiseLite configuration for adjustable strata
        if (strataNoise == null) {
            strataNoise = new FastNoiseLite((int)(WORLD_SEED)+22493);
            strataNoise.SetNoiseType(FastNoiseLite.NoiseType.Value);
            strataNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            strataNoise.SetFractalType(FastNoiseLite.FractalType.None);
            strataNoise.SetFrequency(0.0001f);
        }

        // FastNoiseLite configuration for adjustable strata warping noise
        if (yWarpNoise == null) {
            yWarpNoise = new FastNoiseLite((int)(WORLD_SEED));
            yWarpNoise.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
            yWarpNoise.SetFractalType(FastNoiseLite.FractalType.None);
            yWarpNoise.SetFrequency(0.015f);
        }

        // // FastNoiseLite configuration for adjustable strata tilting noise
        if (yTiltNoise == null) {
            yTiltNoise = new FastNoiseLite((int)(WORLD_SEED));
            yTiltNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
            yTiltNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
            yTiltNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance2);
            yTiltNoise.SetFractalType(FastNoiseLite.FractalType.None);
            yTiltNoise.SetFrequency(0.002f);
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
