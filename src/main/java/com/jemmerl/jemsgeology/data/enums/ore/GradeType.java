package com.jemmerl.jemsgeology.data.enums.ore;

import net.minecraft.util.IStringSerializable;

public enum GradeType implements IStringSerializable {

    NONE("none"),
    LOWGRADE("lowgrade"),
    MIDGRADE("midgrade"),
    HIGHGRADE("highgrade");

    private final String name;

    GradeType(String name) {
        this.name = name;
    }

    @Override
    public String getString() {
        return this.name;
    }
}
