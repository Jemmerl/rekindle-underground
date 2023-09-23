package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreBlockType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class GeoBlockRegistry {

    private final RegistryObject<Block> baseStone;
    private final RegistryObject<Block> regolith;
    private final RegistryObject<Block> cobbles;
    private final RegistryObject<Block> cobblestone;
    private final Map<OreType, OreRegistry> stoneOreRegistry;
    private final Map<OreType, OreRegistry> regolithOreRegistry;

    // TODO add to respective tags
    public GeoBlockRegistry(GeologyType geoType) {
        this.baseStone = ModBlocks.registerStoneGeoBlock(geoType);
        this.regolith = ModBlocks.registerRegolithGeoBlock(geoType);
        this.cobbles = ModBlocks.registerCobblesBlock(geoType);
        this.cobblestone = ModBlocks.registerCobblestoneBlock(geoType);
        this.stoneOreRegistry = fillOreRegistry(geoType, OreBlockType.STONE);
        this.regolithOreRegistry = fillOreRegistry(geoType, OreBlockType.REGOLITH);
    }

    /////////////
    // Getters //
    /////////////

    public Block getBaseStone() { return baseStone.get(); }
    public Block getRegolith() { return regolith.get(); }
    public Block getCobbles() { return cobbles.get(); }
    public Block getCobblestone() { return cobblestone.get(); }

    public Block getStoneOre(OreType oreType, GradeType gradeType) {
        return stoneOreRegistry.get(oreType).getGradeOre(gradeType).get();
    }

    public Block getRegolithOre(OreType oreType, GradeType gradeType) {
        return regolithOreRegistry.get(oreType).getGradeOre(gradeType).get();
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
