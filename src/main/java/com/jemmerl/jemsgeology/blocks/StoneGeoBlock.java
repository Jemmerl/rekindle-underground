package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class StoneGeoBlock extends BaseGeoBlock implements IGeoBlock {

    public StoneGeoBlock(Properties properties, GeologyType geologyType, OreType oreType, GradeType gradeType) {
        super(properties, geologyType, oreType, gradeType);
    }

//    @Override
//    public float getExplosionResistance() {
//        return (this.blastResistance * JemsGeoConfig.SERVER.stoneResistance.get());
//    }

    // Stone blocks experience both a hardness modifier and depth scaling modifier
    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos) * JemsGeoConfig.SERVER.stoneHardness.get();
        if (f == -1.0F) {
            return 0.0F;
        }

        int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100; // Normal "cannot harvest" speed modifier
        if (!this.getStoneGroupType().equals(StoneGroupType.DETRITUS)) {
            int y = pos.getY();
            if (y <= 50) { f *= (1 + (JemsGeoConfig.SERVER.hardnessDepthFactor.get() - 1) * ((50f - y) / 50f)); } // Increases linearly starting at y = 50
        }
        return player.getDigSpeed(state, pos) / f / (float)i;
    }
}
