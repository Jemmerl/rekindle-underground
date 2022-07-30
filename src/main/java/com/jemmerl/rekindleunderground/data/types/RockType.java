package com.jemmerl.rekindleunderground.data.types;

import com.jemmerl.rekindleunderground.item.ModItemGroup;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Locale;

public enum RockType implements IItemProvider {

    // Sedimentary
    CHALK_ROCK("chalk_rock", StoneGroupType.SEDIMENTARY),
    LIMESTONE_ROCK("limestone_rock", StoneGroupType.SEDIMENTARY),
    DOLOSTONE_ROCK("dolostone_rock", StoneGroupType.SEDIMENTARY),
    SHALE_ROCK("shale_rock", StoneGroupType.SEDIMENTARY),
    SANDSTONE_ROCK("sandstone_rock", StoneGroupType.SEDIMENTARY),
    RED_SANDSTONE_ROCK("red_sandstone_rock", StoneGroupType.SEDIMENTARY),
    GREYWACKE_ROCK("greywacke_rock", StoneGroupType.SEDIMENTARY),
    MUDSTONE_ROCK("mudstone_rock", StoneGroupType.SEDIMENTARY),
    VEINQUARTZ_ROCK("veinquartz_rock", StoneGroupType.SEDIMENTARY),

    // Extrusive Igneous
    RHYOLITE_ROCK("rhyolite_rock", StoneGroupType.EXTRUSIVE),
    DACITE_ROCK("dacite_rock", StoneGroupType.EXTRUSIVE),
    ANDESITE_ROCK("andesite_rock", StoneGroupType.EXTRUSIVE),
    BASALT_ROCK("basalt_rock", StoneGroupType.EXTRUSIVE),

    // Intrusive Igneous
    DIORITE_ROCK("diorite_rock", StoneGroupType.INTRUSIVE),
    GRANODIORITE_ROCK("granodiorite_rock", StoneGroupType.INTRUSIVE),
    GRANITE_ROCK("granite_rock", StoneGroupType.INTRUSIVE),
    SYENITE_ROCK("syenite_rock", StoneGroupType.INTRUSIVE),
    GABBRO_ROCK("gabbro_rock", StoneGroupType.INTRUSIVE),
    DIABASE_ROCK("diabase_rock", StoneGroupType.INTRUSIVE),
    PERIDOTITE_ROCK("peridotite_rock", StoneGroupType.INTRUSIVE),
    KIMBERLITE_ROCK("kimberlite_rock", StoneGroupType.INTRUSIVE),
    LAMPROITE_ROCK("lamproite_rock", StoneGroupType.INTRUSIVE),

    // Metamorphic
    QUARTZITE_ROCK("quartzite_rock", StoneGroupType.METAMORPHIC),
    SCHIST_ROCK("schist_rock", StoneGroupType.METAMORPHIC),
    PHYLLITE_ROCK("phyllite_rock", StoneGroupType.METAMORPHIC),
    GNEISS_ROCK("gneiss_rock", StoneGroupType.METAMORPHIC),
    MARBLE_ROCK("marble_rock", StoneGroupType.METAMORPHIC);

    private final String name;
    private final StoneGroupType group;

    RockType(String name, StoneGroupType group) {
        this.name = name;
        this.group = group;
    }

    @SuppressWarnings("NonFinalFieldInEnum")
    private RegistryObject<ItemInternal> item = null;

    @Override
    public Item asItem() {
        if (this.item == null) {
            throw new NullPointerException("RockTypes accessed too early!");
        }
        return this.item.get();
    }

    public String getName() {
        return this.name;
    }

    public static void register(DeferredRegister<Item> items) {
        for (RockType item : values()) {
            item.item = items.register(item.getName(), ItemInternal::new);
        }
    }

    private static final class ItemInternal extends Item {
        ItemInternal() {
            super(new Properties().group(ModItemGroup.RKU_STONE_GROUP));
        }
    }
}
