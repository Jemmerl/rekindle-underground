package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FallingGeoBlock extends FallingBlock implements IGeoBlock {

    private final GeologyType geologyType;
    private final StoneGroupType stoneGroupType;

    public FallingGeoBlock(Properties properties, GeologyType geologyType) {
        super(properties);
        this.geologyType = geologyType;
        this.stoneGroupType = geologyType.getGroup();
        this.setDefaultState(this.stateContainer.getBaseState().with(ORE_TYPE, OreType.NONE).with(GRADE_TYPE, GradeType.LOWGRADE));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ORE_TYPE);
        builder.add(GRADE_TYPE);
        super.fillStateContainer(builder);
    }

    @Override
    public OreType getOreType(World world, BlockPos pos) {
        return world.getBlockState(pos).get(ORE_TYPE);
    }

    @Override
    public GradeType getGradeType(World world, BlockPos pos) {
        return world.getBlockState(pos).get(GRADE_TYPE);
    }

    // Return the stone type of the block
    @Override
    public GeologyType getGeologyType() {
        return this.geologyType;
    }

    // Return stone group type of the block
    @Override
    public StoneGroupType getStoneGroupType() {
        return this.stoneGroupType;
    }
}