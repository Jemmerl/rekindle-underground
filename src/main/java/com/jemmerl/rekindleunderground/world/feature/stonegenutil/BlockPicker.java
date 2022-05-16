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

    public static BlockState selectBlock(List<BlockState> stateList, float regionVal, float strataVal) {
        int randSeed = (int)(regionVal * 10000); // The current region will always have the same seed
        Collections.shuffle(stateList, new Random(randSeed)); // Thus, the current region will always have the same shuffled list
        return stateList.get((int)(UtilMethods.remap(strataVal, new float[]{-1f, 1f}, new float[]{0, stateList.size()})));
    }

    public static List<BlockState> buildStateList(List<String> blockListNames, List<Block> blocks) {
        List<BlockState> stateList = new ArrayList<>();

        // Add block lists to possible strata
        for (String name : blockListNames) {
            BLOCK_LISTS.get(name).forEach((val) -> stateList.add(val.getDefaultState()));
        }
        // Add specific blocks to possible strata
        for (Block i : blocks) {
            stateList.add(i.getDefaultState());
        }
        return stateList;
    }







}
