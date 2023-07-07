package com.jemmerl.jemsgeology.events;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.commands.JacksonCmd;
import com.jemmerl.jemsgeology.commands.OreWallCmd;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new JacksonCmd(event.getDispatcher());
        new OreWallCmd(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
