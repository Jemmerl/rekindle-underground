package com.jemmerl.rekindleunderground.init;

import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModItemGroups {

    public static final ItemGroup RKU_STONE_GROUP = new ItemGroup("rku_stones_tab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(StoneType.BASALT.getStoneState().getBlock());
        }
    };

    public static final ItemGroup RKU_ORES_GROUP = new ItemGroup("rku_ores_tab") {
        @Override
        public ItemStack createIcon() {
            return OreType.FLUORITE.getOreItem().getDefaultInstance();
        }
    };

}