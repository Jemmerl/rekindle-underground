package com.jemmerl.rekindleunderground.data.enums;

import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;

import java.util.EnumSet;

public enum GeologyType {

    // Sedimentary
    CHALK("chalk", StoneGroupType.SEDIMENTARY, true, 0, 0),
    LIMESTONE("limestone", StoneGroupType.SEDIMENTARY, true, 1, 1),
    DOLOSTONE("dolostone", StoneGroupType.SEDIMENTARY, true, 2, 2),
    MARLSTONE("marlstone", StoneGroupType.SEDIMENTARY, true, 2, 2), //TODO COBBLE
    SHALE("shale", StoneGroupType.SEDIMENTARY, true, 2, 2),
    LIMY_SHALE("limy_shale", StoneGroupType.SEDIMENTARY, true, 2, 2), //TODO COBBLE
    SANDSTONE("sandstone", StoneGroupType.SEDIMENTARY, true, 2, 2),
    RED_SANDSTONE("red_sandstone", StoneGroupType.SEDIMENTARY, true, 2, 2),
    ARKOSE("arkose", StoneGroupType.SEDIMENTARY, true, 2, 2), //TODO COBBLE
    GREYWACKE("greywacke", StoneGroupType.SEDIMENTARY, true, 2, 2),
    MUDSTONE("mudstone", StoneGroupType.SEDIMENTARY, true, 1, 1),
    CLAYSTONE("claystone", StoneGroupType.SEDIMENTARY, true, 1, 1), //TODO COBBLE
    SILTSTONE("siltstone", StoneGroupType.SEDIMENTARY, true, 1, 1), //TODO COBBLE
    CONGLOMERATE("conglomerate", StoneGroupType.SEDIMENTARY, true, 2, 2), //TODO COBBLE
    VEIN_QUARTZ("vein_quartz", StoneGroupType.SEDIMENTARY, true, 4, 4),

    // TODO evaporates
    ROCKSALT("rocksalt", StoneGroupType.SEDIMENTARY, false, 1, 1),
    ROCKGYPSUM("rockgypsum", StoneGroupType.SEDIMENTARY, false, 0, 0),
    BORAX("borax", StoneGroupType.SEDIMENTARY, false, 0, 0),
    KERNITE("kernite", StoneGroupType.SEDIMENTARY, false, 0, 0),

    // Extrusive Igneous
    RHYOLITE("rhyolite", StoneGroupType.EXTRUSIVE, true, 2, 2),
    DACITE("dacite", StoneGroupType.EXTRUSIVE, true, 3, 3),
    ANDESITE("andesite", StoneGroupType.EXTRUSIVE, true, 3, 3),
    TRACHYTE("trachyte", StoneGroupType.EXTRUSIVE, true, 3, 3),
    BASALT("basalt", StoneGroupType.EXTRUSIVE, true, 3, 3),
    PAHOEHOE("pahoehoe", StoneGroupType.EXTRUSIVE, true, 2, 2),
    SCORIA("scoria", StoneGroupType.EXTRUSIVE, false, 2, 2),
    RHYOLITIC_TUFF("rhyolitic_tuff", StoneGroupType.EXTRUSIVE, false, 0, 0),
    TRACHYTIC_TUFF("trachytic_tuff", StoneGroupType.EXTRUSIVE, false, 0, 0),
    ANDESITIC_TUFF("andesitic_tuff", StoneGroupType.EXTRUSIVE, false, 0, 0),
    BASALTIC_TUFF("basaltic_tuff", StoneGroupType.EXTRUSIVE, false, 0, 0),
    ULTRAMAFIC_TUFF("ultramafic_tuff", StoneGroupType.EXTRUSIVE, false, 0, 0),

    // Intrusive Igneous
    DIORITE("diorite", StoneGroupType.INTRUSIVE, true, 4, 4),
    GRANODIORITE("granodiorite", StoneGroupType.INTRUSIVE, true, 4, 4),
    GRANITE("granite", StoneGroupType.INTRUSIVE, true, 4, 4),
    SYENITE("syenite", StoneGroupType.INTRUSIVE, true, 4, 4),
    GABBRO("gabbro", StoneGroupType.INTRUSIVE, true, 4, 4),
    DIABASE("diabase", StoneGroupType.INTRUSIVE, true, 4, 4),
    KIMBERLITE("kimberlite", StoneGroupType.INTRUSIVE, true, 3, 3),
    LAMPROITE("lamproite", StoneGroupType.INTRUSIVE, true, 3, 3),

