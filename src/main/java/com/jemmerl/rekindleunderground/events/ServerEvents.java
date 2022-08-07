package com.jemmerl.rekindleunderground.events;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RekindleUnderground.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(RekindleUnderground.DEPOSIT_DATA_READER);
    }
}
