package com.jemmerl.jemsgeology.events;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.commands.JacksonCmd;
import com.jemmerl.jemsgeology.commands.OreWallCmd;
import com.jemmerl.jemsgeology.events.loot.StoneQuarryModifier;
import com.jemmerl.jemsgeology.init.featureinit.VanillaFeatureRemovers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new JacksonCmd(event.getDispatcher());
        new OreWallCmd(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

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
