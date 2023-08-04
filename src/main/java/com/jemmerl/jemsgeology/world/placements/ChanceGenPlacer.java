package com.jemmerl.jemsgeology.world.placements;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.stream.Stream;

public class ChanceGenPlacer extends Placement<ChanceConfig> {

    public ChanceGenPlacer(Codec<ChanceConfig> codec) {
        super(codec);
    }

    // Gets a single, random centered position for placement with a one in CHANCE percent of success
    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, ChanceConfig config, BlockPos pos) {
        if (rand.nextInt(config.chance) == 0) {
            int i = 7 + pos.getX();
            int j = 7 + pos.getZ();
            int k = 1;
            return Stream.of(new BlockPos(i, k, j));
        }
        return Stream.empty();
    }


}
