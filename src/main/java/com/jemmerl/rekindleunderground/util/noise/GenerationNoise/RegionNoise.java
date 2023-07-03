package com.jemmerl.rekindleunderground.util.noise.GenerationNoise;

import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.util.noise.FastNoiseLite;

public class RegionNoise {

    private static FastNoiseLite stoneNoise;
    private static FastNoiseLite faultNoise;
    private static FastNoiseLite volcanicNoise;

    private static long WORLD_SEED;

    // Config values loaded in here to allow for typecasting
    private static final int REGION_SIZE = RKUndergroundConfig.COMMON.regionSize.get();
    private static final double REGIONAL_VARIATION = RKUndergroundConfig.COMMON.regionVariation.get();
    private static final int FAULT_SIZE = RKUndergroundConfig.COMMON.faultSize.get();


    ////////////////////////////////////////////////////////////
    /////            Noise Generators and Tools            /////
    ////////////////////////////////////////////////////////////

    // Returns the cell value for stone strata variation regions
    public static float stoneRegionNoise(int x, int y, int z) {
        FastNoiseLite.Vector3 v3 = new FastNoiseLite.Vector3(x, y, z);
        stoneNoise.DomainWarp(v3);
        return stoneNoise.GetNoise(v3.x, (v3.y / 5f), v3.z);
    }

    // Returns a shifted seed based on the region value and variation config
    public static int getRegionShiftedSeed(int x, int y, int z) {
        return (int)(WORLD_SEED + (stoneRegionNoise(x, y, z) * (10001 * REGIONAL_VARIATION)));
    }

    // Returns the plate value for creating faults
    public static float stoneFaultNoise(int x, int y, int z) {
        return faultNoise.GetNoise(x, (y / 10f), z);
    }

    // Returns the cell value or cell distance for volcanic regions
    public static float volcanicRegionNoise(int x, int z, boolean cellValue) {
        FastNoiseLite.Vector2 v2 = new FastNoiseLite.Vector2(x, z);
        if (cellValue) {
            volcanicNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
        } else {
            volcanicNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance2Div);
        }
        volcanicNoise.DomainWarp(v2);
        return volcanicNoise.GetNoise(x, z);
    }

    ///////////////////////////////////////////////////////
    /////             Noise Configurations            /////
    ///////////////////////////////////////////////////////

    // FastNoiseLite configuration for strata generation
    public static void configNoise(long worldSeed) {
        WORLD_SEED = worldSeed; // Sets the world seed for access in noise generation

        // FastNoiseLite configuration for stone regions
        if (stoneNoise == null) {
            stoneNoise = new FastNoiseLite((int)worldSeed);
            stoneNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
            stoneNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
            stoneNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
            stoneNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            stoneNoise.SetFrequency(0.06f / (float)Math.sqrt(REGION_SIZE));
            stoneNoise.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2Reduced);
            stoneNoise.SetDomainWarpFrequency(0.01f);
            stoneNoise.SetDomainWarpAmp(75f);
        }

        // FastNoiseLite configuration for fault lines
        if (faultNoise == null) {
            faultNoise = new FastNoiseLite((int)(worldSeed) + 15000);
            faultNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
            faultNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
            faultNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.EuclideanSq);
            faultNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            faultNoise.SetFractalType(FastNoiseLite.FractalType.None);
            faultNoise.SetFrequency(0.065f / (float)Math.sqrt(FAULT_SIZE));
            faultNoise.SetCellularJitter(2f);
        }

        // FastNoiseLite configuration for volcanic regions
        if (volcanicNoise == null) {
            volcanicNoise = new FastNoiseLite((int)(worldSeed) - 76321);
            volcanicNoise.SetRotationType3D(FastNoiseLite.RotationType3D.None);
            volcanicNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
            volcanicNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
            volcanicNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Euclidean);
            volcanicNoise.SetCellularJitter(0.70f);
            volcanicNoise.SetFractalType(FastNoiseLite.FractalType.None);
            volcanicNoise.SetFrequency(0.030f / (float)Math.sqrt(REGION_SIZE));
            volcanicNoise.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2Reduced);
            volcanicNoise.SetDomainWarpFrequency(0.025f);
            volcanicNoise.SetDomainWarpAmp(15f);
        }


    }
}
