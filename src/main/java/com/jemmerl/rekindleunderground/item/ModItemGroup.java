package com.jemmerl.rekindleunderground.item;

import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModItemGroup {

    public static final ItemGroup RKU_GROUP = new ItemGroup("rku_tab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(StoneType.BASALT.getStoneState().getBlock());
        }
    };

}