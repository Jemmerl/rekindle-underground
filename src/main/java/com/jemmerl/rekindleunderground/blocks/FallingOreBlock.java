package com.jemmerl.rekindleunderground.blocks;

import com.jemmerl.rekindleunderground.data.types.GeologyType;
import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneGroupType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FallingOreBlock extends FallingBlock {

    public static final EnumProperty<OreType> ORE_TYPE = EnumProperty.create("oretype", OreType.class);
    public static final EnumProperty<GradeType> GRADE_TYPE = EnumProperty.create("gradetype", GradeType.class);
    private final GeologyType geologyType;
    private final StoneGroupType stoneGroupType;

    public FallingOreBlock(Properties properties, GeologyType geologyType) {
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

    // Return ore state of block
    public static OreType getOreType(World world, BlockPos pos) {
        return world.getBlockState(pos).get(ORE_TYPE);
    }

    // Return grade state of block
    public static GradeType getGradeType(World world, BlockPos pos) {
        return world.getBlockState(pos).get(GRADE_TYPE);
    }

    // Return the stone type of the block
    public GeologyType getGeologyType() {
        return this.geologyType;
    }

    // Return stone group type of the block
    public StoneGroupType getStoneGroupType() {
        return this.stoneGroupType;
    }
}