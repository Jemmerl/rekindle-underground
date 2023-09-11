package com.jemmerl.jemsgeology.init.featureinit;

import com.jemmerl.jemsgeology.geology.features.instances.BoulderEntry;
import com.jemmerl.jemsgeology.geology.features.instances.DikeSillEntry;

import java.util.LinkedHashMap;

public class FeatureRegistrar {

    private static LinkedHashMap<String, DikeSillEntry> dikeSills;
    private static LinkedHashMap<String, BoulderEntry> boulders;
    
    public FeatureRegistrar() {
        dikeSills = new LinkedHashMap<>();
        boulders = new LinkedHashMap<>();
    }

    public void addDikeSillFeature(String nameKey, DikeSillEntry entry) {
        dikeSills.put(nameKey, entry);
    }

    public void addBoulderFeature(String nameKey, BoulderEntry entry) {
        boulders.put(nameKey, entry);
    }

    public static LinkedHashMap<String, DikeSillEntry> getDikeSillFeatures() {
        return dikeSills;
    }
    public static LinkedHashMap<String, BoulderEntry> getBoulderFeatures() {
        return boulders;
    }

    public void clearFeatures() {
        dikeSills.clear();
        boulders.clear();
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
