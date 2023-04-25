package com.jemmerl.rekindleunderground.init.featureinit;

import com.jemmerl.rekindleunderground.geology.features.instances.DikeSillEntry;

import java.util.HashMap;

public class FeatureRegistrar {
    private static HashMap<String, DikeSillEntry> dikeSills;


    public FeatureRegistrar() {
        dikeSills = new HashMap<>();
    }

    public void addDikeSillFeature(String nameKey, DikeSillEntry entry) {
        dikeSills.put(nameKey, entry);
    }

    public static HashMap<String, DikeSillEntry> getDikeSillFeatures() {
        return dikeSills;
    }

    public void clearFeatures() {
        dikeSills.clear();
    }

}
