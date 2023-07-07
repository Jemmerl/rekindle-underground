package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FallingOreBlock extends FallingBlock implements IOreBlock{

    private static final int HARDNESS_DEPTH_FACTOR = JemsGeoConfig.COMMON.hardnessDepthFactor.get() - 1;
    private static final boolean DET_SCALING = JemsGeoConfig.COMMON.detritusScaling.get();

    private final GeologyType geologyType;
    private final StoneGroupType stoneGroupType;

    public FallingOreBlock(Properties properties, GeologyType geologyType) {
        super(properties);
        this.geologyType = geologyType;
        this.stoneGroupType = geologyType.getGroup();
        this.setDefaultState(this.stateContainer.getBaseState().with(StoneOreBlock.ORE_TYPE, OreType.NONE).with(StoneOreBlock.GRADE_TYPE, GradeType.LOWGRADE));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(StoneOreBlock.ORE_TYPE);
        builder.add(StoneOreBlock.GRADE_TYPE);
        super.fillStateContainer(builder);
    }

    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos);
        if (f == -1.0F) {
            return 0.0F;
        }

        int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100; // Normal "cannot harvest" speed modifier
        if (DET_SCALING) {
            int y = pos.getY();
            if (y <= 50) { f *= (1 + HARDNESS_DEPTH_FACTOR * ((50f - y) / 50f)); } // Increases linearly starting at y = 50
        }
        return player.getDigSpeed(state, pos) / f / (float)i;
    }


    @Override
    public OreType getOreType(World world, BlockPos pos) {
        return world.getBlockState(pos).get(StoneOreBlock.ORE_TYPE);
    }

    @Override
    public GradeType getGradeType(World world, BlockPos pos) {
        return world.getBlockState(pos).get(StoneOreBlock.GRADE_TYPE);
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