package com.jemmerl.rekindleunderground.tags;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ModTags {
    public static class Blocks {

        public static final Tags.IOptionalNamedTag<Block> RK_STONE_ORE_BLOCKS = createTag("rk_stone_ore_blocks");
        public static final Tags.IOptionalNamedTag<Block> DETRITUS = createTag("detritus");

        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(RekindleUnderground.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Block> createForgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

    public static class Items {

        // Example
        // public static final Tags.IOptionalNamedTag<Item> AMETHYST = createForgeTag("gems/amethyst");

        public static final Tags.IOptionalNamedTag<Item> ROCKS = createTag("rk_rocks");

        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(RekindleUnderground.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}
