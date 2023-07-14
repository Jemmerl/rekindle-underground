package com.jemmerl.jemsgeology.init.featureinit;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;

// Disable features, structures, and carvers(? to be done, maybe) as configured
// Some code adapted from OreConfig by noahc3
// https://github.com/noahc3/oreconfig/blob/master/1.16/src/main/java/com/noahc3/oreconfig/intercepts/BiomeIntercept.java
public class VanillaFeatureRemovers {

    // Looks for water and lava lake features
    public static void processLakes(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.LAKES);
        List<Supplier<ConfiguredFeature<?, ?>>> disabled = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());
            if (resolved.feature instanceof LakesFeature) {
                BlockState fillState = ((BlockStateFeatureConfig)(resolved.getConfig())).state;

                if (JemsGeoConfig.SERVER.disable_water_lakes.get() && fillState.equals(Blocks.WATER.getDefaultState())) {
                    disabled.add(featureSupplier);

                    // Debug
                    if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                        JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: LAKE_WATER from Biome: {}", event.getName());
                    }

                    continue;
                }

                if (JemsGeoConfig.SERVER.disable_lava_lakes.get() && fillState.equals(Blocks.LAVA.getDefaultState())) {
                    disabled.add(featureSupplier);

                    // Debug
                    if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                        JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: LAKE_LAVA from Biome: {}", event.getName());
                    }
                }
            }
        }
        features.removeAll(disabled);
    }


    // Looks for forest_rock feature
    public static void processLocalModifications(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.LOCAL_MODIFICATIONS);
        List<Supplier<ConfiguredFeature<?, ?>>> disabled = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());

            if (JemsGeoConfig.SERVER.disable_forest_rocks.get() && (Objects.requireNonNull(resolved.feature.getRegistryName()).toString().equals("minecraft:forest_rock"))) {
                disabled.add(featureSupplier);

                // Debug
                if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                    JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: FOREST_ROCK from Biome: {}", event.getName());
                }
            }
        }
        features.removeAll(disabled);
    }


    // Looks for monster room, fossils, and mineshaft features
    public static void processUndergroundStructures(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
        List<Supplier<ConfiguredFeature<?, ?>>> disabled = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());
            String registryName = Objects.requireNonNull(resolved.feature.getRegistryName()).toString();

            if (JemsGeoConfig.SERVER.disable_forest_rocks.get() && (registryName.equals("minecraft:monster_room"))) {
                disabled.add(featureSupplier);

                // Debug
                if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                    JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: MONSTER_ROOM from Biome: {}", event.getName());
                }

                continue;
            }

            if (JemsGeoConfig.SERVER.disable_forest_rocks.get() && (registryName.equals("minecraft:fossil"))) {
                disabled.add(featureSupplier);

                // Debug
                if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                    JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: FOSSIL from Biome: {}", event.getName());
                }
            }
        }
        features.removeAll(disabled);
    }


    // Look for ore features
    public static void processUndergroundOres(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
        List<Supplier<ConfiguredFeature<?, ?>>> disabled = new ArrayList<>();

        List<BlockState> disableStates = new ArrayList<>();
        if (JemsGeoConfig.SERVER.disable_underground_dirt.get()) {
            disableStates.add(Blocks.DIRT.getDefaultState());
        }
        if (JemsGeoConfig.SERVER.disable_underground_gravel.get()) {
            disableStates.add(Blocks.GRAVEL.getDefaultState());
        }
        if (JemsGeoConfig.SERVER.disable_overworld_ore.get()) {
            disableStates.add(Blocks.COAL_ORE.getDefaultState());
            disableStates.add(Blocks.IRON_ORE.getDefaultState());
            disableStates.add(Blocks.GOLD_ORE.getDefaultState());
            disableStates.add(Blocks.REDSTONE_ORE.getDefaultState());
            disableStates.add(Blocks.LAPIS_ORE.getDefaultState());
            disableStates.add(Blocks.DIAMOND_ORE.getDefaultState());
            disableStates.add(Blocks.EMERALD_ORE.getDefaultState());
        }
        if (JemsGeoConfig.SERVER.disable_overworld_stone.get()) {
            disableStates.add(Blocks.GRANITE.getDefaultState());
            disableStates.add(Blocks.ANDESITE.getDefaultState());
            disableStates.add(Blocks.DIORITE.getDefaultState());
        }

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());

            BlockState state = null;
            if (resolved.feature instanceof OreFeature) {
                state = ((OreFeatureConfig) resolved.config).state;

                // Debug
                if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                    JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: ORE_{} from Biome: {}",
                            state.getBlock().toString().toUpperCase(Locale.ROOT), event.getName());
                }
            } else if (resolved.feature instanceof ReplaceBlockFeature) {
                state = ((ReplaceBlockConfig) resolved.config).state;

                // Debug
                if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                    JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: ORE_EMERALD from Biome: {}", event.getName());
                }
            }

            if (disableStates.contains(state)) {
                disabled.add(featureSupplier);
            }
        }
        features.removeAll(disabled);
    }


    // Looks for silverfish ore feature
    public static void processUndergroundDecorations(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION);
        List<Supplier<ConfiguredFeature<?, ?>>> disabled = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());

            if (JemsGeoConfig.SERVER.disable_silverfish.get()) {
                if ((resolved.feature instanceof OreFeature) && (((OreFeatureConfig) resolved.config).state == Blocks.INFESTED_STONE.getDefaultState())) {
                    disabled.add(featureSupplier);

                    // Debug
                    if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                        JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: ORE_INFESTED from Biome: {}", event.getName());
                    }
                }
            }
        }
        features.removeAll(disabled);
    }


    // Looks for water and lava spring features
    public static void processVegetalDecorations(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);
        List<Supplier<ConfiguredFeature<?, ?>>> disabled = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());

            if (resolved.feature instanceof SpringFeature && (resolved.config instanceof LiquidsConfig)) {
                LiquidsConfig liquidsConfig = (LiquidsConfig) resolved.config;
                BlockState fluidState = liquidsConfig.state.getBlockState();

                if (JemsGeoConfig.SERVER.disable_water_springs.get() && (fluidState.equals(Blocks.WATER.getDefaultState()))) {
                    disabled.add(featureSupplier);

                    // Debug
                    if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                        JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: SPRING_WATER from Biome: {}", event.getName());
                    }

                    continue;
                }

                if (JemsGeoConfig.SERVER.disable_lava_springs.get() && (fluidState.equals(Blocks.LAVA.getDefaultState()))) {
                    disabled.add(featureSupplier);

                    // Debug
                    if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                        JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: SPRING_LAVA from Biome: {}", event.getName());
                    }
                }
            }
        }
        features.removeAll(disabled);
    }

