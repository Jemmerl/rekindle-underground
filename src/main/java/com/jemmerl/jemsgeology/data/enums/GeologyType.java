package com.jemmerl.jemsgeology.data.enums;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum GeologyType {

    // Sedimentary
    CHALK("chalk", StoneGroupType.SEDIMENTARY, true, 0),
    LIMESTONE("limestone", StoneGroupType.SEDIMENTARY, true, 1),
    DOLOSTONE("dolostone", StoneGroupType.SEDIMENTARY, true, 2),
    MARLSTONE("marlstone", StoneGroupType.SEDIMENTARY, true, 2),
    SHALE("shale", StoneGroupType.SEDIMENTARY, true, 2),
    LIMY_SHALE("limy_shale", StoneGroupType.SEDIMENTARY, true, 2),
    SANDSTONE("sandstone", StoneGroupType.SEDIMENTARY, true, 2),
    RED_SANDSTONE("red_sandstone", StoneGroupType.SEDIMENTARY, true, 2),
    ARKOSE("arkose", StoneGroupType.SEDIMENTARY, true, 2),
    GREYWACKE("greywacke", StoneGroupType.SEDIMENTARY, true, 2),
    MUDSTONE("mudstone", StoneGroupType.SEDIMENTARY, true, 1),
    CLAYSTONE("claystone", StoneGroupType.SEDIMENTARY, true, 1),
    SILTSTONE("siltstone", StoneGroupType.SEDIMENTARY, true, 1),
    CONGLOMERATE("conglomerate", StoneGroupType.SEDIMENTARY, true, 2),
    CALCITE("calcite", StoneGroupType.SEDIMENTARY, true, 1),
    QUARTZ("quartz", StoneGroupType.SEDIMENTARY, true, 4),

    // TODO evaporates
    ROCKSALT("rocksalt", StoneGroupType.SEDIMENTARY, false, 1),
    ROCKGYPSUM("rockgypsum", StoneGroupType.SEDIMENTARY, false, 0),

    // Extrusive Igneous
    RHYOLITE("rhyolite", StoneGroupType.EXTRUSIVE, true, 2),
    DACITE("dacite", StoneGroupType.EXTRUSIVE, true, 3),
    ANDESITE("andesite", StoneGroupType.EXTRUSIVE, true, 3),
    TRACHYTE("trachyte", StoneGroupType.EXTRUSIVE, true, 3),
    BASALT("basalt", StoneGroupType.EXTRUSIVE, true, 3),
    PAHOEHOE("pahoehoe", StoneGroupType.EXTRUSIVE, false, 2),
    RHYOLITIC_TUFF("rhyolitic_tuff", StoneGroupType.EXTRUSIVE, false, 0),
    TRACHYTIC_TUFF("trachytic_tuff", StoneGroupType.EXTRUSIVE, false, 0),
    ANDESITIC_TUFF("andesitic_tuff", StoneGroupType.EXTRUSIVE, false, 0),
    BASALTIC_TUFF("basaltic_tuff", StoneGroupType.EXTRUSIVE, false, 0),
    ULTRAMAFIC_TUFF("ultramafic_tuff", StoneGroupType.EXTRUSIVE, false, 0),

    // Intrusive Igneous
    DIORITE("diorite", StoneGroupType.INTRUSIVE, true, 4),
    GRANODIORITE("granodiorite", StoneGroupType.INTRUSIVE, true, 4),
    GRANITE("granite", StoneGroupType.INTRUSIVE, true, 4),
//    SYENITE("syenite", StoneGroupType.INTRUSIVE, true, 4),
    GABBRO("gabbro", StoneGroupType.INTRUSIVE, true, 4),
    DIABASE("diabase", StoneGroupType.INTRUSIVE, true, 4),
    KIMBERLITE("kimberlite", StoneGroupType.INTRUSIVE, true, 3),
    LAMPROITE("lamproite", StoneGroupType.INTRUSIVE, true, 3),

    // Metamorphic
    QUARTZITE("quartzite", StoneGroupType.METAMORPHIC, true, 4),
    SCHIST("schist", StoneGroupType.METAMORPHIC, true, 1),
    PHYLLITE("phyllite", StoneGroupType.METAMORPHIC, true, 2),
    SLATE("slate", StoneGroupType.METAMORPHIC, true, 3),
    GNEISS("gneiss", StoneGroupType.METAMORPHIC, true, 4),
    MARBLE("marble", StoneGroupType.METAMORPHIC, true, 3),
    PELITIC_HORNFELS("pelitic_hornfels", StoneGroupType.METAMORPHIC, true, 3),
    CARBONATE_HORNFELS("carbonate_hornfels", StoneGroupType.METAMORPHIC, true, 3),
    MAFIC_HORNFELS("mafic_hornfels", StoneGroupType.METAMORPHIC, true, 3),
    METACONGLOMERATE("metaconglomerate", StoneGroupType.METAMORPHIC, true, 3),
    GREISEN("greisen", StoneGroupType.METAMORPHIC, true, 4),

    // Detritus do not really need to be present, but since they can carry ores, they must be to be compatible
    // Stable Detritus
    DIRT("dirt", StoneGroupType.DETRITUS, false, 0),
    COARSE_DIRT("coarse_dirt", StoneGroupType.DETRITUS, false, 0),
    CLAY("clay", StoneGroupType.DETRITUS, false, 1),

    // Falling Detritus
    SAND("sand", StoneGroupType.DETRITUS, false, 0),
    RED_SAND("red_sand", StoneGroupType.DETRITUS, false, 0),
    GRAVEL("gravel", StoneGroupType.DETRITUS, false, 1);

    private final String name;
    private final StoneGroupType group;
    private final boolean hasCobble;
    private final int propIndex;

    GeologyType(String name, StoneGroupType group, boolean hasCobble, int propIndex) {
        this.name = name;
        this.group = group;
        this.hasCobble = hasCobble;
        this.propIndex = propIndex;
    }

    private static class Constants {
        private static final Float[] HARDS = {1f, 1.75f, 2.5f, 3f, 3.5f}; // Relative stone hardnesses
        private static final Float[] RESISTS = {3f, 2.5f, 2f, 1.5f, 1f}; // Relative stone resistances
    }

    public String getName() {
        return name;
    }

    public StoneGroupType getGroup() {
        return group;
    }

    public boolean hasCobble() {
        return hasCobble;
    }

    public float getStoneHardness() {
        return (GeologyType.Constants.HARDS[propIndex]);
    }

    public float getStoneResistance() {
        return (GeologyType.Constants.RESISTS[propIndex]);
    }

    public float getCobbleHardness() {
        return ((propIndex == 0) ? GeologyType.Constants.HARDS[0] : GeologyType.Constants.HARDS[propIndex - 1]);
    }

    public float getCobbleResistance() {
        return ((propIndex == 0) ? GeologyType.Constants.RESISTS[1] : GeologyType.Constants.RESISTS[propIndex]);
    }

    public boolean isInStoneGroup(StoneGroupType group){
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


    ///////////
    // LISTS //
    ///////////

    public static final List<GeologyType> STABLE_DET = Arrays.asList(
            GeologyType.DIRT,
            GeologyType.COARSE_DIRT,
            GeologyType.CLAY
    );

    public static final List<GeologyType> FALLING_DET = Arrays.asList(
            GeologyType.SAND,
            GeologyType.RED_SAND,
            GeologyType.GRAVEL
    );

}
