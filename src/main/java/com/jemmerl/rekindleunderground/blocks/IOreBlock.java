package com.jemmerl.rekindleunderground.blocks;

import com.jemmerl.rekindleunderground.data.enums.GeologyType;
import com.jemmerl.rekindleunderground.data.enums.ore.GradeType;
import com.jemmerl.rekindleunderground.data.enums.ore.OreType;
import com.jemmerl.rekindleunderground.data.enums.StoneGroupType;
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
