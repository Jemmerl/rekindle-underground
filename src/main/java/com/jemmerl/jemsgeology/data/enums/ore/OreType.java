package com.jemmerl.jemsgeology.data.enums.ore;

import com.jemmerl.jemsgeology.util.lists.ModOresList;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;

import java.util.*;

public enum OreType implements IStringSerializable{
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
    PERIDOTITE("peridotite"),
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

    private static final List<OreType> VALUESALL = Collections.unmodifiableList(Arrays.asList(values()));
    private static final List<OreType> VALUES = new ArrayList<>(EnumSet.complementOf(EnumSet.of(OreType.NONE)));
    private static final int SIZE = VALUES.size();

    OreType(String name) {
        this.name = name;
    }

    @Override
    public String getString() {
        return name;
    }

//    public String getString() {
//        return this.name;
//    }

//    public static OreType fromString(String string) {
//        for (OreType type : OreType.values()) {
//            if (type.name.equalsIgnoreCase(string)) {
//                return type;
//            }
//        }
//        return null;
//    }

    // Returns the respective regular ore item for the enum. "DIAMOND" and "NONE" are special cases
    public Item getOreItem() {
        return ModOresList.ORE_LIST.get(this).getOreItem();
    }

    // Returns the respective poor ore item for the enum. "DIAMOND" and "NONE" are special cases
    public Item getPoorOreItem() {
        return ModOresList.ORE_LIST.get(this).getPoorOreItem();
    }

    // Gets a random ore, excluding NONE
    public static OreType getRandomOreType(Random rand) {
        return VALUES.get(rand.nextInt(SIZE));
    }

    public Boolean hasOre() { return this != NONE; }
}
