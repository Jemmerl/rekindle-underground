package com.jemmerl.rekindleunderground.deposit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.util.Pair;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class DepositUtil {

    // public static DepositRegistrar depositRegistrar = new DepositRegistrar();


    // Check if a point lies in the chunk
    // Only works in the StoneGenFeature feature, which
    // generates on a specific corner of every chunk
    public static Boolean isInsideChunk(BlockPos chunkCorner, BlockPos point) {
        return (((chunkCorner.getX() <= point.getX()) && (point.getX() <= (chunkCorner.getX() + 15)))
                && ((chunkCorner.getZ() <= point.getZ()) && (point.getZ() <= (chunkCorner.getZ() + 15))));
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
                OreType oreType = OreType.valueOf(oreObj.get("ore").getAsString().toUpperCase());
                int weight = oreObj.get("weight").getAsInt();
                weightSum += weight;
                if (weightSum > 100) {
                    throw new Exception("Ore weights sum to over 100 in deposit");
                }
                elts.add( new Pair<>(weight, oreType));
            }
        } catch (Exception e) {
            RekindleUnderground.getInstance().LOGGER.warn("Error in valid deposit ore reading.");
            e.printStackTrace();
            return null;
        }
        return new WeightedProbMap<OreType>(elts);
    }

    // Return an array list of valid StoneTypes from a JsonArray
    public static ArrayList<StoneType> getStones(JsonArray jsonArray) {
        ArrayList<StoneType> stoneArray = new ArrayList<>();
            try {
                for (int i=0; i<jsonArray.size(); i++) {
                    stoneArray.add(StoneType.valueOf(jsonArray.get(i).getAsString().toUpperCase()));
                }
            } catch (Exception e) {
                RekindleUnderground.getInstance().LOGGER.warn("Error in deposit valid stone reading.");
                e.printStackTrace();
                return null;
            }
        return stoneArray;
    }


}
