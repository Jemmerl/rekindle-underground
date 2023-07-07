package com.jemmerl.jemsgeology.data.enums;

public enum StoneGroupType {
    SEDIMENTARY("sedimentary"),
    EXTRUSIVE("extrusive"),
    INTRUSIVE("intrusive"),
    METAMORPHIC("metamorphic"),
    DETRITUS("detritus");

    private final String name;

    StoneGroupType(String name) {
        this.name = name;
    }
}
