package com.jemmerl.rekindleunderground.mixin.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.jemmerl.rekindleunderground.RKUndergroundFeatures;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.chunk.listener.IChunkStatusListenerFactory;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.SaveFormat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// Credit goes to Unearthed and lilypuree for this class; edited to fit needs
// https://github.com/lilypuree/UnEarthed/tree/Forge-1.16.X

@Mixin(value = MinecraftServer.class, priority = 0)
public class MixinServerWorldGen {

    @Shadow
    @Final
    protected DynamicRegistries.Impl dynamicRegistries;

    @Inject(
            at = {@At("RETURN")},
            method = "<init>(Ljava/lang/Thread;Lnet/minecraft/util/registry/DynamicRegistries$Impl;Lnet/minecraft/world/storage/SaveFormat$LevelSave;Lnet/minecraft/world/storage/IServerConfiguration;Lnet/minecraft/resources/ResourcePackList;Ljava/net/Proxy;Lcom/mojang/datafixers/DataFixer;Lnet/minecraft/resources/DataPackRegistries;Lcom/mojang/authlib/minecraft/MinecraftSessionService;Lcom/mojang/authlib/GameProfileRepository;Lnet/minecraft/server/management/PlayerProfileCache;Lnet/minecraft/world/chunk/listener/IChunkStatusListenerFactory;)V",
            cancellable = true
    )

    private void replaceRKUStones(Thread serverThread, DynamicRegistries.Impl impl, SaveFormat.LevelSave anvilConverterForAnvilFile, IServerConfiguration serverConfig, ResourcePackList dataPacks, Proxy serverProxy, DataFixer dataFixer, DataPackRegistries dataRegistries, MinecraftSessionService sessionService, GameProfileRepository profileRepo, PlayerProfileCache profileCache, IChunkStatusListenerFactory chunkStatusListenerFactory, CallbackInfo ci) {
        System.out.println("An instance of SomeJavaFile has been created!");
        if (this.dynamicRegistries.getRegistry(Registry.BIOME_KEY) != null) {
            for (Biome biome : dynamicRegistries.getRegistry(Registry.BIOME_KEY)) {
                if (biome.getCategory() == Biome.Category.NETHER) {
                    // Nether biomes
                } else if ((biome.getCategory() != Biome.Category.THEEND) && (biome.getCategory() != Biome.Category.NONE)) {
                    if (biome.getCategory() == Biome.Category.EXTREME_HILLS) {
                        //addFeatureToBiome(biome, GenerationStage.Decoration.VEGETAL_DECORATION, UEFeatures.LICHEN_FEATURE);
                    }
                    //if (UnearthedConfig.disableGeneration.get()) {
                    //} else {
                    //}
                    addFeatureToBiome(biome, GenerationStage.Decoration.TOP_LAYER_MODIFICATION, RKUndergroundFeatures.STONE_CONFIG);
                    System.out.print(5556555);
                    removeFeatureFromBiome(biome, GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIRT, Features.ORE_GRANITE, Features.ORE_DIORITE, Features.ORE_ANDESITE);
                }
            }
        }
    }

    private static void addFeatureToBiome(Biome biome, GenerationStage.Decoration feature, ConfiguredFeature<?, ?> configuredFeature) {
        ConvertImmutableFeatures(biome);
        List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = biome.getGenerationSettings().getFeatures();
        while (biomeFeatures.size() <= feature.ordinal()) {
            biomeFeatures.add(Lists.newArrayList());
        }
        biomeFeatures.get(feature.ordinal()).add(() -> configuredFeature);
    }

    private static void removeFeatureFromBiome(Biome biome, GenerationStage.Decoration feature, ConfiguredFeature<?, ?>... configuredFeatures) {
        ConvertImmutableFeatures(biome);
        List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = biome.getGenerationSettings().getFeatures();
        while (biomeFeatures.size() <= feature.ordinal()) {
            biomeFeatures.add(Lists.newArrayList());
        }
        biomeFeatures.get(feature.ordinal()).removeIf(supplier -> {
            return supplier.get().getConfig() instanceof DecoratedFeatureConfig && Arrays.stream(configuredFeatures).anyMatch(configuredFeature -> serializeAndCompareFeature(configuredFeature, supplier.get()));
        });
    }

    private static void ConvertImmutableFeatures(Biome biome) {
        if (biome.getGenerationSettings().features instanceof ImmutableList) {
            biome.getGenerationSettings().features = biome.getGenerationSettings().getFeatures().stream().map(Lists::newArrayList).collect(Collectors.toList());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GENERAL UTILITIES // from TelepathicGrunt's Repurposed Structures Repo
    // https://github.com/TelepathicGrunt/RepurposedStructures

    /**
     * Will serialize (if possible) both features and check if they are the same feature.
     * If cannot serialize, compare the feature itself to see if it is the same.
     */
    private static boolean serializeAndCompareFeature(ConfiguredFeature<?, ?> configuredFeature1, ConfiguredFeature<?, ?> configuredFeature2) {

        Optional<JsonElement> configuredFeatureJSON1 = ConfiguredFeature.CODEC.encode(configuredFeature1, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();
        Optional<JsonElement> configuredFeatureJSON2 = ConfiguredFeature.CODEC.encode(configuredFeature2, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();

        // One of the configuredfeatures cannot be serialized
        if (!configuredFeatureJSON1.isPresent() || !configuredFeatureJSON2.isPresent()) {
            return false;
        }

        // Compare the JSON to see if it's the same ConfiguredFeature in the end.
        return configuredFeatureJSON1.equals(configuredFeatureJSON2);
    }
}
