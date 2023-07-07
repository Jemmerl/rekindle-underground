package com.jemmerl.jemsgeology.util.noise.GenerationNoise;

import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;

public class BlobNoise {

    private static FastNoiseLite blobNoise;

    // Config values loaded in here to allow for typecasting

    ////////////////////////////////////////////////////////////
    /////            Noise Generators and Tools            /////
    ////////////////////////////////////////////////////////////

    // Returns the noise value to warp the pipe radius
    public static float blobRadiusNoise(int x, int y, int z) {
        FastNoiseLite.Vector3 v3 = new FastNoiseLite.Vector3(x, y, z);
        blobNoise.DomainWarp(v3);
        return blobNoise.GetNoise(v3.x, (v3.y / 5f), v3.z);
    }

    ///////////////////////////////////////////////////////
    /////             Noise Configurations            /////
    ///////////////////////////////////////////////////////

    // FastNoiseLite configuration for pipe generation
    public static void configNoise(long worldSeed) {
        if (blobNoise == null) {
            blobNoise = new FastNoiseLite((int)worldSeed);
            blobNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            blobNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            blobNoise.SetFrequency(0.030f);
            //blobNoise.SetFractalType(FastNoiseLite.FractalType.None);

            blobNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
            blobNoise.SetFractalOctaves(3);
            blobNoise.SetFractalLacunarity(2.00f);
            blobNoise.SetFractalGain(0.80f);
        }

    }
}
