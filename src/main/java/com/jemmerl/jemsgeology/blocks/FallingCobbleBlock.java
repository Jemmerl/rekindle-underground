package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FallingCobbleBlock extends FallingBlock {

    private final GeologyType geologyType;
    private final StoneGroupType stoneGroupType;
    public FallingCobbleBlock(Properties properties, GeologyType geologyType) {
        super(properties);
        this.geologyType = geologyType;
        this.stoneGroupType = geologyType.getGroup();
    }

    // Return the stone type of the block
    public GeologyType getGeologyType() {
        return this.geologyType;
    }

    // Return stone group type of the block
    public StoneGroupType getStoneGroupType() {
        return this.stoneGroupType;
    }

//materialColor
    // TODO MAKE COLOR FOR EACH BLOCK, AS WELL AS FOR MAPS(?)
    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
        return -10724260;
    }

}
