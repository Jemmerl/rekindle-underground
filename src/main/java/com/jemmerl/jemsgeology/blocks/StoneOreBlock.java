package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class StoneOreBlock extends Block implements IOreBlock{

    private static final int HARDNESS_DEPTH_FACTOR = JemsGeoConfig.SERVER.hardnessDepthFactor.get() - 1;
    private static final boolean DET_SCALING = JemsGeoConfig.SERVER.detritusScaling.get();

    public static final EnumProperty<OreType> ORE_TYPE = EnumProperty.create("oretype", OreType.class);
    public static final EnumProperty<GradeType> GRADE_TYPE = EnumProperty.create("gradetype", GradeType.class);
    private final GeologyType geologyType;
    private final StoneGroupType stoneGroupType;

    public StoneOreBlock(Properties properties, GeologyType geologyType) {
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
    public float getExplosionResistance() {
        return (this.blastResistance * JemsGeoConfig.SERVER.stoneResistance.get());
    }

    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos) * JemsGeoConfig.SERVER.stoneHardness.get();
        if (f == -1.0F) {
            return 0.0F;
        }

        int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100; // Normal "cannot harvest" speed modifier
        if (!this.stoneGroupType.equals(StoneGroupType.DETRITUS) || DET_SCALING) {
            int y = pos.getY();
            if (y <= 50) { f *= (1 + HARDNESS_DEPTH_FACTOR * ((50f - y) / 50f)); } // Increases linearly starting at y = 50
        }
        return player.getDigSpeed(state, pos) / f / (float)i;
    }

    // Return ore state of block
    @Override
    public OreType getOreType(World world, BlockPos pos) {
        return world.getBlockState(pos).get(ORE_TYPE);
    }

    // Return grade state of block
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
