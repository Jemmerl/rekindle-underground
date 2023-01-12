package com.jemmerl.rekindleunderground.init;


import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.RockType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RekindleUnderground.MOD_ID);

    static {
        OreType.register(ITEMS);
    }

    ///////////
    // Rocks //
    ///////////

    // Sedimentary
    public static final RegistryObject<Item> CHALK_ROCK = registerRockItem(RockType.CHALK_ROCK);
    public static final RegistryObject<Item> LIMESTONE_ROCK = registerRockItem(RockType.LIMESTONE_ROCK);
    public static final RegistryObject<Item> DOLOSTONE_ROCK = registerRockItem(RockType.DOLOSTONE_ROCK);
    public static final RegistryObject<Item> SHALE_ROCK = registerRockItem(RockType.SHALE_ROCK);
    public static final RegistryObject<Item> SANDSTONE_ROCK = registerRockItem(RockType.SANDSTONE_ROCK);
    public static final RegistryObject<Item> RED_SANDSTONE_ROCK = registerRockItem(RockType.RED_SANDSTONE_ROCK);
    public static final RegistryObject<Item> GREYWACKE_ROCK = registerRockItem(RockType.GREYWACKE_ROCK);
    public static final RegistryObject<Item> MUDSTONE_ROCK = registerRockItem(RockType.MUDSTONE_ROCK);
    public static final RegistryObject<Item> VEINQUARTZ_ROCK = registerRockItem(RockType.VEINQUARTZ_ROCK);

    // Extrusive Igneous
    public static final RegistryObject<Item> RHYOLITE_ROCK = registerRockItem(RockType.RHYOLITE_ROCK);
    public static final RegistryObject<Item> DACITE_ROCK = registerRockItem(RockType.DACITE_ROCK);
    public static final RegistryObject<Item> ANDESITE_ROCK = registerRockItem(RockType.ANDESITE_ROCK);
    public static final RegistryObject<Item> BASALT_ROCK = registerRockItem(RockType.BASALT_ROCK);

    // Intrusive Igneous
    public static final RegistryObject<Item> DIORITE_ROCK = registerRockItem(RockType.DIORITE_ROCK);
    public static final RegistryObject<Item> GRANODIORITE_ROCK = registerRockItem(RockType.GRANODIORITE_ROCK);
    public static final RegistryObject<Item> GRANITE_ROCK = registerRockItem(RockType.GRANITE_ROCK);
    public static final RegistryObject<Item> SYENITE_ROCK = registerRockItem(RockType.SYENITE_ROCK);
    public static final RegistryObject<Item> GABBRO_ROCK = registerRockItem(RockType.GABBRO_ROCK);
    public static final RegistryObject<Item> DIABASE_ROCK = registerRockItem(RockType.DIABASE_ROCK);
    public static final RegistryObject<Item> PERIDOTITE_ROCK = registerRockItem(RockType.PERIDOTITE_ROCK);
    public static final RegistryObject<Item> KIMBERLITE_ROCK = registerRockItem(RockType.KIMBERLITE_ROCK);
    public static final RegistryObject<Item> LAMPROITE_ROCK = registerRockItem(RockType.LAMPROITE_ROCK);

    // Metamorphic
    public static final RegistryObject<Item> QUARTZITE_ROCK = registerRockItem(RockType.QUARTZITE_ROCK);
    public static final RegistryObject<Item> SCHIST_ROCK = registerRockItem(RockType.SCHIST_ROCK);
    public static final RegistryObject<Item> PHYLLITE_ROCK = registerRockItem(RockType.PHYLLITE_ROCK);
    public static final RegistryObject<Item> GNEISS_ROCK = registerRockItem(RockType.GNEISS_ROCK);
    public static final RegistryObject<Item> MARBLE_ROCK = registerRockItem(RockType.MARBLE_ROCK);


    // Rock Item Creation and Registration
    private static RegistryObject<Item> registerRockItem(RockType rockType) {
        return ITEMS.register(rockType.getName(), () -> new Item(new Item.Properties().group(ModItemGroups.RKU_STONE_GROUP)));
    }

    // Item registry method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

    //////////////////
    //     ORES     //
    //////////////////

//    public static final RegistryObject<Item> APATITE_ORE = ITEMS.register("apatite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> AZURITE_ORE = ITEMS.register("azurite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> BARYTE_ORE = ITEMS.register("baryte_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> BERYL_ORE = ITEMS.register("beryl_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> BISMUTHINITE_ORE = ITEMS.register("bismuthinite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> CARNOTITE_ORE = ITEMS.register("carnotite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> CASSITERITE_ORE = ITEMS.register("cassiterite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> CELESTINE_ORE = ITEMS.register("celestine_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> CHALCOPYRITE_ORE = ITEMS.register("chalcopyrite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> CHROMITE_ORE = ITEMS.register("chromite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> CINNABAR_ORE = ITEMS.register("cinnabar_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> COBALTITE_ORE = ITEMS.register("cobaltite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> CRYOLITE_ORE = ITEMS.register("cryolite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> ELECTRUM_ORE = ITEMS.register("electrum_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> FLUORITE_ORE = ITEMS.register("fluorite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> GALENA_ORE = ITEMS.register("galena_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> GOETHITE_ORE = ITEMS.register("goethite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> GRAPHITE_ORE = ITEMS.register("graphite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> HEMATITE_ORE = ITEMS.register("hematite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> ILMENITE_ORE = ITEMS.register("ilmenite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> LEPIDOLITE_ORE = ITEMS.register("lepidolite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> LIMONITE_ORE = ITEMS.register("limonite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> MAGNESITE_ORE = ITEMS.register("magnesite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> MAGNETITE_ORE = ITEMS.register("magnetite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> MALACHITE_ORE = ITEMS.register("malachite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> MOLYBDENITE_ORE = ITEMS.register("molybdenite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> MONAZITE_ORE = ITEMS.register("monazite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> NATIVE_COPPER_ORE = ITEMS.register("native_copper_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> NATIVE_GOLD_ORE = ITEMS.register("native_gold_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> NATIVE_SULFUR_ORE = ITEMS.register("native_sulfur_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> PENTLANDITE_ORE = ITEMS.register("pentlandite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> POLLUCITE_ORE = ITEMS.register("pollucite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> PSILOMELANE_ORE = ITEMS.register("psilomelane_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> PYRITE_ORE = ITEMS.register("pyrite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> PYROCHLORE_ORE = ITEMS.register("pyrochlore_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> PYROLUSITE_ORE = ITEMS.register("pyrolusite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> RUTILE_ORE = ITEMS.register("rutile_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> SCHEELITE_ORE = ITEMS.register("scheelite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> SMITHSONITE_ORE = ITEMS.register("smithsonite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> SPHALERITE_ORE = ITEMS.register("sphalerite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> SPODUMENE_ORE = ITEMS.register("spodumene_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> STIBNITE_ORE = ITEMS.register("stibnite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> SYLVITE_ORE = ITEMS.register("sylvite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> TANTALITE_ORE = ITEMS.register("tantalite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> TETRAHEDRITE_ORE = ITEMS.register("tetrahedrite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> TRONA_ORE = ITEMS.register("trona_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> URANINITE_ORE = ITEMS.register("uraninite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
//
//    public static final RegistryObject<Item> WOLFRAMITE_ORE = ITEMS.register("wolframite_ore",
//        () -> new Item(new Item.Properties().group(ModItemGroup.RKU_ORES_GROUP)));
