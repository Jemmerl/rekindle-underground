package com.jemmerl.jemsgeology.geology.strata;

import com.jemmerl.jemsgeology.data.enums.DefaultSets;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.RegionNoise;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.StrataNoise;
import net.minecraft.world.ISeedReader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.jemmerl.jemsgeology.util.noise.GenerationNoise.RegionNoise.stoneFaultNoise;

public class StoneRegionBuilder {

    // Config values
    private static final int THICK_MIN = JemsGeoConfig.SERVER.thickMin.get();
    private static final int THICK_MAX = JemsGeoConfig.SERVER.thickMax.get();
    private static final int WARP_MIN = JemsGeoConfig.SERVER.warpMin.get();
    private static final int WARP_MAX = JemsGeoConfig.SERVER.warpMax.get();
    private static final int TILT_MIN = JemsGeoConfig.SERVER.tiltMin.get();
    private static final int TILT_MAX = JemsGeoConfig.SERVER.tiltMax.get();

    // Load the block picker for this world
    private static final BlockPicker blockPicker = new BlockPicker();

    // These variables are held to speed up generation times. If the region value is the same,
    // then these values will just be used instead of re-calculating them
    private static Boolean useCached = false;
    private static float cachedRegionNoise = 100f;
    private static float cachedRegionRandom1 = 0f;
    private static float cachedRegionRandom2 = 0f;
    private static float cachedRegionRandom3 = 0f;
    private static final int[] cachedLayerProperties1 = new int[]{0, 0, 0}; // Thickness, warp, tilt
    private static final int[] cachedLayerProperties2 = new int[]{0, 0, 0}; // Thickness, warp, tilt
    private static final int[] cachedLayerProperties3 = new int[]{0, 0, 0}; // Thickness, warp, tilt
    private static List<GeologyType> cachedGeologyTypeList1 = Collections.emptyList();
    private static List<GeologyType> cachedGeologyTypeList2 = Collections.emptyList();
    private static List<GeologyType> cachedGeologyTypeList3 = Collections.emptyList();


    ////////////////////////////////////////////////
    /////            Region Handler            /////
    ////////////////////////////////////////////////

    public static GeologyType getStoneStrataBlock(int x, int y, int z, ISeedReader reader, boolean contactMeta) {
        float regionNoise = RegionNoise.stoneRegionNoise(x, y, z);
        int regionVal = (int)(regionNoise * 3); //TODO gives region types possible rn (-2 to 2)

        // Use already previously generated values if in the same region
        useCached = (cachedRegionNoise == regionNoise);

        // Generate new random values if in a different region. Since the random is seeded by the region whenever
        // a new one is entered, re-entering a previous region will generate (and cache) the same random values again
        if (!useCached) {
            cachedRegionNoise = regionNoise;
            Random random = new Random((int)(regionNoise * 15003));
            cachedRegionRandom1 = random.nextFloat();
            cachedRegionRandom2 = random.nextFloat();
            cachedRegionRandom3 = random.nextFloat();
        }

        GeologyType geologyType;
        switch (regionVal) {
            case -2: // Young Flood Basalt
                geologyType = youngFloodBasaltGen(x, y, z);
                break;
            case 1: // One Layer
                geologyType = oneLayerGeneration(x, y, z, regionNoise);
                break;
            case 2: // Old Flood Basalt
                geologyType = oldFloodBasaltGen(x, y, z, regionNoise);
                break;
            default: // Two Layer (Case 0 and uncaught ints)
                geologyType = twoLayerGeneration(x, y, z, regionNoise);
                break;
        }

        return geologyType;
    }


    // Smooth or angled nonconformity? -> if (rand3 < 0.5) then angled

    // TODO Have faults be RNG, but do it globally across all layer (SAME WITH WARP)

    // TODO regions should be larger

    // TODO use 3 layer ONLY FOR HIGH ELEVATION, I.e. 3rd layer should only be visible with mountains
    // it is too cramped when all below y60

    // TODO Make functions for y-dividing noise, pass median y and range

    // TODO redo layer variable randomization. there must be a nicer way


    //////////////////////////////////////////////////
    /////            Block Generators            /////
    //////////////////////////////////////////////////

    // The description indicates what layers the generator sets, and if they are always (F) or sometimes (F?) faulted

