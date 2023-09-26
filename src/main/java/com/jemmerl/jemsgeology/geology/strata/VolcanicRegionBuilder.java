package com.jemmerl.jemsgeology.geology.strata;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.igneous.BatholithType;
import com.jemmerl.jemsgeology.data.enums.igneous.DikeType;
import com.jemmerl.jemsgeology.data.enums.igneous.IgnProvinceType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.geology.GeoWrapper;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobWarpNoise;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.RegionNoise;
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
    private static GeologyType cachedDikeStoneOne; // Stone the first dike will generate with (if applicable)
    private static GeologyType cachedDikeStoneTwo; // Stone the second dike will generate with (if applicable)
    private static GeologyType cachedDikeStoneThree; // Stone the third dike will generate with (if applicable)

    // Cached batholith properties
    private static BatholithType cachedBatholithType;
    private static GeologyType cachedBatholithStone; // Stone the batholith will generate with (if applicable)
    private static int cachedBatholithHeight; // Max height of the batholith

    // Constants
    private static final int BATHOLITH_DEEP_MIN = 25;
    private static final int BATHOLITH_DEEP_MAX = 50;
    private static final int BATHOLITH_PROT_MIN = 65;
    private static final int BATHOLITH_PROT_MAX = 130;

    public static GeoWrapper getVolcanicBlock(int x, int y, int z, ISeedReader seedReader) {

        // Start as null, as a null GeoType return means no volcanic block to be generated
        GeoWrapper volcanicWrapper = new GeoWrapper(null, OreType.NONE, GradeType.NONE);

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
                    // Config to disable batholiths
                    if (!JemsGeoConfig.SERVER.gen_batholiths.get()) {
                        cachedBatholithType = BatholithType.NONE;
                        break;
                    }

                    if (rfloat > 0.90f) {
                        cachedBatholithType = BatholithType.PROTRUDING; // 10%
                        cachedBatholithHeight = rand.nextInt(BATHOLITH_PROT_MAX - BATHOLITH_PROT_MIN) + BATHOLITH_PROT_MIN;
                        break;
                    } else if (rfloat > 0.65f) {
                        cachedBatholithType = BatholithType.DEEP; // 25%
                        cachedBatholithHeight = rand.nextInt(BATHOLITH_DEEP_MAX - BATHOLITH_DEEP_MIN) + BATHOLITH_DEEP_MIN;
                        break;
                    }
                case EXTRUDED:
                case ERODED:
                case INTRUDED:
                default:
                    cachedBatholithType = BatholithType.NONE; // 75% (35% chance for a batholith if no FB)
            }

            // Pick batholith stone
            rfloat = rand.nextFloat();
            if (cachedBatholithType.equals(BatholithType.NONE)) {
                cachedBatholithStone = GeologyType.SANDSTONE; // Debug, should not appear!
            } else {
                // Most batholiths are mixes of various felsic and intermediate plutonic rocks
                // To save on computing cost (might experiment later), they will be generated as homogenous masses
                // Gabbro is sometimes a small component of batholiths, which is represented here as a very
                // rare chance of generation. Will also provide a non-oceanic source of gabbro for building
                if (rfloat > 0.65f) {
                    cachedBatholithStone = GeologyType.GRANITE; // 35% -- felsic
                } else if (rfloat > 0.41f) {
                    cachedBatholithStone = GeologyType.SYENITE; // 24% -- felsic
                } else if (rfloat > 0.17f) {
                    cachedBatholithStone = GeologyType.GRANODIORITE; // 24% -- intermediate-felsic
                } else if (rfloat > 0.02f) {
                    cachedBatholithStone = GeologyType.DIORITE; // 15% -- intermediate
                } else {
                    cachedBatholithStone = GeologyType.GABBRO; // 2% -- mafic
                }
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
                    } else if (rfloat > 0.90f) {
                        cachedDikeType = DikeType.LINEAR_SWARM; // 6%
                    } else if (rfloat > 0.65f) {
                        cachedDikeType = DikeType.TWO; // 25%
                    } else if (rfloat > 0.35f) {
                        cachedDikeType = DikeType.ONE; // 30%
                    } else {
                        cachedDikeType = DikeType.NONE; // 35% (65% chance of some dike)
                    }
            }
        }


        ////////////////
        // GENERATION //
        ////////////////

        // Builder exits with return if a block is placed, given the appearance of the following:
        // Flood basalts on top, followed by batholiths, with both overlaying on dikes.
        // This is subject to change.

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

        // Build batholith
        switch (cachedBatholithType) {
            case PROTRUDING:
            case DEEP:
                float percentContactMeta = -RegionNoise.volcanicRegionNoise(x, z, false);
                float percentBatholith = (float)Math.pow(percentContactMeta, 1.5);
                if ((y + 20) <= (((cachedBatholithHeight + 20) * percentBatholith) + (5 * BlobWarpNoise.blobWarpRadiusNoise((x * 4), y, (z * 4))))) {
                    // The batholith itself
                    volcanicWrapper.setGeologyType(cachedBatholithStone);
                    return volcanicWrapper;
                } else if ((y + 20) <= ((cachedBatholithHeight + 30) * percentContactMeta)) {
                    // Region of contact metamorphism, null OreType signals contact metamorphism
                    volcanicWrapper.setOreType(null);
                }

                // Debug
                if (JemsGeoConfig.SERVER.debug_batholiths.get() && (percentBatholith > 0.605f) && (percentBatholith < 0.61f)) {
                    JemsGeology.getInstance().LOGGER.info(
                            "Generating batholith with type {} and max height {} at: ({}, {})",
                            cachedBatholithType, cachedBatholithHeight, x, z);
                }

                break;
            case NONE:
            default:
                break;
        }

        // Build dikes
        switch (cachedDikeType) {
            default:
                break;
        }

        return volcanicWrapper;
    }
}

// Batholith Learnings


// Metamorphism Learnings