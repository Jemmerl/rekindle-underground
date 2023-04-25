package com.jemmerl.rekindleunderground.init.featureinit;

import com.jemmerl.rekindleunderground.geology.features.templates.DikeSillTemplate;

import java.util.HashMap;

public class FeatureRegistrar {
    private static HashMap<String, DikeSillTemplate> dikeSills;


    public FeatureRegistrar() {
        dikeSills = new HashMap<>();
    }

    public void addDikeSillFeature(String nameKey, DikeSillTemplate template) {
        dikeSills.put(nameKey, template);
    }

    public static HashMap<String, DikeSillTemplate> getDikeSillFeatures() {
        return dikeSills;
    }

    public void clearFeatures() {
        dikeSills.clear();
    }

}
