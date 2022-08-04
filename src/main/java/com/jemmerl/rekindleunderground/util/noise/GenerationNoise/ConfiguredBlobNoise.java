package com.jemmerl.rekindleunderground.util.noise.GenerationNoise;

import com.jemmerl.rekindleunderground.util.noise.FastNoiseLite;

public class ConfiguredBlobNoise {

    private static FastNoiseLite pipeNoise;

    // Config values loaded in here to allow for typecasting

    ////////////////////////////////////////////////////////////
    /////            Noise Generators and Tools            /////
    ////////////////////////////////////////////////////////////

    // Returns the noise value to warp the pipe radius
    public static float blobRadiusNoise(int x, int y, int z) {
        FastNoiseLite.Vector3 v3 = new FastNoiseLite.Vector3(x, y, z);
        pipeNoise.DomainWarp(v3);
        return pipeNoise.GetNoise(v3.x, (v3.y / 5f), v3.z);
    }

    ///////////////////////////////////////////////////////
    /////             Noise Configurations            /////
    ///////////////////////////////////////////////////////

    // FastNoiseLite configuration for pipe generation
    public static void configNoise(long worldSeed) {
        if (pipeNoise == null) {
            pipeNoise = new FastNoiseLite((int)worldSeed);
            pipeNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            pipeNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            pipeNoise.SetFrequency(0.030f);
            //pipeNoise.SetFractalType(FastNoiseLite.FractalType.None);

            pipeNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
            pipeNoise.SetFractalOctaves(3);
            pipeNoise.SetFractalLacunarity(2.00f);
            pipeNoise.SetFractalGain(0.80f);
        }

    }
}
