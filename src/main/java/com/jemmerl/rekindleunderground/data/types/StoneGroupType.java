package com.jemmerl.rekindleunderground.data.types;

public enum StoneGroupType {
    SEDIMENTARY("sedimentary"),
    EXTRUSIVE("extrusive"),
    INTRUSIVE("intrusive"),
    METAMORPHIC("metamorphic");

    private final String name;

    StoneGroupType(String name) {
        this.name = name;
    }
}
