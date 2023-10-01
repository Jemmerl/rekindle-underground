package com.jemmerl.jemsgeology.util.lists;

import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ModOresList {

    public static Map<OreType, OreListWrapper> ORE_LIST = new HashMap<>();
    static {
        ORE_LIST.put(OreType.NONE, new OreListWrapper(null, null));
        ORE_LIST.put(OreType.APATITE, new OreListWrapper(ModItems.APATITE_ORE.get(), ModItems.POOR_APATITE_ORE.get()));
        ORE_LIST.put(OreType.AZURITE, new OreListWrapper(ModItems.AZURITE_ORE.get(), ModItems.POOR_AZURITE_ORE.get()));
        ORE_LIST.put(OreType.BARYTE, new OreListWrapper(ModItems.BARYTE_ORE.get(), ModItems.POOR_BARYTE_ORE.get()));
        ORE_LIST.put(OreType.BERYL, new OreListWrapper(ModItems.BERYL_ORE.get(), ModItems.POOR_BERYL_ORE.get()));
        ORE_LIST.put(OreType.BISMUTHINITE, new OreListWrapper(ModItems.BISMUTHINITE_ORE.get(), ModItems.POOR_BISMUTHINITE_ORE.get()));
        ORE_LIST.put(OreType.CARNOTITE, new OreListWrapper(ModItems.CARNOTITE_ORE.get(), ModItems.POOR_CARNOTITE_ORE.get()));
        ORE_LIST.put(OreType.CASSITERITE, new OreListWrapper(ModItems.CASSITERITE_ORE.get(), ModItems.POOR_CASSITERITE_ORE.get()));
        ORE_LIST.put(OreType.CELESTINE, new OreListWrapper(ModItems.CELESTINE_ORE.get(), ModItems.POOR_CELESTINE_ORE.get()));
        ORE_LIST.put(OreType.CHALCOPYRITE, new OreListWrapper(ModItems.CHALCOPYRITE_ORE.get(), ModItems.POOR_CHALCOPYRITE_ORE.get()));
        ORE_LIST.put(OreType.CHROMITE, new OreListWrapper(ModItems.CHROMITE_ORE.get(), ModItems.POOR_CHROMITE_ORE.get()));
        ORE_LIST.put(OreType.CINNABAR, new OreListWrapper(ModItems.CINNABAR_ORE.get(), ModItems.POOR_CINNABAR_ORE.get()));
        ORE_LIST.put(OreType.COBALTITE, new OreListWrapper(ModItems.COBALTITE_ORE.get(), ModItems.POOR_COBALTITE_ORE.get()));
        ORE_LIST.put(OreType.CRYOLITE, new OreListWrapper(ModItems.CRYOLITE_ORE.get(), ModItems.POOR_CRYOLITE_ORE.get()));
        ORE_LIST.put(OreType.DIAMOND, new OreListWrapper(Items.DIAMOND, ModItems.POOR_DIAMOND_ORE.get()));
        ORE_LIST.put(OreType.FLUORITE, new OreListWrapper(ModItems.FLUORITE_ORE.get(), ModItems.POOR_FLUORITE_ORE.get()));
        ORE_LIST.put(OreType.GALENA, new OreListWrapper(ModItems.GALENA_ORE.get(), ModItems.POOR_GALENA_ORE.get()));
        ORE_LIST.put(OreType.GOETHITE, new OreListWrapper(ModItems.GOETHITE_ORE.get(), ModItems.POOR_GOETHITE_ORE.get()));
        ORE_LIST.put(OreType.GRAPHITE, new OreListWrapper(ModItems.GRAPHITE_ORE.get(), ModItems.POOR_GRAPHITE_ORE.get()));
        ORE_LIST.put(OreType.HEMATITE, new OreListWrapper(ModItems.HEMATITE_ORE.get(), ModItems.POOR_HEMATITE_ORE.get()));
        ORE_LIST.put(OreType.ILMENITE, new OreListWrapper(ModItems.ILMENITE_ORE.get(), ModItems.POOR_ILMENITE_ORE.get()));
        ORE_LIST.put(OreType.LEPIDOLITE, new OreListWrapper(ModItems.LEPIDOLITE_ORE.get(), ModItems.POOR_LEPIDOLITE_ORE.get()));
        ORE_LIST.put(OreType.LIMONITE, new OreListWrapper(ModItems.LIMONITE_ORE.get(), ModItems.POOR_LIMONITE_ORE.get()));
        ORE_LIST.put(OreType.MAGNESITE, new OreListWrapper(ModItems.MAGNESITE_ORE.get(), ModItems.POOR_MAGNESITE_ORE.get()));
        ORE_LIST.put(OreType.MAGNETITE, new OreListWrapper(ModItems.MAGNETITE_ORE.get(), ModItems.POOR_MAGNETITE_ORE.get()));
        ORE_LIST.put(OreType.MALACHITE, new OreListWrapper(ModItems.MALACHITE_ORE.get(), ModItems.POOR_MALACHITE_ORE.get()));
        ORE_LIST.put(OreType.MOLYBDENITE, new OreListWrapper(ModItems.MOLYBDENITE_ORE.get(), ModItems.POOR_MOLYBDENITE_ORE.get()));
        ORE_LIST.put(OreType.MONAZITE, new OreListWrapper(ModItems.MONAZITE_ORE.get(), ModItems.POOR_MONAZITE_ORE.get()));
        ORE_LIST.put(OreType.NATIVE_COPPER, new OreListWrapper(ModItems.NATIVE_COPPER_ORE.get(), ModItems.POOR_NATIVE_COPPER_ORE.get()));
        ORE_LIST.put(OreType.NATIVE_ELECTRUM, new OreListWrapper(ModItems.NATIVE_ELECTRUM_ORE.get(), ModItems.POOR_NATIVE_ELECTRUM_ORE.get()));
        ORE_LIST.put(OreType.NATIVE_GOLD, new OreListWrapper(ModItems.NATIVE_GOLD_ORE.get(), ModItems.POOR_NATIVE_GOLD_ORE.get()));
        ORE_LIST.put(OreType.NATIVE_SULFUR, new OreListWrapper(ModItems.NATIVE_SULFUR_ORE.get(), ModItems.POOR_NATIVE_SULFUR_ORE.get()));
        ORE_LIST.put(OreType.PENTLANDITE, new OreListWrapper(ModItems.PENTLANDITE_ORE.get(), ModItems.POOR_PENTLANDITE_ORE.get()));
        ORE_LIST.put(OreType.PERIDOTITE, new OreListWrapper(ModItems.PERIDOTITE_ORE.get(), ModItems.POOR_PERIDOTITE_ORE.get()));
        ORE_LIST.put(OreType.POLLUCITE, new OreListWrapper(ModItems.POLLUCITE_ORE.get(), ModItems.POOR_POLLUCITE_ORE.get()));
        ORE_LIST.put(OreType.PSILOMELANE, new OreListWrapper(ModItems.PSILOMELANE_ORE.get(), ModItems.POOR_PSILOMELANE_ORE.get()));
        ORE_LIST.put(OreType.PYRITE, new OreListWrapper(ModItems.PYRITE_ORE.get(), ModItems.POOR_PYRITE_ORE.get()));
        ORE_LIST.put(OreType.PYROCHLORE, new OreListWrapper(ModItems.PYROCHLORE_ORE.get(), ModItems.POOR_PYROCHLORE_ORE.get()));
        ORE_LIST.put(OreType.PYROLUSITE, new OreListWrapper(ModItems.PYROLUSITE_ORE.get(), ModItems.POOR_PYROLUSITE_ORE.get()));
        ORE_LIST.put(OreType.RUTILE, new OreListWrapper(ModItems.RUTILE_ORE.get(), ModItems.POOR_RUTILE_ORE.get()));
        ORE_LIST.put(OreType.SCHEELITE, new OreListWrapper(ModItems.SCHEELITE_ORE.get(), ModItems.POOR_SCHEELITE_ORE.get()));
        ORE_LIST.put(OreType.SMITHSONITE, new OreListWrapper(ModItems.SMITHSONITE_ORE.get(), ModItems.POOR_SMITHSONITE_ORE.get()));
        ORE_LIST.put(OreType.SPHALERITE, new OreListWrapper(ModItems.SPHALERITE_ORE.get(), ModItems.POOR_SPHALERITE_ORE.get()));
        ORE_LIST.put(OreType.SPODUMENE, new OreListWrapper(ModItems.SPODUMENE_ORE.get(), ModItems.POOR_SPODUMENE_ORE.get()));
        ORE_LIST.put(OreType.STIBNITE, new OreListWrapper(ModItems.STIBNITE_ORE.get(), ModItems.POOR_STIBNITE_ORE.get()));
        ORE_LIST.put(OreType.SYLVITE, new OreListWrapper(ModItems.SYLVITE_ORE.get(), ModItems.POOR_SYLVITE_ORE.get()));
        ORE_LIST.put(OreType.TANTALITE, new OreListWrapper(ModItems.TANTALITE_ORE.get(), ModItems.POOR_TANTALITE_ORE.get()));
        ORE_LIST.put(OreType.TETRAHEDRITE, new OreListWrapper(ModItems.TETRAHEDRITE_ORE.get(), ModItems.POOR_TETRAHEDRITE_ORE.get()));
        ORE_LIST.put(OreType.TRONA, new OreListWrapper(ModItems.TRONA_ORE.get(), ModItems.POOR_TRONA_ORE.get()));
        ORE_LIST.put(OreType.URANINITE, new OreListWrapper(ModItems.URANINITE_ORE.get(), ModItems.POOR_URANINITE_ORE.get()));
        ORE_LIST.put(OreType.WOLFRAMITE, new OreListWrapper(ModItems.WOLFRAMITE_ORE.get(), ModItems.POOR_WOLFRAMITE_ORE.get()));
    }
}