    // Young Flood Basalt region generator
    private static GeologyType youngFloodBasaltGen(int xPos, int yPos, int zPos) {
        GeologyType geologyType;

        if (yPos <= (9 + (4 * StrataNoise.strataDividingNoise((xPos * 2), (yPos), (zPos * 2))))) { // Adds Diabase layer smoothly below y 7-15
            geologyType = GeologyType.DIABASE;
        } else { // Adds flood basalt layer above
            float noiseVal = StrataNoise.strataFloodBasaltNoise(xPos, yPos, zPos);
            if (noiseVal < -0.1f) {
                geologyType = GeologyType.GABBRO;
            } else {
                geologyType = GeologyType.BASALT;
            }
        }
        return geologyType;
    }

    // Old Flood Basalt region generator (surface layer of sedimentary rocks)
    private static GeologyType oldFloodBasaltGen(int xPos, int yPos, int zPos, float regionNoise) {
        GeologyType geologyType;
        float noiseVal;

        // Setup sedimentary layer
        if (!useCached) {
            List<String> geoTypesList = DefaultSets.getAllBlocks(DefaultSets.SED_SOIL, DefaultSets.SED_SANDY);
            cachedGeologyTypeList1 = BlockPicker.buildGeologyTypeList(geoTypesList);
        }

        // Sets faulted y for the older flood basalt
        // This is done independently, unlike adjustable layers, due to the unique generation
        int yFault = yPos + (int)(10f * stoneFaultNoise(xPos, yPos, zPos));
        if (yFault <= (9 + (3 * StrataNoise.strataDividingNoise((xPos * 2), (yFault), (zPos * 2))))) { // Adds Diabase layer smoothly below y 4-12
            geologyType = GeologyType.DIABASE;
        } else if (yPos <= (55 + (10 * StrataNoise.strataDividingNoise((xPos * 3), (yFault), (zPos * 3))))) { // Adds flood basalt layer below y 45-65
            noiseVal = StrataNoise.strataFloodBasaltNoise(xPos, yPos, zPos);
            if (noiseVal < -0.1f) {
                geologyType = GeologyType.GABBRO;
            } else {
                geologyType = GeologyType.BASALT;
            }
        } else { // Adds sedimentary strata above
            // TODO rewrite this section, needs to use older methods and make a sedimentary rock layer
            noiseVal = genAdjustableLayers(xPos, yPos, zPos, 1.5f, 20, 10, 30, 0, 1300);
            geologyType = BlockPicker.selectBlock(cachedGeologyTypeList1, regionNoise, noiseVal);
        }
        return geologyType;
    }

    // One layer region generator
    private static GeologyType oneLayerGeneration(int xPos, int yPos, int zPos, float regionNoise) {
        // Setup only layer
        if (!useCached) {
            cachedGeologyTypeList1 = blockPicker.getGeologyTypeList(blockPicker.getRandomPresetName(regionNoise, -1902));
            cachedLayerProperties1[0] = genProperty(35, 25, 40, THICK_MIN, THICK_MAX); // Thickness
            cachedLayerProperties1[1] = genProperty(45, 35, 20, WARP_MIN, WARP_MAX); // Warp
            cachedLayerProperties1[2] = genProperty(25, 50, 25, TILT_MIN, TILT_MAX); // Tilt
        }

        float noiseVal = genAdjustableLayers(xPos, yPos, zPos, 0.1f,
                cachedLayerProperties1[0], cachedLayerProperties1[1], cachedLayerProperties1[2], 20, -13370);
        return BlockPicker.selectBlock(cachedGeologyTypeList1, regionNoise, noiseVal);
    }


