package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import net.minecraft.util.ResourceLocation;

public interface IGeoBlock {

    // Return ore state of block
    OreType getOreType();

    // Return grade state of block
    GradeType getGradeType();

    // Return the stone type of the block
    GeologyType getGeologyType();

    // Return stone group type of the block
    StoneGroupType getStoneGroupType();

    // All GeoBlocks are blocks, but I cannot call a block method from the interface without including here
    ResourceLocation getRegistryName();
}
