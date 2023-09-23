package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreBlockType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class DetritusBlockRegistry {

    private final RegistryObject<Block> baseDetritus;
    private final Map<OreType, OreRegistry> oreRegistry;

    // TODO add to respective tags
    public DetritusBlockRegistry(GeologyType geoType) {
        this.baseDetritus = ModBlocks.registerDetritusBlock(geoType);
        this.oreRegistry = fillOreRegistry(geoType);
    }

    /////////////
    // Getters //
    /////////////

    public Block getBaseDetritus() { return baseDetritus.get(); }

    public Block getDetritusOre(OreType oreType, GradeType gradeType) {
        return oreRegistry.get(oreType).getGradeOre(gradeType).get();
    }


    ////////////////////////
    // Construction Utils //
    ////////////////////////

    private  Map<OreType, OreRegistry> fillOreRegistry(GeologyType geoType) {
        Map<OreType, OreRegistry> oreMap = new HashMap<>();
        for (OreType oreType: EnumSet.complementOf(EnumSet.of(OreType.NONE))) {
            oreMap.put(oreType, new OreRegistry(geoType, oreType, OreBlockType.DETRITUS));
        }
        return oreMap;
    }
}
