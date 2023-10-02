package com.jemmerl.jemsgeology.data.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DefaultSets {
    // For very specific regions, such as the old flood basalts, it is simply better to have a hardcoded
    // block list. I could do it by hand, writing out each block into a list, but to be frank I find that
    // annoying. So I have compiled my default sets here for ease of coding!

    SED_SOIL(new ArrayList<>(Arrays.asList("shale", "mudstone"))),
    SED_SANDY(new ArrayList<>(Arrays.asList("sandstone", "red_sandstone", "greywacke"))),
    SED_CARBONATE(new ArrayList<>(Arrays.asList("chalk", "limestone", "dolostone"))),
    SED_EVAPORATE(new ArrayList<>(Arrays.asList("rocksalt", "rockgypsum"))),
    IGN_EXT(new ArrayList<>(Arrays.asList("dacite", "andesite", "basalt", "rhyolite"))),
    METAMORPHIC(new ArrayList<>(Arrays.asList("quartzite", "schist", "phyllite", "gneiss", "marble")));

    private final List<String> geoTypes;

    DefaultSets(List<String> geoTypes) {
        this.geoTypes = geoTypes;
    }

    public String toString() {
        return this.geoTypes.toString();
    }

    public List<String> getGeoTypes() {
        return this.geoTypes;
    }

    // Allows for quick concatenation of default sets
    public static List<String> getAllBlocks(DefaultSets... defaultSets) {
        List<String> blockList = new ArrayList<>();
        for (DefaultSets set : defaultSets) {
            blockList.addAll(set.getGeoTypes());
        }
        return blockList;
    }

}