//    // Unused
//    public static void processRawGeneration(final BiomeLoadingEvent event) { }

//    // Unused
//    public static void processTopLayerModifications(final BiomeLoadingEvent event) { }


    // Remove specific structures (mineshafts, strongholds, and ruined portals)
    public static void processStructures(final BiomeLoadingEvent event) {
        List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();
        List<Supplier<StructureFeature<?, ?>>> disabled = new ArrayList<>();

        for (Supplier<StructureFeature<?, ?>> structureSupplier : structures) {
            String structureName = structureSupplier.get().field_236268_b_.getStructureName();

            if (JemsGeoConfig.SERVER.disable_mineshafts.get() && (structureName.equals("mineshaft"))) {
                disabled.add(structureSupplier);

                // Debug
                if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                    JemsGeology.getInstance().LOGGER.info("Removed a vanilla mineshaft structure from Biome: {}", event.getName());
                }

                continue;
            }

            if (JemsGeoConfig.SERVER.disable_ruined_portals.get() && (structureName.equals("ruined_portal"))) {
                disabled.add(structureSupplier);

                // Debug
                if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                    JemsGeology.getInstance().LOGGER.info("Removed a vanilla ruined_portal structure from Biome: {}", event.getName());
                }

                continue;
            }

            if (JemsGeoConfig.SERVER.disable_strongholds.get() && (structureName.equals("stronghold"))) {
                disabled.add(structureSupplier);

                // Debug
                if (JemsGeoConfig.SERVER.debug_vanilla_features.get()) {
                    JemsGeology.getInstance().LOGGER.info("Removed a vanilla stronghold structure from Biome: {}", event.getName());
                }
            }
        }
        structures.removeAll(disabled);


    }


    // As far as I can tell, this loops to find the actual feature below some unknown levels of abstraction
    private static ConfiguredFeature<?, ?> resolve(ConfiguredFeature<?, ?> f) {
        ConfiguredFeature<?, ?> subFeature = f;
        while (subFeature.getFeature() instanceof DecoratedFeature) {
            subFeature = ((DecoratedFeatureConfig) subFeature.getConfig()).feature.get();
        }
        return subFeature;
    }
}
