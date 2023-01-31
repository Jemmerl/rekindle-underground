package com.jemmerl.rekindleunderground.world.placements;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StoneGenPlacer extends SimplePlacement<NoPlacementConfig> {
    public StoneGenPlacer(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    // Gets a single spot for placement, the bottom chunk corner
    @Override
    protected Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos) {
        int i = 1;
        return IntStream.range(0, i).mapToObj((p_215060_2_) -> pos);
    }
}
