package com.jemmerl.rekindleunderground.world.feature.stonegenutil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockPicker {

    // Shale, Mudstone //TODO add siltstone???
    private static final ImmutableList<Block> SEDIMENTARY_SOIL = ImmutableList.of(ModBlocks.SHALE.get(), ModBlocks.MUDSTONE.get());

    // Sandstone, Red Sandstone, Greywacke
    private static final ImmutableList<Block> SEDIMENTARY_SANDY = ImmutableList.of(ModBlocks.SANDSTONE.get(), ModBlocks.RED_SANDSTONE.get(), ModBlocks.GREYWACKE.get());

    // Chalk, Limestone, Dolostone
    private static final ImmutableList<Block> SEDIMENTARY_CARBONATE = ImmutableList.of(ModBlocks.CHALK.get(), ModBlocks.LIMESTONE.get(), ModBlocks.DOLOSTONE.get());

    // Rock Salt, Rock Gypsum, Kernite, Borax
    private static final ImmutableList<Block> SEDIMENTARY_EVAPORATE = ImmutableList.of(ModBlocks.ROCK_SALT.get(), ModBlocks.ROCK_GYPSUM.get(), ModBlocks.KERNITE.get(), ModBlocks.BORAX.get());

    // Dacite, Andesite, Basalt
    private static final ImmutableList<Block> EXTRUSIVE_IGNEOUS_MAIN = ImmutableList.of(ModBlocks.DACITE.get(), ModBlocks.ANDESITE.get(), ModBlocks.BASALT.get());

    // Rhyolite, Scoria, Obsidian
    private static final ImmutableList<Block> EXTRUSIVE_IGNEOUS_AUX = ImmutableList.of(ModBlocks.RHYOLITE.get(), ModBlocks.SCORIA.get(), Blocks.OBSIDIAN);

    // Quartzite, Schist, Phyllite, Gneiss, Marble
    private static final ImmutableList<Block> METAMORPHIC = ImmutableList.of(ModBlocks.QUARTZITE.get(), ModBlocks.SCHIST.get(), ModBlocks.PHYLLITE.get(), ModBlocks.GNEISS.get(), ModBlocks.MARBLE.get());

    static final ImmutableMap<String, ImmutableList<Block>> BLOCK_LISTS = ImmutableMap.<String, ImmutableList<Block>>builder()
            .put("sedimentary_soil", SEDIMENTARY_SOIL)
            .put("sedimentary_sandy", SEDIMENTARY_SANDY)
            .put("sedimentary_carbonate", SEDIMENTARY_CARBONATE)
            .put("sedimentary_evaporate", SEDIMENTARY_EVAPORATE)
            .put("extrusive_igneous_main", EXTRUSIVE_IGNEOUS_MAIN)
            .put("extrusive_igneous_aux", EXTRUSIVE_IGNEOUS_AUX)
            .put("metamorphic", METAMORPHIC)
            .build();

    // TODO Rework as enums

    // Picks a block to use for a layer given the options in form of a list of blockstates
    public static BlockState selectBlock(List<BlockState> stateList, float regionNoise, float strataVal) {
        // The current region will always have the same seed
        // Thus, the current region will always have the same shuffled list
        Collections.shuffle(stateList, new Random((int)(regionNoise * 10000)));
        return stateList.get((int)(UtilMethods.remap(strataVal, new float[]{-1f, 1f}, new float[]{0, stateList.size()})));
    }

    // Combines selected block presets and individual misc. blocks into one list of blockstates
    public static List<BlockState> buildStateList(List<String> blockListNames, List<Block> blocks) {
        List<BlockState> stateList = new ArrayList<>();

        // Add block presets to possible blocks
        for (String name : blockListNames) {
            BLOCK_LISTS.get(name).forEach((val) -> stateList.add(val.getDefaultState()));
        }
        // Add specific blocks to possible blocks
        for (Block i : blocks) {
            stateList.add(i.getDefaultState());
        }
        return stateList;
    }

    // Generates a list of blockstates from randomly selected block presets and individual misc. blocks
    public static List<BlockState> getRandomPresets(float regionNoise, int numPick, List<String> blockListNames, List<Block> blocks) {
        // The current region will always have the same seed
        // Thus, the current region will always have the same shuffled list
        Collections.shuffle(blockListNames, new Random((int)(regionNoise * -9050)));

        // Removes the difference between the given options and requested options and returns the remaining
        // Same result as selecting randomly and building a new list, but better
        // Has (some) built in error handling; if requesting >= given options, naturally returns all options
        for (int i = (blockListNames.size() - numPick); i > 0; i--) {
            blockListNames.remove(0);
        }

        return buildStateList(blockListNames, blocks);
    }







}
