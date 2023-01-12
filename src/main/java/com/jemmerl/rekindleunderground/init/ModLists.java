package com.jemmerl.rekindleunderground.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModLists {

//    public static List<Block> ALL_STONES = Arrays.asList(
//            // Sedimentary
//            ModBlocks.CHALK_STONE.get(),
//            ModBlocks.LIMESTONE_STONE.get(),
//            ModBlocks.DOLOSTONE_STONE.get(),
//            ModBlocks.SHALE_STONE.get(),
//            ModBlocks.SANDSTONE_STONE.get(),
//            ModBlocks.RED_SANDSTONE_STONE.get(),
//            ModBlocks.GREYWACKE_STONE.get(),
//            ModBlocks.MUDSTONE_STONE.get(),
//            ModBlocks.ROCKSALT_STONE.get(),
//            ModBlocks.ROCKGYPSUM_STONE.get(),
//            ModBlocks.BORAX_STONE.get(),
//            ModBlocks.KERNITE_STONE.get(),
//            ModBlocks.VEIN_QUARTZ_STONE.get(),
//
//            // Igneous Extrusive
//            ModBlocks.RHYOLITE_STONE.get(),
//            ModBlocks.DACITE_STONE.get(),
//            ModBlocks.ANDESITE_STONE.get(),
//            ModBlocks.BASALT_STONE.get(),
//            ModBlocks.SCORIA_STONE.get(),
//            ModBlocks.TUFF_STONE.get(),
//
//            // Igneous Intrusive
//            ModBlocks.DIORITE_STONE.get(),
//            ModBlocks.GRANODIORITE_STONE.get(),
//            ModBlocks.GRANITE_STONE.get(),
//            ModBlocks.SYENITE_STONE.get(),
//            ModBlocks.GABBRO_STONE.get(),
//            ModBlocks.DIABASE_STONE.get(),
//            ModBlocks.PERIDOTITE_STONE.get(),
//            ModBlocks.KIMBERLITE_STONE.get(),
//            ModBlocks.LAMPROITE_STONE.get(),
//
//            // Metamorphic
//            ModBlocks.QUARTZITE_STONE.get(),
//            ModBlocks.SCHIST_STONE.get(),
//            ModBlocks.PHYLLITE_STONE.get(),
//            ModBlocks.GNEISS_STONE.get(),
//            ModBlocks.MARBLE_STONE.get()
//    );

    public static List<Block> SED_STONES = Arrays.asList(
            ModBlocks.CHALK_STONE.get(),
            ModBlocks.LIMESTONE_STONE.get(),
            ModBlocks.DOLOSTONE_STONE.get(),
            ModBlocks.SHALE_STONE.get(),
            ModBlocks.SANDSTONE_STONE.get(),
            ModBlocks.RED_SANDSTONE_STONE.get(),
            ModBlocks.GREYWACKE_STONE.get(),
            ModBlocks.MUDSTONE_STONE.get(),
            ModBlocks.ROCKSALT_STONE.get(),
            ModBlocks.ROCKGYPSUM_STONE.get(),
            ModBlocks.BORAX_STONE.get(),
            ModBlocks.KERNITE_STONE.get(),
            ModBlocks.VEIN_QUARTZ_STONE.get()
    );

    public static List<Block> IGN_STONES = Arrays.asList(
            ModBlocks.RHYOLITE_STONE.get(),
            ModBlocks.DACITE_STONE.get(),
            ModBlocks.ANDESITE_STONE.get(),
            ModBlocks.BASALT_STONE.get(),
            ModBlocks.SCORIA_STONE.get(),
            ModBlocks.TUFF_STONE.get(),
            ModBlocks.DIORITE_STONE.get(),
            ModBlocks.GRANODIORITE_STONE.get(),
            ModBlocks.GRANITE_STONE.get(),
            ModBlocks.SYENITE_STONE.get(),
            ModBlocks.GABBRO_STONE.get(),
            ModBlocks.DIABASE_STONE.get(),
            ModBlocks.PERIDOTITE_STONE.get(),
            ModBlocks.KIMBERLITE_STONE.get(),
            ModBlocks.LAMPROITE_STONE.get()
    );

    public static List<Block> EXT_STONES = Arrays.asList(
            ModBlocks.RHYOLITE_STONE.get(),
            ModBlocks.DACITE_STONE.get(),
            ModBlocks.ANDESITE_STONE.get(),
            ModBlocks.BASALT_STONE.get(),
            ModBlocks.SCORIA_STONE.get(),
            ModBlocks.TUFF_STONE.get()
    );

    public static List<Block> INT_STONES = Arrays.asList(
            ModBlocks.DIORITE_STONE.get(),
            ModBlocks.GRANODIORITE_STONE.get(),
            ModBlocks.GRANITE_STONE.get(),
            ModBlocks.SYENITE_STONE.get(),
            ModBlocks.GABBRO_STONE.get(),
            ModBlocks.DIABASE_STONE.get(),
            ModBlocks.PERIDOTITE_STONE.get(),
            ModBlocks.KIMBERLITE_STONE.get(),
            ModBlocks.LAMPROITE_STONE.get()
    );

    public static List<Block> MET_STONES = Arrays.asList(
            ModBlocks.QUARTZITE_STONE.get(),
            ModBlocks.SCHIST_STONE.get(),
            ModBlocks.PHYLLITE_STONE.get(),
            ModBlocks.GNEISS_STONE.get(),
            ModBlocks.MARBLE_STONE.get()
    );

//    public static List<Block> DETRITUS = Arrays.asList(
//            // Stable
//            ModBlocks.DIRT_DET.get(),
//            ModBlocks.COARSE_DIRT_DET.get(),
//            ModBlocks.CLAY_DET.get(),
//
//            // Falling
//            ModBlocks.SAND_DET.get(),
//            ModBlocks.RED_SAND_DET.get(),
//            ModBlocks.GRAVEL_DET.get()
//    );

    public static List<Block> STABLE_DET = Arrays.asList(
            ModBlocks.DIRT_DET.get(),
            ModBlocks.COARSE_DIRT_DET.get(),
            ModBlocks.CLAY_DET.get()
    );

    public static List<Block> FALLING_DET = Arrays.asList(
            ModBlocks.SAND_DET.get(),
            ModBlocks.RED_SAND_DET.get(),
            ModBlocks.GRAVEL_DET.get()
    );

    public static List<Block> ALL_STONES = new ArrayList<>();
    public static List<Block> ALL_DETRITUS = new ArrayList<>();
    public static List<Block> ALL_OREBLOCKS = new ArrayList<>();
    static {
        ALL_STONES.addAll(SED_STONES);
        ALL_STONES.addAll(IGN_STONES);
        ALL_STONES.addAll(MET_STONES);

        ALL_DETRITUS.addAll(STABLE_DET);
        ALL_DETRITUS.addAll(FALLING_DET);

        ALL_OREBLOCKS.addAll(ALL_STONES);
        ALL_OREBLOCKS.addAll(ALL_DETRITUS);
    }


    public static HashMap<Block, Item> COBBLESTONES = new HashMap<>();
    static {
        // Sedimentary
        COBBLESTONES.put(ModBlocks.CHALK_COBBLE.get(), ModItems.CHALK_ROCK.get());
        COBBLESTONES.put(ModBlocks.LIMESTONE_COBBLE.get(), ModItems.LIMESTONE_ROCK.get());
        COBBLESTONES.put(ModBlocks.DOLOSTONE_COBBLE.get(), ModItems.DOLOSTONE_ROCK.get());
        COBBLESTONES.put(ModBlocks.SHALE_COBBLE.get(), ModItems.SHALE_ROCK.get());
        COBBLESTONES.put(ModBlocks.SANDSTONE_COBBLE.get(), ModItems.SANDSTONE_ROCK.get());
        COBBLESTONES.put(ModBlocks.RED_SANDSTONE_COBBLE.get(), ModItems.RED_SANDSTONE_ROCK.get());
        COBBLESTONES.put(ModBlocks.GREYWACKE_COBBLE.get(), ModItems.GREYWACKE_ROCK.get());
        COBBLESTONES.put(ModBlocks.MUDSTONE_COBBLE.get(), ModItems.MUDSTONE_ROCK.get());
        COBBLESTONES.put(ModBlocks.VEIN_QUARTZ_COBBLE.get(), ModItems.VEINQUARTZ_ROCK.get());

        // Igneous Extrusive
        COBBLESTONES.put(ModBlocks.RHYOLITE_COBBLE.get(), ModItems.RHYOLITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.DACITE_COBBLE.get(), ModItems.DACITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.ANDESITE_COBBLE.get(), ModItems.ANDESITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.BASALT_COBBLE.get(), ModItems.BASALT_ROCK.get());

        // Igneous Intrusive
        COBBLESTONES.put(ModBlocks.DIORITE_COBBLE.get(), ModItems.DIORITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.GRANODIORITE_COBBLE.get(), ModItems.GRANODIORITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.GRANITE_COBBLE.get(), ModItems.GRANITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.SYENITE_COBBLE.get(), ModItems.SYENITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.GABBRO_COBBLE.get(), ModItems.GABBRO_ROCK.get());
        COBBLESTONES.put(ModBlocks.DIABASE_COBBLE.get(), ModItems.DIABASE_ROCK.get());
        COBBLESTONES.put(ModBlocks.PERIDOTITE_COBBLE.get(), ModItems.PERIDOTITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.KIMBERLITE_COBBLE.get(), ModItems.KIMBERLITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.LAMPROITE_COBBLE.get(), ModItems.LAMPROITE_ROCK.get());

        // Metamorphic
        COBBLESTONES.put(ModBlocks.QUARTZITE_COBBLE.get(), ModItems.QUARTZITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.SCHIST_COBBLE.get(), ModItems.SCHIST_ROCK.get());
        COBBLESTONES.put(ModBlocks.PHYLLITE_COBBLE.get(), ModItems.PHYLLITE_ROCK.get());
        COBBLESTONES.put(ModBlocks.GNEISS_COBBLE.get(), ModItems.GNEISS_ROCK.get());
        COBBLESTONES.put(ModBlocks.MARBLE_COBBLE.get(), ModItems.MARBLE_ROCK.get());
    }
}