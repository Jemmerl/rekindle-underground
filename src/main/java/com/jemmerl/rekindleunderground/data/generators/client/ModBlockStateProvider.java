package com.jemmerl.rekindleunderground.data.generators.client;

import com.jemmerl.rekindleunderground.blocks.StoneOreBlock;
import com.jemmerl.rekindleunderground.util.lists.ModBlockLists;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for (Block block : Stream.concat(ModBlockLists.ALL_STONES.stream(), ModBlockLists.ALL_REGOLITH.stream()).collect(Collectors.toList())) {
            // Generate stone ore model and block states
//            String blockPath;
//            blockPath = block.getRegistryName().getPath();
//            final String tempFinalBlockPath = blockPath;
            getVariantBuilder(block).forAllStates(state -> {
                String oreStateName = state.get(StoneOreBlock.ORE_TYPE).name().toLowerCase(Locale.ROOT);
                String gradeStateName = state.get(StoneOreBlock.GRADE_TYPE).name().toLowerCase(Locale.ROOT);

                return ConfiguredModel.builder()
                        .modelFile(buildModelFile(block, oreStateName, gradeStateName))
                        .build();
            });
        }

        for (Block block : ModBlockLists.STABLE_DET) {
            // Generate stone ore model and block states
//            String blockPath;
//            blockPath = block.getRegistryName().getPath();
//            final String tempFinalBlockPath = blockPath;
            getVariantBuilder(block).forAllStates(state -> {
                String oreStateName = state.get(StoneOreBlock.ORE_TYPE).name().toLowerCase(Locale.ROOT);
                String gradeStateName = state.get(StoneOreBlock.GRADE_TYPE).name().toLowerCase(Locale.ROOT);

                return ConfiguredModel.builder()
                        .modelFile(buildModelFile(block, oreStateName, gradeStateName))
                        .build();
            });
        }

        for (Block block : ModBlockLists.FALLING_DET) {
            // Generate stone ore model and block states
//            String blockPath;
//            blockPath = block.getRegistryName().getPath();
//            final String tempFinalBlockPath = blockPath;
//            final Block tempFinalBlock = block;
            getVariantBuilder(block).forAllStates(state -> {
                String oreStateName = state.get(StoneOreBlock.ORE_TYPE).name().toLowerCase(Locale.ROOT);
                String gradeStateName = state.get(StoneOreBlock.GRADE_TYPE).name().toLowerCase(Locale.ROOT);

                return ConfiguredModel.builder()
                        .modelFile(buildModelFile(block, oreStateName, gradeStateName))
                        .build();
            });
        }

        for (Block block : ModBlockLists.COBBLESTONES) {
            simpleBlock(block);
        }


    }


    // Return the appropriate model file for a given block based on if it has different side textures
    private ModelFile buildModelFile(Block blockIn, String oreStateName, String gradeStateName) {
        String blockPath = blockIn.getRegistryName().getPath();

        if (ModBlockLists.SIDE_TEXTURE_MODELS.contains(blockIn)) {
            return buildModelDiffSides(blockPath, oreStateName, gradeStateName);
        } else {
            return buildModelAllSides(blockPath, oreStateName, gradeStateName);
        }
    }


    // Build the model file given the block path and property names with the same texture on all faces
    private ModelFile buildModelAllSides(String blockPath, String oreStateName, String gradeStateName) {
        ModelFile modelFile;

        if (oreStateName.equals("none")) {
            modelFile = models().withExistingParent("block/" + blockPath,
                            mcLoc("block/cube"))
                    .texture("particle", modLoc("block/" + blockPath))
                    .texture("up", modLoc("block/" + blockPath))
                    .texture("down", modLoc("block/" + blockPath))
                    .texture("north", modLoc("block/" + blockPath))
                    .texture("south", modLoc("block/" + blockPath))
                    .texture("east", modLoc("block/" + blockPath))
                    .texture("west", modLoc("block/" + blockPath));
        } else {
            String stateName;

            if (gradeStateName.equals("highgrade")) {
                stateName = "rich_" + oreStateName;
            } else if (gradeStateName.equals("midgrade")) {
                stateName = oreStateName;
            } else {
                stateName = "poor_" + oreStateName;
            }

            modelFile = models().withExistingParent("block/stoneore/" + blockPath + "/" + stateName,
                            modLoc("block/stone_ore_parent"))
                    .texture("all", modLoc("block/" + blockPath))
                    .texture("particle", modLoc("block/" + blockPath))
                    .texture("overlay", modLoc("block/ore/" + stateName));
        }

        return modelFile;
    }


    // Build the model file given the block path and property names with a different texture on the sides
    private ModelFile buildModelDiffSides(String blockPath, String oreStateName, String gradeStateName) {
        ModelFile modelFile;

        if (oreStateName.equals("none")) {
            modelFile = models().withExistingParent("block/" + blockPath,
                            mcLoc("block/cube"))
                    .texture("particle", modLoc("block/" + blockPath + "1"))
                    .texture("up", modLoc("block/" + blockPath + "1"))
                    .texture("down", modLoc("block/" + blockPath + "1"))
                    .texture("north", modLoc("block/" + blockPath + "2"))
                    .texture("south", modLoc("block/" + blockPath + "2"))
                    .texture("east", modLoc("block/" + blockPath + "2"))
                    .texture("west", modLoc("block/" + blockPath + "2"));
        } else {
            String stateName;

            if (gradeStateName.equals("highgrade")) {
                stateName = "rich_" + oreStateName;
            } else if (gradeStateName.equals("midgrade")) {
                stateName = oreStateName;
            } else {
                stateName = "poor_" + oreStateName;
            }

            modelFile = models().withExistingParent("block/stoneore/" + blockPath + "/" + stateName,
                            modLoc("block/stone_ore_parent_sides"))
                    .texture("particle", modLoc("block/" + blockPath + "1"))
                    .texture("ends", modLoc("block/" + blockPath + "1"))
                    .texture("sides", modLoc("block/" + blockPath + "2"))
                    .texture("overlay", modLoc("block/ore/" + stateName));
        }

        return modelFile;
    }

}
