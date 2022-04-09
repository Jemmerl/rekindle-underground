package com.jemmerl.rekindleunderground.item;


import com.jemmerl.rekindleunderground.RekindleUnderground;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RekindleUnderground.MOD_ID);

    ///////////////////
    //     ROCKS     //
    ///////////////////

    // Sedimentary Rocks
    public static final RegistryObject<Item> CHALK_ROCK = ITEMS.register("chalk_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> LIMESTONE_ROCK = ITEMS.register("limestone_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> DOLOSTONE_ROCK = ITEMS.register("dolostone_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> SHALE_ROCK = ITEMS.register("shale_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> SANDSTONE_ROCK = ITEMS.register("sandstone_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> MUDSTONE_ROCK = ITEMS.register("mudstone_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> VEIN_QUARTZ_ROCK = ITEMS.register("veinquartz_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    // Igneous Extrusive Rocks
    public static final RegistryObject<Item> RHYOLITE_ROCK = ITEMS.register("rhyolite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> DACITE_ROCK = ITEMS.register("dacite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> ANDESITE_ROCK = ITEMS.register("andesite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> BASALT_ROCK = ITEMS.register("basalt_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    // Igneous Intrusive Rocks
    public static final RegistryObject<Item> DIORITE_ROCK = ITEMS.register("diorite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> GRANODIORITE_ROCK = ITEMS.register("granodiorite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> GRANITE_ROCK = ITEMS.register("granite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> SYENITE_ROCK = ITEMS.register("syenite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> GABBRO_ROCK = ITEMS.register("gabbro_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> PERIDOTITE_ROCK = ITEMS.register("peridotite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    // Metamorphic Rocks
    public static final RegistryObject<Item> QUARTZITE_ROCK = ITEMS.register("quartzite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> SCHIST_ROCK = ITEMS.register("schist_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> PHYLLITE_ROCK = ITEMS.register("phyllite_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> GNEISS_ROCK = ITEMS.register("gneiss_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    public static final RegistryObject<Item> MARBLE_ROCK = ITEMS.register("marble_rock",
            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));


    // Item registry method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
