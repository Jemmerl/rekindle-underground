package com.jemmerl.rekindleunderground.data.generators.client;

import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.Locale;
import java.util.Objects;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
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
                                .texture("particle", modLoc("block/" + tempFinalBlockPath))
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
                                .texture("particle", modLoc("block/" + tempFinalBlockPath))
                                .texture("overlay", modLoc("block/ore/" + stateName));
                    }
                    return ConfiguredModel.builder().modelFile(modelFile).build();
                });
            } else {
                simpleBlock(block);
            }
        }
    }
}
