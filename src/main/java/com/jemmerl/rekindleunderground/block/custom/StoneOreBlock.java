package com.jemmerl.rekindleunderground.block.custom;

import com.jemmerl.rekindleunderground.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class StoneOreBlock extends Block {

    private static final int HARDNESS_DEPTH_FACTOR = RKUndergroundConfig.COMMON.hardnessDepthFactor.get() - 1;

    public static final EnumProperty<OreType> ORE_TYPE = EnumProperty.create("oretype", OreType.class);
    private StoneType stoneType;

    public StoneOreBlock(Properties properties, StoneType stoneType) {
        super(properties);
        this.stoneType = stoneType;
        this.setDefaultState(this.stateContainer.getBaseState().with(ORE_TYPE, OreType.NONE));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ORE_TYPE);
        super.fillStateContainer(builder);
    }

    // Return ore state of block
    public static OreType getOreType(World world, BlockPos pos) {
        return world.getBlockState(pos).get(ORE_TYPE);
    }

    // Return stone type of block
    public final StoneType getStoneType() {
        return this.stoneType;
    }

    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos);
        int y = pos.getY();
        if (y <= 50) { f *= (1 + HARDNESS_DEPTH_FACTOR * ((50f - y) / 50f)); }
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100;
            return player.getDigSpeed(state, pos) / f / (float)i;
        }

    }


}
