package com.jemmerl.rekindleunderground.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.geology.features.FeatureUtil;
import com.jemmerl.rekindleunderground.geology.features.instances.DikeSillEntry;
import com.jemmerl.rekindleunderground.geology.features.templates.DikeSillTemplate;
import com.jemmerl.rekindleunderground.init.featureinit.FeatureRegistrar;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Map;

// Reader class and related methods heavily built from Geolosys (oitsjustjose)
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

@Mod.EventBusSubscriber(modid = RekindleUnderground.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FeatureDataLoader extends JsonReloadListener {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private final FeatureRegistrar featureRegistrar = new FeatureRegistrar();

    public FeatureDataLoader() {
        super(GSON, "generation/features"); // Second field is the folder
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        RekindleUnderground.getInstance().LOGGER.info("Beginning to load features...");
        ArrayList<String> nameList = new ArrayList<>(); // Stores the names of each feature to check for duplicates
        featureRegistrar.clearFeatures(); // Flush the previously cached features, to ensure a clean slate

        objectIn.forEach((rl, jsonElement) -> {
            try {
                JsonObject jsonObj = jsonElement.getAsJsonObject();

                // Check for duplicate names
                String name = jsonObj.get("name").getAsString();

                // Ignore test deposits unless in debug mode
                if (!RKUndergroundConfig.COMMON.debug_test_genfeatures.get() && name.contains("test")) {
                    RekindleUnderground.getInstance().LOGGER.info("Test Feature {} ignored", name);
                    return;
                }

                if (!nameList.contains(name)) {
                    nameList.add(name);
                } else {
                    RekindleUnderground.getInstance().LOGGER.warn("Feature has duplicate name: {} in {}", name, rl);
                    throw new Exception();
                }

                // Sort and add deposits
                switch (jsonObj.get("deposit_type").getAsString()) {
                    case "dike_sill":
                        // Parse the settings json element into a LayerTemplate and then use to create a LayerDeposit
                        featureRegistrar.addDikeSillFeature(name, new DikeSillEntry(
                                GSON.fromJson(jsonObj.get("settings"), DikeSillTemplate.class))
                                        .setName(name)
                                        .setStone(FeatureUtil.getGenStone(jsonObj.get("stone").getAsJsonObject())));
                        RekindleUnderground.getInstance().LOGGER.info("Successfully loaded generation feature: {}", rl);
                        break;

                    default:
                        RekindleUnderground.getInstance().LOGGER.warn("Feature type {} not found in: {} ", jsonObj.get("feature_type").getAsString(), rl);
                }

            } catch (Exception e) {
                RekindleUnderground.getInstance().LOGGER.warn("Error reading feature: {}", rl);

                // Debug
                if (RKUndergroundConfig.COMMON.debug_genfeature_reader.get()) {
                    e.printStackTrace();
                }
            }
        });
    }

}
