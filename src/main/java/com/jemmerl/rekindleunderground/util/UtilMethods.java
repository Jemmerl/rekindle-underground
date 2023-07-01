package com.jemmerl.rekindleunderground.util;

import com.jemmerl.rekindleunderground.blocks.IOreBlock;
import com.jemmerl.rekindleunderground.data.enums.StoneGroupType;
import com.jemmerl.rekindleunderground.util.lists.ModBlockLists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
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

//    // Copy blockstate property from one similar block to another
//    public static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
//        return to.with(property, from.get(property));
//    }

    // Convert vanilla detritus to OreBlock detritus; If not convertible, return original state
    public static BlockState convertVanillaToDetritus(BlockState vanillaState) {
        return ModBlockLists.VANILLA_DET_LIST.getOrDefault(vanillaState, vanillaState);
    }

    // Check if the block is some form of vanilla stone
    public static boolean isVanillaStone (Block block) {
        return (block.isIn(Tags.Blocks.OBSIDIAN) || block.isIn(BlockTags.BASE_STONE_OVERWORLD)
                || block.isIn(Tags.Blocks.ORES) || block.equals(Blocks.SANDSTONE) || block.equals(Blocks.RED_SANDSTONE));
    }

    // Check if the block is some form of OreBlock stone
    public static boolean isOreBlockStone (Block block) {
        return ((block instanceof IOreBlock) && !((IOreBlock) block).getStoneGroupType().equals(StoneGroupType.DETRITUS));
    }

    // Check if the block is some form of vanilla detritus
    public static boolean isVanillaDetritus(Block block) {
        return (block.isIn(Tags.Blocks.DIRT) || block.isIn(Tags.Blocks.SAND)
                || block.isIn(Tags.Blocks.GRAVEL));
    }

    // Check if the block is some form of OreBlock detritus
    public static boolean isOreBlockDetritus (Block block) {
        return ((block instanceof IOreBlock) && ((IOreBlock) block).getStoneGroupType().equals(StoneGroupType.DETRITUS));
    }

    // Check if the block is some form of stone (vanilla or oreblock)
    public static boolean isStoneBlock (Block block) {
        return (isOreBlockStone(block) || isVanillaStone(block));
    }

    // Check if the block is some form of vanilla detritus
    public static boolean isDetritusBlock(Block block) {
        return (isVanillaDetritus(block) || isOreBlockDetritus(block));
    }

    // Check a block's type for evaluating its replaceability
    public static ReplaceableStatus replaceableStatus(BlockState blockState) {
        Block replaced = blockState.getBlock();

        if (isOreBlockStone(replaced)) {
            return ReplaceableStatus.OREBLOCK_STONE;
        }

        if (isVanillaStone(replaced)) {
            return ReplaceableStatus.VANILLA_STONE;
        }

        if (isOreBlockDetritus(replaced)) {
            return ReplaceableStatus.OREBLOCK_DETRITUS;
        }

        if (isVanillaDetritus(replaced)) {
            return ReplaceableStatus.VANILLA_DETRITUS;
        }

        // If not any stone or detritus,
        return ReplaceableStatus.FAILED;
    }

}
