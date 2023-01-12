package com.jemmerl.rekindleunderground.data.types;

import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import net.minecraft.block.*;

import java.util.EnumSet;

public enum StoneType {
    
    // Sedimentary
    CHALK("chalk", StoneGroupType.SEDIMENTARY, 0, 0, true),
    LIMESTONE("limestone", StoneGroupType.SEDIMENTARY, 1, 1, true),
    DOLOSTONE("dolostone", StoneGroupType.SEDIMENTARY, 2, 2, true),
    SHALE("shale", StoneGroupType.SEDIMENTARY, 2, 2, true),
    SANDSTONE("sandstone", StoneGroupType.SEDIMENTARY, 2, 2, true),
    RED_SANDSTONE("red_sandstone", StoneGroupType.SEDIMENTARY, 2, 2, true),
    GREYWACKE("greywacke", StoneGroupType.SEDIMENTARY, 2, 2, true),
    MUDSTONE("mudstone", StoneGroupType.SEDIMENTARY, 1, 1, true),
    ROCKSALT("rocksalt", StoneGroupType.SEDIMENTARY, 1, 1, false),
    ROCKGYPSUM("rockgypsum", StoneGroupType.SEDIMENTARY, 0, 0, false),
    BORAX("borax", StoneGroupType.SEDIMENTARY, 0, 0, false),
    KERNITE("kernite", StoneGroupType.SEDIMENTARY, 0, 0, false),
    VEIN_QUARTZ("veinquartz", StoneGroupType.SEDIMENTARY, 4, 4, true),

    // Extrusive Igneous
    RHYOLITE("rhyolite", StoneGroupType.EXTRUSIVE, 2, 2, true),
    DACITE("dacite", StoneGroupType.EXTRUSIVE, 3, 3, true),
    ANDESITE("andesite", StoneGroupType.EXTRUSIVE, 3, 3, true),
    BASALT("basalt", StoneGroupType.EXTRUSIVE, 3, 3, true),
    SCORIA("scoria", StoneGroupType.EXTRUSIVE, 2, 2, false),
    TUFF("tuff", StoneGroupType.EXTRUSIVE, 1, 1, false),

    // Intrusive Igneous
    DIORITE("diorite", StoneGroupType.INTRUSIVE, 4, 4, true),
    GRANODIORITE("granodiorite", StoneGroupType.INTRUSIVE, 4, 4, true),
    GRANITE("granite", StoneGroupType.INTRUSIVE, 4, 4, true),
    SYENITE("syenite", StoneGroupType.INTRUSIVE, 4, 4, true),
    GABBRO("gabbro", StoneGroupType.INTRUSIVE, 4, 4, true),
    DIABASE("diabase", StoneGroupType.INTRUSIVE, 4, 4, true),
    PERIDOTITE("peridotite", StoneGroupType.INTRUSIVE, 3, 3, true),
    KIMBERLITE("kimberlite", StoneGroupType.INTRUSIVE, 3, 3, true),
    LAMPROITE("lamproite", StoneGroupType.INTRUSIVE, 3, 3, true),

    // Metamorphic
    QUARTZITE("quartzite", StoneGroupType.METAMORPHIC, 4, 4, true),
    SCHIST("schist", StoneGroupType.METAMORPHIC, 1, 1, true),
    PHYLLITE("phyllite", StoneGroupType.METAMORPHIC, 2, 2, true),
    GNEISS("gneiss", StoneGroupType.METAMORPHIC, 4, 4, true),
    MARBLE("marble", StoneGroupType.METAMORPHIC, 3, 3, true),

    // Detritus do not really need to be present, but since they can carry ores, they must be to be compatible
    // Stable Detritus
    DIRT("dirt", StoneGroupType.DETRITUS, 0, 0, false),
    COARSE_DIRT("coarse_dirt", StoneGroupType.DETRITUS, 0, 0, false),
    CLAY("clay", StoneGroupType.DETRITUS, 1, 1, false),

    // Falling Detritus
    SAND("sand", StoneGroupType.DETRITUS, 0, 0, false),
    RED_SAND("red_sand", StoneGroupType.DETRITUS, 0, 0, false),
    GRAVEL("gravel", StoneGroupType.DETRITUS, 1, 1, false);

    private static class Constants {
        private static final Float[] HARDS = {1f, 1.75f, 2.5f, 3f, 3.5f}; // Relative stone hardnesses
        private static final Float[] RESISTS = {3f, 2.5f, 2f, 1.5f, 1f}; // Relative stone resistances
        private static final int HARD_MULT = RKUndergroundConfig.COMMON.stoneHardness.get(); // Multiplied by rel. hardnesses; Default 20
        private static final int RESIST_MULT = RKUndergroundConfig.COMMON.stoneResistance.get(); // Multiplied by rel. resistances; Default 6
    }

