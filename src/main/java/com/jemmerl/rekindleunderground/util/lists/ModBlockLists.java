package com.jemmerl.rekindleunderground.util.lists;

import com.jemmerl.rekindleunderground.data.enums.GeologyType;
import com.jemmerl.rekindleunderground.init.ModBlocks;
import com.jemmerl.rekindleunderground.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.*;

public class ModBlockLists {

    ////////////
    // STONES //
    ////////////

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


    //////////////
    // REGOLITH //
    //////////////

    public static List<Block> SED_REGOLITH = Arrays.asList(
            ModBlocks.CHALK_REGOLITH.get(),
            ModBlocks.LIMESTONE_REGOLITH.get(),
            ModBlocks.DOLOSTONE_REGOLITH.get(),
            ModBlocks.SHALE_REGOLITH.get(),
            ModBlocks.SANDSTONE_REGOLITH.get(),
            ModBlocks.RED_SANDSTONE_REGOLITH.get(),
            ModBlocks.GREYWACKE_REGOLITH.get(),
            ModBlocks.MUDSTONE_REGOLITH.get(),
            ModBlocks.VEIN_QUARTZ_REGOLITH.get()
    );

    public static List<Block> EXT_REGOLITH = Arrays.asList(
            ModBlocks.RHYOLITE_REGOLITH.get(),
            ModBlocks.DACITE_REGOLITH.get(),
            ModBlocks.ANDESITE_REGOLITH.get(),
            ModBlocks.BASALT_REGOLITH.get(),
            ModBlocks.SCORIA_REGOLITH.get()
    );

    public static List<Block> INT_REGOLITH = Arrays.asList(
            ModBlocks.DIORITE_REGOLITH.get(),
            ModBlocks.GRANODIORITE_REGOLITH.get(),
            ModBlocks.GRANITE_REGOLITH.get(),
            ModBlocks.SYENITE_REGOLITH.get(),
            ModBlocks.GABBRO_REGOLITH.get(),
            ModBlocks.DIABASE_REGOLITH.get(),
            ModBlocks.KIMBERLITE_REGOLITH.get(),
            ModBlocks.LAMPROITE_REGOLITH.get()
    );

    public static List<Block> IGN_REGOLITH = Arrays.asList(
            ModBlocks.RHYOLITE_REGOLITH.get(),
            ModBlocks.DACITE_REGOLITH.get(),
            ModBlocks.ANDESITE_REGOLITH.get(),
            ModBlocks.BASALT_REGOLITH.get(),
            ModBlocks.SCORIA_REGOLITH.get(),
            ModBlocks.DIORITE_REGOLITH.get(),
            ModBlocks.GRANODIORITE_REGOLITH.get(),
            ModBlocks.GRANITE_REGOLITH.get(),
            ModBlocks.SYENITE_REGOLITH.get(),
            ModBlocks.GABBRO_REGOLITH.get(),
            ModBlocks.DIABASE_REGOLITH.get(),
            ModBlocks.KIMBERLITE_REGOLITH.get(),
            ModBlocks.LAMPROITE_REGOLITH.get()
    );

    public static List<Block> MET_REGOLITH = Arrays.asList(
            ModBlocks.QUARTZITE_REGOLITH.get(),
            ModBlocks.SCHIST_REGOLITH.get(),
            ModBlocks.PHYLLITE_REGOLITH.get(),
            ModBlocks.GNEISS_REGOLITH.get(),
            ModBlocks.MARBLE_REGOLITH.get()
    );


    //////////////
    // DETRITUS //
    //////////////

    public static List<Block> STABLE_DET = Arrays.asList(
            ModBlocks.DIRT_STONE.get(),
            ModBlocks.COARSE_DIRT_STONE.get(),
            ModBlocks.CLAY_STONE.get()
    );

    public static List<Block> FALLING_DET = Arrays.asList(
            ModBlocks.SAND_STONE.get(),
            ModBlocks.RED_SAND_STONE.get(),
            ModBlocks.GRAVEL_STONE.get()
    );


    ////////////
    // COBBLE //
    ////////////

