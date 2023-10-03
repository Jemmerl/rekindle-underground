package com.jemmerl.jemsgeology.data.generators.server;

import com.google.common.collect.ImmutableList;
import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.blockinit.GeoRegistry;
import com.jemmerl.jemsgeology.util.lists.ModBlockLists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {

    // Copied from BlockLootTables, as it is private
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(
            ItemPredicate.Builder.create()
                    .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

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
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationTracker) {
//        for (Map.Entry<ResourceLocation, LootTable> entry : map.entrySet())
//            LootTableManager.validateLootTable(validationTracker, entry.getKey(), entry.getValue());
    }

    // TODO Whole lotta TEMP shite in here
    private static class ModBlockLootTables extends BlockLootTables {
        @Override
        protected void addTables() {
            for (GeoRegistry geoRegistry: ModBlocks.GEOBLOCKS.values()) {
                //System.out.println(geoRegistry.getGeoType().getName());

                boolean hasCobble = geoRegistry.hasCobble();

                // Loot Tables for base stone ores
                registerBaseStoneOreLootTables(geoRegistry, hasCobble);

                if (hasCobble) {
                    registerBaseStoneLootTable(geoRegistry);
                    registerRegolithLootTable(geoRegistry);
                    registerCobblesLootTable(geoRegistry);
                    registerDropSelfLootTable(geoRegistry.getCobblestone());

                    registerDropSelfLootTable(geoRegistry.getRawSlab());
                    registerDropSelfLootTable(geoRegistry.getRawStairs());
                    registerDropSelfLootTable(geoRegistry.getRawWall());
                    registerDropSelfLootTable(geoRegistry.getCobbleSlab());
                    registerDropSelfLootTable(geoRegistry.getCobbleStairs());
                    registerDropSelfLootTable(geoRegistry.getCobbleWall());
                    registerDropSelfLootTable(geoRegistry.getPolishedStone());
                    registerDropSelfLootTable(geoRegistry.getPolishedSlab());
                    registerDropSelfLootTable(geoRegistry.getPolishedStairs());

                    // Loot Tables for base regolith ores
                    registerRegolithOreLootTables(geoRegistry);

                } else {
                    // TODO TEMP! Handles ore-less base stones with no cobble/regolith (evaporites) as well as detritus
                    if (geoRegistry.getGeoType() == GeologyType.PAHOEHOE) {
                        registerBaseStoneLootTable(geoRegistry);
                    } else {
                        registerDropSelfLootTable(geoRegistry.getBaseStone());
                    }
                }
            }
        }


        //////////////////////////
        // Loot Table Registers //
        //////////////////////////

        // Register: LOOSE COBBLES
        private void registerCobblesLootTable(GeoRegistry geoRegistry) {
            Block cobbles = geoRegistry.getCobbles();
            registerLootTable(cobbles, droppingWithSilkTouch(cobbles,
                    withExplosionDecay(cobbles, ItemLootEntry.builder(geoRegistry.getRockItem())
                            .acceptFunction(SetCount.builder(ConstantRange.of(4))))));
        }


        // Register: BASE STONE/DETRITUS - NO ORE
        private void registerBaseStoneLootTable(GeoRegistry geoRegistry) {
            GeologyType geologyType = geoRegistry.getGeoType();
            Block block = geoRegistry.getBaseStone();

            // Catch and handle detritus base stones
            switch (geologyType) {
                case DIRT:
                case COARSE_DIRT:
                case SAND:
                case RED_SAND:
                    registerDropSelfLootTable(ModBlockLists.VANILLA_DET_LIST.inverse().get(geologyType).getBlock());
                    return;
                case CLAY:
                    registerLootTable(block,
                            droppingWithSilkTouchOrRandomly(Blocks.CLAY, Items.CLAY_BALL, ConstantRange.of(4)));
                    return;
                case GRAVEL:
                    registerLootTable(block, droppingWithSilkTouch(Blocks.GRAVEL,
                            withSurvivesExplosion(Blocks.GRAVEL,
                                    ItemLootEntry.builder(Items.FLINT)
                                            .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F))
                                            .alternatively(ItemLootEntry.builder(Blocks.GRAVEL)))));
                return;
                default:
            }

            // Register a Loot Table for rock-dropping base stone
            if (geologyType == GeologyType.PAHOEHOE) {
                registerLootTable(block, droppingWithSilkTouch(block,
                        withExplosionDecay(block, ItemLootEntry.builder( ModBlocks.GEOBLOCKS.get(GeologyType.BASALT).getRockItem())
                                .acceptFunction(SetCount.builder(BinomialRange.of(3, 0.65f))))));
            } else if (!ModBlockLists.FLINT_BEARING.containsKey(geologyType)) {
                // Base stone block without flint
                registerLootTable(block, droppingWithSilkTouch(block,
                        withExplosionDecay(block, ItemLootEntry.builder(geoRegistry.getRockItem())
                                .acceptFunction(SetCount.builder(BinomialRange.of(3, 0.65f))))));
            } else {
                // Alternative loot entry for flint bearing stones
                registerWithSilkTouch(block,
                        buildFlintAltLootEntry(BinomialRange.of(3, 0.65F), geoRegistry.getRockItem(), geologyType, 0));
            }
        }


        // Register: BASE STONE/DETRITUS - WITH ORE
        private void registerBaseStoneOreLootTables(GeoRegistry geoRegistry, boolean hasCobble) {
            GeologyType geologyType = geoRegistry.getGeoType();
            for (Block block: geoRegistry.getStoneOreBlocks()) {
                //System.out.println(block.getRegistryName());

                OreType oreType = ((IGeoBlock) block).getOreType();
                LootEntry.Builder<?> oreEntry = buildOreLootEntry(oreType, ((IGeoBlock) block).getGradeType());

                // Catch and handle detritus ore blocks
                switch (geologyType) {
                    case DIRT:
                    case COARSE_DIRT:
                    case SAND:
                    case RED_SAND:
                        registerLootTable(block, buildOreLootTable(block, oreEntry));
                        continue;
                    case CLAY:
                        registerLootTable(block, buildOreLootTable(Blocks.CLAY, oreEntry,
                                buildRockLootEntry(2, 0.65F, Items.CLAY_BALL)));
                        continue;
                    case GRAVEL:
                        registerLootTable(block, buildOreLootTable(Blocks.GRAVEL, oreEntry,
                                buildRockLootEntry(1, 0.05F, Items.FLINT)));
                        continue;
                    default:
                }

                // Otherwise, process for a stone ore block
                LootEntry.Builder<?> rockEntry;
                if (geologyType == GeologyType.PAHOEHOE) {
                    rockEntry = buildRockLootEntry(2, 0.33F, ModBlocks.GEOBLOCKS.get(GeologyType.BASALT).getRockItem());
                } else if (!hasCobble) {
                    registerLootTable(block, buildOreLootTable(block, oreEntry));
                    continue;
                } else if (!ModBlockLists.FLINT_BEARING.containsKey(geologyType)) {
                    // Base stone block without flint
                    rockEntry = buildRockLootEntry(2, 0.33F, geoRegistry.getRockItem());
                } else {
                    // Alternative loot entry for flint bearing ore stones
                    rockEntry = buildFlintAltLootEntry(BinomialRange.of(2, 0.33F),
                            geoRegistry.getRockItem(), geologyType, 0);
                }

                registerLootTable(block, buildOreLootTable(block, oreEntry, rockEntry));
            }
        }


        // Register: REGOLITH - NO ORE
        private void registerRegolithLootTable(GeoRegistry geoRegistry) {
            GeologyType geologyType = geoRegistry.getGeoType();
            Block block = geoRegistry.getRegolith();
            if (!ModBlockLists.FLINT_BEARING.containsKey(geologyType)) {
                // Base stone block without flint
                registerDropSelfLootTable(block);
            } else {
                // Alternative loot entry for flint bearing stones
                registerWithSilkTouch(block,
                        buildFlintAltLootEntry(ConstantRange.of(1), block.asItem(), geologyType, 2));
            }
        }


        // Register: REGOLITH - WITH ORE
        private void registerRegolithOreLootTables(GeoRegistry geoRegistry) {
            GeologyType geologyType = geoRegistry.getGeoType();
            boolean dropFlint = ModBlockLists.FLINT_BEARING.containsKey(geologyType);

            for (Block block: geoRegistry.getRegolithOreBlocks()) {
                OreType oreType = ((IGeoBlock) block).getOreType();
                LootEntry.Builder<?> oreEntry = buildOreLootEntry(oreType, ((IGeoBlock) block).getGradeType());

                LootEntry.Builder<?> rockEntry;
                if (!ModBlockLists.FLINT_BEARING.containsKey(geologyType)) {
                    // Base stone block without flint
                    rockEntry = buildRockLootEntry(2, 0.15F, geoRegistry.getRockItem());
                } else {
                    // Alternative loot entry for flint bearing ore stones (+2% chance of flint drop)
                    rockEntry = buildFlintAltLootEntry(BinomialRange.of(2, 0.15F), geoRegistry.getRockItem(), geologyType, 2);
                }

                registerLootTable(block, buildOreLootTable(block, oreEntry, rockEntry));
            }
        }


        ///////////////////
        // Loot Builders //
        ///////////////////

        // Build a random item drop Loot Entry for a base stone/detritus
        private LootEntry.Builder<?> buildRockLootEntry(int nIn, float pIn, Item rockItem) {
            return ItemLootEntry.builder(rockItem)
                    .acceptFunction(ExplosionDecay.builder())
                    .acceptFunction(SetCount.builder(BinomialRange.of(nIn, pIn)));
        }

        // Build a flint/alternate drop Loot Entry for a stone with cobbles
        private LootEntry.Builder<?> buildFlintAltLootEntry(IRandomRange randomRange, Item alternate, GeologyType geologyType, int bonus) {
            return ItemLootEntry.builder(Items.FLINT)
                    .acceptFunction(ExplosionDecay.builder())
                    .acceptFunction(SetCount.builder(randomRange))
                    .acceptCondition(RandomChance.builder((ModBlockLists.FLINT_BEARING.getOrDefault(geologyType, 0) + bonus) / 100f))
                    .alternatively(ItemLootEntry.builder(alternate)
                            .acceptFunction(ExplosionDecay.builder()));
        }

        // Build an ore drop Loot Entry
        private LootEntry.Builder<?> buildOreLootEntry(OreType oreType, GradeType gradeType) {
            switch (gradeType) {
                case HIGHGRADE:
                    return buildOreLootEntry(RandomValueRange.of(2,4), oreType.getOreItem());
                case MIDGRADE:
                    return buildOreLootEntry(RandomValueRange.of(1,2), oreType.getOreItem());
                case LOWGRADE:
                default: // NONE should not happen anyway
                    return buildOreLootEntry(RandomValueRange.of(1,2), oreType.getPoorOreItem());
            }
        }


        // Build an ore drop Loot Entry
        private LootEntry.Builder<?> buildOreLootEntry(IRandomRange randomRange, Item oreItem) {
            return ItemLootEntry.builder(oreItem)
                    .acceptFunction(ExplosionDecay.builder())
                    .acceptFunction(SetCount.builder(randomRange));
        }


        // Register a block with the ability to be silk-touched. Much more... accepting than existing methods.
        private void registerWithSilkTouch(Block block, LootEntry.Builder<?> lootEntry) {
            registerLootTable(block, LootTable.builder()
                    .addLootPool(LootPool.builder()
                            .rolls(ConstantRange.of(1))
                            .acceptCondition(SILK_TOUCH)
                            .acceptCondition(SurvivesExplosion.builder())
                            .addEntry(ItemLootEntry.builder(block)
                                    .alternatively(lootEntry))));
        }


        // Register a base stone/detritus ore block with the ability to be silk-touched
        private LootTable.Builder buildOreLootTable(Block block, LootEntry.Builder<?> oreEntry, LootEntry.Builder<?> rockEntry) {
            return buildOreLootTable(block, oreEntry)
                    .addLootPool(LootPool.builder()
                            .rolls(ConstantRange.of(1))
                            .acceptCondition(SILK_TOUCH.inverted())
                            .acceptCondition(SurvivesExplosion.builder())
                            .addEntry(rockEntry));
        }


        // Register a base stone/detritus ore block with the ability to be silk-touched
        private LootTable.Builder buildOreLootTable(Block block, LootEntry.Builder<?> oreEntry) {
            return LootTable.builder()
                    .addLootPool(LootPool.builder()
                            .rolls(ConstantRange.of(1))
                            .acceptCondition(SILK_TOUCH)
                            .acceptCondition(SurvivesExplosion.builder())
                            .addEntry(ItemLootEntry.builder(block)))
                    .addLootPool(LootPool.builder()
                            .rolls(ConstantRange.of(1))
                            .acceptCondition(SILK_TOUCH.inverted())
                            .acceptCondition(SurvivesExplosion.builder())
                            .addEntry(oreEntry));
        }



        //ItemLootEntry.builder(Items.FLINT)
        //                                .acceptFunction(ExplosionDecay.builder())
        //                                .acceptFunction(SetCount.builder(BinomialRange.of(3, 0.65f)))
        //                                .acceptCondition(RandomChance.builder(ModBlockLists.FLINT_BEARING.get(geologyType) / 100f))
        //                                .alternatively(ItemLootEntry.builder(geoRegistry.getRockItem())
        //                                        .acceptFunction(ExplosionDecay.builder()))

        //registerLootTable(block, LootTable.builder()
        //                        .addLootPool(LootPool.builder()
        //                                .rolls(ConstantRange.of(1))
        //                                .acceptCondition(SILK_TOUCH)
        //                                .acceptCondition(SurvivesExplosion.builder())
        //                                .addEntry(ItemLootEntry.builder(block)
        //                                        .alternatively(ItemLootEntry.builder(Items.FLINT)
        //                                                .acceptFunction(ExplosionDecay.builder())
        //                                                .acceptFunction(SetCount.builder(BinomialRange.of(3, 0.65f)))
        //                                                .acceptCondition(RandomChance.builder(ModBlockLists.FLINT_BEARING.get(geologyType) / 100f))
        //                                                .alternatively(ItemLootEntry.builder(geoRegistry.getRockItem())
        //                                                        .acceptFunction(ExplosionDecay.builder()))))));


        // Don't worry about this.
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }

    }
}