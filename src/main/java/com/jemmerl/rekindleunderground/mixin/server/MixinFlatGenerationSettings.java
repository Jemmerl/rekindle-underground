package com.jemmerl.rekindleunderground.mixin.server;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

import static com.jemmerl.rekindleunderground.RKUndergroundFeatures.STONE_CONFIG;

@Mixin(FlatGenerationSettings.class)
public class MixinFlatGenerationSettings {

    @Inject(method = "getBiomeFromSettings", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;build()Lnet/minecraft/world/biome/BiomeGenerationSettings;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addRKUndergroundFeature(CallbackInfoReturnable<Biome> cir, Biome biome, BiomeGenerationSettings biomegenerationsettings, BiomeGenerationSettings.Builder biomegenerationsettings$builder, Map map, boolean flag, BlockState ablockstate[]) {
        biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, STONE_CONFIG);
    }
}
