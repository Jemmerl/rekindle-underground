package com.jemmerl.jemsgeology.geology.features;

import com.google.gson.JsonElement;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;

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
            JemsGeology.getInstance().LOGGER.warn("Error in a feature's generation stone reading.");

            // Debug
            if (JemsGeoConfig.SERVER.debug_genfeature_reader.get()) {
                e.printStackTrace();
            }

            return null;
        }

        return geologyType;
    }

}
