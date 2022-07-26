package com.jemmerl.rekindleunderground.world.feature.stonegenutil;

import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredRegionNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import static com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredStrataNoise.getStoneStrataBlock;

public class StateMap {

    private ChunkReader chunkReader;
    private BlockPos blockPos;
    private double[][][] valMap; // may be unnecessary
    private BlockState[][][] stateMap;

    public StateMap(ChunkReader reader, BlockPos pos) {
        this.chunkReader = reader;
        this.blockPos = pos;
        this.stateMap = new BlockState[16][chunkReader.getMaxHeight()][16];
        generateStateMap();
    }

    public BlockState[][][] getStateMap() {
        return this.stateMap;
    }

    public BlockState getState(int x, int y, int z) {
        return this.stateMap[x][y][z];
    }

    private void generateStateMap() {
        PopulateStrata();
        //PopulateIgneous();
        // generate ores
    }

    /////////////////////////////////////////////////
    /////             Map Generators            /////
    /////////////////////////////////////////////////

    // Fill the chunk state map with generated stones
    public void PopulateStrata() {
        int posX, posZ;
        int topY = chunkReader.getMaxHeight();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < topY; y++) {
                    posX = this.blockPos.getX() + x;
                    posZ = this.blockPos.getZ() + z;

                    stateMap[x][y][z] = getStoneStrataBlock(posX, y, posZ);
                }
            }
        }
    }

    // Replace stones in the current state map with generated igneous features
    public void PopulateIgneous() {

    }

    public void PopulateOres() {

    }



}