    private final String name;
    private final StoneGroupType group;
    private final int hardnessIndex;
    private final int resistanceIndex;
    private final boolean hasCobble;

//    @SuppressWarnings("FieldMayBeFinal")
//    public static ArrayList<String> stoneNameList = new ArrayList<>();
//    @SuppressWarnings("FieldMayBeFinal")
//    public static ArrayList<String> cobbleNameList = new ArrayList<>();

    StoneType(String name, StoneGroupType group, int hardnessIndex, int resistanceIndex, boolean hasCobble) {
        this.name = name;
        this.group = group;
        this.hardnessIndex = hardnessIndex;
        this.resistanceIndex = resistanceIndex;
        this.hasCobble = hasCobble;
    }

    public String getName(){
        return this.name;
    }
    public StoneGroupType getGroup(){
        return this.group;
    }

    // TODO might not be needed anymore with reworked registration
    public BlockState getStoneState() {
        return UtilMethods.stringToBlockState(RekindleUnderground.MOD_ID + ":" + this.name + "_stone");
    }

    public BlockState getCobbleState() {
        if (this.hasCobble) {
            return UtilMethods.stringToBlockState(RekindleUnderground.MOD_ID + ":" + this.name + "_cobblestone");
        } else {
            return null;
        }
    }

    public boolean hasCobble() {
        return this.hasCobble;
    }

    public float getStoneHardness() {
        return (Constants.HARDS[this.hardnessIndex] * Constants.HARD_MULT);
    }

    public float getStoneResistance() {
        return (Constants.RESISTS[this.resistanceIndex] * Constants.RESIST_MULT);
    }

    public float getCobbleHardness() {
        return ((this.hardnessIndex == 0) ? Constants.HARDS[0] : Constants.HARDS[this.hardnessIndex - 1]);
    }

    public float getCobbleResistance() {
        return ((this.hardnessIndex == 0) ? Constants.RESISTS[1] : Constants.RESISTS[this.resistanceIndex]);
    }

    // Typos will always return false!
    public Boolean isInStoneGroup(StoneGroupType group){
        return this.group.equals(group);
    }

    ////////////////////
    // STATIC METHODS //
    ////////////////////

//    public static boolean isInStones(String path) {
//        return stoneNameList.contains(path);
//    }
//
//    public static boolean isInCobbles(String path) {
//        return cobbleNameList.contains(path);
//    }

    // Get all the stone types for the supplied group
    public static EnumSet<StoneType> getAllInGroup(StoneGroupType group) {
        EnumSet<StoneType> enumSet = EnumSet.noneOf(StoneType.class);
        for (StoneType stoneType : StoneType.values()) {
            if (stoneType.getGroup().equals(group)) {
                enumSet.add(stoneType);
            }
        }
        return enumSet;
    }

