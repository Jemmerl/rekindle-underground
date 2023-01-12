package com.jemmerl.rekindleunderground.init;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.blocks.FallingOreBlock;
import com.jemmerl.rekindleunderground.blocks.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, RekindleUnderground.MOD_ID);

    // Property Templates
    public static Block.Properties STONE_PROP = AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0);

    ////////////
    // STONES //
    ////////////

    // Sedimentary
    public static final RegistryObject<Block> CHALK_STONE = registerBlock(StoneType.CHALK.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.CHALK), StoneType.CHALK), 64);
    public static final RegistryObject<Block> LIMESTONE_STONE = registerBlock(StoneType.LIMESTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.LIMESTONE), StoneType.LIMESTONE), 64);
    public static final RegistryObject<Block> DOLOSTONE_STONE = registerBlock(StoneType.DOLOSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.DOLOSTONE), StoneType.DOLOSTONE), 64);
    public static final RegistryObject<Block> SHALE_STONE = registerBlock(StoneType.SHALE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.SHALE), StoneType.SHALE), 64);
    public static final RegistryObject<Block> SANDSTONE_STONE = registerBlock(StoneType.SANDSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.SANDSTONE), StoneType.SANDSTONE), 64);
    public static final RegistryObject<Block> RED_SANDSTONE_STONE = registerBlock(StoneType.RED_SANDSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.RED_SANDSTONE), StoneType.RED_SANDSTONE), 64);
    public static final RegistryObject<Block> GREYWACKE_STONE = registerBlock(StoneType.GREYWACKE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.GREYWACKE), StoneType.GREYWACKE), 64);
    public static final RegistryObject<Block> MUDSTONE_STONE = registerBlock(StoneType.MUDSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.MUDSTONE), StoneType.MUDSTONE), 64);
    public static final RegistryObject<Block> ROCKSALT_STONE = registerBlock(StoneType.ROCKSALT.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.ROCKSALT), StoneType.ROCKSALT), 64);
    public static final RegistryObject<Block> ROCKGYPSUM_STONE = registerBlock(StoneType.ROCKGYPSUM.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.ROCKGYPSUM), StoneType.ROCKGYPSUM), 64);
    public static final RegistryObject<Block> BORAX_STONE = registerBlock(StoneType.BORAX.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.BORAX), StoneType.BORAX), 64);
    public static final RegistryObject<Block> KERNITE_STONE = registerBlock(StoneType.KERNITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.KERNITE), StoneType.KERNITE), 64);
    public static final RegistryObject<Block> VEIN_QUARTZ_STONE = registerBlock(StoneType.VEIN_QUARTZ.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.VEIN_QUARTZ), StoneType.VEIN_QUARTZ), 64);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_STONE = registerBlock(StoneType.RHYOLITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.RHYOLITE), StoneType.RHYOLITE), 64);
    public static final RegistryObject<Block> DACITE_STONE = registerBlock(StoneType.DACITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.DACITE), StoneType.DACITE), 64);
    public static final RegistryObject<Block> ANDESITE_STONE = registerBlock(StoneType.ANDESITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.ANDESITE), StoneType.ANDESITE), 64);
    public static final RegistryObject<Block> BASALT_STONE = registerBlock(StoneType.BASALT.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.BASALT), StoneType.BASALT), 64);
    public static final RegistryObject<Block> SCORIA_STONE = registerBlock(StoneType.SCORIA.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.SCORIA), StoneType.SCORIA), 64);
    public static final RegistryObject<Block> TUFF_STONE = registerBlock(StoneType.TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.TUFF), StoneType.TUFF), 64);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_STONE = registerBlock(StoneType.DIORITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.DIORITE), StoneType.DIORITE), 64);
    public static final RegistryObject<Block> GRANODIORITE_STONE = registerBlock(StoneType.GRANODIORITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.GRANODIORITE), StoneType.GRANODIORITE), 64);
    public static final RegistryObject<Block> GRANITE_STONE = registerBlock(StoneType.GRANITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.GRANITE), StoneType.GRANITE), 64);
    public static final RegistryObject<Block> SYENITE_STONE = registerBlock(StoneType.SYENITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.SYENITE), StoneType.SYENITE), 64);
    public static final RegistryObject<Block> GABBRO_STONE = registerBlock(StoneType.GABBRO.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.GABBRO), StoneType.GABBRO), 64);
    public static final RegistryObject<Block> DIABASE_STONE = registerBlock(StoneType.DIABASE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.DIABASE), StoneType.DIABASE), 64);
    public static final RegistryObject<Block> PERIDOTITE_STONE = registerBlock(StoneType.PERIDOTITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.PERIDOTITE), StoneType.PERIDOTITE), 64);
    public static final RegistryObject<Block> KIMBERLITE_STONE = registerBlock(StoneType.KIMBERLITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.KIMBERLITE), StoneType.KIMBERLITE), 64);
    public static final RegistryObject<Block> LAMPROITE_STONE = registerBlock(StoneType.LAMPROITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.LAMPROITE), StoneType.LAMPROITE), 64);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_STONE = registerBlock(StoneType.QUARTZITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.QUARTZITE), StoneType.QUARTZITE), 64);
    public static final RegistryObject<Block> SCHIST_STONE = registerBlock(StoneType.SCHIST.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.SCHIST), StoneType.SCHIST), 64);
    public static final RegistryObject<Block> PHYLLITE_STONE = registerBlock(StoneType.PHYLLITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.PHYLLITE), StoneType.PHYLLITE), 64);
    public static final RegistryObject<Block> GNEISS_STONE = registerBlock(StoneType.GNEISS.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.GNEISS), StoneType.GNEISS), 64);
    public static final RegistryObject<Block> MARBLE_STONE = registerBlock(StoneType.MARBLE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(StoneType.MARBLE), StoneType.MARBLE), 64);


    /////////////
    // COBBLES //
    /////////////

    // Sedimentary
    public static final RegistryObject<Block> CHALK_COBBLE = registerBlock(StoneType.CHALK.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.CHALK)), 64);
    public static final RegistryObject<Block> LIMESTONE_COBBLE = registerBlock(StoneType.LIMESTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.LIMESTONE)), 64);
    public static final RegistryObject<Block> DOLOSTONE_COBBLE = registerBlock(StoneType.DOLOSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.DOLOSTONE)), 64);
    public static final RegistryObject<Block> SHALE_COBBLE = registerBlock(StoneType.SHALE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.SHALE)), 64);
    public static final RegistryObject<Block> SANDSTONE_COBBLE = registerBlock(StoneType.SANDSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.SANDSTONE)), 64);
    public static final RegistryObject<Block> RED_SANDSTONE_COBBLE = registerBlock(StoneType.RED_SANDSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.RED_SANDSTONE)), 64);
    public static final RegistryObject<Block> GREYWACKE_COBBLE = registerBlock(StoneType.GREYWACKE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.GREYWACKE)), 64);
    public static final RegistryObject<Block> MUDSTONE_COBBLE = registerBlock(StoneType.MUDSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.MUDSTONE)), 64);
    public static final RegistryObject<Block> VEIN_QUARTZ_COBBLE = registerBlock(StoneType.VEIN_QUARTZ.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.VEIN_QUARTZ)), 64);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_COBBLE = registerBlock(StoneType.RHYOLITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.RHYOLITE)), 64);
    public static final RegistryObject<Block> DACITE_COBBLE = registerBlock(StoneType.DACITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.DACITE)), 64);
    public static final RegistryObject<Block> ANDESITE_COBBLE = registerBlock(StoneType.ANDESITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.ANDESITE)), 64);
    public static final RegistryObject<Block> BASALT_COBBLE = registerBlock(StoneType.BASALT.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.BASALT)), 64);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_COBBLE = registerBlock(StoneType.DIORITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.DIORITE)), 64);
    public static final RegistryObject<Block> GRANODIORITE_COBBLE = registerBlock(StoneType.GRANODIORITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.GRANODIORITE)), 64);
    public static final RegistryObject<Block> GRANITE_COBBLE = registerBlock(StoneType.GRANITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.GRANITE)), 64);
    public static final RegistryObject<Block> SYENITE_COBBLE = registerBlock(StoneType.SYENITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.SYENITE)), 64);
    public static final RegistryObject<Block> GABBRO_COBBLE = registerBlock(StoneType.GABBRO.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.GABBRO)), 64);
    public static final RegistryObject<Block> DIABASE_COBBLE = registerBlock(StoneType.DIABASE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.DIABASE)), 64);
    public static final RegistryObject<Block> PERIDOTITE_COBBLE = registerBlock(StoneType.PERIDOTITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.PERIDOTITE)), 64);
    public static final RegistryObject<Block> KIMBERLITE_COBBLE = registerBlock(StoneType.KIMBERLITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.KIMBERLITE)), 64);
    public static final RegistryObject<Block> LAMPROITE_COBBLE = registerBlock(StoneType.LAMPROITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.LAMPROITE)), 64);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_COBBLE = registerBlock(StoneType.QUARTZITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.QUARTZITE)), 64);
    public static final RegistryObject<Block> SCHIST_COBBLE = registerBlock(StoneType.SCHIST.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.SCHIST)), 64);
    public static final RegistryObject<Block> PHYLLITE_COBBLE = registerBlock(StoneType.PHYLLITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.PHYLLITE)), 64);
    public static final RegistryObject<Block> GNEISS_COBBLE = registerBlock(StoneType.GNEISS.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.GNEISS)), 64);
    public static final RegistryObject<Block> MARBLE_COBBLE = registerBlock(StoneType.MARBLE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(StoneType.MARBLE)), 64);


    //////////////
    // DETRITUS //
    //////////////

    // Stable Detritus
    public static final RegistryObject<Block> DIRT_DET = registerBlock(StoneType.DIRT.getName() + "_stone",
            () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.EARTH).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
            .hardnessAndResistance(0.5f), StoneType.DIRT), 64);

    public static final RegistryObject<Block> COARSE_DIRT_DET = registerBlock(StoneType.COARSE_DIRT.getName() + "_stone",
            () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.EARTH).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
                    .hardnessAndResistance(0.5f), StoneType.COARSE_DIRT), 64);

    public static final RegistryObject<Block> CLAY_DET = registerBlock(StoneType.CLAY.getName() + "_stone",
            () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.CLAY).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
                    .hardnessAndResistance(0.6f), StoneType.CLAY), 64);

    // Falling Detritus
    public static final RegistryObject<Block> SAND_DET = registerBlock(StoneType.SAND.getName() + "_stone",
            () -> new FallingOreBlock(AbstractBlock.Properties.create(Material.SAND).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)
            .hardnessAndResistance(0.5f), StoneType.SAND), 64);

    public static final RegistryObject<Block> RED_SAND_DET = registerBlock(StoneType.RED_SAND.getName() + "_stone",
            () -> new FallingOreBlock(AbstractBlock.Properties.create(Material.SAND).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)
                    .hardnessAndResistance(0.5f), StoneType.RED_SAND), 64);

    public static final RegistryObject<Block> GRAVEL_DET = registerBlock(StoneType.GRAVEL.getName() + "_stone",
            () -> new FallingOreBlock(AbstractBlock.Properties.create(Material.SAND).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
                    .hardnessAndResistance(0.6f), StoneType.GRAVEL), 64);

