package com.jemmerl.rekindleunderground.data.generators.client;

import com.jemmerl.rekindleunderground.init.ModBlocks;
import com.jemmerl.rekindleunderground.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        // Generate block item models
        for (RegistryObject<Block> regBlock : ModBlocks.BLOCKS.getEntries()) {
            String path;
            path = regBlock.getId().getPath();
            ModelFile blockItemGenerated = getExistingFile(modLoc("block/" + path));
            getBuilder(path).parent(blockItemGenerated);
        }

        // Generate item models excluding blocks
        for (RegistryObject<Item> regItem : ModItems.ITEMS.getEntries()) {
            String path;
            path = regItem.getId().getPath();
            if (!path.contains("_stone") && !path.contains("_regolith") && !path.contains("_cobblestone")) {
                getBuilder(path).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + path);
            }
        }
    }
}
