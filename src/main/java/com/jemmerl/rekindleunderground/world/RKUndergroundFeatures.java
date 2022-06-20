package com.jemmerl.rekindleunderground.world;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.world.feature.ModFeatures;
import com.jemmerl.rekindleunderground.world.placements.ModFeaturePlacements;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;

// Credit goes to Project Rankine (CannoliCatfish and tritespartan17) for the basis of this class; edited to fit needs
// https://github.com/CannoliCatfish/project-rankine/tree/1.16-1.3.X

public class RKUndergroundFeatures {

    public static final ConfiguredFeature<?, ?> STONE_GEN_CONFIG = ModFeatures.STONE_GEN.get().withConfiguration(new NoFeatureConfig())
            .withPlacement(ModFeaturePlacements.SIMPLE_EMPTY_PLACEMENT.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

    private static <FC extends IFeatureConfig> void CFRegister(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(RekindleUnderground.MOD_ID, name), configuredFeature);
    }

    public static void registerConfiguredFeatures() {
        CFRegister("stone_generator", STONE_GEN_CONFIG);
    }

}
