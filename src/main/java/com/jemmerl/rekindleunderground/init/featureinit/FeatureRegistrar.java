package com.jemmerl.rekindleunderground.init.featureinit;

import com.jemmerl.rekindleunderground.geology.features.instances.DikeSillEntry;

import java.util.LinkedHashMap;

public class FeatureRegistrar {

    private static LinkedHashMap<String, DikeSillEntry> dikeSills;
    
    public FeatureRegistrar() {
        dikeSills = new LinkedHashMap<>();
    }

    public void addDikeSillFeature(String nameKey, DikeSillEntry entry) {
        dikeSills.put(nameKey, entry);
    }

    public static LinkedHashMap<String, DikeSillEntry> getDikeSillFeatures() {
        return dikeSills;
    }

    public void clearFeatures() {
        dikeSills.clear();
    }

    // temp test
//    public static void shuffle() {
//        List<String> list = new ArrayList<>(dikeSills.keySet());
//        Collections.shuffle(list);
//
//        LinkedHashMap<String, DikeSillEntry> shuffleMap = new LinkedHashMap<>();
//        list.forEach(k->shuffleMap.put(k, dikeSills.get(k)));
//
//        dikeSills=shuffleMap;
//    }

}
