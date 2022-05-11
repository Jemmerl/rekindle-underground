package com.jemmerl.rekindleunderground.data.types;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.util.IStringSerializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum StoneType implements IStringSerializable {
    SEDIMENTARY("sedimentary"),
    EXTRUSIVE("extrusive"),
    INTRUSIVE("intrusive"),
    METAMORPHIC("metamorphic");

    private final String name;

    StoneType(String name) {
        this.name = name;
    }


//    // Sedimentary
//    CHALK("chalk", "sedimentary"),
//    LIMESTONE("limestone", "sedimentary"),
//    DOLOSTONE("dolostone", "sedimentary"),
//    SHALE("shale", "sedimentary"),
//    SANDSTONE("sandstone", "sedimentary"),
//    MUDSTONE("mudstone", "sedimentary"),
//    ROCK_SALT("rock_salt", "sedimentary"),
//    ROCK_GYPSUM("rock_gypsum", "sedimentary"),
//    BORAX("borax", "sedimentary"),
//    KERNITE("kernite", "sedimentary"),
//    VEIN_QUARTZ("vein_quartz", "sedimentary"),
//
//    // Extrusive Igneous
//    RHYOLITE("rhyolite", "extrusive"),
//    DACITE("dacite", "extrusive"),
//    ANDESITE("andesite", "extrusive"),
//    BASALT("basalt", "extrusive"),
//    SCORIA("scoria", "extrusive"),
//
//    // Intrusive Igneous
//    DIORITE("diorite", "intrusive"),
//    GRANODIORITE("granodiorite", "intrusive"),
//    GRANITE("granite", "intrusive"),
//    SYENITE("syenite", "intrusive"),
//    GABBRO("gabbro", "intrusive"),
//    DIABASE("diabase", "intrusive"),
//    PERIDOTITE("peridotite", "intrusive"),
//
//    // Metamorphic
//    QUARTZITE("quartzite", "metamorphic"),
//    SCHIST("schist", "metamorphic"),
//    PHYLLITE("phyllite", "metamorphic"),
//    GNEISS("gneiss", "metamorphic"),
//    MARBLE("marble", "metamorphic"),
//    SOAPSTONE("soapstone", "metamorphic");
//
//    private final String name;
//    private final String group;
//
//    StoneType(String name, String group) {
//        this.name = name;
//        this.group = group;
//    }
//
//    // These may not be needed, kept just in case
//    private static final Map<String, StoneType> BY_NAME = new HashMap<>();
//    private static final Map<String, StoneType> BY_GROUP = new HashMap<>();
//
//    static {
//        for (StoneType e : values()) {
//            BY_NAME.put(e.name, e);
//            BY_GROUP.put(e.group, e);
//        }
//    }
//
//    // Typos will always return false!
//    public Boolean isInStoneGroup(String group){
//        return (this.group == group);
//    }

    public String getString(){
        return this.name;
    }
}
