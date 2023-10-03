package com.jemmerl.jemsgeology.data.generators.client;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        // Generate block item models
        for (RegistryObject<Block> regBlock : ModBlocks.BLOCKS.getEntries()) {
            String path = regBlock.getId().getPath();
            String regName = Objects.requireNonNull(regBlock.get().getRegistryName()).toString();

            if (!(regName.contains("pahoehoe") && !regName.contains("_stone"))) {
                ModelFile blockItemGenerated;
                if (regName.contains("grade")) {
                    blockItemGenerated = getExistingFile(modLoc("block/blockore/" + path));
                } else {
                    if (path.contains("wall")) {
                        blockItemGenerated = getExistingFile(modLoc("block/" + path + "_inventory"));
                        getBuilder("item/" + path).parent(blockItemGenerated);
//                        ModelFile modelFile;
//                        modelFile = models().withExistingParent("block/" +
//                                Objects.requireNonNull(geoRegistry.getCobbleWall().getRegistryName()).getPath() +
//                                "_inventory", modLoc("block/wall_inventory")).texture("wall", cobbleRL);
//
//                        getBuilder("item/" + path).parent(getExistingFile(mcLoc( "block/wall_inventory")))
//                                .texture("wall", "block/" + path);
                        continue;
                    } else {
                        blockItemGenerated = getExistingFile(modLoc("block/" + path));
                    }
                }
                getBuilder("item/" + path).parent(blockItemGenerated);
            }
        }

        // Generate item models excluding blocks
        for (RegistryObject<Item> regItem : ModItems.ITEMS.getEntries()) {
            String path = regItem.getId().getPath();
            if (!path.contains("_stone") && !path.contains("_detritus") && !path.contains("_regolith") &&
                    !path.contains("_cobbles") && !path.contains("pahoehoe") && !path.contains("slab") &&
                    !path.contains("stairs") && !path.contains("wall")) {
                getBuilder(path).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + path);
            }
        }
    }
}
