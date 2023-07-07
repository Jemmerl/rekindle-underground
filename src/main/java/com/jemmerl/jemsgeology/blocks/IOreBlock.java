package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IOreBlock {

    // Return ore state of block
    OreType getOreType(World world, BlockPos pos);

    // Return grade state of block
    GradeType getGradeType(World world, BlockPos pos);

    // Return the stone type of the block
    GeologyType getGeologyType();

    // Return stone group type of the block
    StoneGroupType getStoneGroupType();

}
