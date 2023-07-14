package com.jemmerl.jemsgeology.events;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.init.featureinit.VanillaFeatureRemovers;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(JemsGeology.DEPOSIT_DATA_LOADER);
        event.addListener(JemsGeology.FEATURE_DATA_LOADER);
    }


    @SubscribeEvent(priority = EventPriority.LOW)
    public static void BiomeLoadingIntercept(final BiomeLoadingEvent event) {
        VanillaFeatureRemovers.processLakes(event);
        VanillaFeatureRemovers.processLocalModifications(event);
        VanillaFeatureRemovers.processUndergroundStructures(event);
        VanillaFeatureRemovers.processUndergroundOres(event);
        VanillaFeatureRemovers.processUndergroundDecorations(event);
        VanillaFeatureRemovers.processVegetalDecorations(event);

        VanillaFeatureRemovers.processStructures(event);

        // Currently not used
        // VanillaFeatureRemovers.processRawGeneration(event);

        // Currently not used
        // VanillaFeatureRemovers.processLocalModifications(event);
    }
}
