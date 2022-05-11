package com.jemmerl.rekindleunderground.data.generators.client;

import com.google.common.base.Preconditions;
import com.google.gson.*;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.DataGenerators;
import com.jemmerl.rekindleunderground.data.types.OreType;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class OreStoneBlockStateProvider extends BlockStateProvider {

    private DataGenerator dataGenerator;

    public OreStoneBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
        this.dataGenerator = gen;
    }

    @Override
    protected void registerStatesAndModels() {
        Block block;
        String blockPath;
        for (RegistryObject<Block> regBlock : ModBlocks.BLOCKS.getEntries()) {
            block = regBlock.get().getBlock();
            if (block instanceof StoneOreBlock) {

                // Generate stone ore model and block states
                blockPath = block.getRegistryName().getPath();
                final String tempFinalBlockPath = blockPath;
                getVariantBuilder(block).forAllStates(state -> {
                    String stateName = state.get(StoneOreBlock.ORE_TYPE).name().toLowerCase(Locale.ROOT);

                    ModelFile modelFile;
                    if (Objects.equals(stateName, "none")) {
                        modelFile = models().withExistingParent("block/" + tempFinalBlockPath,
                                        mcLoc("block/cube"))
                                .texture("up", modLoc("block/" + tempFinalBlockPath))
                                .texture("down", modLoc("block/" + tempFinalBlockPath))
                                .texture("north", modLoc("block/" + tempFinalBlockPath))
                                .texture("south", modLoc("block/" + tempFinalBlockPath))
                                .texture("east", modLoc("block/" + tempFinalBlockPath))
                                .texture("west", modLoc("block/" + tempFinalBlockPath));
                    } else {
                        modelFile = models().withExistingParent("block/stoneore/" + tempFinalBlockPath + "/" + stateName,
                                        modLoc("block/stone_ore_parent"))
                                .texture("all", modLoc("block/" + tempFinalBlockPath))
                                .texture("overlay", modLoc("block/ore/" + stateName));
                    }
                    return ConfiguredModel.builder().modelFile(modelFile).build();
                });
            } else {
                simpleBlock(block);
            }
        }
    }

    //TODO item models and things like particles

}
