package com.jemmerl.rekindleunderground.deposit;

import com.google.gson.*;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.deposit.generators.LayerDeposit;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

// Reader class and related methods heavily built from Geolosys (oitsjustjose)
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

@Mod.EventBusSubscriber(modid = RekindleUnderground.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DepositDataLoader extends JsonReloadListener {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private final DepositRegistrar depositRegistrar = new DepositRegistrar();

    public DepositDataLoader() {
        super(GSON, "generation/deposits"); // Second field is the folder
        System.out.println("loaded");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        System.out.println("READING DATA");
        depositRegistrar.clearDeposits();
        objectIn.forEach((rl, jsonElement) -> {
            try {
                JsonObject jsonObj = jsonElement.getAsJsonObject();

                switch (jsonObj.get("type").getAsString()) {
                    case "layer":
                        depositRegistrar.addDeposit(new LayerDeposit(jsonObj));
                        break;

                    case "sphere":

                    default:
                        RekindleUnderground.getInstance().LOGGER.warn("Deposit type not found in: {} ", jsonElement.toString());
                }

            } catch (NullPointerException e) {
                RekindleUnderground.getInstance().LOGGER.warn("Error reading deposit type: {}", rl);
            }
        });

    }


}