    public static List<Block> COBBLESTONES = Arrays.asList(
            // Sedimentary
            ModBlocks.CHALK_COBBLE.get(),
            ModBlocks.LIMESTONE_COBBLE.get(),
            ModBlocks.DOLOSTONE_COBBLE.get(),
            ModBlocks.SHALE_COBBLE.get(),
            ModBlocks.SANDSTONE_COBBLE.get(),
            ModBlocks.RED_SANDSTONE_COBBLE.get(),
            ModBlocks.GREYWACKE_COBBLE.get(),
            ModBlocks.MUDSTONE_COBBLE.get(),
            ModBlocks.VEIN_QUARTZ_COBBLE.get(),

            // Igneous Extrusive
            ModBlocks.RHYOLITE_COBBLE.get(),
            ModBlocks.DACITE_COBBLE.get(),
            ModBlocks.ANDESITE_COBBLE.get(),
            ModBlocks.BASALT_COBBLE.get(),

            // Igneous Intrusive
            ModBlocks.DIORITE_COBBLE.get(),
            ModBlocks.GRANODIORITE_COBBLE.get(),
            ModBlocks.GRANITE_COBBLE.get(),
            ModBlocks.SYENITE_COBBLE.get(),
            ModBlocks.GABBRO_COBBLE.get(),
            ModBlocks.DIABASE_COBBLE.get(),
            ModBlocks.KIMBERLITE_COBBLE.get(),
            ModBlocks.LAMPROITE_COBBLE.get(),

            // Metamorphic
            ModBlocks.QUARTZITE_COBBLE.get(),
            ModBlocks.SCHIST_COBBLE.get(),
            ModBlocks.PHYLLITE_COBBLE.get(),
            ModBlocks.GNEISS_COBBLE.get(),
            ModBlocks.MARBLE_COBBLE.get()
    );


    public static List<Block> ALL_STONES = new ArrayList<>();
    public static List<Block> ALL_REGOLITH = new ArrayList<>();
    public static List<Block> ALL_DETRITUS = new ArrayList<>();
    public static List<Block> ALL_OREBLOCKS = new ArrayList<>();

    static {
        ALL_STONES.addAll(SED_STONES);
        ALL_STONES.addAll(IGN_STONES);
        ALL_STONES.addAll(MET_STONES);

        ALL_REGOLITH.addAll(SED_REGOLITH);
        ALL_REGOLITH.addAll(IGN_REGOLITH);
        ALL_REGOLITH.addAll(MET_REGOLITH);

        ALL_DETRITUS.addAll(STABLE_DET);
        ALL_DETRITUS.addAll(FALLING_DET);

        ALL_OREBLOCKS.addAll(ALL_STONES);
        ALL_OREBLOCKS.addAll(ALL_REGOLITH);
        ALL_OREBLOCKS.addAll(ALL_DETRITUS);
    }


