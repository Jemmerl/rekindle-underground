package com.jemmerl.jemsgeology.data.enums.ore;

import net.minecraft.util.IStringSerializable;

public enum GradeType implements IStringSerializable {

    HIGHGRADE("highgrade"),
    MIDGRADE("midgrade"),
    LOWGRADE("lowgrade");

    private final String name;

    GradeType(String name) {
        this.name = name;
    }

    @Override
    public String getString() {
        return this.name;
    }
}
