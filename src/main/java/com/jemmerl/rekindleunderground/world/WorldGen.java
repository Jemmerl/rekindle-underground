package com.jemmerl.rekindleunderground.world;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

// Credit goes to Project Rankine (CannoliCatfish and tritespartan17) for the basis of this class; edited to fit needs
// https://github.com/CannoliCatfish/project-rankine/tree/1.16-1.3.X

@Mod.EventBusSubscriber
public class WorldGen {

    // Use to add intrusions, biome specifics
    private static List<LinkedHashMap.SimpleEntry<ConfiguredFeature<?,?>, List<ResourceLocation>>> getAllStoneFeatures() {
        List<LinkedHashMap.SimpleEntry<ConfiguredFeature<?, ?>, List<ResourceLocation>>> allStoneFeatures = new ArrayList<>();

        allStoneFeatures.add(new AbstractMap.SimpleEntry<>(RKUndergroundFeatures.STONE_GEN_CONFIG, null));
        allStoneFeatures.add(new AbstractMap.SimpleEntry<>(RKUndergroundFeatures.DIATREME_PIPE_GEN_CONFIG, null));

        //allStoneFeatures.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.WORLD_REPLACER_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));

        return allStoneFeatures;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void addWorldGenFeatures(final BiomeLoadingEvent biomeLoadingEvent) {
        if (biomeLoadingEvent.getName() != null) {
            GenerationStage.Decoration undergroundDecStage = GenerationStage.Decoration.UNDERGROUND_ORES;

            for (LinkedHashMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : getAllStoneFeatures()) {
                if ((entry.getValue() == null) || (entry.getValue().contains(biomeLoadingEvent.getName()))) {
                    biomeLoadingEvent.getGeneration().withFeature(undergroundDecStage.ordinal(),entry::getKey);
                }
            }
        }
    }




}
