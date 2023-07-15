package com.jemmerl.jemsgeology.util.lists;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class GeoListWrapper {

    public GeoListWrapper(Block stoneOreBlock, Block cobbleBlock, Block cobblestoneBlock, Block regolithBlock, Item rockItem) {
        this.stoneOreBlock = stoneOreBlock;
        this.cobbleBlock = cobbleBlock;
        this.cobblestoneBlock = cobblestoneBlock;
        this.regolithBlock = regolithBlock;
        this.rockItem = rockItem;
    }

    private final Block stoneOreBlock;
    private final Block cobbleBlock;
    private final Block cobblestoneBlock;
    private final Block regolithBlock;
    private final Item rockItem;

    public Block getStoneOreBlock() {
        return this.stoneOreBlock;
    }

    public Block getCobbleBlock() {
        return this.cobbleBlock;
    }

    public Block getCobblestoneBlock() {
        return this.cobblestoneBlock;
    }

    public Block getRegolithBlock() {
        return this.regolithBlock;
    }

    public Item getRockItem() {
        return this.rockItem;
    }
}
