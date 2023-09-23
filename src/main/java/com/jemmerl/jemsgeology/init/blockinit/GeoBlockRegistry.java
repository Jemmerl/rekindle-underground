package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class GeoBlockRegistry {

    private final RegistryObject<Block> baseStone;
    private final RegistryObject<Block> regolith;
    private final RegistryObject<Block> cobbles;
    private final RegistryObject<Block> cobblestone;
    private Map<OreType, StoneOreRegistry> stoneOreRegistry;
    private Map<OreType, RegolithOreRegistry> regolithOreRegistry;

    // TODO add to respective tags
    public GeoBlockRegistry(GeologyType geoType) {
        this.baseStone = ModBlocks.registerStoneGeoBlock(geoType, OreType.NONE, GradeType.NONE);
        this.regolith = ModBlocks.registerRegolithGeoBlock(geoType, OreType.NONE, GradeType.NONE);
        this.cobbles = ModBlocks.registerCobblesBlock(geoType);
        this.cobblestone = ModBlocks.registerCobblestoneBlock(geoType);
        this.stoneOreRegistry = fillStoneOreRegistry(geoType);
        this.regolithOreRegistry = fillRegolithOreRegistry(geoType);
    }

    /////////////
    // Getters //
    /////////////

    public Block getBaseStoneBlock() { return baseStone.get(); }
    public Block getRegolithBlock() { return regolith.get(); }
    public Block getCobblesBlock() { return cobbles.get(); }
    public Block getCobblestoneBlock() { return cobblestone.get(); }

    public Block getStoneOre(OreType oreType, GradeType gradeType) {
        return stoneOreRegistry.get(oreType).getGradeOre(gradeType).get();
    }

    public Block getRegolithOre(OreType oreType, GradeType gradeType) {
        return regolithOreRegistry.get(oreType).getGradeOre(gradeType).get();
    }


    ////////////////////////
    // Construction Utils //
    ////////////////////////

    private  Map<OreType, StoneOreRegistry> fillStoneOreRegistry(GeologyType geoType) {
        Map<OreType, StoneOreRegistry> oreMap = new HashMap<>();
        for (OreType oreType: OreType.values()) {
            oreMap.put(oreType, new StoneOreRegistry(geoType, oreType));
        }
        return oreMap;
    }

    private  Map<OreType, RegolithOreRegistry> fillRegolithOreRegistry(GeologyType geoType) {
        Map<OreType, RegolithOreRegistry> oreMap = new HashMap<>();
        for (OreType oreType: OreType.values()) {
            oreMap.put(oreType, new RegolithOreRegistry(geoType, oreType));
        }
        return oreMap;
    }
}
