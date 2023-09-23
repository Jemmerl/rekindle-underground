package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreBlockType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;

public class GeoBlockRegistry {

    private final boolean hasCobble;

    private final RegistryObject<Block> baseStone;
    private final RegistryObject<Block> regolith;
    private final RegistryObject<Block> cobbles;
    private final RegistryObject<Block> cobblestone;
    private final RegistryObject<Item> rockItem;

    private final Map<OreType, OreRegistry> stoneOreRegistry;
    private final Map<OreType, OreRegistry> regolithOreRegistry;

    // TODO add to respective tags
    public GeoBlockRegistry(GeologyType geoType) {
        this.hasCobble = geoType.hasCobble();

        this.baseStone = ModBlocks.registerStoneGeoBlock(geoType);
        this.regolith = hasCobble ? ModBlocks.registerRegolithGeoBlock(geoType) : null;
        this.cobbles = hasCobble ? ModBlocks.registerCobblesBlock(geoType) : null;
        this.cobblestone = hasCobble ? ModBlocks.registerCobblestoneBlock(geoType) : null;
        this.rockItem = hasCobble ? ModItems.registerRockItem(geoType) : null;

        this.stoneOreRegistry = geoType.isInStoneGroup(StoneGroupType.DETRITUS) ?
                fillOreRegistry(geoType, OreBlockType.DETRITUS) : fillOreRegistry(geoType, OreBlockType.STONE);
        this.regolithOreRegistry =  hasCobble ? fillOreRegistry(geoType, OreBlockType.REGOLITH) : null;
    }


    //////////////////////////
    // SINGLE BLOCK GETTERS //
    //////////////////////////

    public Block getBaseStone() { return baseStone.get(); }
    public BlockState getBaseState() { return getBaseStone().getDefaultState(); } // Makes some hardcoded stuff cleaner

    // GeoTypes with no cobble use their base stone as their own regolith
    public Block getRegolith() {
        return hasCobble ? regolith.get() : baseStone.get();
    }
    public Block getCobbles() { return cobbles.get(); }
    public Block getCobblestone() { return cobblestone.get(); }
    public Item getRockItem() { return rockItem.get(); }

    public Block getStoneOre(OreType oreType, GradeType gradeType) {
        return stoneOreRegistry.get(oreType).getGradeOre(gradeType).get();
    }

    // GeoTypes with no cobble use their base stone as their own regolith
    public Block getRegolithOre(OreType oreType, GradeType gradeType) {
        return  hasCobble ? regolithOreRegistry.get(oreType).getGradeOre(gradeType).get() : getStoneOre(oreType, gradeType);
    }


    ///////////////////
    // LIST BUILDERS //
    ///////////////////

    // THESE ARE MAINLY TO BE USED DURING INITIALIZATION AND DATA GENERATION
    // The sheer amount of items generated would be excessive during any other stage

    // Get all geo-blocks (not including cobbles and cobblestones)
    public List<Block> getAllGeoBlocks() {
        List<Block> allGeoBlocks = new ArrayList<>(getAllStoneOreBlocks());
        allGeoBlocks.add(getBaseStone());
        if (hasCobble) allGeoBlocks.add(getRegolith());
        return allGeoBlocks;
    }

    // Get all ore-bearing geo-blocks
    public List<Block> getAllOreBlocks() {
        List<Block> allOreBlocks = new ArrayList<>(getAllStoneOreBlocks());
        if (hasCobble) allOreBlocks.addAll(getAllRegolithOreBlocks());
        return allOreBlocks;
    }

    // Get all ore-bearing base stone geo-blocks
    public List<Block> getAllStoneOreBlocks() {
        List<Block> allStoneOreBlocks = new ArrayList<>();
        for (OreRegistry oreRegistry: stoneOreRegistry.values()) {
            allStoneOreBlocks.addAll(oreRegistry.getAllGradedOreBlocks());
        }
        return allStoneOreBlocks;
    }

    // Get all ore-bearing regolith geo-blocks
    // Assumes that the caller has confirmed the geo-type has regolith, else get a null return
    public List<Block> getAllRegolithOreBlocks() {
        if (regolithOreRegistry == null) return null;

        List<Block> allRegolithOreBlocks = new ArrayList<>();
        for (OreRegistry oreRegistry: regolithOreRegistry.values()) {
            allRegolithOreBlocks.addAll(oreRegistry.getAllGradedOreBlocks());
        }
        return allRegolithOreBlocks;
    }


    ////////////////////////
    // Registration Utils //
    ////////////////////////

    private  Map<OreType, OreRegistry> fillOreRegistry(GeologyType geoType, OreBlockType blockType) {
        Map<OreType, OreRegistry> oreMap = new HashMap<>();
        for (OreType oreType: EnumSet.complementOf(EnumSet.of(OreType.NONE))) {
            oreMap.put(oreType, new OreRegistry(geoType, oreType, blockType));
        }
        return oreMap;
    }
}
