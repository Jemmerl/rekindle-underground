package com.jemmerl.rekindleunderground.data.types;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum OreType implements IStringSerializable {
    NONE("none"),
    TEST_ORE_1("test_ore_1"),
    TEST_ORE_2("test_ore_2"),
    TEST_ORE_3("test_ore_3"),
    TEST_ORE_4("test_ore_4");

    private final String name;

    OreType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getString();
    }

    public String getString() {
        return this.name;
    }

    public Boolean hasOre() { return this != NONE; }
}
