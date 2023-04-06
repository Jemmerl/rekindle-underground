package com.jemmerl.rekindleunderground.util.lists;

import net.minecraft.item.Item;

public class OreListWrapper {

    public OreListWrapper(Item oreItem, Item poorOreItem) {
        this.oreItem = oreItem;
        this.poorOreItem = poorOreItem;
    }

    private final Item oreItem;
    private final Item poorOreItem;

    public Item getOreItem() {
        return this.oreItem;
    }

    public Item getPoorOreItem() {
        return this.poorOreItem;
    }
}