    // TODO move to utils and fix... if even needed
    // Convert vanilla detritus to StoneOre detritus
    // If not convertible, return original state
    public static BlockState convertToDetritus(BlockState vanillaState) {
        if (vanillaState.equals(Blocks.SAND.getDefaultState())) {
            return StoneType.SAND.getStoneState();
        } else if (vanillaState.equals(Blocks.RED_SAND.getDefaultState())) {
            return StoneType.RED_SAND.getStoneState();
        } else if (vanillaState.equals(Blocks.GRAVEL.getDefaultState())) {
            return StoneType.GRAVEL.getStoneState();
        } else if (vanillaState.equals(Blocks.DIRT.getDefaultState())) {
            return StoneType.DIRT.getStoneState();
        } else if (vanillaState.equals(Blocks.COARSE_DIRT.getDefaultState())) {
            return StoneType.COARSE_DIRT.getStoneState();
        } else if (vanillaState.equals(Blocks.CLAY.getDefaultState())) {
            return StoneType.CLAY.getStoneState();
        } else {
            return vanillaState;
        }
    }


//    //////////////////
//    // REGISTRATION //
//    //////////////////
//
//    @SuppressWarnings("NonFinalFieldInEnum")
//    private RegistryObject<Block> stoneBlock = null;
//    @SuppressWarnings("NonFinalFieldInEnum")
//    private RegistryObject<Block> cobbleBlock = null;
//
//    // Register stone blocks and items
//    public static void register(DeferredRegister<Block> blocks) {
//        String blockName;
//
//        // Register stone blocks
//        for (StoneType blockEntry : EnumSet.complementOf(StoneType.getAllInGroup(StoneGroupType.DETRITUS))) {
//            if (!blockEntry.group.equals(StoneGroupType.DETRITUS)) {
//                blockName = blockEntry.name + "_stone";
//                stoneNameList.add(blockName);
//                blockEntry.stoneBlock = blocks.register(blockName,
//                        () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.ROCK)
//                                .harvestLevel(0).harvestTool(ToolType.PICKAXE).setRequiresTool()
//                                .hardnessAndResistance(
//                                        (Constants.HARDS[blockEntry.hardnessIndex] * Constants.HARD_MULT),
//                                        (Constants.RESISTS[blockEntry.resistanceIndex] * Constants.RESIST_MULT)),
//                                blockEntry));
//                registerBlockItem(blockName, blockEntry.stoneBlock, 64);
//            }
//        }
//
//        // Register cobblestone blocks
//        for (StoneType blockEntry : values()) {
//            // Drop hardness index by one from host stone, leave resistance the same
//            // If 0 hardness, then leave the hardness the same and raise resistance by one
//            // NOTE: Assumes equal hardness and resistance indexes. If not, then resistance
//            // code needs editing.
//            // Does not use the multipliers, as it is a pile of cobbles and not solid stone!
//            if (blockEntry.hasCobble) {
//                blockName = blockEntry.name + "_cobblestone";
//                cobbleNameList.add(blockName);
//                blockEntry.cobbleBlock = blocks.register(blockName,
//                        () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
//                                .harvestLevel(0).harvestTool(ToolType.PICKAXE).setRequiresTool()
//                                .hardnessAndResistance(
//                                        (blockEntry.hardnessIndex == 0) ? Constants.HARDS[0] : Constants.HARDS[blockEntry.hardnessIndex - 1],
//                                        (blockEntry.hardnessIndex == 0) ? Constants.RESISTS[1] : Constants.RESISTS[blockEntry.resistanceIndex]
//                                )));
//                registerBlockItem(blockName, blockEntry.cobbleBlock, 64);
//            }
//        }
//
//        // Register solid detritus blocks
//        // Dirt
//        blockName = DIRT.name + "_stone";
//        stoneNameList.add(blockName);
//        DIRT.stoneBlock = createSolidDetritusBlock(DIRT, Material.EARTH, SoundType.GROUND, blocks, blockName);
//        registerBlockItem(blockName, DIRT.stoneBlock, 64);
//
//        // Coarse Dirt
//        blockName = COARSE_DIRT.name + "_stone";
//        stoneNameList.add(blockName);
//        COARSE_DIRT.stoneBlock = createSolidDetritusBlock(COARSE_DIRT, Material.EARTH, SoundType.GROUND, blocks, blockName);
//        registerBlockItem(blockName, COARSE_DIRT.stoneBlock, 64);
//
//        // Clay
//        blockName = CLAY.name + "_stone";
//        stoneNameList.add(blockName);
//        CLAY.stoneBlock = createSolidDetritusBlock(DIRT, Material.CLAY, SoundType.GROUND, blocks, blockName);
//        registerBlockItem(blockName, CLAY.stoneBlock, 64);
//
//        // Register falling detritus blocks
//        // Sand
//        blockName = SAND.name + "_stone";
//        stoneNameList.add(blockName);
//        SAND.stoneBlock = createFallingDetritusBlock(SAND, Material.SAND, SoundType.SAND, blocks, blockName);
//        registerBlockItem(blockName, SAND.stoneBlock, 64);
//
//        // Red Sand
//        blockName = RED_SAND.name + "_stone";
//        stoneNameList.add(blockName);
//        RED_SAND.stoneBlock = createFallingDetritusBlock(RED_SAND, Material.SAND, SoundType.SAND, blocks, blockName);
//        registerBlockItem(blockName, RED_SAND.stoneBlock, 64);
//
//        // Gravel
//        blockName = GRAVEL.name + "_stone";
//        stoneNameList.add(blockName);
//        GRAVEL.stoneBlock = createFallingDetritusBlock(GRAVEL, Material.SAND, SoundType.GROUND, blocks, blockName);
//        registerBlockItem(blockName, GRAVEL.stoneBlock, 64);
//
//    }
//
//    // Create a registry object for solid detritus blocks
//    private static RegistryObject<Block> createSolidDetritusBlock(StoneType det, Material material, SoundType sound, DeferredRegister<Block> blocks, String blockName) {
//        return blocks.register(blockName,
//                () -> new StoneOreBlock(AbstractBlock.Properties.create(material)
//                        .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(sound)
//                        .hardnessAndResistance(
//                                Constants.DET_HARDS[det.hardnessIndex],
//                                Constants.DET_RESISTS[det.resistanceIndex]),
//                        det, det.group));
//    }
//
//    // Create a registry object for falling detritus blocks
//    private static RegistryObject<Block> createFallingDetritusBlock(StoneType det, Material material, SoundType sound, DeferredRegister<Block> blocks, String blockName) {
//        return blocks.register(blockName,
//                () -> new FallingOreBlock(AbstractBlock.Properties.create(material)
//                        .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(sound)
//                        .hardnessAndResistance(
//                                Constants.DET_HARDS[det.hardnessIndex],
//                                Constants.DET_RESISTS[det.resistanceIndex]),
//                        det, det.group));
//    }
//
//    // Register the item for a block
//    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Integer stackSize) {
//        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
//                new Item.Properties().group(ModItemGroup.RKU_STONE_GROUP).maxStackSize(stackSize)));
//    }

}