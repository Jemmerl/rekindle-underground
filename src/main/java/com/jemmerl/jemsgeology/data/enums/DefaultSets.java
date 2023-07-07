package com.jemmerl.jemsgeology.data.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DefaultSets {
    // For very specific regions, such as the old flood basalts, it is simply better to have a hardcoded
    // block list. I could do it by hand, writing out each block into a list, but to be frank I find that
    // annoying. So I have compiled my default sets here for ease of coding!

    SED_SOIL(new ArrayList<>(Arrays.asList("jemsgeology:shale_stone", "jemsgeology:mudstone_stone"))),
    SED_SANDY(new ArrayList<>(Arrays.asList("jemsgeology:sandstone_stone", "jemsgeology:red_sandstone_stone", "jemsgeology:greywacke_stone"))),
    SED_CARBONATE(new ArrayList<>(Arrays.asList("jemsgeology:chalk_stone", "jemsgeology:limestone_stone", "jemsgeology:dolostone_stone"))),
    SED_EVAPORATE(new ArrayList<>(Arrays.asList("jemsgeology:rocksalt_stone", "jemsgeology:rockgypsum_stone", "jemsgeology:borax_stone", "jemsgeology:kernite_stone"))),
    IGN_EXT_MAIN(new ArrayList<>(Arrays.asList("jemsgeology:dacite_stone", "jemsgeology:andesite_stone", "jemsgeology:basalt_stone"))),
    IGN_EXT_AUX(new ArrayList<>(Arrays.asList("jemsgeology:rhyolite_stone", "jemsgeology:scoria_stone"))),
    METAMORPHIC(new ArrayList<>(Arrays.asList("jemsgeology:quartzite_stone", "jemsgeology:schist_stone", "jemsgeology:phyllite_stone", "jemsgeology:gneiss_stone", "jemsgeology:marble_stone")));

    private final List<String> blocks;

    DefaultSets(List<String> blocks) {
        this.blocks = blocks;
    }

    public String toString() {
        return this.blocks.toString();
    }

    public List<String> getBlocks() {
        return this.blocks;
    }

    // Allows for quick concatenation of default sets
    public static List<String> getAllBlocks(DefaultSets... defaultSets) {
        List<String> blockList = new ArrayList<>();
        for (DefaultSets set : defaultSets) {
            blockList.addAll(set.getBlocks());
        }
        return blockList;
    }

}




