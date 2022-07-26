package com.jemmerl.rekindleunderground.world.placements;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.world.placements.StoneGenPlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// Credit goes to Project Rankine (CannoliCatfish and tritespartan17) for the basis of this class; edited to fit needs
// https://github.com/CannoliCatfish/project-rankine/tree/1.16-1.3.X

public class ModFeaturePlacements {
    public static final DeferredRegister<Placement<?>> PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, RekindleUnderground.MOD_ID);

    public static final RegistryObject<Placement<NoPlacementConfig>> STONE_GEN_PLACEMENT
            = PLACEMENTS.register("stone_gen_placement", () -> new StoneGenPlacer(NoPlacementConfig.CODEC));

    public static final RegistryObject<Placement<ChanceConfig>> PIPE_CHANCE_PLACEMENT
            = PLACEMENTS.register("pipe_gen_placement", () -> new PipeGenPlacer(ChanceConfig.CODEC));

    public static void register(IEventBus eventBus) {
        PLACEMENTS.register(eventBus);
    }
}
