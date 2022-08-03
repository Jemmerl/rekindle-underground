package com.jemmerl.rekindleunderground.world.feature;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.world.feature.ignfeats.DiatremeMaarFeature;
import com.jemmerl.rekindleunderground.world.feature.orefeats.OreTestFeat;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// Credit goes to Project Rankine (CannoliCatfish and tritespartan17) for the basis of this class; edited to fit needs
// https://github.com/CannoliCatfish/project-rankine/tree/1.16-1.3.X

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, RekindleUnderground.MOD_ID);

    public static final RegistryObject<Feature<NoFeatureConfig>> STONE_GEN
            = FEATURES.register("stone_gen", () -> new StoneGenFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> DIATREME_PIPE_GEN
            = FEATURES.register("diatreme_pipe_gen", () -> new DiatremeMaarFeature(NoFeatureConfig.CODEC));

    // TODO test
    public static final RegistryObject<Feature<NoFeatureConfig>> ORE_TEST_GEN
            = FEATURES.register("ore_test_gen", () -> new OreTestFeat(NoFeatureConfig.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus); }
}
