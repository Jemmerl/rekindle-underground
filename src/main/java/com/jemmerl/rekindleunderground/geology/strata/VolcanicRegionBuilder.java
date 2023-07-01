package com.jemmerl.rekindleunderground.geology.strata;

import com.jemmerl.rekindleunderground.data.enums.igneous.BatholithType;
import com.jemmerl.rekindleunderground.data.enums.igneous.DikeType;
import com.jemmerl.rekindleunderground.data.enums.igneous.IgnProvinceType;
import com.jemmerl.rekindleunderground.init.ModBlocks;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.RegionNoise;
import net.minecraft.block.BlockState;
import net.minecraft.world.ISeedReader;

import java.util.Random;

public class VolcanicRegionBuilder {

    // Note, LIPs are usually 1 million sq km, which translates to 1000 km on each side
    // Now that is a bit much for MC, but it shows these regions will be much larger than the "stone" ones!
    private static float cachedVolRegionVal;

    // Cached flood basalt properties
    private static IgnProvinceType cachedProvinceType;

    // Cached dike properties
    private static DikeType cachedDikeType;
    private static BlockState cachedDikeStoneOne;
    private static BlockState cachedDikeStoneTwo;
    private static BlockState cachedDikeStoneThree;

    // Cached batholith properties
    private static BatholithType cachedBatholithType;
    private static BlockState cachedBatholithStone;
    private static int cachedBatholithHeight;


    // Constants
    private static final int BATHOLITH_DEEP_MIN = 25;
    private static final int BATHOLITH_DEEP_MAX = 45;
    private static final int BATHOLITH_PROT_MIN = 75;
    private static final int BATHOLITH_PROT_MAX = 130; //-0.013x^2 + HEIGHT

    public static BlockState getVolcanicState(int x, int y, int z, ISeedReader seedReader) {

        // Start as null, as a null return means no volcanic state to be generated
        BlockState volcanicState = null;

        // Get volcanic region value
        float volRegionVal = RegionNoise.volcanicRegionNoise(x, z, true);


        ////////////////
        // PROPERTIES //
        ////////////////

        if (volRegionVal != cachedVolRegionVal) {
            cachedVolRegionVal = volRegionVal;
            Random rand = new Random((long)(cachedVolRegionVal * 100000));

            // Select igneous province type
            float rfloat = rand.nextFloat();
            if (rfloat > 0.97f) {
                cachedProvinceType = IgnProvinceType.ERODED; // 3%
            } else if (rfloat > 0.90f) {
                cachedProvinceType = IgnProvinceType.EXTRUDED; // 7%
            } else {
                cachedProvinceType = IgnProvinceType.NONE; // 90%
            }

            // Select batholith type
            rfloat = rand.nextFloat();
            switch (cachedProvinceType) {
                case NONE:
                    if (rfloat > 0.90f) {
                        cachedBatholithType = BatholithType.PROTRUDING; // 10% if no LIP
                        cachedBatholithHeight = rand.nextInt(BATHOLITH_PROT_MAX - BATHOLITH_PROT_MIN) + BATHOLITH_PROT_MIN;
                        break;
                    }
                case EXTRUDED:
                case ERODED:
                    if (rfloat > 0.65f) {
                        cachedBatholithType = BatholithType.DEEP; // 35% if LIP, 25% if none
                        cachedBatholithHeight = rand.nextInt(BATHOLITH_DEEP_MAX - BATHOLITH_DEEP_MIN) + BATHOLITH_DEEP_MIN;
                        break;
                    }
                case INTRUDED:
                default:
                    cachedBatholithType = BatholithType.NONE; // 65% (35% chance for a batholith, unless intruded LIP)
            }

            // Pick batholith stone
            if (cachedBatholithType != BatholithType.NONE) {
                // cachedBatholithStone = BlockPicker.getBatholithStone(volRegionVal);
                cachedBatholithStone = ModBlocks.GRANITE_STONE.get().getDefaultState(); // TODO TEMP
            }

            // Select dike type
            rfloat = rand.nextFloat();
            switch (cachedProvinceType) {
                case EXTRUDED:
                case ERODED:
                case INTRUDED:
                    cachedDikeType = DikeType.LINEAR_SWARM; // LIPs always have swarm feeder dikes
                    break;
                case NONE:
                default:
                    if (rfloat > 0.96f) {
                        cachedDikeType = DikeType.LINEAR_SWARM_ONE; // 4%
                    } else if (rfloat > 0.88f) {
                        cachedDikeType = DikeType.LINEAR_SWARM; // 8%
                    } else if (rfloat > 0.65f) {
                        cachedDikeType = DikeType.TWO; // 23%
                    } else if (rfloat > 0.35f) {
                        cachedDikeType = DikeType.ONE; // 30%
                    } else {
                        cachedDikeType = DikeType.NONE; // 35%
                    }
            }
        }


        ////////////////
        // GENERATION //
        ////////////////

        // Build batholith
        float heightPercent = 1 - (1 / RegionNoise.volcanicRegionNoise(x, z, false));
        switch (cachedBatholithType) {
            case PROTRUDING:

                break;
            case DEEP:

                break;
            case NONE:
            default:
                break;
        }


        // Build dikes



        // Build flood basalt
        switch (cachedProvinceType) {
            case INTRUDED:
                // if below wiggly y
                // and above wiggly y


                break;
            case ERODED:

                break;
            case EXTRUDED:

                break;
            case NONE:
            default:
                break;
        }

        return volcanicState;

//        if (true) {
//        } else if (false) {
//            return Blocks.AIR.getDefaultState();
//        } else {
//            return null;
//        }
//        return null;

    }
}
