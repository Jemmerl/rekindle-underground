package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreBlockType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class DetritusBlockRegistry {

    private final RegistryObject<Block> dirtBlock;
    private final RegistryObject<Block> coarseDirtBlock;
    private final RegistryObject<Block> clayBlock;
    private final RegistryObject<Block> sandBlock;
    private final RegistryObject<Block> redSandBlock;
    private final RegistryObject<Block> gravelBlock;

    private Map<OreType, OreRegistry> stoneOreRegistry;
    private Map<OreType, RegolithOreRegistry> regolithOreRegistry;

    // TODO add to respective tags
    public DetritusBlockRegistry(GeologyType geoType) {
        this.dirtBlock = ModBlocks.registerStoneGeoBlock(geoType);
        this.coarseDirtBlock = ModBlocks.registerRegolithGeoBlock(geoType);
        this.clayBlock = ModBlocks.registerCobblesBlock(geoType);
        this.sandBlock = ModBlocks.registerCobblestoneBlock(geoType);
        this.redSandBlock = ModBlocks.registerCobblestoneBlock(geoType);
        this.gravelBlock = ModBlocks.registerCobblestoneBlock(geoType);


        this.stoneOreRegistry = fillOreRegistry(geoType);
        this.regolithOreRegistry = fillRegolithOreRegistry(geoType);
    }

    /////////////
    // Getters //
    /////////////

    public Block getDirtBlock() { return baseStone.get(); }
    public Block getCoarseDirtBlock() { return regolith.get(); }
    public Block clayBlock() { return cobbles.get(); }
    public Block sandBlock() { return cobblestone.get(); }
    public Block redSandBlock() { return baseStone.get(); }
    public Block gravelBlock() { return baseStone.get(); }


    public Block getStoneOre(OreType oreType, GradeType gradeType) {
        return stoneOreRegistry.get(oreType).getGradeOre(gradeType).get();
    }

    public Block getRegolithOre(OreType oreType, GradeType gradeType) {
        return regolithOreRegistry.get(oreType).getGradeOre(gradeType).get();
    }


    ////////////////////////
    // Construction Utils //
    ////////////////////////

    private  Map<OreType, OreRegistry> fillOreRegistry(GeologyType geoType) {
        Map<OreType, OreRegistry> oreMap = new HashMap<>();
        for (OreType oreType: OreType.values()) {
            oreMap.put(oreType, new OreRegistry(geoType, oreType, OreBlockType.STONE));
        }
        return oreMap;
    }
}
