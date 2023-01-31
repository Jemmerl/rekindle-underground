package com.jemmerl.rekindleunderground.world;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.world.placements.ChanceGenPlacer;
import com.jemmerl.rekindleunderground.world.placements.ConsistentGenPlacer;
import com.jemmerl.rekindleunderground.world.placements.StoneGenPlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeaturePlacements {
    public static final DeferredRegister<Placement<?>> PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, RekindleUnderground.MOD_ID);

    public static final RegistryObject<Placement<NoPlacementConfig>> STONE_GEN_PLACEMENT
            = PLACEMENTS.register("stone_gen_placement", () -> new StoneGenPlacer(NoPlacementConfig.CODEC));

    public static final RegistryObject<Placement<ChanceConfig>> PIPE_CHANCE_PLACEMENT
            = PLACEMENTS.register("pipe_gen_placement", () -> new ChanceGenPlacer(ChanceConfig.CODEC));

    public static final RegistryObject<Placement<NoPlacementConfig>> PLACER_CONSIST_PLACER
            = PLACEMENTS.register("placer_gen_placement", () -> new ConsistentGenPlacer(NoPlacementConfig.CODEC));

    public static void register(IEventBus eventBus) {
        PLACEMENTS.register(eventBus);
    }
}
