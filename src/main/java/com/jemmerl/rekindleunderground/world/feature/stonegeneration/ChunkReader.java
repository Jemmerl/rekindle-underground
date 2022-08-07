package com.jemmerl.rekindleunderground.world.feature.stonegeneration;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.Heightmap;

public class ChunkReader {

    private ISeedReader world;
    private BlockPos pos;
    private int[][] maxHeights;
    private int maxHeight;

    public ChunkReader(ISeedReader world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.maxHeights = new int[17][17];
        this.maxHeight = fillMaxHeights();
    }

    public ISeedReader getSeedReader() {
        return this.world;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public int[][] getMaxHeightArray() {
        return this.maxHeights;
    }

    public int getMaxHeightVal(int x, int z) {
        return this.maxHeights[x][z];
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
