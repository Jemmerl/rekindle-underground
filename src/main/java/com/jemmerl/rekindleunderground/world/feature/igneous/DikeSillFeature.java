package com.jemmerl.rekindleunderground.world.feature.igneous;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class DikeSillFeature extends Feature<NoFeatureConfig> {

    public DikeSillFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {





        return false;




    }

    // Returns a stone's contact metamorphism, if it has one
    private static BlockState ContactMeta() {
        return null;
    }
}
