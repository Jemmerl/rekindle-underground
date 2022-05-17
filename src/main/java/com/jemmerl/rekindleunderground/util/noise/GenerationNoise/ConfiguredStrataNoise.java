package com.jemmerl.rekindleunderground.util.noise.GenerationNoise;

import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.util.noise.FastNoiseLite;
import com.jemmerl.rekindleunderground.world.feature.stonegenutil.BlockPicker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredRegionNoise.stoneFaultNoise;

public class ConfiguredStrataNoise {

    // Configured noise
    private static FastNoiseLite strataNoise; // Used for general adjustable strata generation
    private static FastNoiseLite yWarpNoise; // Used to add warping to adjustable strata
    private static FastNoiseLite yTiltNoise; // Used to add slant to adjustable strata
    private static FastNoiseLite smoothDividingNoise; // Used to determine the bounds of an inserted independent, wavy strata layer
    private static FastNoiseLite floodBasaltNoise; // Used to generate basalt/gabbro layers for flood basalts
    // TODO - add various hornfels for contact metamorphism

    private static long WORLD_SEED;

    // These variables are held to speed up load times. If the region value is the same,
    // then these values will just be re-used instead of
    private static float cachedRegionVal = 0f;
    private static List<BlockState> cachedBlockStateList = Collections.emptyList();
    private static Boolean useCached = false;
    private static int cachedRegionRandom1 = 0;
    private static int cachedRegionRandom2 = 0;
    private static int cachedRegionRandom3 = 0;

    ////////////////////////////////////////////////
    /////            Region Handler            /////
    ////////////////////////////////////////////////

    public static BlockState getStoneStrataBlock(int x, int y, int z) {
        float regionNoise = ConfiguredRegionNoise.stoneRegionNoise(x, y, z);
        //int regionVal = (int)(regionNoise * 5); //TODO 5 gives 11 region types possible rn (-5 to 5)
        //useCached = (cachedRegionVal == regionVal);
        if (!useCached) {
            cachedRegionRandom1 = 1;
            cachedRegionRandom2 = 1;
            cachedRegionRandom3 = 1;
        }
        int regionVal;

        // TEMP
        if (ConfiguredRegionNoise.stoneRegionNoise(x, y, z) < 0) {
            regionVal = -5;
        } else {
            regionVal = 5;
        }

        // TODO re-implement fault shifts

        BlockState blockState;
        switch (regionVal) {
            case -5: // Young Flood Basalt
                blockState = youngFloodBasaltGen(x, y, z);
                break;
            case -4:
                //
                //break;
            case -3:
                //break;
            case -2:
                //break;
            case -1:
                //break;
            case 0:
                //break;
            case 1:
                //break;
            case 2:
                //break;
            case 3:
                //break;
            case 4:
                //break;
            case 5: // Old Flood Basalt
                blockState = oldFloodBasaltGen(x, y, z, regionNoise);
                break;

            default: blockState = Blocks.IRON_BLOCK.getDefaultState();
                break;
        }
        return blockState;
    }


    //////////////////////////////////////////////////
    /////            Block Generators            /////
    //////////////////////////////////////////////////

    // The description indicates what layers the generator sets, and if they are always (F) or sometimes (F?) faulted

    // Gets the block state for a new Flood Basalt region at the specific location (Flood - Diabase)
    private static BlockState youngFloodBasaltGen(int xPos, int yPos, int zPos) {
        BlockState state;
        float noiseVal;

        if (yPos <= (9 + (4 * smoothDividingNoise.GetNoise((xPos * 2), (yPos), (zPos * 2))))) { // Adds Diabase layer smoothly below y 7-15
            state = ModBlocks.DIABASE.get().getDefaultState();
        } else { // Adds flood basalt layer above
            noiseVal = floodBasaltNoise.GetNoise((xPos / 5f), (yPos * 2f), (zPos / 5f));
            if (noiseVal < -0.1f) {
                state = ModBlocks.GABBRO.get().getDefaultState();
            } else {
                state = ModBlocks.BASALT.get().getDefaultState();
            }
        }
        return state;
    }

    // Gets the block state for an old Flood Basalt region at the specific location (Sed - Flood (F) - Diabase)
    private static BlockState oldFloodBasaltGen(int xPos, int yPos, int zPos, float regionNoise) {
        BlockState state;
        float noiseVal;

        int yFault = yPos + (int)(10f * stoneFaultNoise(xPos, yPos, zPos)); // Sets faulted y for older layers
        if (yFault <= (9 + (3 * smoothDividingNoise.GetNoise((xPos * 2), (yFault), (zPos * 2))))) { // Adds Diabase layer smoothly below y 4-12
            state = ModBlocks.DIABASE.get().getDefaultState();
        } else if (yPos <= (55 + (10 * smoothDividingNoise.GetNoise((xPos * 3), (yPos), (zPos * 3))))) { // Adds flood basalt layer below y 45-65
            noiseVal = floodBasaltNoise.GetNoise((xPos / 5f), (yPos * 2f), (zPos / 5f));
            if (noiseVal < -0.1f) {
                state = ModBlocks.GABBRO.get().getDefaultState();
            } else {
                state = ModBlocks.BASALT.get().getDefaultState();
            }
        } else { // Adds sedimentary strata above
            noiseVal = genAdjustableLayers(xPos, yPos, zPos, 1.5f, 20, 10, 30, 0);
            // Put the names of desired block presets into "blockListNames", and any individual blocks wanted into "blocks"
            List<String> blockListNames = new ArrayList<>(Arrays.asList("sedimentary_soil", "sedimentary_sandy", "sedimentary_carbonate"));
            List<Block> blocks = Collections.emptyList();
            List<BlockState> stateList = (useCached) ? cachedBlockStateList : (cachedBlockStateList = BlockPicker.buildStateList(blockListNames, blocks));
            state = BlockPicker.selectBlock(stateList, regionNoise, noiseVal);
        }
        return state;
    }



