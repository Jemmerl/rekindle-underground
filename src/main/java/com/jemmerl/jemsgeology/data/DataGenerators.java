package com.jemmerl.jemsgeology.data;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.generators.client.ModBlockStateProvider;
import com.jemmerl.jemsgeology.data.generators.client.ModItemModelProvider;
import com.jemmerl.jemsgeology.data.generators.server.ModCobblestoneRecipeProvider;
import com.jemmerl.jemsgeology.data.generators.server.ModLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // Client-side data providers
        gen.addProvider(new ModBlockStateProvider(gen, JemsGeology.MOD_ID, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, JemsGeology.MOD_ID, existingFileHelper));

        // Server-side data providers
        gen.addProvider(new ModCobblestoneRecipeProvider(gen));
        gen.addProvider(new ModLootTableProvider(gen));

//        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
//        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
//        gen.addProvider(blockTags);
//        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
//        gen.addProvider(new ModLootTableProvider(gen));
//        gen.addProvider(new ModRecipeProvider(gen));
    }
}
