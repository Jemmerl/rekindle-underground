package com.jemmerl.rekindleunderground.geology.features;

import com.jemmerl.rekindleunderground.geology.features.instances.DikeSillEntry;
import com.jemmerl.rekindleunderground.init.featureinit.FeatureRegistrar;
import com.jemmerl.rekindleunderground.util.lists.ModBlockLists;
import com.jemmerl.rekindleunderground.util.noise.FastNoiseLite;
import net.minecraft.block.BlockState;

public class DikeSillGen {

    public static BlockState generate(int x, int y, int z) {
        BlockState state = null;

        FastNoiseLite noise = new FastNoiseLite(100);

        for (final DikeSillEntry dikeSillEntry : FeatureRegistrar.getDikeSillFeatures().values()) {
            noise.SetSeed(10243 * dikeSillEntry.getInteg());
            if (noise.GetNoise(x * 2, y * 2, z * 2) > 0.6) {
                state = ModBlockLists.GEO_LIST.get(dikeSillEntry.getStone()).getStoneOreBlock().getDefaultState();
            }
        }

        return state;
    }

}
