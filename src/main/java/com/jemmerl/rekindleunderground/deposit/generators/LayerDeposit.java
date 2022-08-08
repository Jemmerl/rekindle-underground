package com.jemmerl.rekindleunderground.deposit.generators;

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
        System.out.println(this.validStones.size());
        return this;
    }

    public int getWeight() {
        return this.layerTemplate.getWeight();
    }

    public boolean generate(ChunkReader reader, Random rand, BlockPos pos, BlockState[][][] stateMap) {

        int maxRadius = rand.nextInt(20) + 15; // TODO TEMP

        // TODO
        // Randomly place deposit within chunk
        // check if block being set is within the active chunk
        // if so, continue. if not, enqueue!
        // TEMP: just make it get ignored :P

        ////////////////////////
        // DEPOSIT PROPERTIES //
        ////////////////////////

        float densityPercent = ((rand.nextInt(this.layerTemplate.getMaxDensity() - this.layerTemplate.getMinDensity()) + this.layerTemplate.getMinDensity()) / 100f);
        int layers = rand.nextInt(this.layerTemplate.getMaxLayers() - 1) + 1;
        int totalHeight = layers * this.layerTemplate.getAvgLayerThick();
        int heightMiddle = rand.nextInt(this.layerTemplate.getMaxYHeight() - this.layerTemplate.getMinYHeight()) + this.layerTemplate.getMinYHeight();
        int heightStart = heightMiddle - (totalHeight / 2);
        int heightEnd = heightMiddle + (totalHeight / 2);

        // Check if the deposit is generating higher than possible (prevent StateMap ArrayOutOfBounds in the Y direction)
        // If so, try to truncate to that value. If that does not work, prevent it from generating.
        if (heightEnd > reader.getMaxHeight()) {
            heightEnd = reader.getMaxHeight();
            if (heightEnd < heightStart) {
                return false;
            }
        }

        // Approximate center of the deposit
        BlockPos originPos = new BlockPos(
                (pos.getX() + rand.nextInt(16)),
                heightMiddle,
                (pos.getZ() + rand.nextInt(16))
        );
        int originPosX = originPos.getX();
        int originPosZ = originPos.getZ();
        System.out.println(originPos.getX() + ", " + heightMiddle + ", " + originPos.getZ());
        float radius; // Radius is generated dynamically, this is just a pre-initialization

        // TODO TODO TODO
        // generate with radius using random blob like the diatrememaar
        // REWORK LAYER WAVYNESS TO USE A SINGLE, GLOBAL HEIGHT MORPHER!!
        // ALLOW ORES TO ACCESS IT! WILL LET ORES FOLLOW THE EFFECTS!

        // TODO TEST
        BlockState indicatorState = Blocks.RED_WOOL.getDefaultState();

        for (int y = heightStart; y < heightEnd; y++) {
            for (BlockPos areaPos : BlockPos.getAllInBoxMutable(
                    new BlockPos((originPosX - maxRadius), y, (originPosZ - maxRadius)),
                    new BlockPos((originPosX + maxRadius), y, (originPosZ + maxRadius))))
            {
                radius = (float)(maxRadius); // TODO TEMP
                if (UtilMethods.getHypotenuse(areaPos.getX(), areaPos.getZ(), originPosX, originPosZ) <= radius) {
                    if (DepositUtil.isInsideChunk(pos, areaPos)) {

                        BlockState hostBlock = stateMap[(areaPos.getX() - pos.getX())][y][(areaPos.getZ() - pos.getZ())];

                        if (DepositUtil.isValidStone(hostBlock, this.validStones) && (rand.nextFloat() < densityPercent)) {
                        //if ((hostBlock.getBlock() instanceof StoneOreBlock) && (rand.nextFloat() < densityPercent)) {
                            stateMap[(areaPos.getX() - pos.getX())][y][(areaPos.getZ() - pos.getZ())] = hostBlock.with(StoneOreBlock.ORE_TYPE, this.ores.nextElt());
                            indicatorState = Blocks.DIAMOND_BLOCK.getDefaultState();
                        }
                    } else {
                        continue;
                    }


                }


            }
        }

        for (int yPole = heightEnd; yPole < 120; yPole++) {
            reader.getSeedReader().setBlockState(new BlockPos(originPosX, yPole, originPosZ), indicatorState, 2);
        }



        return true;
    }
}


/*

//    private final DepositType type;
//    private final int weight;
//    private final int maxLayers;
//    private final int avgLayerThickness;
//    private final int minDensity;
//    private final int maxDensity;
//    private final int minHeight;
//    private final int maxHeight;
//    private final int maxRadius;

        //DepositType tempType = null;
        int tempWeight = 1;
        int tempMaxLayers = 0;
        int tempAvgLayerThickness = 0;
        int tempMinDensity = 0;
        int tempMaxDensity = 0;
        int tempMinYHeight = 0;
        int tempMaxYHeight = 0;
        int tempMaxRadius = 1;
        WeightedProbMap<OreType> tempOreArray = null;
        ArrayList<StoneType> tempStoneArray = null;

        try {
            JsonObject settingsObj = setupObj.get("settings").getAsJsonObject();
            //tempType = DepositType.valueOf(setupObj.get("type").getAsString());
            tempWeight = settingsObj.get("weight").getAsInt();
            tempMaxLayers = settingsObj.get("max_layers").getAsInt();
            tempAvgLayerThickness = settingsObj.get("avg_layer_thickness").getAsInt();
            tempMinDensity = settingsObj.get("min_density").getAsInt();
            tempMaxDensity = settingsObj.get("max_density").getAsInt();
            tempMinYHeight = settingsObj.get("min_yheight").getAsInt();
            tempMaxYHeight = settingsObj.get("max_yheight").getAsInt();
            tempMaxRadius = settingsObj.get("max_radius").getAsInt();
            tempOreArray = DepositUtil.getOres(setupObj.get("ores").getAsJsonArray());
            tempStoneArray = DepositUtil.getStones(setupObj.get("stones").getAsJsonArray());
        } catch (Exception e) {
            RekindleUnderground.getInstance().LOGGER.warn("Error parsing deposit settings due to: {}", e.toString());
        }

        //this.type = tempType;
        this.weight = tempWeight;
        this.maxLayers = tempMaxLayers;
        this.avgLayerThickness = tempAvgLayerThickness;
        this.minDensity = tempMinDensity;
        this.maxDensity = tempMaxDensity;
        this.minHeight = tempMinYHeight;
        this.maxHeight = tempMaxYHeight;
        this.maxRadius = tempMaxRadius;
        this.ores = tempOreArray;
        this.validStones = tempStoneArray;

 */
