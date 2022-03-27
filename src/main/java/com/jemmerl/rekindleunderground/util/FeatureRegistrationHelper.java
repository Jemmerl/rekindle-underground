package com.jemmerl.rekindleunderground.util;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.ArrayList;
import java.util.List;

// Credit goes to Unearthed and lilypuree for this class; edited to fit needs
// https://github.com/lilypuree/UnEarthed/tree/Forge-1.16.X

public class FeatureRegistrationHelper {

    public static List<Feature<?>> features = new ArrayList<>();

    public static ConfiguredFeature<?, ?> newConfiguredFeature(String registryName, ConfiguredFeature<?, ?> configuredFeature) {
        if (!WorldGenRegistries.CONFIGURED_FEATURE.keySet().contains(new ResourceLocation(RekindleUnderground.MOD_ID, registryName)))
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(RekindleUnderground.MOD_ID, registryName), configuredFeature);
        return configuredFeature;
    }

    public static <T extends IFeatureConfig, G extends Feature<T>> G registerFeature(String registryName, G feature) {
        if (!Registry.FEATURE.keySet().contains(new ResourceLocation(RekindleUnderground.MOD_ID, registryName)))
            feature.setRegistryName(new ResourceLocation(RekindleUnderground.MOD_ID, registryName));
        features.add(feature);
        return feature;
    }
}
