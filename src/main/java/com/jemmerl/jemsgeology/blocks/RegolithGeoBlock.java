package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class RegolithGeoBlock extends BaseGeoBlock implements IGeoBlock {

    public RegolithGeoBlock(AbstractBlock.Properties properties, GeologyType geologyType) {
        super(properties, geologyType);
        this.setDefaultState(this.stateContainer.getBaseState().with(ORE_TYPE, OreType.NONE).with(GRADE_TYPE, GradeType.LOWGRADE));
    }

    @Override
    public float getExplosionResistance() {
        return (this.blastResistance * JemsGeoConfig.SERVER.stoneResistance.get());
    }

    // Regolith blocks experience a hardness modifier, but not a depth scaling modifier
    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos) * JemsGeoConfig.SERVER.stoneHardness.get();
        if (f == -1.0F) {
            return 0.0F;
        }

        int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100; // Normal "cannot harvest" speed modifier
        return player.getDigSpeed(state, pos) / f / (float)i;
    }


}
