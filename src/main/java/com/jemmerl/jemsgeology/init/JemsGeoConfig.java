package com.jemmerl.jemsgeology.init;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

// Taken from the TFC github for temporary personal reference...
//https://github.com/TerraFirmaCraft/TerraFirmaCraft/blob/1.18.x/src/main/java/net/dries007/tfc/config/TFCConfig.java
/*
 * Central point for all configuration options
 * - Common is used for options which need to be world-agnostic, or are independent of side
 * - Server is used for generic mechanics options, stuff which is synchronized but server priority, etc.
 * - Client is used for purely graphical or client side only options
 */


public final class JemsGeoConfig {

    public static final ServerConfig.Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    public static final CommonConfig.Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static //constructor
    {
        Pair<ServerConfig.Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig.Server::new);
        SERVER = serverSpecPair.getLeft();
        SERVER_SPEC = serverSpecPair.getRight();

        Pair<CommonConfig.Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CommonConfig.Common::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }
}
