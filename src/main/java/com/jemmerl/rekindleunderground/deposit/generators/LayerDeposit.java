package com.jemmerl.rekindleunderground.deposit.generators;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.deposit.DepositUtil;
import com.jemmerl.rekindleunderground.deposit.IDeposit;
import com.jemmerl.rekindleunderground.deposit.templates.LayerTemplate;
import com.jemmerl.rekindleunderground.init.NoiseInit;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredBlobNoise;
import com.jemmerl.rekindleunderground.world.capability.chunk.IChunkGennedCapability;
import com.jemmerl.rekindleunderground.world.capability.deposit.IDepositCapability;
import com.jemmerl.rekindleunderground.world.feature.stonegeneration.ChunkReader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Random;

public class LayerDeposit implements IDeposit {

    private final LayerTemplate layerTemplate;

    private String name;
    private WeightedProbMap<OreType> ores;
    private ArrayList<StoneType> validStones;

    public LayerDeposit(LayerTemplate template) {
        this.layerTemplate = template;
    }

    @Override
    public LayerDeposit setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public LayerDeposit setOres(WeightedProbMap<OreType> oreMap) {
        this.ores = oreMap;
        return this;
    }

    @Override
    public LayerDeposit setStones(ArrayList<StoneType> stoneList) {
        this.validStones = stoneList;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public WeightedProbMap<OreType> getOres() {
        return this.ores;
    }

    @Override
    public ArrayList<StoneType> getStones() {
        return this.validStones;
    }

    public int getWeight() {
        return this.layerTemplate.getWeight();
    }

    public boolean generate(ChunkReader reader, Random rand, BlockPos pos, BlockState[][][] stateMap,
                            IDepositCapability depositCapability, IChunkGennedCapability chunkGennedCapability) {

        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(reader.getSeedReader().getSeed());
        }

        ////////////////////////
        // DEPOSIT PROPERTIES //
        ////////////////////////

        // Get a normally distributed average radius (for the individual deposit) around the average configured radius
        int avgDepositRadius = getAvgRadius(rand);

        // Get a uniformly distributed density value for the deposit within the min and max density range
        float densityPercent = ((rand.nextInt(this.layerTemplate.getMaxDensity() - this.layerTemplate.getMinDensity()) + this.layerTemplate.getMinDensity()) / 100f);

        // If the max layers is 1, return 1. Else, random spread. (Having 1 max layer inputs 0 into rand.nextInt, which is illegal)
        int layers = (this.layerTemplate.getMaxLayers() == 1) ? 1 : (rand.nextInt(this.layerTemplate.getMaxLayers() - 1) + 1);

        // Add 1 block for in-between spacing
        int totalHeight = layers * (this.layerTemplate.getAvgLayerThick() + 1);
        int heightStart = rand.nextInt(this.layerTemplate.getMaxYHeight() - this.layerTemplate.getMinYHeight()) + this.layerTemplate.getMinYHeight();
        int heightEnd = heightStart + totalHeight;

        // Check if the deposit is generating higher than possible (prevent StateMap ArrayOutOfBounds in the Y direction)
        // If so, try to truncate to that value. If that does not work, prevent it from generating.
        if (heightEnd > reader.getMaxHeight()) {
            heightEnd = reader.getMaxHeight();
            if (heightEnd < heightStart) {
                return false;
            }
        }

        // Update the new total height
        totalHeight = heightEnd - heightStart;

        // Set approximate center of the deposit
        BlockPos originPos = new BlockPos(
                (pos.getX() + rand.nextInt(16)),
                (int)((heightStart + heightEnd) / 2f),
                (pos.getZ() + rand.nextInt(16))
        );


        ////////////////////////
        // DEPOSIT GENERATION //
        ////////////////////////

        // Debug
        if (RKUndergroundConfig.COMMON.debug.get()) {
            RekindleUnderground.getInstance().LOGGER.info("Generating deposit at {}, with {} layers and {} total height.", originPos, layers, totalHeight);
        }

        // Radius is generated dynamically, this is just a pre-initialization
        float radius;

        // Set the first layer's height
        int currLayerHeight = getLayerHeight(rand);
        int countLayerHeight = 0; // Used to count and put spacings between deposit layers
        int countLayers = 0; // Used to count how many layers have generated so far
        float adjDensityPercent; // Use to dynamically change density for spacing layers

        for (int y = heightStart; y < heightEnd; y++) {

            // Check number of layers
            if (countLayers == layers) {
                break; // If deposit limit reached, end generation
            }

            // Check current layer height
            if (countLayerHeight >= currLayerHeight) {
                currLayerHeight = getLayerHeight(rand); // Get next layer height
                countLayerHeight = rand.nextInt(3) - 3; // Tells the loop to generate one or two spacer layers
                adjDensityPercent = densityPercent * 0.2f; // Set the density of the spacing layer to be very small
                countLayers++; // Incrememnt the number of layers generated
            } else {
                adjDensityPercent = densityPercent; // Reset to regular layer density
            }


            for (BlockPos areaPos : BlockPos.getAllInBoxMutable(
                    new BlockPos((originPos.getX() - avgDepositRadius), y, (originPos.getZ() - avgDepositRadius)),
                    new BlockPos((originPos.getX() + avgDepositRadius), y, (originPos.getZ() + avgDepositRadius))))
            {

                // TODO always returning 100??
                // Oops. modeled for radius. back to drawing board. dunno why large values from hypot tho...
                double taperPercent = Math.min(100, ((60 / (1 + Math.exp(-(Math.hypot(originPos.getY(), y)) + 0.8 * (totalHeight / 2f)))) + 45));
                //System.out.println(taperPercent + " " + Math.hypot(originPos.getY(), y) + " " + (float)(totalHeight / 2f));

                // Gets the radius and then multiplies that radius by a logistic regression from 100% to 45%
                // based on the current and total vertical distance from the center. Tapers off the tops and bottoms!
                radius = (float) ((avgDepositRadius + ConfiguredBlobNoise.blobRadiusNoise(areaPos.getX(), y, areaPos.getZ()))
                        * taperPercent);

                // Generate the ore block if within the radius and rolls a success against the density percent
                if ((UtilMethods.getHypotenuse(areaPos.getX(), areaPos.getZ(), originPos.getX(), originPos.getZ()) <= radius)
                        && (rand.nextFloat() < adjDensityPercent)) {
                    DepositUtil.enqueueBlockPlacement(reader.getSeedReader(), areaPos, this.ores.nextElt(),
                            this.name, pos, stateMap, depositCapability, chunkGennedCapability);
                }
            }
            countLayerHeight++;
        }

        // ((100 / ( - totalHeight + 5)) + 10))

        // Debug tool
        if (RKUndergroundConfig.COMMON.debug.get()) {
            for (int yPole = heightEnd; yPole < 120; yPole++) {
                reader.getSeedReader().setBlockState(new BlockPos(originPos.getX(), yPole, originPos.getZ()),
                        Blocks.RED_WOOL.getDefaultState(), 2);
            }
        }

        return true;
    }


    ///////////////////////
    // DEPOSIT UTILITIES //
    ///////////////////////

    // Setup: Gets a normally distributed average radius. The actual radius value will vary around this value as a base
    private int getAvgRadius(Random rand) {
        int radius = (int)((rand.nextGaussian() * (this.layerTemplate.getAvgRadius() / 3f)) + this.layerTemplate.getAvgRadius());
        return (radius <= 0) ? 1 : radius;

    }

    // Setup: Gets a normally distributed layer height
    private int getLayerHeight(Random rand) {
        int height = (int)((rand.nextGaussian() * (this.layerTemplate.getAvgLayerThick() / 2f)) + this.layerTemplate.getAvgLayerThick());
        return (height <= 0) ? 1 : height;
    }

}