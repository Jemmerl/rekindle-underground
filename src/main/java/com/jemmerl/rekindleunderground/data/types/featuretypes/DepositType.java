package com.jemmerl.rekindleunderground.data.types.featuretypes;

public enum DepositType {
    LAYER("layer"),
    SPHERE("sphere");

    private final String name;

    DepositType(String name) {
        this.name = name;
    }

}
