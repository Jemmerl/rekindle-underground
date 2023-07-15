package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {

    public static final ItemGroup JEMGEO_STONE_GROUP = new ItemGroup("jemsgeo_stones_tab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.BASALT_STONE.get());
        }
    };

    public static final ItemGroup JEMGEO_COBBLE_GROUP = new ItemGroup("jemsgeo_cobbles_tab") {
        @Override
        public ItemStack createIcon() {
            return ModItems.BASALT_ROCK.get().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMGEO_ORES_GROUP = new ItemGroup("jemsgeo_ores_tab") {
        @Override
        public ItemStack createIcon() {
            return OreType.FLUORITE.getOreItem().getDefaultInstance();
        }
    };

}