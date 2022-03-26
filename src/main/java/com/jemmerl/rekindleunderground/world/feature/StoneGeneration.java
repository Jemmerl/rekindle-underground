package com.jemmerl.rekindleunderground.world.feature;


import com.jemmerl.rekindleunderground.data.noise.OpenSimplex2;
import com.jemmerl.rekindleunderground.world.feature.stonegenutil.ChunkReader;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import static java.lang.Math.abs;

public class StoneGeneration extends Feature<NoFeatureConfig> {

    public StoneGeneration(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private static final int X_LENGTH = 512;
    private static final int Z_LENGTH = 2048;
    private static final int Y_HEIGHT = 256;
    private static final double STRATA_SIZE = 512;
    private static final double DIKE_SIZE = 64;

    private boolean setSeed = false;
    private long worldSeed = 0;
    OpenSimplex2 noise = new OpenSimplex2();

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        if(!setSeed){
            worldSeed = reader.getSeed();
            setSeed = true;
        }

        System.out.print(223322);

        ChunkReader chunkReader = new ChunkReader(reader, pos);
        processChunk(reader, chunkReader, pos);

        return true;
    }


    private void processChunk(ISeedReader reader, ChunkReader chunkReader, BlockPos pos) {
        IChunk chunk = reader.getChunk(pos);
        System.out.print(11155111);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int posX = pos.getX() + x;
                int posZ = pos.getZ() + z;
                mutable.setPos(posX, 0, posZ);

                int topY = chunkReader.maxHeights[x][z];
                for (int y = 0; y < topY; y++) {
                    BlockState original = chunk.getBlockState(mutable);
                    BlockState replaced = replaceBlock(original, mutable);

                    if (original != replaced && replaced != null) {
                        chunk.getSections()[y >> 4].setBlockState(x, y & 15, z, replaced, false);
                    }
                    mutable.move(Direction.UP);
                }
            }
        }
    }

    private BlockState replaceBlock(BlockState original, BlockPos pos) {
        if (original.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
            BlockState replacer = dikeGen(noise, worldSeed, pos);
            if (replacer == null) {
                replacer = strataGen(noise, worldSeed, pos);
            }
            return replacer ;
        } else {
            return original;
        }
    }

    public BlockState dikeGen(OpenSimplex2 noise, long seed, BlockPos pos){
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        double value = noise.noise3_ImproveXZ(seed, (x / (DIKE_SIZE)), (z / (DIKE_SIZE)), (y / (DIKE_SIZE*8)));

        if (abs(value) >= 0.8) {
            return Blocks.SPONGE.getDefaultState();
        } else {
            return null;
        }
    }

    public BlockState strataGen(OpenSimplex2 noise, long seed, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        double value = noise.noise3_ImproveXZ(seed, (x / (STRATA_SIZE * 2)), (z / (STRATA_SIZE * 2)), (y / (STRATA_SIZE * 0.4)));

        if (value <= -0.8) {
            return Blocks.NETHERITE_BLOCK.getDefaultState();
        } else if (value <= -0.6) {
            return Blocks.GOLD_BLOCK.getDefaultState();
        } else if (value <= -0.4) {
            return Blocks.LAPIS_BLOCK.getDefaultState();
        } else if (value <= -0.2) {
            return Blocks.COAL_BLOCK.getDefaultState();
        } else if (value <= 0.0) {
            return Blocks.IRON_BLOCK.getDefaultState();
        } else if (value <= 0.2) {
            return Blocks.REDSTONE_BLOCK.getDefaultState();
        } else if (value <= 0.4) {
            return Blocks.EMERALD_BLOCK.getDefaultState();
        } else if (value <= 0.6) {
            return Blocks.DIAMOND_BLOCK.getDefaultState();
        } else if (value <= 0.8) {
            return Blocks.STONE.getDefaultState();
        } else {
            return Blocks.DIRT.getDefaultState();
        }
    }
}






