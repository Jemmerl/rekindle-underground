package com.jemmerl.rekindleunderground.data.generators.client;

import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.block.custom.FallingOreBlock;
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
                    String oreStateName = state.get(StoneOreBlock.ORE_TYPE).name().toLowerCase(Locale.ROOT);
                    String gradeStateName = state.get(StoneOreBlock.GRADE_TYPE).name().toLowerCase(Locale.ROOT);

                    return ConfiguredModel.builder()
                            .modelFile(buildModelFile(tempFinalBlockPath, oreStateName, gradeStateName))
                            .build();
                });

            } else if (block instanceof FallingOreBlock) {

                // Generate stone ore model and block states
                blockPath = block.getRegistryName().getPath();
                final String tempFinalBlockPath = blockPath;
                getVariantBuilder(block).forAllStates(state -> {
                    String oreStateName = state.get(FallingOreBlock.ORE_TYPE).name().toLowerCase(Locale.ROOT);
                    String gradeStateName = state.get(FallingOreBlock.GRADE_TYPE).name().toLowerCase(Locale.ROOT);

                    return ConfiguredModel.builder()
                            .modelFile(buildModelFile(tempFinalBlockPath, oreStateName, gradeStateName))
                            .build();
                });

            } else {
                simpleBlock(block);
            }
        }
    }

    // Build the model file given the block path and property names
    private ModelFile buildModelFile(String tempFinalBlockPath, String oreStateName, String gradeStateName) {
        ModelFile modelFile;
        if (oreStateName.equals("none")) {
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
            String stateName;

            if (gradeStateName.equals("highgrade")) {
                stateName = "rich_" + oreStateName;
            } else if (gradeStateName.equals("midgrade")) {
                stateName = oreStateName;
            } else {
                stateName = "poor_" + oreStateName;
            }

            modelFile = models().withExistingParent("block/stoneore/" + tempFinalBlockPath + "/" + stateName,
                            modLoc("block/stone_ore_parent"))
                    .texture("all", modLoc("block/" + tempFinalBlockPath))
                    .texture("particle", modLoc("block/" + tempFinalBlockPath))
                    .texture("overlay", modLoc("block/ore/" + stateName));
        }

        return modelFile;
    }

}