//    static {
//        StoneType.register(BLOCKS);
//    }

    //////////////////
    // TOOL METHODS //
    //////////////////

    // Stone Property Builder
    private static Block.Properties buildStoneProperties(StoneType stoneType) {
        return STONE_PROP.hardnessAndResistance(stoneType.getStoneHardness(), stoneType.getStoneResistance());
    }

    // Cobble Property Builder
    private static Block.Properties buildCobbleProperties(StoneType stoneType) {
        return STONE_PROP.hardnessAndResistance(stoneType.getCobbleHardness(), stoneType.getCobbleResistance());
    }


    // Block and BlockItem registry methods
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, Integer stackSize) {
        RegistryObject<T> registeredBlock = BLOCKS.register(name, block);
        registerBlockItem(name, registeredBlock, stackSize);
        return registeredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Integer stackSize) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroups.RKU_STONE_GROUP).maxStackSize(stackSize)));
    }

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }

}


// TODO BELOW HERE IS OLD CODE, PRESERVED IF NEEDED. WILL BE PURGED UPON ALPHA RELEASE

//    @OnlyIn(Dist.CLIENT)
//    public IFormattableTextComponent getTranslatedName() {
//        return new TranslationTextComponent(this.getTranslationKey());
//    }
//
//    /**
//     * Returns the unlocalized name of the block with "tile." appended to the front.
//     */
//    public String getTranslationKey() {
//        if (this.translationKey == null) {
//            this.translationKey = Util.makeTranslationKey("block", Registry.BLOCK.getKey(this));
//        }
//
//        return this.translationKey;
//    }
