package com.jemmerl.rekindleunderground.world.placements;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.SimplePlacement;

import java.util.Random;
import java.util.stream.Stream;

public class ConsistentGenPlacer extends SimplePlacement<NoPlacementConfig> {

    public ConsistentGenPlacer(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    // Gets a single, random position for placement
    @Override
    protected Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos) {
        int i = random.nextInt(16) + pos.getX();
        int j = random.nextInt(16) + pos.getZ();
        int k = 1;
        return Stream.of(new BlockPos(i, k, j));
    }
}
