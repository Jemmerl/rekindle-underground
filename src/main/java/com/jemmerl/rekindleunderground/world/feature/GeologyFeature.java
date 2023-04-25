package com.jemmerl.rekindleunderground.world.feature;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.blocks.StoneOreBlock;
import com.jemmerl.rekindleunderground.init.NoiseInit;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.util.lists.ModBlockLists;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredBlobNoise;
import com.jemmerl.rekindleunderground.geology.ChunkReader;
import com.jemmerl.rekindleunderground.geology.StateMapBuilder;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GeologyFeature extends Feature<NoFeatureConfig> {

    public GeologyFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader seedReader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (!NoiseInit.configured) {
            NoiseInit.init(seedReader.getSeed());
        }

        ChunkReader chunkReader = new ChunkReader(seedReader, pos);
        StateMapBuilder stateMapBuilder = new StateMapBuilder(chunkReader, pos, rand);
        processChunk(seedReader, chunkReader, stateMapBuilder, pos);

        return true;
    }


    private void processChunk(ISeedReader reader, ChunkReader chunkReader, StateMapBuilder stateMap, BlockPos pos) {
        IChunk chunk = reader.getChunk(pos);
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int posX = pos.getX() + x;
                int posZ = pos.getZ() + z;
                mutablePos.setPos(posX, 0, posZ);

                int topY = chunkReader.getMaxHeightVal(x, z);

                for (int y = 0; y < topY; y++) {
                    mutablePos.setY(y);

                    BlockState stoneState = stateMap.getStoneState(x, y, z);
                    switch (UtilMethods.replaceableStatus(chunk.getBlockState(mutablePos))) {
                        case FAILED:
                        case OREBLOCK_STONE:
                        case OREBLOCK_DETRITUS:
                            break;
                        case VANILLA_STONE:
                            chunk.getSections()[y >> 4].setBlockState(x, y & 15, z, stoneState, false);
                            break;
                        case VANILLA_DETRITUS:
                            if (y <= (topY - getDepth(mutablePos.toImmutable()))) {
                                BlockState regolith = ModBlockLists.GEO_LIST.get(((StoneOreBlock) stoneState.getBlock()).getGeologyType())
                                        .getRegolithBlock().getDefaultState();

                                // Add ore properties from original stone
                                regolith = regolith.with(StoneOreBlock.GRADE_TYPE, stoneState.get(StoneOreBlock.GRADE_TYPE))
                                        .with(StoneOreBlock.ORE_TYPE, stoneState.get(StoneOreBlock.ORE_TYPE));
                                chunk.getSections()[y >> 4].setBlockState(x, y & 15, z, regolith, false);
                            }
                            break;
                        default:
                            RekindleUnderground.getInstance().LOGGER.warn("Unexpected block condition in Stone Generation replacement; contact mod dev!");
                    }
                }
            }
        }
    }

    private int getDepth(BlockPos pos) {
        return Math.round((Math.abs(ConfiguredBlobNoise.blobRadiusNoise((pos.getX() * 2), 0, (pos.getZ() * 2))) * 3) + 3);
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



