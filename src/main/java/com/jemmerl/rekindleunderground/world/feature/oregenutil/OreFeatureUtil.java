package com.jemmerl.rekindleunderground.world.feature.oregenutil;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.util.Pair;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public class OreFeatureUtil {

    public static DepositRegistrar depositRegistrar = new DepositRegistrar();

    public static void init() {
        MinecraftForge.EVENT_BUS.register(depositRegistrar);
    }

    // Check if a blockstate is a valid stone for ore generation
    // If not a StoneOreBlock, then the second condition (which
    // assumes that is is such) will never be reached.
    public static Boolean isValidStone(BlockState blockIn, ArrayList<StoneType> validStones) {
        if ((blockIn.getBlock() instanceof StoneOreBlock) && validStones.contains(((StoneOreBlock) blockIn.getBlock()).getStoneType()) ) {
            return true;
        } else {
            return false;
        }
    }

    // Return an weighted probability map of OreTypes from a JsonArray
    public static WeightedProbMap<OreType> getOres(JsonArray oresArray) {
        ArrayList<Pair<Integer, OreType>> elts = new ArrayList<>();
        try {
            int weightSum = 0;
            for (int i=0; i<oresArray.size(); i++) {
                JsonObject oreObj = oresArray.get(i).getAsJsonObject();
                OreType oreType = OreType.valueOf(oreObj.get("ore").getAsString());
                int weight = oreObj.get("weight").getAsInt();
                weightSum += weight;
                if (weightSum > 100) {
                    throw new Exception("Ore weights sum to over 100 in deposit");
                }
                elts.add( new Pair<>(weight, oreType));
            }
        } catch (Exception e) {
            return null;
        }
        return new WeightedProbMap<OreType>(elts);
    }

    // Return an array list of valid StoneTypes from a JsonArray
    public static ArrayList<StoneType> getStones(JsonArray jsonArray) {
        ArrayList<StoneType> stoneArray = new ArrayList<>();
            try {
                for (int i=0; i<jsonArray.size(); i++) {
                    stoneArray.add(StoneType.valueOf(jsonArray.get(i).getAsString()));
                }
            } catch (Exception e) {
                return null;
            }
        return stoneArray;
    }


}
