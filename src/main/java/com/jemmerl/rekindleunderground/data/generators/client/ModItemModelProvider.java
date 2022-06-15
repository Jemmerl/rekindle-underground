package com.jemmerl.rekindleunderground.data.generators.client;

import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.Collection;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        String path;

        // Generate block item models
        for (RegistryObject<Block> regBlock : ModBlocks.BLOCKS.getEntries()) {
            path = regBlock.getId().getPath();
            ModelFile blockItemGenerated = getExistingFile(modLoc("block/" + path));
            getBuilder(path).parent(blockItemGenerated);
        }

        // Generate item models excluding blocks
        for (RegistryObject<Item> regItem : ModItems.ITEMS.getEntries()) {
            path = regItem.getId().getPath();
            if (!path.contains("_stone") && !path.contains("_cobblestone")) {
                getBuilder(path).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + path);
            }
        }
    }
}
