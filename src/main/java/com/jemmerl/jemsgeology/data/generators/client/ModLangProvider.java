package com.jemmerl.jemsgeology.data.generators.client;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import com.jemmerl.jemsgeology.init.ModTags;
import com.jemmerl.jemsgeology.init.blockinit.GeoRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;
import org.codehaus.plexus.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ModLangProvider extends LanguageProvider {

    public ModLangProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {

        // Misc items and stuff
        add("item." + JemsGeology.MOD_ID + ".lime_mortar", "Lime Mortar");
        add("item." + JemsGeology.MOD_ID + ".quarry_tool", "Quarrying Chisel");
        add("itemGroup.jemsgeo_base_stones_tab", "Jem's Geology: Stones");
        add("itemGroup.jemsgeo_cobbles_tab", "Jem's Geology: Cobbles");
        add("itemGroup.jemsgeo_ores_tab", "Jem's Geology: Ores");
        add("itemGroup.jemsgeo_misc_tab", "Jem's Geology: Misc");
        add("itemGroup.jemsgeo_ore_blocks_tab", "Jem's Geology: Ore Blocks");
        add("tooltip.all." + JemsGeology.MOD_ID + ".shiftinfo", "\u00A77Hold\u00A7r \u00A7eSHIFT\u00A7r \u00A77for details...\u00A7r"); // Might be used again in future

        // I cannot use tags in data-gen but want the lang file to be orderly anyway.
        // It's not like this will run during normal gameplay anyway, so I don't care if it is optimal.
        List<Item> goodOre = new ArrayList<>();
        List<Item> poorOre = new ArrayList<>();
        List<Item> rocks = new ArrayList<>();
        for (RegistryObject<Item> itemReg: ModItems.ITEMS.getEntries()) {
            Item item = itemReg.get();
            if (item.getRegistryName().getPath().contains("poor_")){
                poorOre.add(item);
            } else if (item.getRegistryName().getPath().contains("_ore")) {
                goodOre.add(item);
            } else if  (item.getRegistryName().getPath().contains("_rock")) {
                rocks.add(item);
            }
        }

        // Same deal here, fight me.
        List<Block> cobbles = new ArrayList<>();
        List<Block> cobblestones = new ArrayList<>();
        List<Block> stones = new ArrayList<>();
        List<Block> regolith = new ArrayList<>();
        List<Block> detritus = new ArrayList<>();
        for (GeoRegistry geoRegistry: ModBlocks.GEOBLOCKS.values()) {
            boolean hasCobble = geoRegistry.hasCobble();

            if (geoRegistry.getGeoType().isInStoneGroup(StoneGroupType.DETRITUS)) {
                detritus.addAll(geoRegistry.getStoneGeoBlocks());
            } else {
                stones.addAll(geoRegistry.getStoneGeoBlocks());
            }

            if (hasCobble) {
                cobbles.add(geoRegistry.getCobbles());
                cobblestones.add(geoRegistry.getCobblestone());
                regolith.addAll(geoRegistry.getRegolithGeoBlocks());
            }
        }

        // Normal Ore Items
        for (Item item: goodOre) {
            String path = Objects.requireNonNull(item.getRegistryName()).getPath().toLowerCase(Locale.ROOT);
            String oreName = path.split("_ore", 2)[0].replace('_', ' ');
            String displayName = StringUtils.capitaliseAllWords(oreName);
            add(item, displayName);
        }

        // Poor Ore Items
        for (Item item: poorOre) {
            String path = Objects.requireNonNull(item.getRegistryName()).getPath().toLowerCase(Locale.ROOT);
            String oreName = path.split("poor_", 2)[1].split("_ore", 2)[0].replace('_', ' ');
            String displayName = "Small " + StringUtils.capitaliseAllWords(oreName);
            add(item, displayName);
        }

        // Cobble (Rock) Items
        for (Item item: rocks) {
            String path = Objects.requireNonNull(item.getRegistryName()).getPath().toLowerCase(Locale.ROOT);
            String rockName = path.split("_rock", 2)[0].replace('_', ' ');
            String displayName = StringUtils.capitaliseAllWords(rockName) + " Cobble";
            add(item, displayName);
        }

        // Cobble Blocks
        for (Block block: cobbles) {
            String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ROOT);
            String displayName = "Loose " + StringUtils.capitaliseAllWords(path.replace('_', ' '));
            add(block, displayName);
        }

        // Cobblestones
        for (Block block: cobblestones) {
            String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ROOT);
            String displayName = StringUtils.capitaliseAllWords(path.replace('_', ' '));
            add(block, displayName);
        }

        // Stones/Ores
        for (Block block: stones) {
            String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ROOT);
            String displayName;

            if (path.contains("grade")) {
                String[] dividePath = path.split("/", 3);
                String stoneName = StringUtils.capitaliseAllWords(dividePath[0].split("_stone", 2)[0].replace('_', ' '));
                String oreName = StringUtils.capitaliseAllWords(dividePath[1].replace('_', ' '));
                String[] divideGrade = dividePath[2].split("grade", 2);
                String gradeName = StringUtils.capitalise(divideGrade[0]) + "-Grade";
                displayName = gradeName + " " + stoneName + " " + oreName + " Ore";
            } else {
                String[] dividePath = path.split("_stone", 2);
                displayName = StringUtils.capitaliseAllWords(dividePath[0].replace('_', ' '));
            }

            add(block, displayName);
        }

        // Regolith/Ores
        for (Block block: regolith) {
            String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ROOT);
            String displayName;

            if (path.contains("grade")) {
                String[] dividePath = path.split("/", 3);
                String regolithName = StringUtils.capitaliseAllWords(dividePath[0].replace('_', ' '));
                String oreName = StringUtils.capitaliseAllWords(dividePath[1].replace('_', ' '));
                String[] divideGrade = dividePath[2].split("grade", 2);
                String gradeName = StringUtils.capitalise(divideGrade[0]) + "-Grade";
                displayName = gradeName + " " + regolithName + " " + oreName + " Ore";
            } else {
                displayName = StringUtils.capitaliseAllWords(path.replace('_', ' '));
            }

            add(block, displayName);
        }

        // Detritus/Ores
        for (Block block: detritus) {
            String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ROOT);
            String displayName;

            if (path.contains("grade")) {
                String[] dividePath = path.split("/", 3);
                String detritusName = StringUtils.capitaliseAllWords(dividePath[0].split("_detritus", 2)[0].replace('_', ' '));
                String oreName = StringUtils.capitaliseAllWords(dividePath[1].replace('_', ' '));
                String[] divideGrade = dividePath[2].split("grade", 2);
                String gradeName = StringUtils.capitalise(divideGrade[0]) + "-Grade";
                displayName = gradeName + " " + detritusName + " " + oreName + " Ore";
            } else {
                String[] dividePath = path.split("_detritus", 2);
                displayName = StringUtils.capitaliseAllWords(dividePath[0].replace('_', ' '));
            }

            add(block, displayName);
        }
    }
}
