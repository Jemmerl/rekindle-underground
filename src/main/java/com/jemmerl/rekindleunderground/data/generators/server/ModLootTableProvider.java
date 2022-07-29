package com.jemmerl.rekindleunderground.data.generators.server;

import com.google.common.collect.ImmutableList;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.block.ModBlocks;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
            Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        for (Map.Entry<ResourceLocation, LootTable> entry : map.entrySet())
            LootTableManager.validateLootTable(validationtracker, entry.getKey(), entry.getValue());
    }

    private static class ModBlockLootTables extends BlockLootTables {
        @Override
        protected void addTables() {
            for (StoneType stoneType : StoneType.values()) {
                Block stoneBlock = stoneType.getStoneState().getBlock();
                if (stoneType.hasCobble()) {
                    // Register stone -> rock drop
                    registerLootTable(stoneBlock, LootTable.builder().addLootPool(
                            LootPool.builder().rolls(BinomialRange.of(3, 0.65f)).addEntry(
                                    ItemLootEntry.builder(UtilMethods.stringToItem(RekindleUnderground.MOD_ID + ":" + stoneType.getName() + "_rock"))))
                    );

                    // Register cobble drop
                    registerDropSelfLootTable(stoneType.getCobbleState().getBlock());
                } else { // TODO TEMP
                    registerDropSelfLootTable(stoneBlock); // i mean it! temp!
                }
            }

        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }
    }
}
