package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreBlockType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.fml.RegistryObject;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class GeoBlockRegistry {

    private final  GeologyType geologyType;
    private final RegistryObject<Block> baseStone;
    private final RegistryObject<Block> regolith;
    private final RegistryObject<Block> cobbles;
    private final RegistryObject<Block> cobblestone;
    private final Map<OreType, OreRegistry> stoneOreRegistry;
    private final Map<OreType, OreRegistry> regolithOreRegistry;

    // TODO add to respective tags
    public GeoBlockRegistry(GeologyType geoType) {
        this.geologyType = geoType;
        this.baseStone = ModBlocks.registerStoneGeoBlock(geoType);
        this.regolith = geoType.hasCobble() ? ModBlocks.registerRegolithGeoBlock(geoType) : null;
        this.cobbles = geoType.hasCobble() ? ModBlocks.registerCobblesBlock(geoType) : null;
        this.cobblestone = geoType.hasCobble() ? ModBlocks.registerCobblestoneBlock(geoType) : null;
        this.stoneOreRegistry = geoType.isInStoneGroup(StoneGroupType.DETRITUS) ?
                fillOreRegistry(geoType, OreBlockType.DETRITUS) : fillOreRegistry(geoType, OreBlockType.STONE);
        this.regolithOreRegistry =  geoType.hasCobble() ? fillOreRegistry(geoType, OreBlockType.REGOLITH) : null;
    }

    /////////////
    // Getters //
    /////////////

    public Block getBaseStone() { return baseStone.get(); }
    public BlockState getBaseState() { return getBaseStone().getDefaultState(); } // Makes some hardcoded stuff cleaner

    // GeoTypes with no cobble use their base stone as their own regolith
    public Block getRegolith() {
        return geologyType.hasCobble() ? regolith.get() : baseStone.get();
    }
    public Block getCobbles() { return cobbles.get(); }
    public Block getCobblestone() { return cobblestone.get(); }

    public Block getStoneOre(OreType oreType, GradeType gradeType) {
        return stoneOreRegistry.get(oreType).getGradeOre(gradeType).get();
    }

    // GeoTypes with no cobble use their base stone as their own regolith
    public Block getRegolithOre(OreType oreType, GradeType gradeType) {
        return  geologyType.hasCobble() ?
                regolithOreRegistry.get(oreType).getGradeOre(gradeType).get() : getStoneOre(oreType, gradeType);
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
