package com.jemmerl.rekindleunderground.data.types;

import com.jemmerl.rekindleunderground.item.ModItemGroup;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Locale;

public enum RockType implements IItemProvider {
    // Sedimentary
    CHALK_ROCK,
    LIMESTONE_ROCK,
    DOLOSTONE_ROCK,
    SHALE_ROCK,
    SANDSTONE_ROCK,
    MUDSTONE_ROCK,
    VEIN_QUARTZ_ROCK,

    // Extrusive Igneous
    RHYOLITE_ROCK,
    DACITE_ROCK,
    ANDESITE_ROCK,
    BASALT_ROCK,

    // Intrusive Igneous
    DIORITE_ROCK,
    GRANODIORITE_ROCK,
    GRANITE_ROCK,
    SYENITE_ROCK,
    GABBRO_ROCK,
    DIABASE_ROCK,
    PERIDOTITE_ROCK,

    // Metamorphic
    QUARTZITE_ROCK,
    SCHIST_ROCK,
    PHYLLITE_ROCK,
    GNEISS_ROCK,
    MARBLE_ROCK;

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
        return name().toLowerCase(Locale.ROOT);
    }

    public static void register(DeferredRegister<Item> items) {
        for (RockType item : values()) {
            item.item = items.register(item.getName(), ItemInternal::new);
        }
    }

    private static final class ItemInternal extends Item {
        ItemInternal() {
            super(new Properties().group(ModItemGroup.RKU_GROUP));
        }
    }
}
