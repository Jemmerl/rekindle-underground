package com.jemmerl.rekindleunderground.world.feature.orefeats;

import com.google.gson.JsonObject;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.data.types.featuretypes.DepositType;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import com.jemmerl.rekindleunderground.world.feature.oregenutil.IDeposit;
import com.jemmerl.rekindleunderground.world.feature.oregenutil.OreFeatureUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.Random;

// TODO test

public class LayerDeposit implements IDeposit {

    //private final DepositType type;
    private final int maxLayers;
    private final int avgLayerThickness;
    private final int minDensity;
    private final int maxDensity;
    private final int minHeight;
    private final int maxHeight;
    private final WeightedProbMap<OreType> ores;
    private final ArrayList<StoneType> validStones;

    public LayerDeposit(JsonObject setupObj) {
        //DepositType tempType = null;
        int tempMaxLayers = 0;
        int tempAvgLayerThickness = 0;
        int tempMinDensity = 0;
        int tempMaxDensity = 0;
        int tempMinYHeight = 0;
        int tempMaxYHeight = 0;
        WeightedProbMap<OreType> tempOreArray = null;
        ArrayList<StoneType> tempStoneArray = null;

        try {
            JsonObject settingsObj = setupObj.get("settings").getAsJsonObject();
            //tempType = DepositType.valueOf(setupObj.get("type").getAsString());
            tempMaxLayers = settingsObj.get("max_layers").getAsInt();
            tempAvgLayerThickness = settingsObj.get("avg_layer_thickness").getAsInt();
            tempMinDensity = settingsObj.get("min_density").getAsInt();
            tempMaxDensity = settingsObj.get("max_density").getAsInt();
            tempMinYHeight = settingsObj.get("min_yheight").getAsInt();
            tempMaxYHeight = settingsObj.get("max_yheight").getAsInt();
            tempOreArray = OreFeatureUtil.getOres(setupObj.get("ores").getAsJsonArray());
            tempStoneArray = OreFeatureUtil.getStones(setupObj.get("stones").getAsJsonArray());
        } catch (Exception e) {
            RekindleUnderground.getInstance().LOGGER.warn("Error parsing deposit settings due to: {}", e.toString());
        }

        //this.type = tempType;
        this.maxLayers = tempMaxLayers;
        this.avgLayerThickness = tempAvgLayerThickness;
        this.minDensity = tempMinDensity;
        this.maxDensity = tempMaxDensity;
        this.minHeight = tempMinYHeight;
        this.maxHeight = tempMaxYHeight;
        this.ores = tempOreArray;
        this.validStones = tempStoneArray;
    }


    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        int maxRadius = rand.nextInt(20) + 15;
        OreType oreType = OreType.getRandomOreType(rand);
        System.out.println(oreType.getString());
        System.out.println(pos.getX() + ", " + pos.getZ());

        float densityPercent = ((rand.nextInt(this.maxDensity - this.minDensity) + this.minDensity) / 100f);

        // Deposit physical properties
        int layers = rand.nextInt(this.maxLayers - 1) + 1;
        int totalHeight = layers * this.avgLayerThickness;
        int heightMiddle = rand.nextInt(this.maxHeight - this.minHeight) + this.minHeight;
        int heightStart = heightMiddle - (totalHeight / 2);
        int heightEnd = heightMiddle + (totalHeight / 2);
        float radius;

        // TODO TODO TODO
        // generate with radius using random blob like the diatrememaar
        // REWORK LAYER WAVYNESS TO USE A SINGLE, GLOBAL HEIGHT MORPHER!!
        // ALLOW ORES TO ACCESS IT! WILL LET ORES FOLLOW THE EFFECTS!

        for (int y = heightStart; y < heightEnd; y++) {
            for (BlockPos areaPos : BlockPos.getAllInBoxMutable(pos.add(-maxRadius, 0, -maxRadius), pos.add(maxRadius, 0, maxRadius))) {
                radius = (float)(maxRadius);
                BlockPos setPos = new BlockPos(areaPos.getX(), y, areaPos.getZ());
                if (UtilMethods.getHypotenuse(areaPos.getX(), areaPos.getZ(), pos.getX(), pos.getZ()) <= radius) {
                    BlockState hostBlock = reader.getBlockState(setPos);

                    if (OreFeatureUtil.isValidStone(hostBlock, this.validStones) && (rand.nextFloat() < densityPercent)) {
                        reader.setBlockState(setPos, hostBlock.with(StoneOreBlock.ORE_TYPE, this.ores.nextElt()), 2);
                    }
                }


            }
        }

        for (int yPole = heightEnd; yPole < 120; yPole++) {
            reader.setBlockState(new BlockPos(pos.getX(), yPole, pos.getZ()), Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
        }



        return true;
    }
}
