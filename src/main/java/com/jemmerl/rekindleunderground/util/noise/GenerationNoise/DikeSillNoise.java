package com.jemmerl.rekindleunderground.util.noise.GenerationNoise;

import com.jemmerl.rekindleunderground.util.noise.FastNoiseLite;

import java.util.Random;

public class DikeSillNoise {

    private static FastNoiseLite dikeAreaNoise;

    private static long WORLD_SEED;

    // proof of concept, can be optimized
    public static float getShiftedDikeNoise(int x, int y, int z, int genY, int dikeEntrySeed, float dikeDensity) {
        float regNoise = RegionNoise.stoneRegionNoise(x, genY, z);
        Random rand = new Random((long) (regNoise * 329704 * dikeEntrySeed));
        dikeAreaNoise.SetSeed(rand.nextInt());

        float vShort = rand.nextInt(3) * rand.nextFloat() + 0.1f;
        int vLong = rand.nextInt(11) + 15 + (int)vShort;

        float rotAngleH = (float) Math.toRadians(rand.nextInt(361));
        float vX = (float)((x * Math.cos(rotAngleH)) - (z * Math.sin(rotAngleH)));
        float vZ = (float)((x * Math.sin(rotAngleH)) + (z * Math.cos(rotAngleH)));

        float tilt = rand.nextFloat() * 12 * y - 70;
        float rotAngleY = (float) Math.toRadians(rand.nextInt(361));
        float yX = (float)(tilt * Math.cos(rotAngleY));
        float yZ = (float)(tilt * Math.sin(rotAngleY));

        return dikeAreaNoise.GetNoise((vX * vShort + yX) * 2f, (vZ * vLong + yZ) * 2f);
    }



    ///////////////////////////////////////////////////////
    /////             Noise Configurations            /////
    ///////////////////////////////////////////////////////

    // FastNoiseLite configuration for dike-sill generation
    public static void configNoise(long worldSeed) {
        WORLD_SEED = worldSeed;

        // FastNoiseLite configuration for dike/sills
        if (dikeAreaNoise == null) {
            dikeAreaNoise = new FastNoiseLite((int)WORLD_SEED);
            dikeAreaNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            dikeAreaNoise.SetRotationType3D(FastNoiseLite.RotationType3D.None);
            dikeAreaNoise.SetFractalType(FastNoiseLite.FractalType.None);
            dikeAreaNoise.SetFrequency(0.001f);
            dikeAreaNoise.SetDomainWarpAmp(0f);
        }

    }

}
