package com.jemmerl.rekindleunderground.util.lists;

import com.jemmerl.rekindleunderground.data.enums.GeologyType;
import com.jemmerl.rekindleunderground.init.ModBlocks;
import com.jemmerl.rekindleunderground.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;

public class ModBlockLists {

    ////////////
    // STONES //
    ////////////

    public static List<Block> SED_STONES = Arrays.asList(
            ModBlocks.CHALK_STONE.get(),
            ModBlocks.LIMESTONE_STONE.get(),
            ModBlocks.DOLOSTONE_STONE.get(),
            ModBlocks.MARLSTONE_STONE.get(),
            ModBlocks.SHALE_STONE.get(),
            ModBlocks.LIMY_SHALE_STONE.get(),
            ModBlocks.SANDSTONE_STONE.get(),
            ModBlocks.RED_SANDSTONE_STONE.get(),
            ModBlocks.ARKOSE_STONE.get(),
            ModBlocks.GREYWACKE_STONE.get(),
            ModBlocks.MUDSTONE_STONE.get(),
            ModBlocks.CLAYSTONE_STONE.get(),
            ModBlocks.SILTSTONE_STONE.get(),
            ModBlocks.CONGLOMERATE_STONE.get(),
            ModBlocks.ROCKSALT_STONE.get(),
            ModBlocks.ROCKGYPSUM_STONE.get(),
            ModBlocks.BORAX_STONE.get(),
            ModBlocks.KERNITE_STONE.get(),
            ModBlocks.VEIN_QUARTZ_STONE.get()
    );

