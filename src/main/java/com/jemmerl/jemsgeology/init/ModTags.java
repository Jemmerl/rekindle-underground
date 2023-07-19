package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ModTags {
    public static class Blocks {

        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_STONEORE = createTag("jemsgeo_stone");
        public static final Tags.IOptionalNamedTag<Block> DETRITUS = createTag("detritus");
        public static final Tags.IOptionalNamedTag<Block> COBBLES_CAN_BREAK = createTag("jemsgeo_cobbles_break"); // Cobbles cannot be supported by...
        public static final Tags.IOptionalNamedTag<Block> COBBLES_CAN_SMASH = createTag("jemsgeo_cobbles_smash"); // Cobbles cannot be STOPPED by...

        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(JemsGeology.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Block> createForgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

    public static class Items {

        // Example
        // public static final Tags.IOptionalNamedTag<Item> AMETHYST = createForgeTag("gems/amethyst");

        public static final Tags.IOptionalNamedTag<Item> ROCKS = createTag("jemsgeo_rocks");

        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(JemsGeology.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}
