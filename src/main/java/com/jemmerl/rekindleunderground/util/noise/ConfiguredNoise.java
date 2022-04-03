package com.jemmerl.rekindleunderground.util.noise;

import com.jemmerl.rekindleunderground.RKUndergroundConfig;

public class ConfiguredNoise {

    private static FastNoiseLite regionNoise;
    private static FastNoiseLite faultNoise;
    private static FastNoiseLite strataNoise;

    private static long WORLD_SEED;

    // Config values loaded in here to allow for typecasting
    private static final int REGION_SIZE = RKUndergroundConfig.COMMON.regionSize.get();
    private static final double REGIONAL_VARIATION = RKUndergroundConfig.COMMON.regionVariation.get();
    private static final int FAULT_SIZE = RKUndergroundConfig.COMMON.faultSize.get();
    private static final double FAULT_VARIATION = RKUndergroundConfig.COMMON.faultVariation.get();
    private static final double STRATA_DEPTH = RKUndergroundConfig.COMMON.strataDepth.get();


    //////////////////////////////////////////////////
    /////            Noise Generators            /////
    //////////////////////////////////////////////////

    // Returns the cell value for strata variation regions
    public static float stoneRegionNoise(int x, int y, int z) {
        FastNoiseLite.Vector3 v3 = new FastNoiseLite.Vector3(x, y, z);
        regionNoise.DomainWarp(v3);
        return regionNoise.GetNoise(v3.x, (v3.y / 5f), v3.z);
    }

    // Returns the plate value for creating faults
    public static float stoneFaultNoise(int x, int y, int z) {
        return faultNoise.GetNoise(x, (y / 10f), z);
    }

    // Returns the strata layer noise value for determining rock type
    public static float stoneStrataNoise(int x, int y, int z) {
        int faultShiftedY = y + (int)((50 * FAULT_VARIATION) * stoneFaultNoise(x, y, z));
        int shiftedSeed = (int)(WORLD_SEED + (stoneRegionNoise(x, y, z) * (10000 * REGIONAL_VARIATION)));
        strataNoise.SetSeed(shiftedSeed);
        return strataNoise.GetNoise(x * (15f * 1), faultShiftedY * (400.0f * (1f - (float)STRATA_DEPTH)), z * (15f * 1)); // include a strata size factor?
    }


    ///////////////////////////////////////////////////////
    /////             Noise Configurations            /////
    ///////////////////////////////////////////////////////

    // FastNoiseLite configuration for strata generation
    public static void configNoise(long worldSeed) {
        WORLD_SEED = worldSeed; // Sets the world seed for access in noise generation

        // FastNoiseLite configuration for regions
        if (regionNoise == null) {
            regionNoise = new FastNoiseLite((int)WORLD_SEED);
            regionNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
            regionNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
            regionNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
            regionNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            regionNoise.SetFrequency(0.06f / (float)Math.sqrt(REGION_SIZE));
            regionNoise.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2Reduced);
            regionNoise.SetDomainWarpFrequency(0.01f);
            regionNoise.SetDomainWarpAmp(75f);
        }

        // FastNoiseLite configuration for fault lines
        if (faultNoise == null) {
            faultNoise = new FastNoiseLite((int)(WORLD_SEED)+15000);
            faultNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
            faultNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
            faultNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.EuclideanSq);
            faultNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            faultNoise.SetFractalType(FastNoiseLite.FractalType.None);
            faultNoise.SetFrequency(0.065f / (float)Math.sqrt(FAULT_SIZE));
            faultNoise.SetCellularJitter(2f);
        }

        // FastNoiseLite configuration for strata
        if (strataNoise == null) {
            strataNoise = new FastNoiseLite((int)(WORLD_SEED)+22493);
            strataNoise.SetNoiseType(FastNoiseLite.NoiseType.Value);
            strataNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            strataNoise.SetFractalType(FastNoiseLite.FractalType.None);
            strataNoise.SetFrequency(0.0001f);
        }
    }

}
