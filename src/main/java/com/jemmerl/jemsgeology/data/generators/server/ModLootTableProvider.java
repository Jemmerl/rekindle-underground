package com.jemmerl.jemsgeology.data.generators.server;

import com.google.common.collect.ImmutableList;
import com.jemmerl.jemsgeology.blocks.FallingCobbleBlock;
import com.jemmerl.jemsgeology.blocks.StoneOreBlock;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.util.lists.GeoListWrapper;
import com.jemmerl.jemsgeology.util.lists.ModBlockLists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.TableBonus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.jemmerl.jemsgeology.blocks.StoneOreBlock.GRADE_TYPE;
import static com.jemmerl.jemsgeology.blocks.StoneOreBlock.ORE_TYPE;

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

            ////////////////////////
            // Stones Loot Tables //
            ////////////////////////

            for (Block block : ModBlockLists.ALL_STONES) {
                GeologyType geologyType = ((StoneOreBlock) block).getGeologyType();
                GeoListWrapper geoList = ModBlockLists.GEO_LIST.get(geologyType);
                if (geologyType.hasCobble()) {
                    // Register stone -> rock drop
                    LootTable.Builder lootTable = buildStoneLootTable(geoList);
                    registerLootTable(block, lootTable);
                } else {
                    // TODO TEMP
                    registerDropSelfLootTable(block); // i mean it! temp!
                }
            }

            for (Block block : ModBlockLists.ALL_REGOLITH) {
                GeologyType geologyType = ((StoneOreBlock) block).getGeologyType();
                GeoListWrapper geoList = ModBlockLists.GEO_LIST.get(geologyType);

                LootTable.Builder lootTable = buildRegolithLootTable(geoList);
                registerLootTable(block, lootTable);
            }


            for (Block block : ModBlockLists.COBBLES) {
                GeologyType geologyType = ((FallingCobbleBlock) block).getGeologyType();
                GeoListWrapper geoList = ModBlockLists.GEO_LIST.get(geologyType);

                LootTable.Builder lootTable = buildCobblesLootTable(geoList);
                registerLootTable(block, lootTable);
            }

            for (Block block : ModBlockLists.COBBLESTONES) {
                registerDropSelfLootTable(block);
            }

            //////////////////////////
            // Detritus Loot Tables //
            //////////////////////////

            // Dirt
            registerLootTable(ModBlocks.DIRT_STONE.get(), buildDetritusLootTable(Blocks.DIRT.getDefaultState()));

            // Coarse Dirt
            registerLootTable(ModBlocks.COARSE_DIRT_STONE.get(), buildDetritusLootTable(Blocks.COARSE_DIRT.getDefaultState()));

            // Sand
            registerLootTable(ModBlocks.SAND_STONE.get(), buildDetritusLootTable(Blocks.SAND.getDefaultState()));

            // Red Sand
            registerLootTable(ModBlocks.RED_SAND_STONE.get(), buildDetritusLootTable(Blocks.RED_SAND.getDefaultState()));

            // Gravel -- lower flint drop rate
            registerLootTable(ModBlocks.GRAVEL_STONE.get(), buildGravelDetritusLootTable());

            // Clay -- may drop < 4 clay
            registerLootTable(ModBlocks.CLAY_STONE.get(), buildClayDetritusLootTable());

        }


        /////////////////////////
        // Loot Table Builders //
        /////////////////////////

        // Creates and fills a loot table with the respective rock for cobbles blocks
        private static LootTable.Builder buildCobblesLootTable(GeoListWrapper geoList) {
            LootTable.Builder lootTableBuilder = new LootTable.Builder();

            lootTableBuilder.addLootPool(LootPool.builder()
                    .name("Rocks")
                    .rolls(ConstantRange.of(4))
                    .addEntry(ItemLootEntry.builder(geoList.getRockItem()))
            );

            return lootTableBuilder;
        }


        // Creates and fills a loot table with pools for each OreType for stone blocks
        private static LootTable.Builder buildStoneLootTable(GeoListWrapper geoList) {
            LootTable.Builder lootTableBuilder = new LootTable.Builder();

            // Loot pool for normal stone, "NONE" ore
            lootTableBuilder.addLootPool(LootPool.builder()
                    .name(OreType.NONE.getString())
                    .rolls(BinomialRange.of(3, 0.65f))
                    .addEntry(ItemLootEntry.builder(geoList.getRockItem()))
            );

            // Add ore loot pools
            fillOreTables(lootTableBuilder, geoList.getStoneOreBlock());

            return lootTableBuilder;
        }

        // Creates and fills a loot table with pools for each OreType for regolith blocks
        private static LootTable.Builder buildRegolithLootTable(GeoListWrapper geoList) {
            Block regolithBlock = geoList.getRegolithBlock();
            LootTable.Builder lootTableBuilder = new LootTable.Builder();

            // Loot pool for normal regolith, "NONE" ore
            lootTableBuilder.addLootPool(LootPool.builder()
                    .name(OreType.NONE.getString())
                    .addEntry(ItemLootEntry.builder(regolithBlock.asItem()))
            );

            // Add ore loot pools
            fillOreTables(lootTableBuilder, regolithBlock);

            return lootTableBuilder;
        }

        // Creates and fills a loot table with pools for each OreType for detritus that drops itself
        private static LootTable.Builder buildDetritusLootTable(BlockState vanillaState) {
            LootTable.Builder lootTableBuilder = new LootTable.Builder();

            // Loot pool for the detritus itself and "NONE" ore
            lootTableBuilder.addLootPool(LootPool.builder()
                    .name(OreType.NONE.getString())
                    .addEntry(ItemLootEntry.builder(vanillaState.getBlock().asItem()))
            );

            // Add ore loot pools
            fillOreTables(lootTableBuilder, ModBlockLists.VANILLA_DET_LIST.get(vanillaState).getBlock());

            return lootTableBuilder;
        }


        // Create and fills a loot table with pools for each OreType for gravel detritus
        private static LootTable.Builder buildGravelDetritusLootTable() {
            LootTable.Builder lootTableBuilder = new LootTable.Builder();

            // Gravel or flint loot pool for itself and "NONE" ore
            lootTableBuilder.addLootPool(LootPool.builder()
                    .name(OreType.NONE.getString())
                    .addEntry(ItemLootEntry.builder(Items.FLINT)
                            .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F))
                            .alternatively(ItemLootEntry.builder(Blocks.GRAVEL)))
            );

            // Add ore loot pools
            fillOreTables(lootTableBuilder, ModBlocks.GRAVEL_STONE.get());

            return lootTableBuilder;
        }


        // Create and fills a loot table with pools for each OreType for clay detritus
        private static LootTable.Builder buildClayDetritusLootTable() {
            LootTable.Builder lootTableBuilder = new LootTable.Builder();

            // Clay drops loot pool for the clay itself and "NONE" ore
            lootTableBuilder.addLootPool(LootPool.builder()
                    .name(OreType.NONE.getString())
                    .rolls(BinomialRange.of(4, 0.6f))
                    .addEntry(ItemLootEntry.builder(Items.CLAY_BALL))
            );

            // Add ore loot pools
            fillOreTables(lootTableBuilder, ModBlocks.CLAY_STONE.get());

            return lootTableBuilder;
        }


        // Add filled ore loot tables
        // TODO make all drop some amount of small ore
        private static void fillOreTables(LootTable.Builder lootTableBuilder, Block block) {
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
                                    .builder(block)
                                    .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                            .withProp(ORE_TYPE, oreType)
                                            .withProp(GRADE_TYPE, gradeType))));
                }
            }
        }

        // Don't worry about this.
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }

    }
}
