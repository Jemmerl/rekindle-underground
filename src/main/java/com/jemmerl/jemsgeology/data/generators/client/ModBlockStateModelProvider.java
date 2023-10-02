package com.jemmerl.jemsgeology.data.generators.client;

import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.blockinit.GeoRegistry;
import com.jemmerl.jemsgeology.util.lists.ModBlockLists;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Locale;
import java.util.Objects;

public class ModBlockStateModelProvider extends BlockStateProvider {

    public ModBlockStateModelProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (GeoRegistry geoRegistry: ModBlocks.GEOBLOCKS.values()) {
            // Base stone/ores
            for (Block block: geoRegistry.getStoneGeoBlocks()) {
                if (block instanceof IGeoBlock) {
                    buildSimpleOreBlock(block,
                            Objects.requireNonNull(geoRegistry.getBaseStone().getRegistryName()).getPath());
                }
            }

            // Cobble based blocks and regolith/ores
            if (geoRegistry.hasCobble()) {

                if (geoRegistry.getGeoType() != GeologyType.PAHOEHOE) {
                    simpleBlock(geoRegistry.getCobbles());
                    simpleBlock(geoRegistry.getCobblestone());

                    for (Block block: geoRegistry.getRegolithGeoBlocks()) {
                        if (block instanceof IGeoBlock) {
                            buildSimpleOreBlock(block,
                                    Objects.requireNonNull(geoRegistry.getRegolith().getRegistryName()).getPath());
                        }
                    }
                }
            }
        }
    }


    // Construct the state and model for a block with ore
    private void buildSimpleOreBlock(Block oreBlock, String basePath) {
        IGeoBlock geoBlock = (IGeoBlock) oreBlock;
        getVariantBuilder(oreBlock).partialState()
                .setModels(new ConfiguredModel(buildModelFile(
                        geoBlock.getGeologyType(), geoBlock.getOreType(), geoBlock.getGradeType(), basePath)));

    }


    // Return the appropriate model file for a given block based on if it has different side textures
    private ModelFile buildModelFile(GeologyType geologyType, OreType oreType, GradeType gradeType, String basePath) {
        if (ModBlockLists.SIDE_TEXTURE_MODELS.contains(geologyType)) {
            return buildModelDiffSides(basePath, oreType, gradeType);
        } else {
            return buildModelAllSides(basePath, oreType, gradeType);
        }
    }


    // Build the model file given the block path and property names with the same texture on all faces
    private ModelFile buildModelAllSides(String basePath, OreType oreType, GradeType gradeType) {
        ModelFile modelFile;

        if (!oreType.hasOre()) {
            modelFile = models().withExistingParent("block/" + basePath, mcLoc("block/cube"))
                    .texture("particle", modLoc("block/" + basePath))
                    .texture("up", modLoc("block/" + basePath))
                    .texture("down", modLoc("block/" + basePath))
                    .texture("north", modLoc("block/" + basePath))
                    .texture("south", modLoc("block/" + basePath))
                    .texture("east", modLoc("block/" + basePath))
                    .texture("west", modLoc("block/" + basePath));
        } else {
            String oreName = oreType.name().toLowerCase(Locale.ROOT);
            String gradeName = gradeType.name().toLowerCase(Locale.ROOT);

            modelFile = models().withExistingParent("block/blockore/" + basePath + "/" + oreName + "/" + gradeName,
                            modLoc("block/stone_ore_parent"))
                    .texture("all", modLoc("block/" + basePath))
                    .texture("particle", modLoc("block/" + basePath))
                    .texture("overlay", modLoc("block/ore/" + gradeType.getAssetName() + oreName));
        }

        return modelFile;
    }


    // Build the model file given the block path and property names with a different texture on the sides
    private ModelFile buildModelDiffSides(String basePath, OreType oreType, GradeType gradeType) {
        ModelFile modelFile;

        if (!oreType.hasOre()) {
            modelFile = models().withExistingParent("block/" + basePath, mcLoc("block/cube"))
                    .texture("particle", modLoc("block/" + basePath + "1"))
                    .texture("up", modLoc("block/" + basePath + "1"))
                    .texture("down", modLoc("block/" + basePath + "1"))
                    .texture("north", modLoc("block/" + basePath + "2"))
                    .texture("south", modLoc("block/" + basePath + "2"))
                    .texture("east", modLoc("block/" + basePath + "2"))
                    .texture("west", modLoc("block/" + basePath + "2"));
        } else {
            String oreName = oreType.name().toLowerCase(Locale.ROOT);
            String gradeName = gradeType.name().toLowerCase(Locale.ROOT);

            modelFile = models().withExistingParent("block/blockore/" + basePath + "/" + oreName + "/" + gradeName,
                            modLoc("block/stone_ore_parent_sides"))
                    .texture("particle", modLoc("block/" + basePath + "1"))
                    .texture("ends", modLoc("block/" + basePath + "1"))
                    .texture("sides", modLoc("block/" + basePath + "2"))
                    .texture("overlay", modLoc("block/ore/" + gradeType.getAssetName() + oreName));
        }

        return modelFile;
    }

}