    // Two layer region generator
    private static GeologyType twoLayerGeneration(int xPos, int yPos, int zPos, float regionNoise) {
        GeologyType geologyType;

        // Refresh cached settings if different region
        if (!useCached) {
            // Setup bottom layer
            cachedGeologyTypeList2 = blockPicker.getGeologyTypeList(blockPicker.getRandomPresetName(regionNoise, 2902));
            cachedLayerProperties2[0] = genProperty(15, 65, 20, THICK_MIN, THICK_MAX); // Thickness
            cachedLayerProperties2[1] = genProperty(25, 45, 30, WARP_MIN, WARP_MAX); // Warp
            cachedLayerProperties2[2] = genProperty(35, 25, 40, TILT_MIN, TILT_MAX); // Tilt

            // Setup top layer
            cachedGeologyTypeList1 = blockPicker.getGeologyTypeList(blockPicker.getRandomPresetName(regionNoise, -6110));
            cachedLayerProperties1[0] = genProperty(25, 50, 25, THICK_MIN, THICK_MAX); // Thickness
            cachedLayerProperties1[1] = genProperty(60, 25, 15, WARP_MIN, WARP_MAX); // Warp
            cachedLayerProperties1[2] = genProperty(20, 35, 45, TILT_MIN, TILT_MAX); // Tilt
        }

        if (yPos < 35) { // BOTTOM LAYER // TODO TEMP DIVISION
            float noiseVal = genAdjustableLayers(xPos, yPos, zPos, 0.1f,
                    cachedLayerProperties2[0], cachedLayerProperties2[1], cachedLayerProperties2[2], 20, 86753);
            geologyType = BlockPicker.selectBlock(cachedGeologyTypeList2, regionNoise, noiseVal);

        } else { // TOP LAYER
            float noiseVal = genAdjustableLayers(xPos, yPos, zPos, 0.1f,
                    cachedLayerProperties1[0], cachedLayerProperties1[1], cachedLayerProperties1[2], 20, -9055);
            geologyType = BlockPicker.selectBlock(cachedGeologyTypeList1, regionNoise, noiseVal);

        }

        return geologyType;
    }


    // Three layer region generator
    private static GeologyType threeLayerGeneration(int xPos, int yPos, int zPos, float regionNoise) {
        GeologyType geologyType;

        // Refresh cached settings if different region
        if (!useCached) {
            // Setup bottom layer
            cachedGeologyTypeList3 = blockPicker.getGeologyTypeList(blockPicker.getRandomPresetName(regionNoise,  8675), 2);
            cachedLayerProperties3[0] = genProperty(45, 35, 20, THICK_MIN, THICK_MAX); // Thickness
            cachedLayerProperties3[1] = genProperty(35, 25, 40, WARP_MIN, WARP_MAX); // Warp
            cachedLayerProperties3[2] = genProperty(25, 50, 25, TILT_MIN, TILT_MAX); // Tilt

            // Setup middle layer
            cachedGeologyTypeList2 = blockPicker.getGeologyTypeList(blockPicker.getRandomPresetName(regionNoise, 3090), 2);
            cachedLayerProperties2[0] = genProperty(45, 40, 15, THICK_MIN, THICK_MAX); // Thickness
            cachedLayerProperties2[1] = genProperty(50, 20, 30, WARP_MIN, WARP_MAX); // Warp
            cachedLayerProperties2[2] = genProperty(15, 65, 20, TILT_MIN, TILT_MAX); // Tilt

            // Setup top layer
            cachedGeologyTypeList1 = blockPicker.getGeologyTypeList(blockPicker.getRandomPresetName(regionNoise, -1337), 2);
            cachedLayerProperties1[0] = genProperty(20, 45, 35, THICK_MIN, THICK_MAX); // Thickness
            cachedLayerProperties1[1] = genProperty(50, 40, 10, WARP_MIN, WARP_MAX); // Warp
            cachedLayerProperties1[2] = genProperty(30, 50, 20, TILT_MIN, TILT_MAX); // Tilt
        }

        if (yPos < 25) { // BOTTOM LAYER // TODO TEMP DIVISION
            float noiseVal = genAdjustableLayers(xPos, yPos, zPos, 0.1f,
                    cachedLayerProperties3[0], cachedLayerProperties3[1], cachedLayerProperties3[2], 20, 31301);
            geologyType = BlockPicker.selectBlock(cachedGeologyTypeList3, regionNoise, noiseVal);

        } else if (yPos < 50) { // MIDDLE LAYER // TODO TEMP DIVISION
            float noiseVal = genAdjustableLayers(xPos, yPos, zPos, 0.1f,
                    cachedLayerProperties2[0], cachedLayerProperties2[1], cachedLayerProperties2[2], 20, -13200);
            geologyType = BlockPicker.selectBlock(cachedGeologyTypeList2, regionNoise, noiseVal);

        } else { // TOP LAYER
            float noiseVal = genAdjustableLayers(xPos, yPos, zPos, 0.1f,
                    cachedLayerProperties1[0], cachedLayerProperties1[1], cachedLayerProperties1[2], 20, 13000);
            geologyType = BlockPicker.selectBlock(cachedGeologyTypeList1, regionNoise, noiseVal);

        }

        return geologyType;
    }


    /////////////////////////////////////////////////
    /////       Noise Generators and Util       /////
    /////////////////////////////////////////////////

