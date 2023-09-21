package com.jemmerl.jemsgeology.init.depositinit;

import com.google.gson.*;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.deposits.DepositUtil;
import com.jemmerl.jemsgeology.geology.deposits.instances.ConstantScatterDeposit;
import com.jemmerl.jemsgeology.geology.deposits.instances.LayerEnqueuedDeposit;
import com.jemmerl.jemsgeology.geology.deposits.instances.PlacerDeposit;
import com.jemmerl.jemsgeology.geology.deposits.templates.ConstantScatterTemplate;
import com.jemmerl.jemsgeology.geology.deposits.templates.LayerTemplate;
import com.jemmerl.jemsgeology.geology.deposits.templates.PlacerTemplate;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Map;

// Reader class and related methods heavily built from Geolosys (oitsjustjose)
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DepositDataLoader extends JsonReloadListener {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private final DepositRegistrar depositRegistrar = new DepositRegistrar();

    public DepositDataLoader() {
        super(GSON, "generation/deposits"); // Second field is the folder
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        JemsGeology.getInstance().LOGGER.info("Beginning to load ore deposits...");
        ArrayList<String> nameList = new ArrayList<>(); // Stores the names of each deposits to check for duplicates
        depositRegistrar.clearDeposits(); // Flush the previously cached deposits, to ensure a clean slate

        objectIn.forEach((rl, jsonElement) -> {
            try {
                JsonObject jsonObj = jsonElement.getAsJsonObject();

                // Check for duplicate names
                String name = jsonObj.get("name").getAsString();

                // Ignore test deposits unless in debug mode
                if (!JemsGeoConfig.SERVER.debug_test_deposits.get() && name.contains("test")) {
                    JemsGeology.getInstance().LOGGER.info("Test Deposit {} ignored", name);
                    return;
                }

                if (!nameList.contains(name)) {
                    nameList.add(name);
                } else {
                    JemsGeology.getInstance().LOGGER.warn("Deposit has duplicate name: {} in {}", name, rl);
                    throw new Exception();
                }

                // Parse the settings json element into the needed template and then use to create the respective deposit type
                switch (jsonObj.get("deposit_type").getAsString()) {
                    case "layer":
                        depositRegistrar.addOreDeposit(name, new LayerEnqueuedDeposit(
                                GSON.fromJson(jsonObj.get("settings"), LayerTemplate.class))
                                        .setName(name)
                                        .setOres(DepositUtil.getOres(jsonObj.get("ores").getAsJsonArray(), name))
                                        .setGrades(DepositUtil.getGrades(jsonObj.get("grades").getAsJsonObject(), name))
                                        .setValid(DepositUtil.getOreStones(jsonObj.get("stones").getAsJsonArray(), name))
                                        .setBiomes(DepositUtil.getBiomes(jsonObj.get("biomes").getAsJsonArray(), name)));
                        JemsGeology.getInstance().LOGGER.info("Successfully loaded Layer deposit {}!", rl);
                        break;

                    case "placer":
                        depositRegistrar.addPlacerDeposit(name, new PlacerDeposit(
                                GSON.fromJson(jsonObj.get("settings"), PlacerTemplate.class))
                                        .setName(name)
                                        .setOres(DepositUtil.getOres(jsonObj.get("ores").getAsJsonArray(), name))
                                        .setGrades(DepositUtil.getGrades(jsonObj.get("grades").getAsJsonObject(), name))
                                        .setValid(DepositUtil.getOreStones(jsonObj.get("stones").getAsJsonArray(), name))
                                        .setBiomes(DepositUtil.getBiomes(jsonObj.get("biomes").getAsJsonArray(), name)));
                        JemsGeology.getInstance().LOGGER.info("Successfully loaded Placer deposit {}", rl);
                        break;

                    case "constant_scatter":
                        depositRegistrar.addConstScatterDeposit(name, new ConstantScatterDeposit(
                                GSON.fromJson(jsonObj.get("settings"), ConstantScatterTemplate.class))
                                        .setName(name)
                                        .setOres(DepositUtil.getOres(jsonObj.get("ores").getAsJsonArray(), name))
                                        .setGrades(DepositUtil.getGrades(jsonObj.get("grades").getAsJsonObject(), name))
                                        .setValid(DepositUtil.getOreStones(jsonObj.get("stones").getAsJsonArray(), name))
                                        .setBiomes(DepositUtil.getBiomes(jsonObj.get("biomes").getAsJsonArray(), name)));
                        JemsGeology.getInstance().LOGGER.info("Successfully loaded Constant-Scatter deposit {}", rl);
                        break;
                    default:
                        JemsGeology.getInstance().LOGGER.warn("Deposit type {} not found in: {} ", jsonObj.get("deposit_type").getAsString(), rl);
                }

            } catch (Exception e) {
                JemsGeology.getInstance().LOGGER.warn("Error reading deposit type: {}", rl);

                // Debug
                if (JemsGeoConfig.SERVER.debug_deposit_reader.get()) {
                    e.printStackTrace();
                }
            }
        });
    }

}
