package com.jemmerl.rekindleunderground.world.feature.stonegenutil;

import com.jemmerl.rekindleunderground.util.noise.ConfiguredNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

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
        //gen strata ore
        //gen intrusive rock
        //gen intrusive rock ore
    }

    /////////////////////////////////////////////////
    /////             Map Generators            /////
    /////////////////////////////////////////////////

    public void PopulateStrata() {
        BlockState state;
        int posX, posZ;
        int topY = chunkReader.getMaxHeight();
        float strataNoise;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < topY; y++) {
                    posX = this.blockPos.getX() + x;
                    posZ = this.blockPos.getZ() + z;
                    strataNoise = ConfiguredNoise.stoneStrataNoise(posX, y, posZ);

                    // Temporary
                    if (strataNoise <= -0.8) {
                        state = Blocks.NETHERITE_BLOCK.getDefaultState();
                    } else if (strataNoise <= -0.6) {
                        state = Blocks.GOLD_BLOCK.getDefaultState();
                    } else if (strataNoise <= -0.4) {
                        state = Blocks.LAPIS_BLOCK.getDefaultState();
                    } else if (strataNoise <= -0.2) {
                        state = Blocks.COAL_BLOCK.getDefaultState();
                    } else if (strataNoise <= 0.0) {
                        state = Blocks.IRON_BLOCK.getDefaultState();
                    } else if (strataNoise <= 0.2) {
                        state = Blocks.REDSTONE_BLOCK.getDefaultState();
                    } else if (strataNoise <= 0.4) {
                        state = Blocks.EMERALD_BLOCK.getDefaultState();
                    } else if (strataNoise <= 0.6) {
                        state = Blocks.DIAMOND_BLOCK.getDefaultState();
                    } else if (strataNoise <= 0.8) {
                        state = Blocks.STONE.getDefaultState();
                    } else {
                        state = Blocks.DIRT.getDefaultState();
                    }

                    stateMap[x][y][z] = state;
                }
            }
        }
    }



}
