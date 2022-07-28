package com.jemmerl.rekindleunderground.events;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.commands.JacksonCmd;
import com.jemmerl.rekindleunderground.commands.OreWallCmd;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = RekindleUnderground.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new JacksonCmd(event.getDispatcher());
        new OreWallCmd(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
