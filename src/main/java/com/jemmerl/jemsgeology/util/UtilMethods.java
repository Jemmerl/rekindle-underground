package com.jemmerl.jemsgeology.util;

import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.blocks.StoneGeoBlock;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.util.lists.ModBlockLists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;
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

    // Returns the 2D unit vector using 2D coordinates
    public static float[] points2DUnitVec(float x, float y) {
        float magnitude = (float)Math.sqrt(x*x + y*y);
        return new float[]{(x / magnitude), (y / magnitude)};
    }

    // Returns the 3D unit vector using 3D coordinates
    public static float[] points3DUnitVec(float x, float y, float z) {
        float magnitude = (float)Math.sqrt(x*x + y*y + z*z);
        return new float[]{(x / magnitude), (y / magnitude), (z / magnitude)};
    }

    // Returns the 2D unit vector using an angle in radians
    public static float[] angles2DUnitVec(float alpha) {
        return new float[]{((float)Math.cos(alpha)), ((float)Math.sin(alpha))};
    }

    // Returns the 3D unit vector using two angles in radians
    public static float[] angles3DUnitVec(float alpha, float beta) {
        float cosB = (float)Math.cos(beta);
        return new float[]{(cosB * (float)Math.sin(alpha)), ((float)Math.sin(beta)), (cosB * (float)Math.cos(alpha))};
    }

    // Return the 2D distance between two variables
    public static double getDistance2D(int x1, int z1, int x2, int z2) {
        return Math.hypot((x1 - x2), (z1 - z2));
    }

    // Return the 3D distance squared between two integer-based variables
    public static float getSquareDistance3D(int x1, int y1, int z1, int x2, int y2, int z2) {
        return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1));
    }

    // Return the 3D distance squared between two float-based variables
    public static float getSquareDistance3D(float x1, float y1, float z1, float x2, float y2, float z2) {
        return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1));
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
        return (block instanceof StoneGeoBlock);
    }

    // Check if the block is some form of vanilla detritus
    public static boolean isVanillaDetritus(Block block) {
        return (block.isIn(Tags.Blocks.DIRT) || block.isIn(Tags.Blocks.SAND)
                || block.isIn(Tags.Blocks.GRAVEL));
    }

    // Check if the block is some form of OreBlock detritus
    public static boolean isOreBlockDetritus (Block block) {
        return ((block instanceof IGeoBlock) && ((IGeoBlock) block).getStoneGroupType().equals(StoneGroupType.DETRITUS));
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

    // Rotate a face in a relative direction :)
    /*
        Assumes "UP" rotations return the input whereas "DOWN" returns its opposite
        Assumes N,S,E,W to be the relative directions of up, down, right, and left respectively
        The result is rotation in any relative direction based off the starting face
        You can confirm this by building a 6x6 logic table of the input face and rotation directions and its outputs!
     */
    public static Direction rotateDirection(Direction face, Direction rotation) {
        // Rotations based on input face (UP and DOWN)
        switch (face) {
            case UP:
                return rotation;
            case DOWN:
                return rotation.getOpposite();
            default:
        }

        // Rotations based on rotation direction (N, S, E, W)
        switch (rotation) {
            case NORTH:
                return Direction.UP;
            case SOUTH:
                return Direction.DOWN;
            case EAST:
                return face.rotateYCCW();
            case WEST:
                return face.rotateY();
            case DOWN:
                return face.getOpposite();
            case UP:
            default:
                return face;
        }
    }

    // Adds a value to specific BlockPos ordinate(s)
    public static BlockPos addToOrdinate(BlockPos pos, float add, int flag) {
        switch (flag) {
            case 0:
                return pos.add(add, 0, 0);
            case 1:
                return pos.add(0, add, 0);
            case 2:
                return pos.add(0, 0, add);
            case 3:
                return pos.add(add, add, 0);
            case 4:
                return pos.add(0, add, add);
            case 5:
                return pos.add(add, 0, add);
//            case 3:
//                return pos.add(add, add, add);
            default:
                return pos;
        }
    }
}
