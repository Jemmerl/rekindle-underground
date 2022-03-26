package com.jemmerl.rekindleunderground.world.feature.stonegenutil;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.Heightmap;

public class ChunkReader {

    public ISeedReader world;
    public BlockPos pos;
    public int[][] maxHeights;
    public int maxHeight;

    public ChunkReader(ISeedReader world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.maxHeights = new int[17][17];
        maxHeight = fillMaxHeights();
    }

    private int fillMaxHeights() {
        int maxHeight = 0;
        for (int x = 0; x < 17; x++) {
            for (int z = 0; z < 17; z++) {
                int posX = pos.getX() + x;
                int posZ = pos.getZ() + z;
                int height = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, posX, posZ);
                if (height > maxHeight) maxHeight = height;
                maxHeights[x][z] = height;
            }
        }
        return maxHeight;
    }



}
