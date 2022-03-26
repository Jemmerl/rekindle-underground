package com.jemmerl.rekindleunderground;

import com.jemmerl.rekindleunderground.world.feature.StoneGeneration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import com.jemmerl.rekindleunderground.util.FeatureRegistrationHelper;

public class RKUndergroundFeatures {
    public static final Feature<NoFeatureConfig> STONE_NO_CONFIG = FeatureRegistrationHelper.registerFeature("stone_generator", new StoneGeneration(NoFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> STONE_CONFIG = FeatureRegistrationHelper.newConfiguredFeature("new_generator",
            RKUndergroundFeatures.STONE_NO_CONFIG.withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(new NoPlacementConfig())));
}
