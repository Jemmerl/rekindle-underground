package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreBlockType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

public class OreRegistry {

    private final RegistryObject<Block> lowOreBlock;
    private final RegistryObject<Block> midOreBlock;
    private final RegistryObject<Block> highOreBlock;

    public OreRegistry(GeologyType geoType, OreType oreType, OreBlockType blockType) {
        this.lowOreBlock = registerOreBlock(geoType, oreType, GradeType.LOWGRADE, blockType);
        this.midOreBlock = registerOreBlock(geoType, oreType, GradeType.MIDGRADE, blockType);
        this.highOreBlock = registerOreBlock(geoType, oreType, GradeType.HIGHGRADE, blockType);
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

    private RegistryObject<Block> registerOreBlock(GeologyType geoType, OreType oreType, GradeType gradeType, OreBlockType blockType) {
        switch (blockType) {
            case STONE:
                return ModBlocks.registerStoneGeoBlock(geoType, oreType, gradeType);
            case REGOLITH:
                return ModBlocks.registerRegolithGeoBlock(geoType, oreType, gradeType);
            case DETRITUS:
                return ModBlocks.registerDetritusBlock(geoType, oreType, gradeType);
            default:
                return null;
        }
    }

}
