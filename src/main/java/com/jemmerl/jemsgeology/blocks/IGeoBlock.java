package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGeoBlock {

    // Return ore state of block
    OreType getOreType();

    // Return grade state of block
    GradeType getGradeType();

    // Return the stone type of the block
    GeologyType getGeologyType();

    // Return stone group type of the block
    StoneGroupType getStoneGroupType();

}
