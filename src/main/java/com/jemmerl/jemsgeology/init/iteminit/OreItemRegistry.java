package com.jemmerl.jemsgeology.init.iteminit;

import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class OreItemRegistry {

    private final RegistryObject<Item> oreItem;
    private final RegistryObject<Item> poorOreItem;

    public OreItemRegistry(OreType oreType) {
        this.oreItem = (oreType == OreType.DIAMOND) ? null : ModItems.registerOreItem(oreType);
        this.poorOreItem = ModItems.registerPoorOreItem(oreType);
    }

    public Item getOreItem() {
        return oreItem.get();
    }
    public Item getPoorOreItem() {
        return poorOreItem.get();
    }

}
