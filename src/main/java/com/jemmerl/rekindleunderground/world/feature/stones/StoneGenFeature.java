package com.jemmerl.rekindleunderground.world.feature.stones;

import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredRegionNoise;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredStrataNoise;
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
import net.minecraftforge.common.Tags;

import java.util.Random;

public class StoneGenFeature extends Feature<NoFeatureConfig> {

    public StoneGenFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private static boolean setSeed = false;
    private static long worldSeed;

    @Override
    public boolean generate(ISeedReader seedReader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (!setSeed) {
            worldSeed = seedReader.getSeed();
            ConfiguredRegionNoise.configNoise(worldSeed);
            ConfiguredStrataNoise.configNoise(worldSeed);
            setSeed = true;
        }

        ChunkReader chunkReader = new ChunkReader(seedReader, pos);
        StateMap chunkStateMap = new StateMap(chunkReader, pos, rand);
        processChunk(seedReader, chunkReader, chunkStateMap, pos);

        return true;
    }


    private void processChunk(ISeedReader reader, ChunkReader chunkReader, StateMap stateMap, BlockPos pos) {
        IChunk chunk = reader.getChunk(pos);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int posX = pos.getX() + x;
                int posZ = pos.getZ() + z;
                mutable.setPos(posX, 0, posZ);

                int topY = chunkReader.getMaxHeightVal(x, z);
                for (int y = 0; y < topY; y++) {
                    BlockState original = chunk.getBlockState(mutable); // Block that generated in vanilla
                    BlockState replacing = stateMap.getStoneState(x, y, z); // Mod stone block attempting to generate
                    //replaced = replaceBlock(original, replacing); // The method returns the block that *will* be placed

                    if (replaceStone(original)) {
                        chunk.getSections()[y >> 4].setBlockState(x, y & 15, z, replacing, false);
                    }

                    mutable.move(Direction.UP);
                }
            }
        }
    }

    private BlockState replaceBlock(BlockState original, BlockState replacing) {
        if ((replacing != null) &&
                (original.isIn(BlockTags.BASE_STONE_OVERWORLD) || original.isIn(Tags.Blocks.ORES))
                || original.getBlock().equals(Blocks.SANDSTONE) || original.getBlock().equals(Blocks.RED_SANDSTONE)) {
            return replacing;
        } else {
            return original;
        }
    }

    // Check if the block being replaced is a valid for OreBlock replacement
    private Boolean replaceStone(BlockState original) {
        return (original.isIn(BlockTags.BASE_STONE_OVERWORLD) || original.isIn(Tags.Blocks.ORES)
                || original.getBlock().equals(Blocks.SANDSTONE) || original.getBlock().equals(Blocks.RED_SANDSTONE));
    }

}


/*
Boolean isDet;
                topY = chunkReader.getMaxHeightVal(x, z);
                for (int y = 0; y < topY; y++) {
                    original = chunk.getBlockState(mutable);
                    isDet = original.getBlock().isIn(ModTags.Blocks.DETRITUS); // Check if valid detritus

                    if (!isDet) {
                        replacing = stateMap.getStoneState(x, y, z);
                        // Check if the pulled blockstate is null or if the block being replaced is invalid
                        // If either is true, then skip this position
                        if ((replacing == null) || !replaceStone(original)) {
                            continue;
                        }
                    } else {
                        BlockState stateHolder = stateMap.getDetritusState(x, y, z);
                        // Same as above, except there is no need to check if the block is valid
                        // Since it is already confirmed to be a detritus block, placement is valid
                        if (stateHolder == null) {
                            continue;
                        }
                        // Pull the stored state properties and put them into the appropriate detritus
                        replacing = StoneType.convertToDetritus(original)
                                .with(StoneOreBlock.ORE_TYPE, stateHolder.get(StoneOreBlock.ORE_TYPE))
                                .with(StoneOreBlock.GRADE_TYPE, stateHolder.get(StoneOreBlock.GRADE_TYPE));
                    }
 */



