package com.jemmerl.rekindleunderground.block.custom;

import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoneOreBlock extends Block {
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

/*
{
  "variants": {
    "oretype=none":    { "model": ":rekindleunderground:block/chalk_stone" },
    "oretype=test_ore_1":    { "model": "rekindleunderground:block/stoneore/chalk_stone/test_ore_1" },
    "oretype=test_ore_2":    { "model": "rekindleunderground:block/stoneore/chalk_stone/test_ore_2" },
    "oretype=test_ore_3":    { "model": "rekindleunderground:block/stoneore/chalk_stone/test_ore_3" },
    "oretype=test_ore_4":    { "model": "rekindleunderground:block/stoneore/chalk_stone/test_ore_4" }
  }
}
*/

}
