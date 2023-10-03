package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.blocks.*;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.blockinit.GeoRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, JemsGeology.MOD_ID);


    ///////////////////////
    // GEOBLOCK REGISTRY //
    ///////////////////////

    public static final HashMap<GeologyType, GeoRegistry> GEOBLOCKS = new HashMap<>();
    static {
        for (GeologyType geologyType: GeologyType.values()) {
            GEOBLOCKS.put(geologyType, new GeoRegistry(geologyType));
        }
    }


    ///////////////////////
    // PROPERTY BUILDERS //
    ///////////////////////

    private static final Block.Properties STONE_PROP = AbstractBlock.Properties.create(Material.ROCK)
            .sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0);

    private static final Block.Properties REGOLITH_PROP = AbstractBlock.Properties.create(Material.EARTH)
            .sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.7f);

    private static final Block.Properties DIRT_PROP = AbstractBlock.Properties.create(Material.EARTH)
            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).hardnessAndResistance(0.5f);

    private static final Block.Properties CLAY_PROP = AbstractBlock.Properties.create(Material.CLAY)
            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).hardnessAndResistance(0.6f);

    private static final Block.Properties SAND_PROP = AbstractBlock.Properties.create(Material.SAND)
            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND).hardnessAndResistance(0.5f);

    private static final Block.Properties GRAVEL_PROP = AbstractBlock.Properties.create(Material.SAND)
            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).hardnessAndResistance(0.6f);

    private static final AbstractBlock.Properties DECOR_STONE_PROP = AbstractBlock.Properties.create(Material.ROCK)
            .setRequiresTool().hardnessAndResistance(2.0F, 6.0F);

    private static final AbstractBlock.Properties POLISHED_STONE_PROP = AbstractBlock.Properties.create(Material.ROCK)
            .setRequiresTool().hardnessAndResistance(1.5F, 6.0F);

    // Stone Property Builder
    private static Block.Properties buildStoneProperties(GeologyType geologyType) {
        return STONE_PROP.hardnessAndResistance(geologyType.getStoneHardness(), geologyType.getStoneResistance());
    }

    // Cobbles Property Builder
    private static Block.Properties buildCobblesProperties(GeologyType geologyType) {
        return STONE_PROP.hardnessAndResistance(geologyType.getCobbleHardness(), geologyType.getCobbleResistance());
    }

    // Cobblestone Property Builder
    private static Block.Properties buildCobblestoneProperties() {
        return STONE_PROP.hardnessAndResistance(2F, 7F);
    }


    //////////////////////////
    // REGISTRATION METHODS //
    //////////////////////////

    /////// GEO BLOCKS ///////

    // For base stone blocks
    public static <T extends Block>RegistryObject<T> registerStoneGeoBlock(GeologyType geologyType) {
        String blockTypeName = (geologyType.isInStoneGroup(StoneGroupType.DETRITUS)) ? "_detritus" : "_stone";
        String name = geologyType.getName() + blockTypeName;
        Supplier<T> blockSupplier = () -> (T) new StoneGeoBlock(buildStoneProperties(geologyType), geologyType, OreType.NONE, GradeType.NONE);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMGEO_BASE_STONE_GROUP);
    }

    // For ore bearing stone blocks
    public static <T extends Block>RegistryObject<T> registerStoneGeoBlock(GeologyType geologyType, OreType oreType, GradeType gradeType) {
        String blockTypeName = (geologyType.isInStoneGroup(StoneGroupType.DETRITUS)) ? "_detritus" : "_stone";
        String name = geologyType.getName() + blockTypeName + "/" + oreType.getString() + "/" + gradeType.getString();
        Supplier<T> blockSupplier = () -> (T) new StoneGeoBlock(buildStoneProperties(geologyType), geologyType, oreType, gradeType);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMGEO_ORE_BLOCK_GROUP);
    }

    // For base regolith blocks
    public static <T extends Block>RegistryObject<T> registerRegolithGeoBlock(GeologyType geologyType) {
        String name = geologyType.getName() + "_regolith";
        Supplier<T> blockSupplier = () -> (T) new RegolithGeoBlock(REGOLITH_PROP, geologyType, OreType.NONE, GradeType.NONE);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMGEO_BASE_STONE_GROUP);
    }

    // For ore bearing regolith blocks
    public static <T extends Block>RegistryObject<T> registerRegolithGeoBlock(GeologyType geologyType, OreType oreType, GradeType gradeType) {
        String name = geologyType.getName() + "_regolith/" + oreType.getString() + "/" + gradeType.getString();
        Supplier<T> blockSupplier = () -> (T) new RegolithGeoBlock(REGOLITH_PROP, geologyType, oreType, gradeType);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMGEO_ORE_BLOCK_GROUP);
    }

    // For base detritus blocks
    public static <T extends Block>RegistryObject<T> registerDetritusBlock(GeologyType geologyType) {
        String name = geologyType.getName() + "_detritus";
        return registerBlock(name, buildDetritusBlock(geologyType, OreType.NONE, GradeType.NONE), ModItemGroups.JEMGEO_BASE_STONE_GROUP);
    }

    // For ore bearing detritus blocks
    public static <T extends Block>RegistryObject<T> registerDetritusBlock(GeologyType geologyType, OreType oreType, GradeType gradeType) {
        String name = geologyType.getName() + "_detritus/" + oreType.getString() + "/" + gradeType.getString();
        return registerBlock(name, buildDetritusBlock(geologyType, oreType, gradeType), ModItemGroups.JEMGEO_ORE_BLOCK_GROUP);
    }

    // Build the correct detritus block given its geology type
    private static <T extends Block>Supplier<T> buildDetritusBlock(GeologyType geologyType, OreType oreType, GradeType gradeType) {
        switch (geologyType) {
            case DIRT:
                return () -> (T) new BaseGeoBlock(DIRT_PROP, GeologyType.DIRT, oreType, gradeType);
            case COARSE_DIRT:
                return () -> (T) new BaseGeoBlock(DIRT_PROP, GeologyType.COARSE_DIRT, oreType, gradeType);
            case CLAY:
                return () -> (T) new BaseGeoBlock(CLAY_PROP, GeologyType.CLAY, oreType, gradeType);
            case SAND:
                return () -> (T) new FallingGeoBlock(SAND_PROP, GeologyType.SAND, oreType, gradeType);
            case RED_SAND:
                return () -> (T) new FallingGeoBlock(SAND_PROP, GeologyType.RED_SAND, oreType, gradeType);
            case GRAVEL:
                return () -> (T) new FallingGeoBlock(GRAVEL_PROP, GeologyType.GRAVEL, oreType, gradeType);
            default:
                return null;
        }
    }


    /////// DECORATIVE BLOCKS ///////

    // CUBE BLOCKS //
    public static <T extends Block>RegistryObject<T> registerCobblesBlock(GeologyType geologyType) {
        String name = geologyType.getName() + "_cobbles";
        Supplier<T> blockSupplier = () -> (T) new FallingCobbleBlock(buildCobblesProperties(geologyType), geologyType);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMGEO_BASE_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerCobblestoneBlock(GeologyType geologyType) {
        String name = geologyType.getName() + "_cobblestone";
        Supplier<T> blockSupplier = () -> (T) new Block(buildCobblestoneProperties());
        return registerBlock(name, blockSupplier, ModItemGroups.JEMGEO_COBBLE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerPolishedStoneBlock(GeologyType geologyType) {
        String name = "polished_" + geologyType.getName() + "_stone";
        Supplier<T> blockSupplier = () -> (T) new Block(POLISHED_STONE_PROP);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    // SLABS //
    public static <T extends Block>RegistryObject<T> registerCobbleSlab(GeologyType geologyType) {
        String name = geologyType.getName() + "_cobble_slab";
        Supplier<T> blockSupplier = () -> (T) new SlabBlock(DECOR_STONE_PROP);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerRawStoneSlab(GeologyType geologyType) {
        String name = geologyType.getName() + "_slab";
        Supplier<T> blockSupplier = () -> (T) new SlabBlock(DECOR_STONE_PROP);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerPolishedSlab(GeologyType geologyType) {
        String name = "polished_" + geologyType.getName() + "_slab";
        Supplier<T> blockSupplier = () -> (T) new SlabBlock(POLISHED_STONE_PROP);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    // STAIRS //
    public static <T extends Block>RegistryObject<T> registerCobbleStairs(GeologyType geologyType, java.util.function.Supplier<BlockState> state) {
        String name = geologyType.getName() + "_cobble_stairs";
        Supplier<T> blockSupplier = () -> (T) new StairsBlock(state, DECOR_STONE_PROP);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerRawStoneStairs(GeologyType geologyType, java.util.function.Supplier<BlockState> state) {
        String name = geologyType.getName() + "_stairs";
        Supplier<T> blockSupplier = () -> (T) new StairsBlock(state, DECOR_STONE_PROP);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerPolishedStairs(GeologyType geologyType, java.util.function.Supplier<BlockState> state) {
        String name = "polished_" + geologyType.getName() + "_stairs";
        Supplier<T> blockSupplier = () -> (T) new StairsBlock(state, POLISHED_STONE_PROP);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    // WALLS //
    public static <T extends Block>RegistryObject<T> registerCobbleWall(GeologyType geologyType) {
        String name = geologyType.getName() + "_cobble_wall";
        Supplier<T> blockSupplier = () -> (T) new WallBlock(DECOR_STONE_PROP);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerRawStoneWall(GeologyType geologyType) {
        String name = geologyType.getName() + "_wall";
        Supplier<T> blockSupplier = () -> (T) new WallBlock(DECOR_STONE_PROP);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }


    /////// BASIC BLOCK REGISTRY ///////

    public static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, ItemGroup itemGroup) {
        RegistryObject<T> registeredBlock = BLOCKS.register(name, block);
        registerBlockItem(name, registeredBlock, 64, itemGroup);
        return registeredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, int stackSize, ItemGroup itemGroup) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(itemGroup).maxStackSize(stackSize)));
    }

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }
}