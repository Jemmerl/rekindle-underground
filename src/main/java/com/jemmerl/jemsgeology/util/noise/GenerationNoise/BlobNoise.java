package com.jemmerl.jemsgeology.util.noise.GenerationNoise;

import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;

// All the lumpy noises for making smooth things lumpy
public class BlobNoise {

    private static FastNoiseLite boulderNoise;
    private static FastNoiseLite blobWarpNoise;
    private static FastNoiseLite xenolithNoise;

    public static final float xLengthBoulder = 7F;
    public static final float zLengthBoulder = 13F;

    // Return the noise of a rotatable blob shape
    // Certain parameters must be used to preserve the shape:
    //      The shape is an oblong blob centered at XX of size 16x16 (1 chunk)
    //      Its approximate length is 13 (z-direction), and approximate width is 7 (x-direction)
    //      It is orientated (approximately) along the Z-axis
    public static float getBoulderNoise(float x, float z) {
        return boulderNoise.GetNoise(x / 2f, z / 2f);
    }
    //7x7

    // Returns the noise value to warp a radius
    public static float blobWarpRadiusNoise(float x, float y, float z) {
        FastNoiseLite.Vector3 v3 = new FastNoiseLite.Vector3(x, y, z);
        blobWarpNoise.DomainWarp(v3);
        return blobWarpNoise.GetNoise(v3.x, (v3.y / 5f), v3.z);
    }

    public static float getXenolithNoise(float x, float y, float z) {
        return xenolithNoise.GetNoise(x, y, z);
    }

    //////////////////////////////////////////////////////
    /////             Noise Configuration            /////
    //////////////////////////////////////////////////////

    // FastNoiseLite configuration for pipe generation
    public static void configNoise(long worldSeed) {
        if (boulderNoise == null) {
            boulderNoise = new FastNoiseLite(2054); // THIS MUST NOT BE CHANGED! (Unless a better seed is found)
            //1480 ORIGINAL, 1533, 1720, 1791, 1814 NEW, 2054
            boulderNoise.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
            boulderNoise.SetRotationType3D(FastNoiseLite.RotationType3D.None);
            boulderNoise.SetFrequency(0.140f);
            boulderNoise.SetFractalType(FastNoiseLite.FractalType.None);
        }

        if (blobWarpNoise == null) {
            blobWarpNoise = new FastNoiseLite((int)worldSeed);
            blobWarpNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            blobWarpNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            blobWarpNoise.SetFrequency(0.030f);
            //blobNoise.SetFractalType(FastNoiseLite.FractalType.None);
            blobWarpNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
            blobWarpNoise.SetFractalOctaves(3);
            blobWarpNoise.SetFractalLacunarity(2.00f);
            blobWarpNoise.SetFractalGain(0.80f);
        }

        if (xenolithNoise == null) {
            xenolithNoise = new FastNoiseLite((int)worldSeed);
            xenolithNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            xenolithNoise.SetFrequency(0.07f);
            xenolithNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);

        }
    }
}
