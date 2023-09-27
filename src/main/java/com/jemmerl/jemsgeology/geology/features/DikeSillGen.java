package com.jemmerl.jemsgeology.geology.features;

import com.jemmerl.jemsgeology.geology.features.instances.DikeSillEntry;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.util.lists.ModBlockLists;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.DikeSillNoise;
import net.minecraft.block.BlockState;

public class DikeSillGen {

    public static BlockState generate(int x, int y, int z, DikeSillEntry dikeSillEntry) {

        BlockState state = null;

        float dikeSillNoise = DikeSillNoise.getShiftedDikeNoise(x, y, z, 0, dikeSillEntry.getSeed(), 1f);

        if (dikeSillNoise > 0.6) {
            state = ModBlocks.GEOBLOCKS.get(dikeSillEntry.getStone()).getBaseState();
        }

        return state;
    }
}
