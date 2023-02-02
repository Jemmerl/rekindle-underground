package com.jemmerl.rekindleunderground.util;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class UtilMethods {

    // Returns a float over a given range mapped to a new range
    public static float remap(float oldVal, float[] oldDomain, float[] newDomain) {
        float oldRange = oldDomain[1] - oldDomain[0];
        float oldRangeValue = oldVal - oldDomain[0];
        float percentile = oldRangeValue / oldRange;

        float newRange = newDomain[1] - newDomain[0];
        float newRangeValue = percentile * newRange;
        float newVal = newRangeValue + newDomain[0];

        return newVal;
    }

    // Returns a block given a string representation of its resource location
    // Reliance on this should be minimized
    public static BlockState stringToBlockState(String blockName) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName))).getDefaultState();
    }

    // Returns an item given a string representation of its resource location
    // Reliance on this should be minimized
    public static Item stringToItem(String itemName) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
    }

    // Returns the unit vector for a given vector of any size
    public static double[] getUnitVector(double[] vals) {
        double squareSum = 0;
        for (double n:vals) {
            squareSum += n*n;
        }
        double magnitude = Math.cbrt(squareSum);
        double[] unitVec = new double[vals.length];
        for (int i = 0; i < vals.length; i++) {
            unitVec[i] = (vals[i] / magnitude);
        }
        return unitVec;
    }

    // Return the hypotenuse of two values
    public static double getHypotenuse(int a1, int b1, int a2, int b2) {
        return Math.hypot((a1 - a2), (b1 - b2));
    }

    // Convert vanilla detritus to OreBlock detritus; If not convertible, return original state
    public static BlockState convertVanillaToDetritus(BlockState vanillaState) {
        return ModBlockLists.VANILLA_DET_LIST.getOrDefault(vanillaState, vanillaState);
    }

}
