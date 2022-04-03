package com.jemmerl.rekindleunderground.item;


import com.jemmerl.rekindleunderground.RekindleUnderground;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RekindleUnderground.MOD_ID);

    // Create items
//    public static final RegistryObject<Item> ASH = ITEMS.register("fire_ash",
//            () -> new Item(new Item.Properties().group(ModItemGroup.RKU_GROUP)));

    // Item registry method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
