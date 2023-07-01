package com.jemmerl.rekindleunderground.init;

import com.jemmerl.rekindleunderground.data.enums.ore.OreType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {

    public static final ItemGroup RKU_STONE_GROUP = new ItemGroup("rku_stones_tab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.BASALT_STONE.get());
        }
    };

    public static final ItemGroup RKU_ORES_GROUP = new ItemGroup("rku_ores_tab") {
        @Override
        public ItemStack createIcon() {
            return OreType.FLUORITE.getOreItem().getDefaultInstance();
        }
    };

}