package com.jemmerl.rekindleunderground;

import com.jemmerl.rekindleunderground.init.ModBlocks;
import com.jemmerl.rekindleunderground.deposit.DepositDataLoader;
import com.jemmerl.rekindleunderground.init.ModItems;
import com.jemmerl.rekindleunderground.util.lists.ModBlockLists;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.world.capability.chunk.ChunkGennedCapProvider;
import com.jemmerl.rekindleunderground.world.capability.chunk.ChunkGennedCapStorage;
import com.jemmerl.rekindleunderground.world.capability.chunk.ChunkGennedCapability;
import com.jemmerl.rekindleunderground.world.capability.chunk.IChunkGennedCapability;
import com.jemmerl.rekindleunderground.world.capability.deposit.DepositCapProvider;
import com.jemmerl.rekindleunderground.world.capability.deposit.DepositCapStorage;
import com.jemmerl.rekindleunderground.world.capability.deposit.DepositCapability;
import com.jemmerl.rekindleunderground.world.capability.deposit.IDepositCapability;
import com.jemmerl.rekindleunderground.world.feature.ModFeatures;
import com.jemmerl.rekindleunderground.world.RKUndergroundFeatures;
import com.jemmerl.rekindleunderground.world.ModFeaturePlacements;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(RekindleUnderground.MOD_ID)
public class RekindleUnderground
{
    private static RekindleUnderground instance;
    public static final String MOD_ID = "rekindleunderground";
    public final Logger LOGGER = LogManager.getLogger();

    public static DepositDataLoader DEPOSIT_DATA_LOADER = new DepositDataLoader();

    public RekindleUnderground() {
        instance = this;
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModFeaturePlacements.register(eventBus);
        ModFeatures.register(eventBus);

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);
        eventBus.addListener(this::clientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RKUndergroundConfig.COMMON_SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static RekindleUnderground getInstance() {
        return instance;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        CapabilityManager.INSTANCE.register(IDepositCapability.class, new DepositCapStorage(), DepositCapability::new);
        CapabilityManager.INSTANCE.register(IChunkGennedCapability.class, new ChunkGennedCapStorage(), ChunkGennedCapability::new);

        // Dont remove lambda incase needed later
        DeferredWorkQueue.runLater(() -> {
            RKUndergroundFeatures.registerConfiguredFeatures();
        });

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Set transparent textures for the stone ore blocks
        for (Block block : ModBlockLists.ALL_OREBLOCKS) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    // TODO CHECK IF CAN ONLY IMPLEMENT IN OVERWORLD??
    @SubscribeEvent
    public void attachCap(AttachCapabilitiesEvent<World> event) {
        String dimName = event.getObject().getDimensionKey().getLocation().toString();

        event.addCapability(new ResourceLocation(MOD_ID, "deposit"), new DepositCapProvider());
        event.addCapability(new ResourceLocation(MOD_ID, "generated_chunks"), new ChunkGennedCapProvider());
        LOGGER.info("RKU capabilities successfully attached for {}", dimName);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    }
}
