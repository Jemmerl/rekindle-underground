package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

public class StoneOreRegistry {

    private final RegistryObject<Block> lowOreBlock;
    private final RegistryObject<Block> midOreBlock;
    private final RegistryObject<Block> highOreBlock;

    public StoneOreRegistry(GeologyType geoType, OreType oreType) {
        this.lowOreBlock = ModBlocks.registerStoneGeoBlock(geoType, oreType, GradeType.LOWGRADE);
        this.midOreBlock = ModBlocks.registerStoneGeoBlock(geoType, oreType, GradeType.MIDGRADE);
        this.highOreBlock = ModBlocks.registerStoneGeoBlock(geoType, oreType, GradeType.HIGHGRADE);
    }

    public RegistryObject<Block> getGradeOre(GradeType gradeType) {
        switch (gradeType) {
            case LOWGRADE:
                return lowOreBlock;
            case MIDGRADE:
                return midOreBlock;
            case HIGHGRADE:
                return highOreBlock;
            default:
                return null;
        }
    }

}
