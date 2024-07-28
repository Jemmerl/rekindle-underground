package com.jemmerl.jemsgeology.geology.strata;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.igneous.BatholithType;
import com.jemmerl.jemsgeology.data.enums.igneous.IgnBodyType;
import com.jemmerl.jemsgeology.data.enums.igneous.IgnProvinceType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.geology.ChunkReader;
import com.jemmerl.jemsgeology.geology.GeoWrapper;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobWarpNoise;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.RegionNoise;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class VolcanicRegionBuilder {

    // Note, LIPs are usually 1 million sq km, which translates to 1000 km on each side
    // Now that is a bit much for MC, but it shows these regions will be much larger than the "stone" ones!
    private static float cachedVolRegionVal;

    // Cached flood basalt properties
    private static IgnProvinceType cachedProvinceType;

    // Cached dike properties
    private static IgnBodyType cachedDikeType;
    private static GeologyType cachedDikeStoneOne; // Stone the first dike will generate with (if applicable)
    private static GeologyType cachedDikeStoneTwo; // Stone the second dike will generate with (if applicable)
    private static GeologyType cachedDikeStoneThree; // Stone the third dike will generate with (if applicable)

    // Cached batholith properties


    // Constants



    // Batholith properties
    private static final int BATHOLITH_DEEP_MIN = 25;
    private static final int BATHOLITH_DEEP_MAX = 50;
    private static final int BATHOLITH_PROT_MIN = 65;
    private static final int BATHOLITH_PROT_MAX = 130;

    private static BatholithType cachedBathType;
    private static GeologyType cachedBathStone; // Stone the batholith will generate with (if applicable)
    private static int cachedBathHeight; // Max height of the batholith
    private static float cachedBathXMult;
    private static float cachedBathZMult;
    private static float cachedBathHeightMult;


    // do some dikes and laccoliths and sills
    // batholith time
    // do some more dikes and laccoliths and sills

    //todo have stark separations where eroded batholiths started and stopped, maybe do with a sharp jump of
    // deform heights over a boundary?

    //todo add xenoliths within batholith by a bubbly noise preserving overlying strata randomly (read 5-10 strata blocks up)
    // can be done by not placing a (eg granite) bath block at the spot, but adding a NEGATIVE deform to the spot


    public static void getVolcanicBlocks(GeoWrapper[][][] volcanicWrappers, int[][][] deformHeights, ChunkReader chunkReader, BlockPos cornerPos) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int posX = cornerPos.getX() + x;
                int posZ = cornerPos.getZ() + z;

                ////////////////
                // PROPERTIES //
                ////////////////

                // Get current volcanic region value
                // This, and related properties, do not vary by Y value
                float volRegionVal = RegionNoise.volcanicRegionNoise(posX, posZ, true);

                // Check if the volcanic region value has changed from the previously cached properties, update if so
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
                                cachedBathType = BatholithType.NONE;
                                break;
                            }

                            if (rfloat > 0.60f) { //0.90
                                cachedBathType = BatholithType.PROTRUDING; // 10%
                                cachedBathHeight = rand.nextInt(BATHOLITH_PROT_MAX - BATHOLITH_PROT_MIN) + BATHOLITH_PROT_MIN;
                                //TODO TEMP DUPE FROM DEEP
                                cachedBathXMult = rand.nextFloat() * 0.4f + 0.1f;
                                cachedBathZMult = rand.nextFloat() * 0.4f + 0.1f;
                                cachedBathHeightMult = rand.nextInt(41);
                                break;
                            } else if (rfloat > 0.35f) { //0.65
                                cachedBathType = BatholithType.DEEP; // 25%
                                cachedBathHeight = rand.nextInt(BATHOLITH_DEEP_MAX - BATHOLITH_DEEP_MIN) + BATHOLITH_DEEP_MIN;
                                cachedBathXMult = rand.nextFloat() * 0.4f + 0.1f;
                                cachedBathZMult = rand.nextFloat() * 0.4f + 0.1f;
                                cachedBathHeightMult = rand.nextInt(41);
                                break;
                            }
                        case EXTRUDED:
                        case ERODED:
                        case INTRUDED:
                        default:
                            cachedBathType = BatholithType.NONE; // 75% (35% chance for a batholith if no FB)
                    }

                    // Pick batholith stone
                    rfloat = rand.nextFloat();
                    if (cachedBathType.equals(BatholithType.NONE)) {
                        cachedBathStone = GeologyType.SANDSTONE; // Debug, should not appear!
                    } else {
                        // Most batholiths are mixes of various felsic and intermediate plutonic rocks
                        // To save on computing cost (might experiment later), they will be generated as homogenous masses
                        // Gabbro is sometimes a small component of batholiths, NEVER a major by any means,
                        // which is represented here as a very rare chance of generation.
                        // Will also provide a non-oceanic source of gabbro for building
                        if (rfloat > 0.40f) {
                            cachedBathStone = GeologyType.GRANITE; // 60% -- felsic
                        } else if (rfloat > 0.20f) {
                            cachedBathStone = GeologyType.GRANODIORITE; // 20% -- intermediate-felsic
                        } else if (rfloat > 0.05f) {
                            cachedBathStone = GeologyType.DIORITE; // 15% -- intermediate
                        } else {
                            cachedBathStone = GeologyType.GABBRO; // 5% -- mafic
                            // TODO add anorthosite? major batholith rock (see Archean anorthosites as a non-batholith source)
                        }
                    }


                    // Select dike type
                    rfloat = rand.nextFloat();
                    switch (cachedProvinceType) {
                        case EXTRUDED:
                        case ERODED:
                        case INTRUDED:
                            cachedDikeType = IgnBodyType.NONE; // LIPs always have swarm feeder dikes
                            break;
                        case NONE:
                        default:
                            if (rfloat > 0.96f) {
                                cachedDikeType = IgnBodyType.NONE; // 4%
                            } else if (rfloat > 0.90f) {
                                cachedDikeType = IgnBodyType.NONE; // 6%
                            } else if (rfloat > 0.65f) {
                                cachedDikeType = IgnBodyType.NONE; // 25%
                            } else if (rfloat > 0.35f) {
                                cachedDikeType = IgnBodyType.NONE; // 30%
                            } else {
                                cachedDikeType = IgnBodyType.NONE; // 35% (65% chance of some dike)
                            }
                    }
                }


                ////////////////
                // GENERATION //
                ////////////////

                int bathTop = 0;
                // y=-10 isnt so needed for lower deform because the bedrock layer hides lowest levels anyway
                for (int y = 0; y < chunkReader.getMaxHeight(); y++) {
                    // Start as null, as a null GeoType return means no volcanic block to be generated
                    GeoWrapper volcanicWrapper = new GeoWrapper(null, OreType.NONE, GradeType.NONE);

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
                    switch (cachedBathType) {
                        case PROTRUDING:
                            // Massive batholith that rapidly reaches surface
                        case DEEP:
                            // Low-lying batholith deep underground
                            float percentContactMeta = -RegionNoise.volcanicRegionNoise(posX, posZ, false);
                            float percentBatholith = (float)Math.pow(percentContactMeta, 1.5);
                            int warpAddition = (int)(cachedBathHeightMult * BlobWarpNoise.blobWarpRadiusNoise((posX * cachedBathXMult), y, (posZ * cachedBathZMult)));
                            if ((y + 20) <= (((cachedBathHeight + 20) * percentBatholith) + warpAddition)) {
                                // The batholith itself
                                volcanicWrapper.setGeologyType(cachedBathStone);
                                volcanicWrappers[x][y][z] = volcanicWrapper; // Acts as a return does in the old way
                                bathTop = y; // Y starts at 0, so last update is the highest batholith point
                                // /tp Dev 2 76 -409 2050211422615214186
                                // tp Dev 61 70 -428
                                // tp Dev 211 63 -392
                                continue;
                            } else {
                                if ((y + 20) <= (((cachedBathHeight + 30) * percentContactMeta) + warpAddition)) {
                                    // Region of contact metamorphism, null OreType signals contact metamorphism
                                    volcanicWrapper.setOreType(null); //todo replace with contact meta bool feild and mayb contact ign (felsic) type for ore reason
                                }
                                //deformHeights[x][y][z] = Math.max(0, (y - (int)Math.floor((y-bathTop) * (y-bathTop) * 0.1))); // trails off too fast, extend compress
                                //deformHeights[x][y][z] = Math.max(0, (y - (int)Math.floor(3.8*((10f / (-y+bathTop-4)) + 3))));
                                int wildDeform = (int) Math.round(Math.exp((-0.12 + (0.0008*bathTop))*(y-bathTop)+2.3)); //10
                                deformHeights[x][y][z] = Math.max(0, Math.min(10, wildDeform));
                            }

                            // Debug
                            if (JemsGeoConfig.SERVER.debug_batholiths.get() && (percentBatholith > 0.605f) && (percentBatholith < 0.61f)) {
                                JemsGeology.getInstance().LOGGER.info(
                                        "Generating batholith with type {} and max height {} at: ({}, {})",
                                        cachedBathType, cachedBathHeight, posX, posZ);
                            }

                            break;
                        case NONE:
                        default:
                            break;
                    }

                    volcanicWrappers[x][y][z] = volcanicWrapper; // Acts as a return does in the old way
                }
            }
        }


    }


}

// Batholith Learnings


// Metamorphism Learnings