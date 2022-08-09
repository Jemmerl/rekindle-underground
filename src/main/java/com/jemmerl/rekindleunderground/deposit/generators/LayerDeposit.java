package com.jemmerl.rekindleunderground.deposit.generators;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.deposit.DepositUtil;
import com.jemmerl.rekindleunderground.deposit.IDeposit;
import com.jemmerl.rekindleunderground.deposit.templates.LayerTemplate;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import com.jemmerl.rekindleunderground.world.feature.stonegeneration.ChunkReader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Random;

// TODO test

public class LayerDeposit implements IDeposit {

    private WeightedProbMap<OreType> ores;
    private ArrayList<StoneType> validStones;

    private final LayerTemplate layerTemplate;

    public LayerDeposit(LayerTemplate template) {
        this.layerTemplate = template;
    }

    public LayerDeposit setOres(WeightedProbMap<OreType> oreMap) {
        this.ores = oreMap;
        return this;
    }

    public LayerDeposit setStones(ArrayList<StoneType> stoneList) {
        this.validStones = stoneList;
        return this;
    }

    public int getWeight() {
        return this.layerTemplate.getWeight();
    }

    public boolean generate(ChunkReader reader, Random rand, BlockPos pos, BlockState[][][] stateMap) {

        ////////////////////////
        // DEPOSIT PROPERTIES //
        ////////////////////////

        // Get a normally distributed average radius (for the individual deposit) around the average configured radius
        int avgDepositRadius = getRadius(rand);

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

        // Set approximate center of the deposit
        BlockPos originPos = new BlockPos(
                (pos.getX() + rand.nextInt(16)),
                heightStart,
                (pos.getZ() + rand.nextInt(16))
        );


        ////////////////////////
        // DEPOSIT GENERATION //
        ////////////////////////

        // TODO TEST
        BlockState indicatorState = Blocks.RED_WOOL.getDefaultState();

        RekindleUnderground.getInstance().LOGGER.info("Generating deposit at {}, with {} layers.", originPos, layers);
        float radius; // Radius is generated dynamically, this is just a pre-initialization

        // Set the first layer's height
        int currLayerHeight = getLayerHeight(rand);
        System.out.println(currLayerHeight); // TODO TEST
        int countLayerHeight = 0; // Used to count and put spacings between deposit layers
        int countLayers = 0; // Used to count how many layers have generated so far
        float adjDensityPercent = densityPercent; // Use to dynamically change density for spacing layers

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
                radius = (float)(avgDepositRadius); // TODO TEMP
                if (UtilMethods.getHypotenuse(areaPos.getX(), areaPos.getZ(), originPos.getX(), originPos.getZ()) <= radius) {
                    if (DepositUtil.isInsideChunk(pos, areaPos)) {

                        BlockState hostBlock = stateMap[(areaPos.getX() - pos.getX())][y][(areaPos.getZ() - pos.getZ())];

                        //if (DepositUtil.isValidStone(hostBlock, this.validStones) && (rand.nextFloat() < adjDensityPercent)) {
                        if ((hostBlock.getBlock() instanceof StoneOreBlock) && (rand.nextFloat() < adjDensityPercent)) {
                            stateMap[(areaPos.getX() - pos.getX())][y][(areaPos.getZ() - pos.getZ())] = hostBlock.with(StoneOreBlock.ORE_TYPE, this.ores.nextElt());
                            indicatorState = Blocks.DIAMOND_BLOCK.getDefaultState(); // TODO TEST
                        }
                    } else {
                        continue; // IGNORE PLACEMENT IF OUT OF CHUNK BORDER
                    }
                }
            }
            countLayerHeight++;
        }

        // TODO TEST
        for (int yPole = heightEnd; yPole < 120; yPole++) {
            reader.getSeedReader().setBlockState(new BlockPos(originPos.getX(), yPole, originPos.getZ()), indicatorState, 2);
        }

        return true;
    }


    ///////////////////////
    // DEPOSIT UTILITIES //
    ///////////////////////

    // Gets a normally distributed avg radius
    private int getRadius(Random rand) {
        int radius = (int)((rand.nextGaussian() * (this.layerTemplate.getAvgRadius() / 3f)) + this.layerTemplate.getAvgRadius());
        return (radius <= 0) ? 1 : radius;

    }

    // Gets a normally distributed layer height
    private int getLayerHeight(Random rand) {
        int height = (int)((rand.nextGaussian() * (this.layerTemplate.getAvgLayerThick() / 2f)) + this.layerTemplate.getAvgLayerThick());
        return (height <= 0) ? 1 : height;
    }


}