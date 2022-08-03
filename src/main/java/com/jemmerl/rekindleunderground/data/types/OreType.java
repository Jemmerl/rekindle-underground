package com.jemmerl.rekindleunderground.data.types;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IStringSerializable;

import java.util.*;

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

    private static final List<OreType> VALUESALL =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final List<OreType> VALUES = new ArrayList<>(EnumSet.complementOf(EnumSet.of(OreType.NONE)));
    private static final int SIZE = VALUES.size();

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

    // Returns the respective ore item for the enum. "DIAMOND" and "NONE" are special cases
    public Item getOreItem() {
        if (this.equals(OreType.DIAMOND)) {
            return Items.DIAMOND;
        } else if (this.equals(OreType.NONE)) {
            return null;
        } else {
            return UtilMethods.stringToItem(RekindleUnderground.MOD_ID + ":" + this.name + "_ore");
        }
    }

    public String getString() {
        return this.name;
    }

    // Gets a random ore, excluding NONE
    public static OreType getRandomOreType(Random rand) {
        return VALUES.get(rand.nextInt(SIZE));
    }

    public Boolean hasOre() { return this != NONE; }
}
