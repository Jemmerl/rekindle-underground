package com.jemmerl.rekindleunderground.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class GeoListWrapper {

    public GeoListWrapper(Block stoneOreBlock, Block cobbleBlock, Item rockItem) {
        this.stoneOreBlock = stoneOreBlock;
        this.cobbleBlock = cobbleBlock;
        this.rockItem = rockItem;
    }

    private Block stoneOreBlock;
    private Block cobbleBlock;
    private Item rockItem;

    public Block getStoneOreBlock() {
        return this.stoneOreBlock;
    }

    public Block getCobbleBlock() {
        return this.cobbleBlock;
    }

    public Item getRockItem() {
        return this.rockItem;
    }
}
