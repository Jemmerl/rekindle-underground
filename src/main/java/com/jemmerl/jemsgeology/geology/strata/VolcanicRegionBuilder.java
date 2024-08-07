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
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobNoise;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.RegionNoise;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Random;

public class VolcanicRegionBuilder {

    // Note, LIPs are usually 1 million sq km, which translates to 1000 km on each side
    // Now that is a bit much for MC, but it shows these regions will be much larger than the "stone" ones!
    private static float cachedVolRegionVal;

    // Cached dike properties
    private static IgnBodyType cachedDikeType;
    private static GeologyType cachedDikeStoneOne; // Stone the first dike will generate with (if applicable)
    private static GeologyType cachedDikeStoneTwo; // Stone the second dike will generate with (if applicable)
    private static GeologyType cachedDikeStoneThree; // Stone the third dike will generate with (if applicable)

    // Cached batholith properties


    // Constants



    // Batholith properties
    private static final int BATHOLITH_DEEP_MIN = 25;//25
    private static final int BATHOLITH_DEEP_MAX = 50;//50
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

    //todo occasionally batholiths have different stones for the same seed- is a bug.

    public static void getVolcanicBlocks(GeoWrapper[][][] volcanicWrappers, int[][][] deformHeights, ChunkReader chunkReader, BlockPos cornerPos) {

        // do other stuff
        //TODO dont allow batholiths and flood basalts together?, one or the other
        generateBatholith(volcanicWrappers, deformHeights, chunkReader, cornerPos);


    }


    private static void generateBatholith(GeoWrapper[][][] volcanicWrappers, int[][][] deformHeights, ChunkReader chunkReader, BlockPos cornerPos) {
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

                    // Select batholith type
                    float rfloat = rand.nextFloat();
                    // Config to disable batholiths
                    if (!JemsGeoConfig.SERVER.gen_batholiths.get()) {
                        cachedBathType = BatholithType.NONE;
                        continue;
                    }

                    if (rfloat > 0.9f) { //0.90
                        cachedBathType = BatholithType.PROTRUDING; // 10%
                        cachedBathHeight = rand.nextInt(BATHOLITH_PROT_MAX - BATHOLITH_PROT_MIN) + BATHOLITH_PROT_MIN;
                        cachedBathXMult = rand.nextFloat() * 0.5f + 0.1f;
                        cachedBathZMult = rand.nextFloat() * 0.5f + 0.1f;
                        cachedBathHeightMult = rand.nextInt(61);

                    } else if (rfloat > 0.65f) { //0.65
                        cachedBathType = BatholithType.DEEP; // 25%
                        cachedBathHeight = rand.nextInt(BATHOLITH_DEEP_MAX - BATHOLITH_DEEP_MIN) + BATHOLITH_DEEP_MIN;
                        cachedBathXMult = rand.nextFloat() * 0.4f + 0.1f;
                        cachedBathZMult = rand.nextFloat() * 0.4f + 0.1f;
                        cachedBathHeightMult = rand.nextInt(41);

                    } else {
                        cachedBathType = BatholithType.NONE; // 65%
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
                }


                ////////////////
                // GENERATION //
                ////////////////

                if (cachedBathType == BatholithType.NONE) {
                    continue;
                }

                float percentBatholith = -RegionNoise.volcanicRegionNoise(posX, posZ, false)*0.85f;
                float bathWarp = cachedBathHeightMult * BlobNoise.blobWarpRadiusNoise((posX * cachedBathXMult), 0, (posZ * cachedBathZMult));
                float bathTop = ((cachedBathHeight + 20) * percentBatholith) + bathWarp;

                for (int y = 0; y < chunkReader.getMaxHeight(); y++) {
                    int yShift = y + 10; // Kinda just moving it down to help balance warp overshooting the max height

                    if (yShift <= bathTop) {
                        // Country rock xenoliths
                        float xenoPercent = 0.0065f*(yShift-bathTop+25);
                        if ((-0.55f+xenoPercent) > BlobNoise.getXenolithNoise(posX, y, posZ)) {
                            volcanicWrappers[x][y][z].setOreType(null); // Contact metamorph any strata engulfed, ofc
                            volcanicWrappers[x][y][z].setGeologyType(null);
                            deformHeights[x][y][z] = batholithDeform(yShift, bathTop); // Matches with strata if on edge of batholith
                            continue;
                        }

                        // Regular batholith stone
                        volcanicWrappers[x][y][z].setGeologyType(cachedBathStone);
                        continue;

                    } else {
                        // Calculate contact metamorphism
                        float metaWarp = (cachedBathHeightMult) * Math.abs(BlobNoise.blobWarpRadiusNoise(
                                ((posX+1000) * cachedBathXMult), y, ((posZ-1000) * cachedBathZMult)));
                        float metaHeight = bathTop + 13 + metaWarp;
                        if (yShift <= metaHeight) {
                            // Region of contact metamorphism, null OreType signals contact metamorphism
                            volcanicWrappers[x][y][z].setOreType(null); //todo replace with contact meta bool feild and mayb contact ign (like felsic) type for ore reason
                        }

                        // Deform overlaying strata
                        deformHeights[x][y][z] = batholithDeform(yShift, bathTop);
                    }

                    // Debug
                    if (JemsGeoConfig.SERVER.debug_batholiths.get() && (percentBatholith > 0.605f) && (percentBatholith < 0.61f)) {
                        JemsGeology.getInstance().LOGGER.info(
                                "Generating batholith with type {} and max height {} at: ({}, {})",
                                cachedBathType, cachedBathHeight, posX, posZ);
                    }
                }
            }
        }
    }
// fill ~-18 ~-10 ~-18 ~18 ~10 ~18 air replace jemsgeology:diorite_stone

    // tp Dev -1141 80 -116

    private static int batholithDeform(int yShift, float bathTop) {
        int wildDeform = (int) Math.round(Math.exp(-0.05*(yShift-bathTop-40)+0.8))-1; //15
        return Math.max(0, Math.min(15, wildDeform));
    }
}