package com.jemmerl.rekindleunderground.world.feature;

import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredRegionNoise;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredStrataNoise;
import com.jemmerl.rekindleunderground.world.feature.stonegenutil.ChunkReader;
import com.jemmerl.rekindleunderground.world.feature.stonegenutil.StateMap;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class StoneGenFeature extends Feature<NoFeatureConfig> {

    public StoneGenFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private boolean setSeed = false;
    private long worldSeed;

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        if(!setSeed){
            worldSeed = reader.getSeed();
            ConfiguredRegionNoise.configNoise(worldSeed);
            ConfiguredStrataNoise.configNoise(worldSeed);
            setSeed = true;
        }

        ChunkReader chunkReader = new ChunkReader(reader, pos);
        StateMap chunkStateMap = new StateMap(chunkReader, pos);
        processChunk(reader, chunkReader, chunkStateMap, pos);

        return true;
    }


    private void processChunk(ISeedReader reader, ChunkReader chunkReader, StateMap stateMap, BlockPos pos) {
        IChunk chunk = reader.getChunk(pos);
        int posX, posZ, topY;
        BlockState original, replacing, replaced;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                posX = pos.getX() + x;
                posZ = pos.getZ() + z;
                mutable.setPos(posX, 0, posZ);

                topY = chunkReader.getMaxHeightVal(x, z);
                for (int y = 0; y < topY; y++) {
                    original = chunk.getBlockState(mutable);
                    replacing = stateMap.getState(x, y, z);
                    replaced = replaceBlock(original, replacing);

                    if (original != replaced && replaced != null) {
                        chunk.getSections()[y >> 4].setBlockState(x, y & 15, z, replaced, false);
                    }
                    mutable.move(Direction.UP);
                }
            }
        }
    }

    private BlockState replaceBlock(BlockState original, BlockState replacing) {
        if ((replacing != null) && (original.isIn(BlockTags.BASE_STONE_OVERWORLD) || original.isIn(Tags.Blocks.ORES))) {
            return replacing;
        } else {
            return original;
        }
    }
}






