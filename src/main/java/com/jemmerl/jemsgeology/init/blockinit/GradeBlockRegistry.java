package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class GradeBlockRegistry {

    private final RegistryObject<Block> lowOreBlock;
    private final RegistryObject<Block> midOreBlock;
    private final RegistryObject<Block> highOreBlock;

    public GradeBlockRegistry(GeologyType geoType, OreType oreType) {
        this.lowOreBlock = null;
        this.midOreBlock = null;
        this.highOreBlock = null;
    }

}