    //////////////////////////////////////////////////
    /////            Strata Templates            /////
    //////////////////////////////////////////////////

    /*
     *  These are "plug and play" blocks for coding various regions
     *  They have no function during operation, only for development work
     */

    //////////
    /*


     */


    //////////





    // Generate fault line vertical shifts
    /*
    if (faults) {
        yPos += (int)([MAX_DISPLACEMENT] * stoneFaultNoise(xPos, yPos, zPos));
    }
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


    //////////////////////////////////////////////////
    /////            Noise Generators            /////
    //////////////////////////////////////////////////

    /*
    *  Only needed if excessive dynamic manipulation of input or output is required.
    *  Else, operations will be done in args or where called.
    */

    private static float genAdjustableLayers(int x, int y, int z, float curvature, int thickness, int warp, int tilt, int fault) {
        // Warp determines how "buckled" the layers are, tilt determines what angle the strata form at.
        // Orogeny is if the region is in (or what was once) a mountain biome; means warp is
        // an increasing function of y (more y, more warp)

        // Apply regional seed shift
        int shiftedSeed = ConfiguredRegionNoise.getRegionShiftedSeed(x, y, z);
        yWarpNoise.SetSeed(shiftedSeed + 13408);
        yTiltNoise.SetSeed(shiftedSeed - 15605);
        strataNoise.SetSeed(shiftedSeed);

        // Bound input variables
        // curvature:(0,5]; all others:[0-100]
        // Regarding curvature -> 0.01f is flat, 0.05f-0.3f is slight, 0.5f-1f is normal, >1f is increasingly squished
        curvature = ((curvature <= 0) || (curvature > 5f)) ? 1f : curvature;
        thickness = ((thickness < 0) || (thickness > 100)) ? 25 : thickness;
        warp = ((warp < 0) || (warp > 100)) ? 10 : warp;
        tilt = ((tilt < 0) || (tilt > 100)) ? 30 : tilt;
        fault = ((fault < 0) || (fault > 100)) ? 20 : fault;

        // Applies tile and then warp to the layers
        int shiftedY = y + (int)((tilt * tilt * tilt / 30) * yTiltNoise.GetNoise(x / 30f, z / 30f));
        shiftedY += (int)(warp * 0.3f * yWarpNoise.GetNoise(x * 15, z * 15));
        // Applies fault vertical shift
        shiftedY += (int)((fault / 2f) * stoneFaultNoise(x, y, z));

        // Note: Tilting the layers compresses layer height and curving them thickens them, tilt and curvature values are used here to partially offset these effects
        return strataNoise.GetNoise(x * 15f * curvature, shiftedY * ((350f - (tilt * 3f)) * (1.01f - (thickness / 100f))) * ((curvature / 5f) + 1), z * 15f * curvature);
    }


    ///////////////////////////////////////////////////////
    /////             Noise Configurations            /////
    ///////////////////////////////////////////////////////

    // FastNoiseLite configuration for strata generation
    public static void configNoise(long worldSeed) {
        WORLD_SEED = worldSeed; // Sets the world seed for access in noise generation

        // FastNoiseLite configuration for adjustable strata
        if (strataNoise == null) {
            strataNoise = new FastNoiseLite((int)(WORLD_SEED)+22493);
            strataNoise.SetNoiseType(FastNoiseLite.NoiseType.Value);
            strataNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            strataNoise.SetFractalType(FastNoiseLite.FractalType.None);
            strataNoise.SetFrequency(0.0001f);
        }

        // FastNoiseLite configuration for adjustable strata warping noise
        if (yWarpNoise == null) {
            yWarpNoise = new FastNoiseLite((int)(WORLD_SEED));
            yWarpNoise.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
            yWarpNoise.SetFractalType(FastNoiseLite.FractalType.None);
            yWarpNoise.SetFrequency(0.015f);
        }

        // // FastNoiseLite configuration for adjustable strata tilting noise
        if (yTiltNoise == null) {
            yTiltNoise = new FastNoiseLite((int)(WORLD_SEED));
            yTiltNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
            yTiltNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
            yTiltNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance2);
            yTiltNoise.SetFractalType(FastNoiseLite.FractalType.None);
            yTiltNoise.SetFrequency(0.002f);
        }

        // FastNoiseLite configuration for inserted strata
        if (smoothDividingNoise == null) {
            smoothDividingNoise = new FastNoiseLite((int)(WORLD_SEED)-22493);
            smoothDividingNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            smoothDividingNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            smoothDividingNoise.SetFrequency(0.04f);
        }

        // FastNoiseLite configuration for flood basalts
        if (floodBasaltNoise == null) {
            floodBasaltNoise = new FastNoiseLite((int)(WORLD_SEED)-22493);
            floodBasaltNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            floodBasaltNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);
            floodBasaltNoise.SetFrequency(0.03f);
        }
    }

}
