package com.jemmerl.jemsgeology.util.noise.GenerationNoise;

import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;

public class BlobNoise {

    private static FastNoiseLite blobNoise;

    public static final float xLength = 7F;
    public static final float zLength = 13F;

    // Return the noise of a rotatable blob shape
    // Certain parameters must be used to preserve the shape:
    //      The shape is an oblong blob centered at XX of size 16x16 (1 chunk)
    //      Its approximate length is 13 (z-direction), and approximate width is 7 (x-direction)
    //      It is orientated (approximately) along the Z-axis
    public static float getNoise(float x, float z) {
        return blobNoise.GetNoise(x / 2f, z / 2f);
    }
//7x7
    //////////////////////////////////////////////////////
    /////             Noise Configuration            /////
    //////////////////////////////////////////////////////

    // FastNoiseLite configuration for pipe generation
    public static void configNoise(long worldSeed) {
        if (blobNoise == null) {
            blobNoise = new FastNoiseLite(2054); // THIS MUST NOT BE CHANGED! (Unless a better seed is found)
            blobNoise.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
            blobNoise.SetRotationType3D(FastNoiseLite.RotationType3D.None);
            blobNoise.SetFrequency(0.140f);
            blobNoise.SetFractalType(FastNoiseLite.FractalType.None);
        }
    }
}
//1480 ORIGINAL, 1533, 1720, 1791, 1814 NEW, 2054