    // Metamorphic
    QUARTZITE("quartzite", StoneGroupType.METAMORPHIC, true, 4, 4),
    SCHIST("schist", StoneGroupType.METAMORPHIC, true, 1, 1),
    PHYLLITE("phyllite", StoneGroupType.METAMORPHIC, true, 2, 2),
    SLATE("slate", StoneGroupType.METAMORPHIC, true, 3, 3),
    GNEISS("gneiss", StoneGroupType.METAMORPHIC, true, 4, 4),
    MARBLE("marble", StoneGroupType.METAMORPHIC, true, 3, 3),
    PELITIC_HORNFELS("pelitic_hornfels", StoneGroupType.METAMORPHIC, true, 3, 3), //TODO COBBLE
    CARBONATE_HORNFELS("carbonate_hornfels", StoneGroupType.METAMORPHIC, true, 3, 3), //TODO COBBLE
    MAFIC_HORNFELS("mafic_hornfels", StoneGroupType.METAMORPHIC, true, 3, 3), //TODO COBBLE
    METACONGLOMERATE("metaconglomerate", StoneGroupType.METAMORPHIC, true, 3, 3), //TODO COBBLE
    GREISEN("greisen", StoneGroupType.METAMORPHIC, true, 4, 4), //TODO COBBLE

    // Detritus do not really need to be present, but since they can carry ores, they must be to be compatible
    // Stable Detritus
    DIRT("dirt", StoneGroupType.DETRITUS, false, 0, 0),
    COARSE_DIRT("coarse_dirt", StoneGroupType.DETRITUS, false, 0, 0),
    CLAY("clay", StoneGroupType.DETRITUS, false, 1, 1),

    // Falling Detritus
    SAND("sand", StoneGroupType.DETRITUS, false, 0, 0),
    RED_SAND("red_sand", StoneGroupType.DETRITUS, false, 0, 0),
    GRAVEL("gravel", StoneGroupType.DETRITUS, false, 1, 1);

    private final String name;
    private final StoneGroupType group;
    private final boolean hasCobble;
    private final int hardnessIndex;
    private final int resistanceIndex;

    GeologyType(String name, StoneGroupType group, boolean hasCobble, int hardnessIndex, int resistanceIndex) {
        this.name = name;
        this.group = group;
        this.hasCobble = hasCobble;
        this.hardnessIndex = hardnessIndex;
        this.resistanceIndex = resistanceIndex;
    }

    private static class Constants {
        private static final Float[] HARDS = {1f, 1.75f, 2.5f, 3f, 3.5f}; // Relative stone hardnesses
        private static final Float[] RESISTS = {3f, 2.5f, 2f, 1.5f, 1f}; // Relative stone resistances
        private static final int HARD_MULT = RKUndergroundConfig.COMMON.stoneHardness.get(); // Multiplied by rel. hardnesses; Default 20
        private static final int RESIST_MULT = RKUndergroundConfig.COMMON.stoneResistance.get(); // Multiplied by rel. resistances; Default 6
    }

    public String getName() {
        return this.name;
    }

    public StoneGroupType getGroup() {
        return this.group;
    }

    public boolean hasCobble() {
        return this.hasCobble;
    }

    public float getStoneHardness() {
        return (GeologyType.Constants.HARDS[this.hardnessIndex] * GeologyType.Constants.HARD_MULT);
    }

    public float getStoneResistance() {
        return (GeologyType.Constants.RESISTS[this.resistanceIndex] * GeologyType.Constants.RESIST_MULT);
    }

    public float getCobbleHardness() {
        return ((this.hardnessIndex == 0) ? GeologyType.Constants.HARDS[0] : GeologyType.Constants.HARDS[this.hardnessIndex - 1]);
    }

    public float getCobbleResistance() {
        return ((this.hardnessIndex == 0) ? GeologyType.Constants.RESISTS[1] : GeologyType.Constants.RESISTS[this.resistanceIndex]);
    }

    public Boolean isInStoneGroup(StoneGroupType group){
        return this.group.equals(group);
    }

    ////////////////////
    // STATIC METHODS //
    ////////////////////

    // Get all the stone types for the supplied group
    public static EnumSet<GeologyType> getAllInGroup(StoneGroupType group) {
        EnumSet<GeologyType> enumSet = EnumSet.noneOf(GeologyType.class);
        for (GeologyType geoType : GeologyType.values()) {
            if (geoType.getGroup().equals(group)) {
                enumSet.add(geoType);
            }
        }
        return enumSet;
    }

}