    public static Map<GeologyType, GeoListWrapper> GEO_LIST = new HashMap<>();
    static {
        GEO_LIST.put(GeologyType.CHALK, new GeoListWrapper(ModBlocks.CHALK_STONE.get(), ModBlocks.CHALK_COBBLE.get(), ModBlocks.CHALK_REGOLITH.get(), ModItems.CHALK_ROCK.get()));
        GEO_LIST.put(GeologyType.LIMESTONE, new GeoListWrapper(ModBlocks.LIMESTONE_STONE.get(), ModBlocks.LIMESTONE_COBBLE.get(), ModBlocks.LIMESTONE_REGOLITH.get(), ModItems.LIMESTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.DOLOSTONE, new GeoListWrapper(ModBlocks.DOLOSTONE_STONE.get(), ModBlocks.DOLOSTONE_COBBLE.get(), ModBlocks.DOLOSTONE_REGOLITH.get(), ModItems.DOLOSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.SHALE, new GeoListWrapper(ModBlocks.SHALE_STONE.get(), ModBlocks.SHALE_COBBLE.get(), ModBlocks.SHALE_REGOLITH.get(), ModItems.SHALE_ROCK.get()));
        GEO_LIST.put(GeologyType.SANDSTONE, new GeoListWrapper(ModBlocks.SANDSTONE_STONE.get(), ModBlocks.SANDSTONE_COBBLE.get(), ModBlocks.SANDSTONE_REGOLITH.get(), ModItems.SANDSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.RED_SANDSTONE, new GeoListWrapper(ModBlocks.RED_SANDSTONE_STONE.get(), ModBlocks.RED_SANDSTONE_COBBLE.get(), ModBlocks.RED_SANDSTONE_REGOLITH.get(), ModItems.RED_SANDSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.GREYWACKE, new GeoListWrapper(ModBlocks.GREYWACKE_STONE.get(), ModBlocks.GREYWACKE_COBBLE.get(), ModBlocks.GREYWACKE_REGOLITH.get(), ModItems.GREYWACKE_ROCK.get()));
        GEO_LIST.put(GeologyType.MUDSTONE, new GeoListWrapper(ModBlocks.MUDSTONE_STONE.get(), ModBlocks.MUDSTONE_COBBLE.get(), ModBlocks.MUDSTONE_REGOLITH.get(), ModItems.MUDSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.ROCKSALT, new GeoListWrapper(ModBlocks.ROCKSALT_STONE.get(), null, ModBlocks.ROCKSALT_STONE.get(), null));
        GEO_LIST.put(GeologyType.ROCKGYPSUM, new GeoListWrapper(ModBlocks.ROCKGYPSUM_STONE.get(), null, ModBlocks.ROCKGYPSUM_STONE.get(), null));
        GEO_LIST.put(GeologyType.BORAX, new GeoListWrapper(ModBlocks.BORAX_STONE.get(), null, ModBlocks.BORAX_STONE.get(), null));
        GEO_LIST.put(GeologyType.KERNITE, new GeoListWrapper(ModBlocks.KERNITE_STONE.get(), null, ModBlocks.KERNITE_STONE.get(), null));
        GEO_LIST.put(GeologyType.VEIN_QUARTZ, new GeoListWrapper(ModBlocks.VEIN_QUARTZ_STONE.get(), ModBlocks.VEIN_QUARTZ_COBBLE.get(), ModBlocks.VEIN_QUARTZ_REGOLITH.get(), ModItems.VEIN_QUARTZ_ROCK.get()));

        // Igneous Extrusive
        GEO_LIST.put(GeologyType.RHYOLITE, new GeoListWrapper(ModBlocks.RHYOLITE_STONE.get(), ModBlocks.RHYOLITE_COBBLE.get(), ModBlocks.RHYOLITE_REGOLITH.get(), ModItems.RHYOLITE_ROCK.get()));
        GEO_LIST.put(GeologyType.DACITE, new GeoListWrapper(ModBlocks.DACITE_STONE.get(), ModBlocks.DACITE_COBBLE.get(), ModBlocks.DACITE_REGOLITH.get(), ModItems.DACITE_ROCK.get()));
        GEO_LIST.put(GeologyType.ANDESITE, new GeoListWrapper(ModBlocks.ANDESITE_STONE.get(), ModBlocks.ANDESITE_COBBLE.get(), ModBlocks.ANDESITE_REGOLITH.get(), ModItems.ANDESITE_ROCK.get()));
        GEO_LIST.put(GeologyType.BASALT, new GeoListWrapper(ModBlocks.BASALT_STONE.get(), ModBlocks.BASALT_COBBLE.get(), ModBlocks.BASALT_REGOLITH.get(), ModItems.BASALT_ROCK.get()));
        GEO_LIST.put(GeologyType.SCORIA, new GeoListWrapper(ModBlocks.SCORIA_STONE.get(), null, ModBlocks.SCORIA_REGOLITH.get(), null));
        GEO_LIST.put(GeologyType.TUFF, new GeoListWrapper(ModBlocks.TUFF_STONE.get(), null, ModBlocks.TUFF_STONE.get(), null));

        // Igneous Intrusive
        GEO_LIST.put(GeologyType.DIORITE, new GeoListWrapper(ModBlocks.DIORITE_STONE.get(), ModBlocks.DIORITE_COBBLE.get(), ModBlocks.DIORITE_REGOLITH.get(), ModItems.DIORITE_ROCK.get()));
        GEO_LIST.put(GeologyType.GRANODIORITE, new GeoListWrapper(ModBlocks.GRANODIORITE_STONE.get(), ModBlocks.GRANODIORITE_COBBLE.get(), ModBlocks.GRANODIORITE_REGOLITH.get(), ModItems.GRANODIORITE_ROCK.get()));
        GEO_LIST.put(GeologyType.GRANITE, new GeoListWrapper(ModBlocks.GRANITE_STONE.get(), ModBlocks.GRANITE_COBBLE.get(), ModBlocks.GRANITE_REGOLITH.get(), ModItems.GRANITE_ROCK.get()));
        GEO_LIST.put(GeologyType.SYENITE, new GeoListWrapper(ModBlocks.SYENITE_STONE.get(), ModBlocks.SYENITE_COBBLE.get(), ModBlocks.SYENITE_REGOLITH.get(), ModItems.SYENITE_ROCK.get()));
        GEO_LIST.put(GeologyType.GABBRO, new GeoListWrapper(ModBlocks.GABBRO_STONE.get(), ModBlocks.GABBRO_COBBLE.get(), ModBlocks.GABBRO_REGOLITH.get(), ModItems.GABBRO_ROCK.get()));
        GEO_LIST.put(GeologyType.DIABASE, new GeoListWrapper(ModBlocks.DIABASE_STONE.get(), ModBlocks.DIABASE_COBBLE.get(), ModBlocks.DIABASE_REGOLITH.get(), ModItems.DIABASE_ROCK.get()));
        GEO_LIST.put(GeologyType.KIMBERLITE, new GeoListWrapper(ModBlocks.KIMBERLITE_STONE.get(), ModBlocks.KIMBERLITE_COBBLE.get(), ModBlocks.KIMBERLITE_REGOLITH.get(), ModItems.KIMBERLITE_ROCK.get()));
        GEO_LIST.put(GeologyType.LAMPROITE, new GeoListWrapper(ModBlocks.LAMPROITE_STONE.get(), ModBlocks.LAMPROITE_COBBLE.get(), ModBlocks.LAMPROITE_REGOLITH.get(), ModItems.LAMPROITE_ROCK.get()));

        // Metamorphic
        GEO_LIST.put(GeologyType.QUARTZITE, new GeoListWrapper(ModBlocks.QUARTZITE_STONE.get(), ModBlocks.QUARTZITE_COBBLE.get(), ModBlocks.QUARTZITE_REGOLITH.get(), ModItems.QUARTZITE_ROCK.get()));
        GEO_LIST.put(GeologyType.SCHIST, new GeoListWrapper(ModBlocks.SCHIST_STONE.get(), ModBlocks.SCHIST_COBBLE.get(), ModBlocks.SCHIST_REGOLITH.get(), ModItems.SCHIST_ROCK.get()));
        GEO_LIST.put(GeologyType.PHYLLITE, new GeoListWrapper(ModBlocks.PHYLLITE_STONE.get(), ModBlocks.PHYLLITE_COBBLE.get(), ModBlocks.PHYLLITE_REGOLITH.get(), ModItems.PHYLLITE_ROCK.get()));
        GEO_LIST.put(GeologyType.GNEISS, new GeoListWrapper(ModBlocks.GNEISS_STONE.get(), ModBlocks.GNEISS_COBBLE.get(), ModBlocks.GNEISS_REGOLITH.get(), ModItems.GNEISS_ROCK.get()));
        GEO_LIST.put(GeologyType.MARBLE, new GeoListWrapper(ModBlocks.MARBLE_STONE.get(), ModBlocks.MARBLE_COBBLE.get(), ModBlocks.MARBLE_REGOLITH.get(), ModItems.MARBLE_ROCK.get()));

        // Stable Detritus
        GEO_LIST.put(GeologyType.DIRT, new GeoListWrapper(ModBlocks.DIRT_STONE.get(), null, null, null));
        GEO_LIST.put(GeologyType.COARSE_DIRT, new GeoListWrapper(ModBlocks.COARSE_DIRT_STONE.get(), null, null, null));
        GEO_LIST.put(GeologyType.CLAY, new GeoListWrapper(ModBlocks.CLAY_STONE.get(), null, null, null));

        // Falling Detritus
        GEO_LIST.put(GeologyType.SAND, new GeoListWrapper(ModBlocks.SAND_STONE.get(), null, null, null));
        GEO_LIST.put(GeologyType.RED_SAND, new GeoListWrapper(ModBlocks.RED_SAND_STONE.get(), null, null, null));
        GEO_LIST.put(GeologyType.GRAVEL, new GeoListWrapper(ModBlocks.GRAVEL_STONE.get(), null, null, null));
    }


    //////////////////////
    // VANILLA DETRITUS //
    //////////////////////

    public static Map<BlockState, BlockState> VANILLA_DET_LIST = new HashMap<>();
    static {
        VANILLA_DET_LIST.put(Blocks.SAND.getDefaultState(), ModBlocks.SAND_STONE.get().getDefaultState());
        VANILLA_DET_LIST.put(Blocks.RED_SAND.getDefaultState(), ModBlocks.RED_SAND_STONE.get().getDefaultState());
        VANILLA_DET_LIST.put(Blocks.GRAVEL.getDefaultState(), ModBlocks.GRAVEL_STONE.get().getDefaultState());
        VANILLA_DET_LIST.put(Blocks.DIRT.getDefaultState(), ModBlocks.DIRT_STONE.get().getDefaultState());
        VANILLA_DET_LIST.put(Blocks.COARSE_DIRT.getDefaultState(), ModBlocks.COARSE_DIRT_STONE.get().getDefaultState());
        VANILLA_DET_LIST.put(Blocks.CLAY.getDefaultState(), ModBlocks.CLAY_STONE.get().getDefaultState());
    }

}

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

//    public static HashMap<Block, Item> COBBLESTONES = new HashMap<>();
//    static {
//        // Sedimentary
//        COBBLESTONES.put(ModBlocks.CHALK_COBBLE.get(), ModItems.CHALK_ROCK.get());
//        COBBLESTONES.put(ModBlocks.LIMESTONE_COBBLE.get(), ModItems.LIMESTONE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.DOLOSTONE_COBBLE.get(), ModItems.DOLOSTONE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.SHALE_COBBLE.get(), ModItems.SHALE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.SANDSTONE_COBBLE.get(), ModItems.SANDSTONE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.RED_SANDSTONE_COBBLE.get(), ModItems.RED_SANDSTONE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.GREYWACKE_COBBLE.get(), ModItems.GREYWACKE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.MUDSTONE_COBBLE.get(), ModItems.MUDSTONE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.VEIN_QUARTZ_COBBLE.get(), ModItems.VEIN_QUARTZ_ROCK.get());
//
//        // Igneous Extrusive
//        COBBLESTONES.put(ModBlocks.RHYOLITE_COBBLE.get(), ModItems.RHYOLITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.DACITE_COBBLE.get(), ModItems.DACITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.ANDESITE_COBBLE.get(), ModItems.ANDESITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.BASALT_COBBLE.get(), ModItems.BASALT_ROCK.get());
//
//        // Igneous Intrusive
//        COBBLESTONES.put(ModBlocks.DIORITE_COBBLE.get(), ModItems.DIORITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.GRANODIORITE_COBBLE.get(), ModItems.GRANODIORITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.GRANITE_COBBLE.get(), ModItems.GRANITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.SYENITE_COBBLE.get(), ModItems.SYENITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.GABBRO_COBBLE.get(), ModItems.GABBRO_ROCK.get());
//        COBBLESTONES.put(ModBlocks.DIABASE_COBBLE.get(), ModItems.DIABASE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.PERIDOTITE_COBBLE.get(), ModItems.PERIDOTITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.KIMBERLITE_COBBLE.get(), ModItems.KIMBERLITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.LAMPROITE_COBBLE.get(), ModItems.LAMPROITE_ROCK.get());
//
//        // Metamorphic
//        COBBLESTONES.put(ModBlocks.QUARTZITE_COBBLE.get(), ModItems.QUARTZITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.SCHIST_COBBLE.get(), ModItems.SCHIST_ROCK.get());
//        COBBLESTONES.put(ModBlocks.PHYLLITE_COBBLE.get(), ModItems.PHYLLITE_ROCK.get());
//        COBBLESTONES.put(ModBlocks.GNEISS_COBBLE.get(), ModItems.GNEISS_ROCK.get());
//        COBBLESTONES.put(ModBlocks.MARBLE_COBBLE.get(), ModItems.MARBLE_ROCK.get());
//    }
