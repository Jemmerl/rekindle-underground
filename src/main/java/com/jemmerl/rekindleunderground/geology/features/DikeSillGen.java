package com.jemmerl.rekindleunderground.geology.features;

import com.jemmerl.rekindleunderground.geology.features.instances.DikeSillEntry;
import com.jemmerl.rekindleunderground.init.ModBlocks;
import com.jemmerl.rekindleunderground.util.lists.ModBlockLists;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.DikeSillNoise;
import net.minecraft.block.BlockState;

public class DikeSillGen {

    public static BlockState generate(int x, int y, int z, DikeSillEntry dikeSillEntry) {

        BlockState state = null;

        float dikeSillNoise = DikeSillNoise.getShiftedDikeNoise(x, y, z, 0, dikeSillEntry.getSeed(), 1f);

        if (dikeSillNoise > 0.6) {
            state = ModBlockLists.GEO_LIST.get(dikeSillEntry.getStone()).getStoneOreBlock().getDefaultState();
            //state = ModBlocks.RED_SANDSTONE_STONE.get().getDefaultState();
        }

        return state;
    }
}
