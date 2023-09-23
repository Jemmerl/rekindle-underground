package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {

    public static final ItemGroup JEMGEO_BASE_STONE_GROUP = new ItemGroup("jemsgeo_base_stones_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEOBLOCKS.get(GeologyType.BASALT).getBaseStone().asItem().getDefaultInstance();
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

    public static final ItemGroup JEMGEO_MISC_GROUP = new ItemGroup("jemsgeo_misc_tab") {
        @Override
        public ItemStack createIcon() {
            return ModItems.QUARRY_TOOL.get().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMGEO_ORE_BLOCK_GROUP = new ItemGroup("jemsgeo_ore_blocks_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEOBLOCKS.get(GeologyType.BASALT)
                    .getStoneOre(OreType.APATITE, GradeType.MIDGRADE).asItem().getDefaultInstance();
        }
    };

}