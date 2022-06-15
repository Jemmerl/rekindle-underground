package com.jemmerl.rekindleunderground.data.types;

import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.item.ModItemGroup;
import com.jemmerl.rekindleunderground.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

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

    // Intrusive Igneous
    DIORITE("diorite", StoneGroupType.INTRUSIVE, 4, 4, true),
    GRANODIORITE("granodiorite", StoneGroupType.INTRUSIVE, 4, 4, true),
    GRANITE("granite", StoneGroupType.INTRUSIVE, 4, 4, true),
    SYENITE("syenite", StoneGroupType.INTRUSIVE, 4, 4, true),
    GABBRO("gabbro", StoneGroupType.INTRUSIVE, 4, 4, true),
    DIABASE("diabase", StoneGroupType.INTRUSIVE, 4, 4, true),
    PERIDOTITE("peridotite", StoneGroupType.INTRUSIVE, 3, 3, true),

    // Metamorphic
    QUARTZITE("quartzite", StoneGroupType.METAMORPHIC, 4, 4, true),
    SCHIST("schist", StoneGroupType.METAMORPHIC, 1, 1, true),
    PHYLLITE("phyllite", StoneGroupType.METAMORPHIC, 2, 2, true),
    GNEISS("gneiss", StoneGroupType.METAMORPHIC, 4, 4, true),
    MARBLE("marble", StoneGroupType.METAMORPHIC, 3, 3, true);

    private static class Constants {
        private static final Float[] HARDS = new Float[]{10f, 12.5f, 15f, 20f, 25f};
        private static final Float[] RESISTS = {18f, 15f, 12f, 9f, 6f};
    }

    private final String name;
    private final StoneGroupType group;
    private final int hardnessIndex;
    private final int resistanceIndex;
    private final boolean hasCobble;

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

    public float getHardness(){
        return Constants.HARDS[this.hardnessIndex];
    }

    public float getResistance(){
        return Constants.HARDS[this.resistanceIndex];
    }

    public boolean hasCobble(){
        return this.hasCobble;
    }

    @SuppressWarnings("NonFinalFieldInEnum")
    private RegistryObject<Block> stoneBlock = null;
    @SuppressWarnings("NonFinalFieldInEnum")
    private RegistryObject<Block> cobbleBlock = null;

    // Typos will always return false!
    public Boolean isInStoneGroup(StoneGroupType group){
        return (this.group == group);
    }

    // Register stone blocks and items
    public static void register(DeferredRegister<Block> blocks) {
        String blockName;

        // Register stone blocks
        for (StoneType blockEntry : values()) {
            blockName = blockEntry.name + "_stone";
            blockEntry.stoneBlock = blocks.register(blockName,
                    () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.ROCK)
                            .harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()
                            .hardnessAndResistance(Constants.HARDS[blockEntry.hardnessIndex],
                                    Constants.RESISTS[blockEntry.resistanceIndex]), blockEntry.group));
            registerBlockItem(blockName, blockEntry.stoneBlock, 64);
        }

        // Register cobblestone blocks
        for (StoneType blockEntry : values()) {
            // Drop hardness index by one from host stone, leave resistance the same
            // If 0 hardness, then leave the hardness the same and raise resistance by one
            // NOTE: Assumes equal hardness and resistance indexes. If not, then resistance
            // code needs editing.
            if (blockEntry.hasCobble) {
                blockName = blockEntry.name + "_cobblestone";
                blockEntry.cobbleBlock = blocks.register(blockName,
                        () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                                .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()
                                .hardnessAndResistance(
                                        (blockEntry.hardnessIndex == 0) ? Constants.HARDS[0] : Constants.HARDS[blockEntry.hardnessIndex - 1],
                                        (blockEntry.hardnessIndex == 0) ? Constants.RESISTS[1] : Constants.RESISTS[blockEntry.resistanceIndex]
                                )));
                registerBlockItem(blockName, blockEntry.cobbleBlock, 64);
            }
        }
    }


    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Integer stackSize) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.RKU_GROUP).maxStackSize(stackSize)));
    }

}