package com.jemmerl.rekindleunderground.block;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.item.ModItemGroup;
import com.jemmerl.rekindleunderground.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, RekindleUnderground.MOD_ID);

    /////////////////////
    //     STONES     //
    /////////////////////

    // Sedimentary Stones
    public static final RegistryObject<Block> CHALK = registerBlock("chalk_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> LIMESTONE = registerBlock("limestone_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> DOLOSTONE = registerBlock("dolostone_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SHALE = registerBlock("shale_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SANDSTONE = registerBlock("sandstone_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> MUDSTONE = registerBlock("mudstone_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> ROCK_SALT = registerBlock("rocksalt_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> ROCK_GYPSUM = registerBlock("rockgypsum_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> BORAX = registerBlock("borax_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> KERNITE = registerBlock("kernite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> VEIN_QUARTZ = registerBlock("veinquartz_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    // Igneous Extrusive Stones
    public static final RegistryObject<Block> RHYOLITE = registerBlock("rhyolite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> DACITE = registerBlock("dacite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> ANDESITE = registerBlock("andesite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> BASALT = registerBlock("basalt_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SCORIA = registerBlock("scoria_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    // Igneous Intrusive Stones
    public static final RegistryObject<Block> DIORITE = registerBlock("diorite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> GRANODIORITE = registerBlock("granodiorite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> GRANITE = registerBlock("granite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SYENITE = registerBlock("syenite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> GABBRO = registerBlock("gabbro_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> PERIDOTITE = registerBlock("peridotite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    // Metamorphic Stones
    public static final RegistryObject<Block> QUARTZITE = registerBlock("quartzite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SCHIST = registerBlock("schist_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> PHYLLITE = registerBlock("phyllite_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> GNEISS = registerBlock("gneiss_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> MARBLE = registerBlock("marble_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SOAPSTONE = registerBlock("soapstone_stone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    /////////////////////
    //     COBBLES     //
    /////////////////////

    // Sedimentary Stones
    public static final RegistryObject<Block> CHALK_CS = registerBlock("chalk_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> LIMESTONE_CS = registerBlock("limestone_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> DOLOSTONE_CS = registerBlock("dolostone_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SHALE_CS = registerBlock("shale_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SANDSTONE_CS = registerBlock("sandstone_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> MUDSTONE_CS = registerBlock("mudstone_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> VEIN_QUARTZ_CS = registerBlock("veinquartz_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    // Igneous Extrusive Stones
    public static final RegistryObject<Block> RHYOLITE_CS = registerBlock("rhyolite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> DACITE_CS = registerBlock("dacite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> ANDESITE_CS = registerBlock("andesite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> BASALT_CS = registerBlock("basalt_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    // Igneous Intrusive Stones
    public static final RegistryObject<Block> DIORITE_CS = registerBlock("diorite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> GRANODIORITE_CS = registerBlock("granodiorite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> GRANITE_CS = registerBlock("granite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SYENITE_CS = registerBlock("syenite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> GABBRO_CS = registerBlock("gabbro_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> PERIDOTITE_CS = registerBlock("peridotite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    // Metamorphic Stones
    public static final RegistryObject<Block> QUARTZITE_CS = registerBlock("quartzite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> SCHIST_CS = registerBlock("schist_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> PHYLLITE_CS = registerBlock("phyllite_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> GNEISS_CS = registerBlock("gneiss_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    public static final RegistryObject<Block> MARBLE_CS = registerBlock("marble_cobblestone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    ///////////////////
    //     SOILS     //
    ///////////////////

    // Soil Blocks
    public static final RegistryObject<Block> LATERITE_SOIL = registerBlock("laterite_soil",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);



    // Block and BlockItem registry methods
    // STANDARD
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, Integer stackSize) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, stackSize);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Integer stackSize) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.RKU_GROUP).maxStackSize(stackSize)));
    }


    // OVERLOAD FOR TOOLTIPS
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, Integer stackSize, String tooltipKey) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, stackSize, tooltipKey);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Integer stackSize, String tooltipKey) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.RKU_GROUP).maxStackSize(stackSize)) {
            @Override
            public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
                if(!Screen.hasShiftDown()) {
                    tooltip.add(new TranslationTextComponent("tooltip.all.rekindleprimitive.shiftinfo"));
                } else {
                    tooltip.add(new TranslationTextComponent(tooltipKey));
                }
                super.addInformation(stack, worldIn, tooltip, flagIn);
            }
        });
    }

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }


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



}
