package com.jemmerl.jemsgeology.world.placements;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.*;

import java.util.Random;
import java.util.stream.Stream;

public class CenteredGenPlacer extends HeightmapBasedPlacement<NoPlacementConfig> {

    public CenteredGenPlacer(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, NoPlacementConfig config, BlockPos pos) {
        int i = 7 + pos.getX();
        int j = 7 + pos.getZ();
        int k = helper.func_242893_a(this.func_241858_a(config), i, j);
        return Stream.of(new BlockPos(i, k, j));
    }

    protected Heightmap.Type func_241858_a(NoPlacementConfig config) {
        return Heightmap.Type.OCEAN_FLOOR_WG;
    }

}
