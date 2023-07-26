package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.entities.FallingCobbleEntity;
import com.jemmerl.jemsgeology.init.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

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

    // Edit of "callFallThrough" that returns blocks cobbles can fall through
    public static boolean canFallThrough(BlockState state) {
        Material material = state.getMaterial();
        return state.isIn(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable() || state.isIn(ModTags.Blocks.COBBLES_CAN_BREAK);
    }

     // Edit of "callFallThrough" that returns blocks cobbles can smash through, a.k.a. break after already falling
    public static boolean canSmashThrough(BlockState state) {
        return canFallThrough(state) || state.isIn(ModTags.Blocks.COBBLES_CAN_SMASH);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        boolean canFallThru = canFallThrough(worldIn.getBlockState(pos.down()));
        if ((worldIn.isAirBlock(pos.down()) || canFallThru) && pos.getY() >= 0) {
            if (canFallThru) {
                worldIn.destroyBlock(pos.down(), true);
            }
            FallingCobbleEntity fallingCobbleEntity = new FallingCobbleEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, worldIn.getBlockState(pos));
            this.onStartFalling(fallingCobbleEntity);
            worldIn.addEntity(fallingCobbleEntity);
        }
    }

    //materialColor
    // TODO MAKE COLOR FOR EACH BLOCK, AS WELL AS FOR MAPS(?)
    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.getMaterialColor(reader, pos).colorValue;
        //return -10724260;
    }

}
