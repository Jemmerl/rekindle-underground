package com.jemmerl.rekindleunderground.geology.features;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.data.types.GeologyType;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;

public class FeatureUtil {


    ////////////////////////
    // Registration Utils //
    ////////////////////////

    // Read in a generated feature's stone
    public static GeologyType getGenStone(JsonElement jsonElement) {
        GeologyType geologyType;

        try {
            String oreStoneStr = jsonElement.getAsString().toUpperCase();
            geologyType = GeologyType.valueOf(oreStoneStr);
        } catch (Exception e) {
            RekindleUnderground.getInstance().LOGGER.warn("Error in a feature's generation stone reading.");

            // Debug
            if (RKUndergroundConfig.COMMON.debug_genfeature_reader.get()) {
                e.printStackTrace();
            }

            return null;
        }

        return geologyType;
    }

}