    // Highly configurable layer generation based on input parameters
    private static float genAdjustableLayers(int x, int y, int z, float curvature, int thickness, int warp, int tilt, int fault, int seedShift) {
        // Curvature
        // Thickness should be self-explanatory
        // Warp determines how "buckled" the layers are
        // Tilt determines what general angle intensity the strata can form at.
        // Fault

        // Apply regional seed shift
        int shiftedSeed = RegionNoise.getRegionShiftedSeed(x, y, z) + seedShift;
        StrataNoise.setStrataSeed(shiftedSeed);
        StrataNoise.setWarpSeed(shiftedSeed + 13408);
        StrataNoise.setTiltSeed(shiftedSeed - 15605);

        // Bound input variables
        // curvature:(0,5]; all others:[0-100]
        // Regarding curvature -> 0.01f is flat, 0.05f-0.3f is slight, 0.5f-1f is normal, >1f is increasingly squished
        curvature = ((curvature <= 0) || (curvature > 5f)) ? 1f : curvature;
        thickness = ((thickness < 0) || (thickness > 100)) ? 25 : thickness;
        warp = ((warp < 0) || (warp > 100)) ? 10 : warp;
        tilt = ((tilt < 0) || (tilt > 100)) ? 30 : tilt;
        fault = ((fault < 0) || (fault > 100)) ? 20 : fault;

        // Applies tilt and then warp to the layers
        int shiftedY = y + (int)((tilt * tilt * tilt / 30) * StrataNoise.strataTiltNoise(x, z));
        shiftedY += (int)(warp * 0.3f * StrataNoise.strataWarpNoise(x * 15, z * 15));
        // Applies fault vertical shift
        shiftedY += (int)((fault / 2f) * stoneFaultNoise(x, y, z));

        // Note: Tilting the layers compresses layer height and curving them thickens them,
        // tilt and curvature values are used here to partially offset these effects
        int warpedY = (int) (shiftedY * ((350f - (tilt * 3f)) * (1.01f - (thickness / 100f))) * ((curvature / 5f) + 1));

        return StrataNoise.stoneStrataNoise(x * 15f * curvature, warpedY, z * 15f * curvature);
    }

    // Generate layer properties
    private static int genProperty(int c1, int c2, int c3, int MIN, int MAX) {
        return (int)UtilMethods.remap((c1 * cachedRegionRandom1 + c2 * cachedRegionRandom2 + c3 * cachedRegionRandom3),
                new float[]{0f, 100f}, new float[]{(float)MIN, (float)MAX});
    }


}

//////////////////////////////////////////////////
/////            Strata Templates            /////
//////////////////////////////////////////////////

/*
 *  These are "plug and play" blocks for coding various regions
 *  They have no function during operation, only for development work
 */

// Generate fault line vertical shifts
    /*
    if (faults) {
        yPos += (int)([MAX_DISPLACEMENT] * stoneFaultNoise(xPos, yPos, zPos));
    }

    OR

    int yFault = yPos + (int)([MAX_DISPLACEMENT] * stoneFaultNoise(xPos, yPos, zPos));
    */

// Generate multiple different strata sections. Multiply positions to conpress, divide to stretch
// Add or remove "else if" statements to generate different amounts of strata
    /*
    if (yPos <= ([MEDIAN_HEIGHT] + ([MAX_DEVIATION] * smoothDividingNoise.GetNoise((xPos * [HORIZ_TRANSFORM]), (yPos * [VERT_TRANSFORM]), (zPos * [HORIZ_TRANSFORM]))))) {
        // GENERATE BOTTOM STRATA
    } else if (yPos <= ([MEDIAN_HEIGHT] + ([MAX_DEVIATION] * smoothDividingNoise.GetNoise((xPos * [HORIZ_TRANSFORM]), (yPos * [VERT_TRANSFORM]), (zPos * [HORIZ_TRANSFORM]))))) {
        // GENERATE MIDDLE STRATA
    } else {
        // GENERATE TOP STRATA
    }

    // Just the vertical boundary condition
    // (yPos <= ([MEDIAN_HEIGHT] + ([MAX_DEVIATION] * smoothDividingNoise.GetNoise((xPos * [HORIZ_TRANSFORM]), (yPos * [VERT_TRANSFORM]), (zPos * [HORIZ_TRANSFORM])))))

    */

// Replace the (yPos <=) in a strata separation layer to angle the division. Tilt value should be [20, 100] for optimal results
    /*
        ((yPos + (int)(([TILT] * [TILT] * [TILT] / 30) * yTiltNoise.GetNoise(x / 30f, z / 30f)))
    */