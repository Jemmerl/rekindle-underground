package com.jemmerl.rekindleunderground.data.generators.server;

import com.google.common.collect.ImmutableList;
import com.jemmerl.rekindleunderground.blocks.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.GeologyType;
import com.jemmerl.rekindleunderground.init.ModBlocks;
import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.util.GeoListWrapper;
import com.jemmerl.rekindleunderground.util.ModLists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.jemmerl.rekindleunderground.blocks.StoneOreBlock.GRADE_TYPE;
import static com.jemmerl.rekindleunderground.blocks.StoneOreBlock.ORE_TYPE;

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
//        for (Map.Entry<ResourceLocation, LootTable> entry : map.entrySet())
//            LootTableManager.validateLootTable(validationtracker, entry.getKey(), entry.getValue());
    }

    // TODO Whole lotta TEMP shite in here
    private static class ModBlockLootTables extends BlockLootTables {
        @Override
        protected void addTables() {

            for (Block block : ModLists.ALL_STONES) {
                GeologyType geologyType = ((StoneOreBlock) block).getGeologyType();
                GeoListWrapper geoList = ModLists.GEO_LIST.get(geologyType);
                if (geologyType.hasCobble()) {
                    // Register stone -> rock drop
                    LootTable.Builder lootTable = buildStoneLootTable(geoList);
                    registerLootTable(block, lootTable);

                    // Register cobble drop
                    registerDropSelfLootTable(geoList.getCobbleBlock());
                } else {
                    // TODO TEMP
                    registerDropSelfLootTable(block); // i mean it! temp!
                }
            }


            for (Block block : ModLists.COBBLESTONES) {
                registerDropSelfLootTable(block);
            }

            // Todo temp
            for (Block block : ModLists.ALL_DETRITUS) {
                registerDropSelfLootTable(block);
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }


        // Creates and fills a loot table with pools for each OreType
        private static LootTable.Builder buildStoneLootTable(GeoListWrapper geoList) {
            LootTable.Builder lootTableBuilder = new LootTable.Builder();

            // Loot pool for normal stone, "NONE" ore
            lootTableBuilder.addLootPool(LootPool.builder()
                    .name(OreType.NONE.getString())
                    .rolls(BinomialRange.of(3, 0.65f))
                    .addEntry(ItemLootEntry.builder(geoList.getRockItem()))

            );

            // Add ore loot pools
            for (OreType oreType : EnumSet.complementOf(EnumSet.of(OreType.NONE))) {
                for (GradeType gradeType : GradeType.values()) {
                    String lootName;
                    Item oreDropItem;
                    IRandomRange rollsIn;
                    if (gradeType.equals(GradeType.HIGHGRADE)) {
                        lootName = oreType.getString() + "_highgrade";
                        oreDropItem = oreType.getOreItem();
                        rollsIn = RandomValueRange.of(2,4);
                    } else if (gradeType.equals(GradeType.MIDGRADE)) {
                        lootName = oreType.getString() + "_midgrade";
                        oreDropItem = oreType.getOreItem();
                        rollsIn = RandomValueRange.of(1,2);
                    } else {
                        lootName = oreType.getString() + "_lowgrade";
                        oreDropItem = oreType.getPoorOreItem();
                        rollsIn = RandomValueRange.of(1,2);
                    }

                    lootTableBuilder.addLootPool(LootPool.builder()
                            .name(lootName)
                            .rolls(rollsIn)
                            .addEntry(ItemLootEntry.builder(Objects.requireNonNull(oreDropItem)))
                            .acceptCondition(BlockStateProperty
                                    .builder(geoList.getStoneOreBlock())
                                    .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                            .withProp(ORE_TYPE, oreType)
                                            .withProp(GRADE_TYPE, gradeType))));
                }
            }

            return lootTableBuilder;
        }
    }
}
