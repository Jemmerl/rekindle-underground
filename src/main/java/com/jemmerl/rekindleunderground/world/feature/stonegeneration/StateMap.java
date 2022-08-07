package com.jemmerl.rekindleunderground.world.feature.stonegeneration;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.deposit.DepositRegistrar;
import com.jemmerl.rekindleunderground.deposit.IDeposit;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Random;

import static com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredStrataNoise.getStoneStrataBlock;

public class StateMap {

    private ChunkReader chunkReader;
    private BlockPos blockPos;
    private Random rand;
    private double[][][] valMap; // may be unnecessary
    private BlockState[][][] stateMap;

    public StateMap(ChunkReader reader, BlockPos pos, Random rand) {
        this.chunkReader = reader;
        this.blockPos = pos;
        this.rand = rand;
        this.stateMap = new BlockState[16][this.chunkReader.getMaxHeight()][16];
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
        PopulateOres();
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

                    this.stateMap[x][y][z] = getStoneStrataBlock(posX, y, posZ);
                }
            }
        }
    }

    // Replace stones in the current state map with generated igneous features
    // Will be used for less technical generation, such as dikes or LIPs
    public void PopulateIgneous() {

    }

    // Populate ore deposits
    public void PopulateOres() {
        System.out.println(DepositRegistrar.getDeposits().size());
        // Generates the ore deposit with a one out of the deposit's weight chance
        for (IDeposit deposit : new HashSet<>(DepositRegistrar.getDeposits())) {
            if (this.rand.nextInt(deposit.getWeight()) == 0) {
                // Tries to update the stateMap with the generating feature
                if (!deposit.generate(this.chunkReader.getSeedReader(), this.rand, this.blockPos, this.stateMap)) {
                    RekindleUnderground.getInstance().LOGGER.warn("Failed to generate deposit at {}", this.blockPos.toString());
                }
            }
        }



    }



}
