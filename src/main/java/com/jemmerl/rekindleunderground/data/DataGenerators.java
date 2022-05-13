package com.jemmerl.rekindleunderground.data;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.data.generators.client.ModBlockStateProvider;
import com.jemmerl.rekindleunderground.data.generators.client.ModItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = RekindleUnderground.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModBlockStateProvider(gen, RekindleUnderground.MOD_ID, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, RekindleUnderground.MOD_ID, existingFileHelper));

//        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
//        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
//        gen.addProvider(blockTags);
//        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
//        gen.addProvider(new ModLootTableProvider(gen));
//        gen.addProvider(new ModRecipeProvider(gen));
    }
}
