package com.jemmerl.rekindleunderground.block.custom;

import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneGroupType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class StoneOreBlock extends Block {

    private static final int HARDNESS_DEPTH_FACTOR = RKUndergroundConfig.COMMON.hardnessDepthFactor.get() - 1;

    public static final EnumProperty<OreType> ORE_TYPE = EnumProperty.create("oretype", OreType.class);
    public static final EnumProperty<GradeType> GRADE_TYPE = EnumProperty.create("gradetype", GradeType.class);
    private final StoneType stoneType;
    private final StoneGroupType stoneGroupType;

    public StoneOreBlock(Properties properties, StoneType stoneType, StoneGroupType stoneGroupType) {
        super(properties);
        this.stoneType = stoneType;
        this.stoneGroupType = stoneGroupType;
        this.setDefaultState(this.stateContainer.getBaseState().with(ORE_TYPE, OreType.NONE).with(GRADE_TYPE, GradeType.LOWGRADE));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ORE_TYPE);
        builder.add(GRADE_TYPE);
        super.fillStateContainer(builder);
    }

    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos);
        int y = pos.getY();
        if (y <= 50) { f *= (1 + HARDNESS_DEPTH_FACTOR * ((50f - y) / 50f)); } // Increases linearly starting at y = 50
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100; // Normal "cannot harvest" speed modifier
            return player.getDigSpeed(state, pos) / f / (float)i;
        }
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
    public StoneType getStoneType() {
        return this.stoneType;
    }

    // Return stone group type of the block
    public StoneGroupType getStoneGroupType() {
        return this.stoneGroupType;
    }

}
