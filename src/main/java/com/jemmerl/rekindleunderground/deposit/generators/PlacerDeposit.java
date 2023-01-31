package com.jemmerl.rekindleunderground.deposit.generators;

import com.jemmerl.rekindleunderground.data.types.DepositType;
import com.jemmerl.rekindleunderground.data.types.GeologyType;
import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.deposit.IDeposit;
import com.jemmerl.rekindleunderground.deposit.templates.PlacerTemplate;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public class PlacerDeposit implements IDeposit {

    private final PlacerTemplate placerTemplate;

    private String name;
    private WeightedProbMap<OreType> ores;
    private WeightedProbMap<GradeType> gradesMap;
    private ArrayList<GeologyType> validList;
    private ArrayList<Biome.Category> validBiomes;

    public PlacerDeposit(PlacerTemplate template) {
        this.placerTemplate = template;
    }

    /////////////
    // SETTERS //
    /////////////

    @Override
    public PlacerDeposit setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public PlacerDeposit setOres(WeightedProbMap<OreType> oreMap) {
        this.ores = oreMap;
        return this;
    }

    @Override
    public PlacerDeposit setGrades(WeightedProbMap<GradeType> gradesMap) {
        this.gradesMap = gradesMap;
        return this;
    }

    @Override
    public PlacerDeposit setValid(ArrayList<GeologyType> validList) {
        this.validList = validList;
        return this;
    }

    @Override
    public PlacerDeposit setBiomes(ArrayList<Biome.Category> validBiomes) {
        this.validBiomes = validBiomes;
        return this;
    }

    /////////////
    // GETTERS //
    /////////////

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public DepositType getType() {
        return DepositType.PLACER;
    }

    @Override
    public WeightedProbMap<OreType> getOres() {
        return this.ores;
    }

    @Override
    public WeightedProbMap<GradeType> getGrades() {
        return this.gradesMap;
    }

    @Override
    public ArrayList<GeologyType> getValid() {
        return this.validList;
    }

    @Override
    public ArrayList<Biome.Category> getBiomes() {
        return this.validBiomes;
    }

    @Override
    public int getWeight() {
        return this.placerTemplate.getWeight();
    }

    public int getAvgRadius() {
        return this.placerTemplate.getAvgRadius();
    }

    public int getMinDensity() {
        return this.placerTemplate.getMinDensity();
    }

    public int getMaxDensity() {
        return this.placerTemplate.getMaxDensity();
    }

}
/*

    //////////////////////////
    //  DEPOSIT GENERATION  //
    //////////////////////////

    @Override
    public boolean generate(ChunkReader reader, Random rand, BlockPos pos, StateMap stateMap,
                            IDepositCapability depositCapability, IChunkGennedCapability chunkGennedCapability) {

        if (rand.nextFloat() < 2) {
            return false;
        }

        ISeedReader seedReader = reader.getSeedReader();

        // Constants
        final int MAX_SEARCHES = 3;
        final int VARIANCE = 3;

        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(seedReader.getSeed());
        }

        ////////////////////////
        // DEPOSIT PROPERTIES //
        ////////////////////////

        // TODO HAVE EXPOSED DEPOSIT BLOCKS BE LESS LIKELY TO BE ORES? THAT WAY MOST IS HIDDEN, NOT AS UGLY
        // Get a uniformly distributed density value for the deposit within the min and max density range
        float densityPercent = ((rand.nextInt(this.placerTemplate.getMaxDensity() -
                this.placerTemplate.getMinDensity()) + this.placerTemplate.getMinDensity()) / 100f);

        // Get the weighted random grade of the ore deposit
        GradeType grade = this.gradesMap.nextElt();

        // TODO DUNNO IF WILL STAY WITH THIS SYSTEM, PLACER DEPOSITS SHOULDNT BE THAT ROUND
        // Get a normally distributed average radius (for the individual deposit) around the average configured radius
        int avgDepositRadius = getAvgRadius(rand);

        // TODO WILL NEED LOTS OF TWEAKING. MAYBE SUBTRACT OCEAN_FLOOR_WG FROM SURFACE_WG THEN ADD 1-3? START AT SURFACE_WG
        // Get the generation depth of the deposit (how deep into sediment) from 2 to 4
        int depth = rand.nextInt(3) + 2;

        // Get the deposit origin coordinates
        int xShift = rand.nextInt(16);
        int yShift = rand.nextInt(16);
        int originX = pos.getX() + xShift;
        int originZ = pos.getZ() + yShift;
        int originY = reader.getMaxHeightVal(xShift, yShift); // TODO TEST ADJUSTMENT, ADD +1?
        BlockPos originPos = new BlockPos(originX, originY, originZ);


        ////////////////////////
        // DEPOSIT GENERATION //
        ////////////////////////

//        // Debug
//        if (RKUndergroundConfig.COMMON.debug.get()) {
//            RekindleUnderground.getInstance().LOGGER.info("Generating placer deposit at {}, with {} density and {} grade.", originPos, densityPercent, grade.getString());
//        }

        // Debug TODO TEMP
        if (true) {
            RekindleUnderground.getInstance().LOGGER.info("Generating placer deposit at {}, with {} density and {} grade.", originPos, densityPercent, grade.getString());
        }

        // Check for valid biome placement. If not in a valid biome, cancel generation
        Biome biome1 = seedReader.getBiome(originPos);
        if (!this.validBiomes.contains(biome1.getCategory())) {
            // Debug
            if (RKUndergroundConfig.COMMON.debug.get()) {
                RekindleUnderground.getInstance().LOGGER.info("Invalid biome for placer deposit at {}, failed to generate.", originPos);
            }
            return false;
        }


        // Pre-init dynamic variables
        float radius; // Generated radius
        float adjDensityPercent; // Variable density for spacing level generation
        BlockPos checkPos;
        ServerWorld world = seedReader.getWorld();

        BlockPos genPos = originPos;
        for (int i = 0; i < MAX_SEARCHES; i++) {
            // Generate a placer splotch
            for (int y = originY; y > (originY - depth); y--) {
                for (BlockPos areaPos : BlockPos.getAllInBoxMutable(
                        new BlockPos((genPos.getX() - avgDepositRadius - (VARIANCE)), y, (genPos.getZ() - avgDepositRadius - (VARIANCE))),
                        new BlockPos((genPos.getX() + avgDepositRadius + (VARIANCE)), y, (genPos.getZ() + avgDepositRadius + (VARIANCE)))))
                {
//                    HANDLE IN THE DEPOSIT UTIL. IF DEPOSIT = PLACER, THEN IF BELOW CONDITIONS, THEN IF rand.nextFloat < 0.7f PLACE
//                    int surfaceHeight = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, areaPos.getX(), areaPos.getZ());
//                    if (y == surfaceHeight) {
//                        adjDensityPercent = densityPercent * 0.3f;
//                    }

                    radius = Math.max(avgDepositRadius + (rand.nextInt(VARIANCE * 2) - VARIANCE + 1), 1);

                    // Generate the ore block if within the radius and rolls a success against the density percent
                    if ((rand.nextFloat() < densityPercent) &&
                            (UtilMethods.getHypotenuse(areaPos.getX(), areaPos.getZ(), originPos.getX(), originPos.getZ()) <= radius)) {
                        DepositUtil.enqueueBlockPlacement(reader.getSeedReader(), areaPos, this.ores.nextElt(), grade,
                                this.name, pos, stateMap, depositCapability, chunkGennedCapability);
                    }

                    // TODO AAAAHHH IT GENERATES IN AND REPLACES VANILLA BLOCKS, NEED TO REWORK DEPOSITUTIL AAAAH

                }

            }

            // Try to generate an adjacent ore splotch, if successfully rolled
            if ((rand.nextFloat() < this.placerTemplate.getWeight()) && (rand.nextInt(i + 1) == 0)) {
                checkPos = world.getChunkProvider().generator.getBiomeProvider().findBiomePosition(
                        genPos.getX(), originY, genPos.getZ(), (2*avgDepositRadius), avgDepositRadius,
                        ((biome2) -> this.validBiomes.contains(biome2.getCategory())), rand, true);

                // If valid biome not found
                if (checkPos == null) {
                    break;
                }

                // Else set generation spot to found location and generate a new splotch
                genPos = checkPos;

            }
        }

        return true;
    }


    ///////////////////////
    // DEPOSIT UTILITIES //
    ///////////////////////

    // Setup: Gets a normally distributed radius around the given average radius
    private int getAvgRadius(Random rand) {
        int radius = (int)((rand.nextGaussian() * (this.placerTemplate.getAvgRadius() / 3f)) + this.placerTemplate.getAvgRadius());
        return (radius <= 0) ? 1 : radius;

    }

 */

// Check 5 blocks max NSEW, noting distances if block found
//
