package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JemsGeology.MOD_ID);


    //////////
    // MISC //
    //////////

    public static final RegistryObject<Item> MORTAR = ITEMS.register("lime_mortar", () -> new Item(new Item.Properties().group(ModItemGroups.JEMGEO_MISC_GROUP)));
    public static final RegistryObject<Item> QUARRY_TOOL = ITEMS.register("quarry_tool", () -> new Item(new Item.Properties().maxDamage(128).group(ModItemGroups.JEMGEO_MISC_GROUP)));


    //////////
    // Ores //
    //////////

    public static final RegistryObject<Item> APATITE_ORE = registerOreItem(OreType.APATITE);
    public static final RegistryObject<Item> POOR_APATITE_ORE = registerPoorOreItem(OreType.APATITE);

    public static final RegistryObject<Item> AZURITE_ORE = registerOreItem(OreType.AZURITE);
    public static final RegistryObject<Item> POOR_AZURITE_ORE = registerPoorOreItem(OreType.AZURITE);

    public static final RegistryObject<Item> BARYTE_ORE = registerOreItem(OreType.BARYTE);
    public static final RegistryObject<Item> POOR_BARYTE_ORE = registerPoorOreItem(OreType.BARYTE);

    public static final RegistryObject<Item> BERYL_ORE = registerOreItem(OreType.BERYL);
    public static final RegistryObject<Item> POOR_BERYL_ORE = registerPoorOreItem(OreType.BERYL);

    public static final RegistryObject<Item> BISMUTHINITE_ORE = registerOreItem(OreType.BISMUTHINITE);
    public static final RegistryObject<Item> POOR_BISMUTHINITE_ORE = registerPoorOreItem(OreType.BISMUTHINITE);

    public static final RegistryObject<Item> CARNOTITE_ORE = registerOreItem(OreType.CARNOTITE);
    public static final RegistryObject<Item> POOR_CARNOTITE_ORE = registerPoorOreItem(OreType.CARNOTITE);

    public static final RegistryObject<Item> CASSITERITE_ORE = registerOreItem(OreType.CASSITERITE);
    public static final RegistryObject<Item> POOR_CASSITERITE_ORE = registerPoorOreItem(OreType.CASSITERITE);

    public static final RegistryObject<Item> CELESTINE_ORE = registerOreItem(OreType.CELESTINE);
    public static final RegistryObject<Item> POOR_CELESTINE_ORE = registerPoorOreItem(OreType.CELESTINE);

    public static final RegistryObject<Item> CHALCOPYRITE_ORE = registerOreItem(OreType.CHALCOPYRITE);
    public static final RegistryObject<Item> POOR_CHALCOPYRITE_ORE = registerPoorOreItem(OreType.CHALCOPYRITE);

    public static final RegistryObject<Item> CHROMITE_ORE = registerOreItem(OreType.CHROMITE);
    public static final RegistryObject<Item> POOR_CHROMITE_ORE = registerPoorOreItem(OreType.CHROMITE);

    public static final RegistryObject<Item> CINNABAR_ORE = registerOreItem(OreType.CINNABAR);
    public static final RegistryObject<Item> POOR_CINNABAR_ORE = registerPoorOreItem(OreType.CINNABAR);

    public static final RegistryObject<Item> COBALTITE_ORE = registerOreItem(OreType.COBALTITE);
    public static final RegistryObject<Item> POOR_COBALTITE_ORE = registerPoorOreItem(OreType.COBALTITE);

    public static final RegistryObject<Item> CRYOLITE_ORE = registerOreItem(OreType.CRYOLITE);
    public static final RegistryObject<Item> POOR_CRYOLITE_ORE = registerPoorOreItem(OreType.CRYOLITE);

    public static final RegistryObject<Item> POOR_DIAMOND_ORE = registerPoorOreItem(OreType.DIAMOND);

    public static final RegistryObject<Item> ELECTRUM_ORE = registerOreItem(OreType.ELECTRUM);
    public static final RegistryObject<Item> POOR_ELECTRUM_ORE = registerPoorOreItem(OreType.ELECTRUM);

    public static final RegistryObject<Item> FLUORITE_ORE = registerOreItem(OreType.FLUORITE);
    public static final RegistryObject<Item> POOR_FLUORITE_ORE = registerPoorOreItem(OreType.FLUORITE);

    public static final RegistryObject<Item> GALENA_ORE = registerOreItem(OreType.GALENA);
    public static final RegistryObject<Item> POOR_GALENA_ORE = registerPoorOreItem(OreType.GALENA);

    public static final RegistryObject<Item> GOETHITE_ORE = registerOreItem(OreType.GOETHITE);
    public static final RegistryObject<Item> POOR_GOETHITE_ORE = registerPoorOreItem(OreType.GOETHITE);

    public static final RegistryObject<Item> GRAPHITE_ORE = registerOreItem(OreType.GRAPHITE);
    public static final RegistryObject<Item> POOR_GRAPHITE_ORE = registerPoorOreItem(OreType.GRAPHITE);

    public static final RegistryObject<Item> HEMATITE_ORE = registerOreItem(OreType.HEMATITE);
    public static final RegistryObject<Item> POOR_HEMATITE_ORE = registerPoorOreItem(OreType.HEMATITE);

    public static final RegistryObject<Item> ILMENITE_ORE = registerOreItem(OreType.ILMENITE);
    public static final RegistryObject<Item> POOR_ILMENITE_ORE = registerPoorOreItem(OreType.ILMENITE);

    public static final RegistryObject<Item> LEPIDOLITE_ORE = registerOreItem(OreType.LEPIDOLITE);
    public static final RegistryObject<Item> POOR_LEPIDOLITE_ORE = registerPoorOreItem(OreType.LEPIDOLITE);

    public static final RegistryObject<Item> LIMONITE_ORE = registerOreItem(OreType.LIMONITE);
    public static final RegistryObject<Item> POOR_LIMONITE_ORE = registerPoorOreItem(OreType.LIMONITE);

    public static final RegistryObject<Item> MAGNESITE_ORE = registerOreItem(OreType.MAGNESITE);
    public static final RegistryObject<Item> POOR_MAGNESITE_ORE = registerPoorOreItem(OreType.MAGNESITE);

    public static final RegistryObject<Item> MAGNETITE_ORE = registerOreItem(OreType.MAGNETITE);
    public static final RegistryObject<Item> POOR_MAGNETITE_ORE = registerPoorOreItem(OreType.MAGNETITE);

    public static final RegistryObject<Item> MALACHITE_ORE = registerOreItem(OreType.MALACHITE);
    public static final RegistryObject<Item> POOR_MALACHITE_ORE = registerPoorOreItem(OreType.MALACHITE);

    public static final RegistryObject<Item> MOLYBDENITE_ORE = registerOreItem(OreType.MOLYBDENITE);
    public static final RegistryObject<Item> POOR_MOLYBDENITE_ORE = registerPoorOreItem(OreType.MOLYBDENITE);

    public static final RegistryObject<Item> MONAZITE_ORE = registerOreItem(OreType.MONAZITE);
    public static final RegistryObject<Item> POOR_MONAZITE_ORE = registerPoorOreItem(OreType.MONAZITE);

    public static final RegistryObject<Item> NATIVE_COPPER_ORE = registerOreItem(OreType.NATIVE_COPPER);
    public static final RegistryObject<Item> POOR_NATIVE_COPPER_ORE = registerPoorOreItem(OreType.NATIVE_COPPER);

    public static final RegistryObject<Item> NATIVE_GOLD_ORE = registerOreItem(OreType.NATIVE_GOLD);
    public static final RegistryObject<Item> POOR_NATIVE_GOLD_ORE = registerPoorOreItem(OreType.NATIVE_GOLD);

    public static final RegistryObject<Item> NATIVE_SULFUR_ORE = registerOreItem(OreType.NATIVE_SULFUR);
    public static final RegistryObject<Item> POOR_NATIVE_SULFUR_ORE = registerPoorOreItem(OreType.NATIVE_SULFUR);

    public static final RegistryObject<Item> PENTLANDITE_ORE = registerOreItem(OreType.PENTLANDITE);
    public static final RegistryObject<Item> POOR_PENTLANDITE_ORE = registerPoorOreItem(OreType.PENTLANDITE);

    public static final RegistryObject<Item> PERIDOTITE_ORE = registerOreItem(OreType.PERIDOTITE);
    public static final RegistryObject<Item> POOR_PERIDOTITE_ORE = registerPoorOreItem(OreType.PERIDOTITE);

    public static final RegistryObject<Item> POLLUCITE_ORE = registerOreItem(OreType.POLLUCITE);
    public static final RegistryObject<Item> POOR_POLLUCITE_ORE = registerPoorOreItem(OreType.POLLUCITE);

    public static final RegistryObject<Item> PSILOMELANE_ORE = registerOreItem(OreType.PSILOMELANE);
    public static final RegistryObject<Item> POOR_PSILOMELANE_ORE = registerPoorOreItem(OreType.PSILOMELANE);

    public static final RegistryObject<Item> PYRITE_ORE = registerOreItem(OreType.PYRITE);
    public static final RegistryObject<Item> POOR_PYRITE_ORE = registerPoorOreItem(OreType.PYRITE);

    public static final RegistryObject<Item> PYROCHLORE_ORE = registerOreItem(OreType.PYROCHLORE);
    public static final RegistryObject<Item> POOR_PYROCHLORE_ORE = registerPoorOreItem(OreType.PYROCHLORE);

    public static final RegistryObject<Item> PYROLUSITE_ORE = registerOreItem(OreType.PYROLUSITE);
    public static final RegistryObject<Item> POOR_PYROLUSITE_ORE = registerPoorOreItem(OreType.PYROLUSITE);

    public static final RegistryObject<Item> RUTILE_ORE = registerOreItem(OreType.RUTILE);
    public static final RegistryObject<Item> POOR_RUTILE_ORE = registerPoorOreItem(OreType.RUTILE);

    public static final RegistryObject<Item> SCHEELITE_ORE = registerOreItem(OreType.SCHEELITE);
    public static final RegistryObject<Item> POOR_SCHEELITE_ORE = registerPoorOreItem(OreType.SCHEELITE);

    public static final RegistryObject<Item> SMITHSONITE_ORE = registerOreItem(OreType.SMITHSONITE);
    public static final RegistryObject<Item> POOR_SMITHSONITE_ORE = registerPoorOreItem(OreType.SMITHSONITE);

    public static final RegistryObject<Item> SPHALERITE_ORE = registerOreItem(OreType.SPHALERITE);
    public static final RegistryObject<Item> POOR_SPHALERITE_ORE = registerPoorOreItem(OreType.SPHALERITE);

    public static final RegistryObject<Item> SPODUMENE_ORE = registerOreItem(OreType.SPODUMENE);
    public static final RegistryObject<Item> POOR_SPODUMENE_ORE = registerPoorOreItem(OreType.SPODUMENE);

    public static final RegistryObject<Item> STIBNITE_ORE = registerOreItem(OreType.STIBNITE);
    public static final RegistryObject<Item> POOR_STIBNITE_ORE = registerPoorOreItem(OreType.STIBNITE);

    public static final RegistryObject<Item> SYLVITE_ORE = registerOreItem(OreType.SYLVITE);
    public static final RegistryObject<Item> POOR_SYLVITE_ORE = registerPoorOreItem(OreType.SYLVITE);

    public static final RegistryObject<Item> TANTALITE_ORE = registerOreItem(OreType.TANTALITE);
    public static final RegistryObject<Item> POOR_TANTALITE_ORE = registerPoorOreItem(OreType.TANTALITE);

    public static final RegistryObject<Item> TETRAHEDRITE_ORE = registerOreItem(OreType.TETRAHEDRITE);
    public static final RegistryObject<Item> POOR_TETRAHEDRITE_ORE = registerPoorOreItem(OreType.TETRAHEDRITE);

    public static final RegistryObject<Item> TRONA_ORE = registerOreItem(OreType.TRONA);
    public static final RegistryObject<Item> POOR_TRONA_ORE = registerPoorOreItem(OreType.TRONA);

    public static final RegistryObject<Item> URANINITE_ORE = registerOreItem(OreType.URANINITE);
    public static final RegistryObject<Item> POOR_URANINITE_ORE = registerPoorOreItem(OreType.URANINITE);

    public static final RegistryObject<Item> WOLFRAMITE_ORE = registerOreItem(OreType.WOLFRAMITE);
    public static final RegistryObject<Item> POOR_WOLFRAMITE_ORE = registerPoorOreItem(OreType.WOLFRAMITE);


    //////////////////////////
    // Registration Methods //
    //////////////////////////

    // Rock Item Creation and Registration
    public static RegistryObject<Item> registerRockItem(GeologyType geologyType) {
        return ITEMS.register((geologyType.getName() + "_rock"), () -> new Item(new Item.Properties().group(ModItemGroups.JEMGEO_COBBLE_GROUP)));
    }

    private static RegistryObject<Item> registerOreItem(OreType oreType) {
        return ITEMS.register((oreType.getString() + "_ore"), () -> new Item(new Item.Properties().group(ModItemGroups.JEMGEO_ORES_GROUP)));
    }

    private static RegistryObject<Item> registerPoorOreItem(OreType oreType) {
        return ITEMS.register(("poor_" + oreType.getString() + "_ore"), () -> new Item(new Item.Properties().group(ModItemGroups.JEMGEO_ORES_GROUP)));
    }

    // Item registry method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

