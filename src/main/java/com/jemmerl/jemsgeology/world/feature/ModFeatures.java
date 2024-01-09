package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// Credit goes to Project Rankine (CannoliCatfish and tritespartan17) for the basis of this class; edited to fit needs
// https://github.com/CannoliCatfish/project-rankine/tree/1.16-1.3.X

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, JemsGeology.MOD_ID);

    public static final RegistryObject<Feature<NoFeatureConfig>> STONE_GEN
            = FEATURES.register("stone_gen", () -> new GeologyFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> MAAR_DIATREME_GEN
            = FEATURES.register("maar_diatreme_gen", () -> new MaarDiatremeFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> BOULDER_GEN
            = FEATURES.register("boulder_gen", () -> new BoulderFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> ORE_PLACER_GEN
            = FEATURES.register("ore_placer_gen", () -> new OrePlacerFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> ORE_CONST_SCATTER_GEN
            = FEATURES.register("ore_const_scatter_gen", () -> new OreConstantScatterFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> DELAYED_ORE_GEN
            = FEATURES.register("delayed_ore_gen", () -> new DelayedOreFeature(NoFeatureConfig.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus); }
}
