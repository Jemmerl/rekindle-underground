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

    // Create blocks
//    public static final RegistryObject<Block> CHROMITE_ORE = registerBlock("chromite_ore",
//            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
//                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), 64);

    // Block and BlockItem registry methods
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

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }

}