    public static List<Block> EXT_STONES = Arrays.asList(
            ModBlocks.RHYOLITE_STONE.get(),
            ModBlocks.DACITE_STONE.get(),
            ModBlocks.ANDESITE_STONE.get(),
            ModBlocks.TRACHYTE_STONE.get(),
            ModBlocks.BASALT_STONE.get(),
            ModBlocks.PAHOEHOE_STONE.get(),
            ModBlocks.SCORIA_STONE.get(),
            ModBlocks.RHYOLITIC_TUFF_STONE.get(),
            ModBlocks.TRACHYTIC_TUFF_STONE.get(),
            ModBlocks.ANDESITIC_TUFF_STONE.get(),
            ModBlocks.BASALTIC_TUFF_STONE.get(),
            ModBlocks.ULTRAMAFIC_TUFF_STONE.get()
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

    public static List<Block> IGN_STONES = new ArrayList<>();
    static {
        IGN_STONES.addAll(EXT_STONES);
        IGN_STONES.addAll(INT_STONES);
    }

    public static List<Block> MET_STONES = Arrays.asList(
            ModBlocks.QUARTZITE_STONE.get(),
            ModBlocks.SCHIST_STONE.get(),
            ModBlocks.PHYLLITE_STONE.get(),
            ModBlocks.SLATE_STONE.get(),
            ModBlocks.GNEISS_STONE.get(),
            ModBlocks.MARBLE_STONE.get(),
            ModBlocks.PELITIC_HORNFELS_STONE.get(),
            ModBlocks.CARBONATE_HORNFELS_STONE.get(),
            ModBlocks.MAFIC_HORNFELS_STONE.get(),
            ModBlocks.METACONGLOMERATE_STONE.get(),
            ModBlocks.GREISEN_STONE.get()
    );


    //////////////
    // REGOLITH //
    //////////////

    public static List<Block> SED_REGOLITH = Arrays.asList(
            ModBlocks.CHALK_REGOLITH.get(),
            ModBlocks.LIMESTONE_REGOLITH.get(),
            ModBlocks.DOLOSTONE_REGOLITH.get(),
            ModBlocks.MARLSTONE_REGOLITH.get(),
            ModBlocks.SHALE_REGOLITH.get(),
            ModBlocks.LIMY_SHALE_REGOLITH.get(),
            ModBlocks.SANDSTONE_REGOLITH.get(),
            ModBlocks.RED_SANDSTONE_REGOLITH.get(),
            ModBlocks.ARKOSE_REGOLITH.get(),
            ModBlocks.GREYWACKE_REGOLITH.get(),
            ModBlocks.MUDSTONE_REGOLITH.get(),
            ModBlocks.CLAYSTONE_REGOLITH.get(),
            ModBlocks.SILTSTONE_REGOLITH.get(),
            ModBlocks.CONGLOMERATE_REGOLITH.get(),
            ModBlocks.VEIN_QUARTZ_REGOLITH.get()
    );

    public static List<Block> EXT_REGOLITH = Arrays.asList(
            ModBlocks.RHYOLITE_REGOLITH.get(),
            ModBlocks.DACITE_REGOLITH.get(),
            ModBlocks.ANDESITE_REGOLITH.get(),
            ModBlocks.TRACHYTE_REGOLITH.get(),
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

    public static List<Block> IGN_REGOLITH = new ArrayList<>();
    static {
        IGN_REGOLITH.addAll(EXT_REGOLITH);
        IGN_REGOLITH.addAll(INT_REGOLITH);
    }

    public static List<Block> MET_REGOLITH = Arrays.asList(
            ModBlocks.QUARTZITE_REGOLITH.get(),
            ModBlocks.SCHIST_REGOLITH.get(),
            ModBlocks.PHYLLITE_REGOLITH.get(),
            ModBlocks.SLATE_REGOLITH.get(),
            ModBlocks.GNEISS_REGOLITH.get(),
            ModBlocks.MARBLE_REGOLITH.get(),
            ModBlocks.PELITIC_HORNFELS_REGOLITH.get(),
            ModBlocks.CARBONATE_HORNFELS_REGOLITH.get(),
            ModBlocks.MAFIC_HORNFELS_REGOLITH.get(),
            ModBlocks.METACONGLOMERATE_REGOLITH.get(),
            ModBlocks.GREISEN_REGOLITH.get()
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
            ModBlocks.MARLSTONE_COBBLE.get(),
            ModBlocks.SHALE_COBBLE.get(),
            ModBlocks.LIMY_SHALE_COBBLE.get(),
            ModBlocks.SANDSTONE_COBBLE.get(),
            ModBlocks.RED_SANDSTONE_COBBLE.get(),
            ModBlocks.ARKOSE_COBBLE.get(),
            ModBlocks.GREYWACKE_COBBLE.get(),
            ModBlocks.MUDSTONE_COBBLE.get(),
            ModBlocks.CLAYSTONE_COBBLE.get(),
            ModBlocks.SILTSTONE_COBBLE.get(),
            ModBlocks.CONGLOMERATE_COBBLE.get(),
            ModBlocks.VEIN_QUARTZ_COBBLE.get(),

            // Igneous Extrusive
            ModBlocks.RHYOLITE_COBBLE.get(),
            ModBlocks.DACITE_COBBLE.get(),
            ModBlocks.ANDESITE_COBBLE.get(),
            ModBlocks.TRACHYTE_COBBLE.get(),
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
            ModBlocks.SLATE_COBBLE.get(),
            ModBlocks.GNEISS_COBBLE.get(),
            ModBlocks.MARBLE_COBBLE.get(),
            ModBlocks.PELITIC_HORNFELS_COBBLE.get(),
            ModBlocks.CARBONATE_HORNFELS_COBBLE.get(),
            ModBlocks.MAFIC_HORNFELS_COBBLE.get(),
            ModBlocks.METACONGLOMERATE_COBBLE.get(),
            ModBlocks.GREISEN_COBBLE.get()
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
        GEO_LIST.put(GeologyType.MARLSTONE, new GeoListWrapper(ModBlocks.MARLSTONE_STONE.get(), ModBlocks.MARLSTONE_COBBLE.get(), ModBlocks.MARLSTONE_REGOLITH.get(), ModItems.MARLSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.SHALE, new GeoListWrapper(ModBlocks.SHALE_STONE.get(), ModBlocks.SHALE_COBBLE.get(), ModBlocks.SHALE_REGOLITH.get(), ModItems.SHALE_ROCK.get()));
        GEO_LIST.put(GeologyType.LIMY_SHALE, new GeoListWrapper(ModBlocks.LIMY_SHALE_STONE.get(), ModBlocks.LIMY_SHALE_COBBLE.get(), ModBlocks.LIMY_SHALE_REGOLITH.get(), ModItems.LIMY_SHALE_ROCK.get()));
        GEO_LIST.put(GeologyType.SANDSTONE, new GeoListWrapper(ModBlocks.SANDSTONE_STONE.get(), ModBlocks.SANDSTONE_COBBLE.get(), ModBlocks.SANDSTONE_REGOLITH.get(), ModItems.SANDSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.RED_SANDSTONE, new GeoListWrapper(ModBlocks.RED_SANDSTONE_STONE.get(), ModBlocks.RED_SANDSTONE_COBBLE.get(), ModBlocks.RED_SANDSTONE_REGOLITH.get(), ModItems.RED_SANDSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.ARKOSE, new GeoListWrapper(ModBlocks.ARKOSE_STONE.get(), ModBlocks.ARKOSE_COBBLE.get(), ModBlocks.ARKOSE_REGOLITH.get(), ModItems.ARKOSE_ROCK.get()));
        GEO_LIST.put(GeologyType.GREYWACKE, new GeoListWrapper(ModBlocks.GREYWACKE_STONE.get(), ModBlocks.GREYWACKE_COBBLE.get(), ModBlocks.GREYWACKE_REGOLITH.get(), ModItems.GREYWACKE_ROCK.get()));
        GEO_LIST.put(GeologyType.MUDSTONE, new GeoListWrapper(ModBlocks.MUDSTONE_STONE.get(), ModBlocks.MUDSTONE_COBBLE.get(), ModBlocks.MUDSTONE_REGOLITH.get(), ModItems.MUDSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.CLAYSTONE, new GeoListWrapper(ModBlocks.CLAYSTONE_STONE.get(), ModBlocks.CLAYSTONE_COBBLE.get(), ModBlocks.CLAYSTONE_REGOLITH.get(), ModItems.CLAYSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.SILTSTONE, new GeoListWrapper(ModBlocks.SILTSTONE_STONE.get(), ModBlocks.SILTSTONE_COBBLE.get(), ModBlocks.SILTSTONE_REGOLITH.get(), ModItems.SILTSTONE_ROCK.get()));
        GEO_LIST.put(GeologyType.CONGLOMERATE, new GeoListWrapper(ModBlocks.CONGLOMERATE_STONE.get(), ModBlocks.CONGLOMERATE_COBBLE.get(), ModBlocks.CONGLOMERATE_REGOLITH.get(), ModItems.CONGLOMERATE_ROCK.get()));
        GEO_LIST.put(GeologyType.ROCKSALT, new GeoListWrapper(ModBlocks.ROCKSALT_STONE.get(), null, ModBlocks.ROCKSALT_STONE.get(), null));
        GEO_LIST.put(GeologyType.ROCKGYPSUM, new GeoListWrapper(ModBlocks.ROCKGYPSUM_STONE.get(), null, ModBlocks.ROCKGYPSUM_STONE.get(), null));
        GEO_LIST.put(GeologyType.BORAX, new GeoListWrapper(ModBlocks.BORAX_STONE.get(), null, ModBlocks.BORAX_STONE.get(), null));
        GEO_LIST.put(GeologyType.KERNITE, new GeoListWrapper(ModBlocks.KERNITE_STONE.get(), null, ModBlocks.KERNITE_STONE.get(), null));
        GEO_LIST.put(GeologyType.VEIN_QUARTZ, new GeoListWrapper(ModBlocks.VEIN_QUARTZ_STONE.get(), ModBlocks.VEIN_QUARTZ_COBBLE.get(), ModBlocks.VEIN_QUARTZ_REGOLITH.get(), ModItems.VEIN_QUARTZ_ROCK.get()));

        // Igneous Extrusive
        GEO_LIST.put(GeologyType.RHYOLITE, new GeoListWrapper(ModBlocks.RHYOLITE_STONE.get(), ModBlocks.RHYOLITE_COBBLE.get(), ModBlocks.RHYOLITE_REGOLITH.get(), ModItems.RHYOLITE_ROCK.get()));
        GEO_LIST.put(GeologyType.DACITE, new GeoListWrapper(ModBlocks.DACITE_STONE.get(), ModBlocks.DACITE_COBBLE.get(), ModBlocks.DACITE_REGOLITH.get(), ModItems.DACITE_ROCK.get()));
        GEO_LIST.put(GeologyType.ANDESITE, new GeoListWrapper(ModBlocks.ANDESITE_STONE.get(), ModBlocks.ANDESITE_COBBLE.get(), ModBlocks.ANDESITE_REGOLITH.get(), ModItems.ANDESITE_ROCK.get()));
        GEO_LIST.put(GeologyType.TRACHYTE, new GeoListWrapper(ModBlocks.TRACHYTE_STONE.get(), ModBlocks.TRACHYTE_COBBLE.get(), ModBlocks.TRACHYTE_REGOLITH.get(), ModItems.TRACHYTE_ROCK.get()));
        GEO_LIST.put(GeologyType.BASALT, new GeoListWrapper(ModBlocks.BASALT_STONE.get(), ModBlocks.BASALT_COBBLE.get(), ModBlocks.BASALT_REGOLITH.get(), ModItems.BASALT_ROCK.get()));
        GEO_LIST.put(GeologyType.PAHOEHOE, new GeoListWrapper(ModBlocks.PAHOEHOE_STONE.get(), ModBlocks.BASALT_COBBLE.get(), ModBlocks.BASALT_REGOLITH.get(), ModItems.BASALT_ROCK.get()));
        GEO_LIST.put(GeologyType.SCORIA, new GeoListWrapper(ModBlocks.SCORIA_STONE.get(), null, ModBlocks.SCORIA_REGOLITH.get(), null));
        GEO_LIST.put(GeologyType.RHYOLITIC_TUFF, new GeoListWrapper(ModBlocks.RHYOLITIC_TUFF_STONE.get(), null, ModBlocks.RHYOLITIC_TUFF_STONE.get(), null));
        GEO_LIST.put(GeologyType.TRACHYTIC_TUFF, new GeoListWrapper(ModBlocks.TRACHYTIC_TUFF_STONE.get(), null, ModBlocks.TRACHYTIC_TUFF_STONE.get(), null));
        GEO_LIST.put(GeologyType.ANDESITIC_TUFF, new GeoListWrapper(ModBlocks.ANDESITIC_TUFF_STONE.get(), null, ModBlocks.ANDESITIC_TUFF_STONE.get(), null));
        GEO_LIST.put(GeologyType.BASALTIC_TUFF, new GeoListWrapper(ModBlocks.BASALTIC_TUFF_STONE.get(), null, ModBlocks.BASALTIC_TUFF_STONE.get(), null));
        GEO_LIST.put(GeologyType.ULTRAMAFIC_TUFF, new GeoListWrapper(ModBlocks.ULTRAMAFIC_TUFF_STONE.get(), null, ModBlocks.ULTRAMAFIC_TUFF_STONE.get(), null));

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
        GEO_LIST.put(GeologyType.SLATE, new GeoListWrapper(ModBlocks.SLATE_STONE.get(), ModBlocks.SLATE_COBBLE.get(), ModBlocks.SLATE_REGOLITH.get(), ModItems.SLATE_ROCK.get()));
        GEO_LIST.put(GeologyType.GNEISS, new GeoListWrapper(ModBlocks.GNEISS_STONE.get(), ModBlocks.GNEISS_COBBLE.get(), ModBlocks.GNEISS_REGOLITH.get(), ModItems.GNEISS_ROCK.get()));
        GEO_LIST.put(GeologyType.MARBLE, new GeoListWrapper(ModBlocks.MARBLE_STONE.get(), ModBlocks.MARBLE_COBBLE.get(), ModBlocks.MARBLE_REGOLITH.get(), ModItems.MARBLE_ROCK.get()));
        GEO_LIST.put(GeologyType.PELITIC_HORNFELS, new GeoListWrapper(ModBlocks.PELITIC_HORNFELS_STONE.get(), ModBlocks.PELITIC_HORNFELS_COBBLE.get(), ModBlocks.PELITIC_HORNFELS_REGOLITH.get(), ModItems.PELITIC_HORNFELS_ROCK.get()));
        GEO_LIST.put(GeologyType.CARBONATE_HORNFELS, new GeoListWrapper(ModBlocks.CARBONATE_HORNFELS_STONE.get(), ModBlocks.CARBONATE_HORNFELS_COBBLE.get(), ModBlocks.CARBONATE_HORNFELS_REGOLITH.get(), ModItems.CARBONATE_HORNFELS_ROCK.get()));
        GEO_LIST.put(GeologyType.MAFIC_HORNFELS, new GeoListWrapper(ModBlocks.MAFIC_HORNFELS_STONE.get(), ModBlocks.MAFIC_HORNFELS_COBBLE.get(), ModBlocks.MAFIC_HORNFELS_REGOLITH.get(), ModItems.MAFIC_HORNFELS_ROCK.get()));
        GEO_LIST.put(GeologyType.METACONGLOMERATE, new GeoListWrapper(ModBlocks.METACONGLOMERATE_STONE.get(), ModBlocks.METACONGLOMERATE_COBBLE.get(), ModBlocks.METACONGLOMERATE_REGOLITH.get(), ModItems.METACONGLOMERATE_ROCK.get()));
        GEO_LIST.put(GeologyType.GREISEN, new GeoListWrapper(ModBlocks.GREISEN_STONE.get(), ModBlocks.GREISEN_COBBLE.get(), ModBlocks.GREISEN_REGOLITH.get(), ModItems.GREISEN_ROCK.get()));

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


    //////////////////////////
    // CONTACT METAMORPHISM //
    //////////////////////////

    public static Map<GeologyType, BlockState> CONTACT_META_MAP = new HashMap<>();
    static {
        // Quartzite
        CONTACT_META_MAP.put(GeologyType.SANDSTONE, ModBlocks.QUARTZITE_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.RED_SANDSTONE, ModBlocks.QUARTZITE_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.ARKOSE, ModBlocks.QUARTZITE_STONE.get().getDefaultState());

        // Marble
        CONTACT_META_MAP.put(GeologyType.LIMESTONE, ModBlocks.MARBLE_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.DOLOSTONE, ModBlocks.MARBLE_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.CHALK, ModBlocks.MARBLE_STONE.get().getDefaultState());

        // Carbonate Hornfels
        CONTACT_META_MAP.put(GeologyType.MARLSTONE, ModBlocks.CARBONATE_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.LIMY_SHALE, ModBlocks.CARBONATE_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.GREYWACKE, ModBlocks.CARBONATE_HORNFELS_STONE.get().getDefaultState());

        // Pelitic Hornfels
        CONTACT_META_MAP.put(GeologyType.MUDSTONE, ModBlocks.PELITIC_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.CLAYSTONE, ModBlocks.PELITIC_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.SILTSTONE, ModBlocks.PELITIC_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.SHALE, ModBlocks.PELITIC_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.SLATE, ModBlocks.PELITIC_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.SCHIST, ModBlocks.PELITIC_HORNFELS_STONE.get().getDefaultState());

        // Mafic Hornfels TODO NOT COMPLETE LIST
        CONTACT_META_MAP.put(GeologyType.RHYOLITIC_TUFF, ModBlocks.MAFIC_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.TRACHYTIC_TUFF, ModBlocks.MAFIC_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.ANDESITIC_TUFF, ModBlocks.MAFIC_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.BASALTIC_TUFF, ModBlocks.MAFIC_HORNFELS_STONE.get().getDefaultState());
        CONTACT_META_MAP.put(GeologyType.ULTRAMAFIC_TUFF, ModBlocks.MAFIC_HORNFELS_STONE.get().getDefaultState());

        // Other
        CONTACT_META_MAP.put(GeologyType.CONGLOMERATE, ModBlocks.METACONGLOMERATE_STONE.get().getDefaultState());
    }


    ////////////////////
    // MODEL BUILDING //
    ////////////////////

    // List of blocks with a different side texture
    public static List<Block> SIDE_TEXTURE_MODELS = Arrays.asList(
            ModBlocks.PAHOEHOE_STONE.get()
    );

}

// TODO Skarn will be an ore deposit type vs a rock to save my sanity