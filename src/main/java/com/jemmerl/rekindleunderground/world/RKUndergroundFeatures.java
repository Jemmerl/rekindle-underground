package com.jemmerl.rekindleunderground.world;

import com.jemmerl.rekindleunderground.world.feature.StoneGeneration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import com.jemmerl.rekindleunderground.util.FeatureRegistrationHelper;

// Credit goes to Unearthed and lilypuree for this class; edited to fit needs
// https://github.com/lilypuree/UnEarthed/tree/Forge-1.16.X

public class RKUndergroundFeatures {
    public static final Feature<NoFeatureConfig> STONE_NO_CONFIG = FeatureRegistrationHelper.registerFeature("stone_generator", new StoneGeneration(NoFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> STONE_CONFIG = FeatureRegistrationHelper.newConfiguredFeature("new_generator",
            RKUndergroundFeatures.STONE_NO_CONFIG.withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(new NoPlacementConfig())));
}
