package com.jemmerl.jemsgeology.data.generators.server;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModTags;
import com.jemmerl.jemsgeology.init.blockinit.GeoRegistry;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Jem's Geo Item Tags";
    }

    @Override
    protected void registerTags() {
        Builder<Item> tagBuilderGeoRocks = this.getOrCreateBuilder(ModTags.Items.JEMSGEO_ROCKS);

        for (GeoRegistry geoRegistry: ModBlocks.GEOBLOCKS.values()) {
            if (geoRegistry.hasCobble()) {
                tagBuilderGeoRocks.add(geoRegistry.getRockItem());
            }
        }
    }
}
