package com.jemmerl.rekindleunderground.data.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DefaultSets {
    // For very specific regions, such as the old flood basalts, it is simply better to have a hardcoded
    // block list. I could do it by hand, writing out each block into a list, but to be frank I find that
    // annoying. So I have compiled my default sets here for ease of coding!

    SED_SOIL(new ArrayList<>(Arrays.asList("rekindleunderground:shale_stone", "rekindleunderground:mudstone_stone"))),
    SED_SANDY(new ArrayList<>(Arrays.asList("rekindleunderground:sandstone_stone", "rekindleunderground:red_sandstone_stone", "rekindleunderground:greywacke_stone"))),
    SED_CARBONATE(new ArrayList<>(Arrays.asList("rekindleunderground:chalk_stone", "rekindleunderground:limestone_stone", "rekindleunderground:dolostone_stone"))),
    SED_EVAPORATE(new ArrayList<>(Arrays.asList("rekindleunderground:rocksalt_stone", "rekindleunderground:rockgypsum_stone", "rekindleunderground:borax_stone", "rekindleunderground:kernite_stone"))),
    IGN_EXT_MAIN(new ArrayList<>(Arrays.asList("rekindleunderground:dacite_stone", "rekindleunderground:andesite_stone", "rekindleunderground:basalt_stone"))),
    IGN_EXT_AUX(new ArrayList<>(Arrays.asList("rekindleunderground:rhyolite_stone", "rekindleunderground:scoria_stone", "minecraft:obsidian"))),
    METAMORPHIC(new ArrayList<>(Arrays.asList("rekindleunderground:quartzite_stone", "rekindleunderground:schist_stone", "rekindleunderground:phyllite_stone", "rekindleunderground:gneiss_stone", "rekindleunderground:marble_stone")));

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




