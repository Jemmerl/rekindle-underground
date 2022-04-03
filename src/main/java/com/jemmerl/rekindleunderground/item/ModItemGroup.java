package com.jemmerl.rekindleunderground.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModItemGroup {

    public static final ItemGroup RKU_GROUP = new ItemGroup("rekindleUndergroundTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.ACACIA_DOOR);
        } // TODO
    };

}