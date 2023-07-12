package com.jemmerl.jemsgeology.util.noise.GenerationNoise;

import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;

public class StrataNoise {

    // Configured noise
    private static FastNoiseLite strataGenNoise; // Used for general adjustable strata generation
    private static FastNoiseLite yWarpNoise; // Used to add warping to adjustable strata
    private static FastNoiseLite yTiltNoise; // Used to add slant to adjustable strata
    private static FastNoiseLite smoothDividingNoise; // Used to determine the bounds of an inserted independent, wavy strata layer
    private static FastNoiseLite floodBasaltNoise; // Used to generate basalt/gabbro layers for flood basalts DEPRECATED


    ///////////////////////////////////////////
    /////         Noise Generators        /////
    ///////////////////////////////////////////

    public static float stoneStrataNoise(float x, float y, float z) {
        return strataGenNoise.GetNoise(x, y, z);
    }

    public static float strataWarpNoise(int x, int z) {
        return yWarpNoise.GetNoise(x, z);
    }

    public static float strataTiltNoise(int x, int z) {
        return yTiltNoise.GetNoise((x / 30f), (z / 30f));
    }

    public static float strataDividingNoise(int x, int y, int z) {
        return smoothDividingNoise.GetNoise(x, y, z);
    }

    public static float strataFloodBasaltNoise(int x, int y, int z) {
        return floodBasaltNoise.GetNoise((x / 5f), (y * 2f), (z / 5f));
    }



    ///////////////////////////////////////
    /////         Seed Setters        /////
    ///////////////////////////////////////

    public static void setWarpSeed(int seed) {
        yWarpNoise.SetSeed(seed);
    }

    public static void setTiltSeed(int seed) {
        yTiltNoise.SetSeed(seed);
    }

    public static void setStrataSeed(int seed) {
        strataGenNoise.SetSeed(seed);
    }



    ///////////////////////////////////////////////////////
    /////             Noise Configurations            /////
    ///////////////////////////////////////////////////////

    // FastNoiseLite configuration for strata generation
    public static void configNoise(long worldSeed) {

        // FastNoiseLite configuration for adjustable strata
        if (strataGenNoise == null) {
            strataGenNoise = new FastNoiseLite((int)(worldSeed)+22493);
            strataGenNoise.SetNoiseType(FastNoiseLite.NoiseType.Value);
            strataGenNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            strataGenNoise.SetFractalType(FastNoiseLite.FractalType.None);
            strataGenNoise.SetFrequency(0.0001f);
        }

        // FastNoiseLite configuration for adjustable strata warping noise
        if (yWarpNoise == null) {
            yWarpNoise = new FastNoiseLite((int)(worldSeed));
            yWarpNoise.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
            yWarpNoise.SetFractalType(FastNoiseLite.FractalType.None);
            yWarpNoise.SetFrequency(0.015f);
        }

        // FastNoiseLite configuration for adjustable strata tilting noise
        if (yTiltNoise == null) {
            yTiltNoise = new FastNoiseLite((int)(worldSeed));
            yTiltNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
            yTiltNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
            yTiltNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance2);
            yTiltNoise.SetFractalType(FastNoiseLite.FractalType.None);
            yTiltNoise.SetFrequency(0.002f);
        }

        // FastNoiseLite configuration for inserted strata
        if (smoothDividingNoise == null) {
            smoothDividingNoise = new FastNoiseLite((int)(worldSeed)-22493);
            smoothDividingNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            smoothDividingNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            smoothDividingNoise.SetFrequency(0.04f);
        }

        // FastNoiseLite configuration for flood basalts
        if (floodBasaltNoise == null) {
            floodBasaltNoise = new FastNoiseLite((int)(worldSeed)-22493);
            floodBasaltNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            floodBasaltNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            floodBasaltNoise.SetFrequency(0.03f);
        }
    }

}