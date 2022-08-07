package com.jemmerl.rekindleunderground;

import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.deposit.DepositDataLoader;
import com.jemmerl.rekindleunderground.item.ModItems;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.world.feature.ModFeatures;
import com.jemmerl.rekindleunderground.world.RKUndergroundFeatures;
import com.jemmerl.rekindleunderground.world.ModFeaturePlacements;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
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

    public static DepositDataLoader DEPOSIT_DATA_READER = new DepositDataLoader();

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
        // Dont remove lambda incase needed later
        DeferredWorkQueue.runLater(() -> {
            RKUndergroundFeatures.registerConfiguredFeatures();
        });

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Set transparent textures for the stone ore blocks
        Block block;
        for (RegistryObject<Block> regBlock : ModBlocks.BLOCKS.getEntries()) {
            block = regBlock.get().getBlock();
            if (block instanceof StoneOreBlock) {
                RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
            }
        }
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    }
}
