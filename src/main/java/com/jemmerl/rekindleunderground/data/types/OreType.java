package com.jemmerl.rekindleunderground.data.types;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum OreType implements IStringSerializable {
    NONE("none"),
    APATITE("apatite"),
    AZURITE("azurite"),
    BARYTE("baryte"),
    BERYL("beryl"),
    BISMUTHINITE("bismuthinite"),
    CARNOTITE("carnotite"),
    CASSITERITE("cassiterite"),
    CELESTINE("celestine"),
    CHALCOPYRITE("chalcopyrite"),
    CHROMITE("chromite"),
    CINNABAR("cinnabar"),
    COBALTITE("cobaltite"),
    CRYOLITE("cryolite"),
    DIAMOND("diamond"),
    ELECTRUM("electrum"),
    FLUORITE ("fluorite"),
    GALENA("galena"),
    GOETHITE("goethite"),
    GRAPHITE("graphite"),
    HEMATITE("hematite"),
    ILMENITE("ilmenite"),
    LEPIDOLITE("lepidolite"),
    LIMONITE("limonite"),
    MAGNESITE("magnesite"),
    MAGNETITE("magnetite"),
    MALACHITE("malachite"),
    MOLYBDENITE("molybdenite"),
    MONAZITE("monazite"),
    NATIVE_COPPER("native_copper"),
    NATIVE_GOLD("native_gold"),
    NATIVE_SULFUR("native_sulfur"),
    PENTLANDITE("pentlandite"),
    POLLUCITE("pollucite"),
    PSILOMELANE("psilomelane"),
    PYRITE("pyrite"),
    PYROCHLORE("pyrochlore"),
    PYROLUSITE("pyrolusite"),
    RUTILE("rutile"),
    SCHEELITE("scheelite"),
    SMITHSONITE("smithsonite"),
    SPHALERITE("sphalerite"),
    SPODUMENE("spodumene"),
    STIBNITE("stibnite"),
    SYLVITE("sylvite"),
    TANTALITE("tantalite"),
    TETRAHEDRITE("tetrahedrite"),
    TRONA("trona"),
    URANINITE("uraninite"),
    WOLFRAMITE("wolframite");

    private final String name;

    OreType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getString();
    }

    public static OreType fromString(String string) {
        for (OreType type : OreType.values()) {
            if (type.name.equalsIgnoreCase(string)) {
                return type;
            }
        }
        return null;
    }

    public String getString() {
        return this.name;
    }

    public Boolean hasOre() { return this != NONE; }
}
