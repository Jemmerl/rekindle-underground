package com.jemmerl.jemsgeology.util.noise.GenerationNoise;

import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;

public class BlobWarpNoise {

    private static FastNoiseLite blobWarpNoise;

    // Config values loaded in here to allow for typecasting

    ////////////////////////////////////////////////////////////
    /////            Noise Generators and Tools            /////
    ////////////////////////////////////////////////////////////

    // Returns the noise value to warp a radius
    public static float blobWarpRadiusNoise(int x, int y, int z) {
        FastNoiseLite.Vector3 v3 = new FastNoiseLite.Vector3(x, y, z);
        blobWarpNoise.DomainWarp(v3);
        return blobWarpNoise.GetNoise(v3.x, (v3.y / 5f), v3.z);
    }

    ///////////////////////////////////////////////////////
    /////             Noise Configurations            /////
    ///////////////////////////////////////////////////////

    // FastNoiseLite configuration for pipe generation
    public static void configNoise(long worldSeed) {
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

    }
}
