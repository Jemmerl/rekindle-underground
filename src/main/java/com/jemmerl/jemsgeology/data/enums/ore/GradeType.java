package com.jemmerl.jemsgeology.data.enums.ore;

import net.minecraft.util.IStringSerializable;

public enum GradeType implements IStringSerializable {

    NONE("none", ""),
    LOW("lowgrade", "poor_"),
    MID("midgrade", "");
    //HIGHGRADE("highgrade", "rich_");

    private final String name;
    private final String assetName;

    GradeType(String name, String assetName) {
        this.name = name;
        this.assetName = assetName;
    }

    @Override
    public String getString() { return this.name; }
    public String getAssetName() { return assetName; }
    public boolean hasGrade() { return this != NONE; }
}
