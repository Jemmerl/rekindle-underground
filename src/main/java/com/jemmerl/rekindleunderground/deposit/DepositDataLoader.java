package com.jemmerl.rekindleunderground.deposit;

import com.google.gson.*;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.deposit.generators.LayerDeposit;
import com.jemmerl.rekindleunderground.deposit.templates.LayerTemplate;
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
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        RekindleUnderground.getInstance().LOGGER.info("Beginning to load ore deposits...");
        depositRegistrar.clearDeposits();
        objectIn.forEach((rl, jsonElement) -> {
            try {
                JsonObject jsonObj = jsonElement.getAsJsonObject();

                switch (jsonObj.get("deposit_type").getAsString()) {
                    case "layer":
                        // Parse the settings json element into a LayerTemplate and then use to create a LayerDeposit
                        depositRegistrar.addDeposit(
                                new LayerDeposit(GSON.fromJson(jsonObj.get("settings"), LayerTemplate.class))
                                        .setOres(DepositUtil.getOres(jsonObj.get("ores").getAsJsonArray()))
                                        .setStones(DepositUtil.getStones(jsonObj.get("stones").getAsJsonArray())));
                        RekindleUnderground.getInstance().LOGGER.info("Successfully loaded deposit {}!", rl);
                        break;

                    case "sphere":

                    default:
                        RekindleUnderground.getInstance().LOGGER.warn("Deposit type {} not found in: {} ", jsonObj.get("deposit_type").getAsString(), rl);
                }

            } catch (Exception e) {
                RekindleUnderground.getInstance().LOGGER.warn("Error reading deposit type: {}", rl);
                e.printStackTrace();
            }
        });

    }


}
