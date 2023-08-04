package com.jemmerl.jemsgeology.world;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.world.placements.CenteredGenPlacer;
import com.jemmerl.jemsgeology.world.placements.ChanceGenPlacer;
import com.jemmerl.jemsgeology.world.placements.BottomCornerPlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeaturePlacements {
    public static final DeferredRegister<Placement<?>> PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, JemsGeology.MOD_ID);

    public static final RegistryObject<Placement<NoPlacementConfig>> BOTTOM_CORNER_PLACEMENT
            = PLACEMENTS.register("bottom_corner_placement", () -> new BottomCornerPlacer(NoPlacementConfig.CODEC));

    public static final RegistryObject<Placement<ChanceConfig>> PIPE_CHANCE_PLACEMENT
            = PLACEMENTS.register("pipe_gen_placement", () -> new ChanceGenPlacer(ChanceConfig.CODEC));

    public static final RegistryObject<Placement<ChanceConfig>> BOULDER_CHANCE_PLACEMENT
            = PLACEMENTS.register("boulder_gen_placement", () -> new ChanceGenPlacer(ChanceConfig.CODEC));

    public static final RegistryObject<Placement<NoPlacementConfig>> PLACER_CONSIST_PLACER
            = PLACEMENTS.register("placer_gen_placement", () -> new CenteredGenPlacer(NoPlacementConfig.CODEC));

    public static void register(IEventBus eventBus) {
        PLACEMENTS.register(eventBus);
    }
}
