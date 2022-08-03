package com.jemmerl.rekindleunderground.world.feature.orefeats;

import com.jemmerl.rekindleunderground.block.custom.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

// TODO test

public class OreTestFeat extends Feature<NoFeatureConfig> {

    public OreTestFeat(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        int maxRadius = rand.nextInt(20) + 15;
        OreType oreType = OreType.getRandomOreType(rand);
        System.out.println(oreType.getString());
        System.out.println(pos.getX() + ", " + pos.getZ());

        float densityPercent = rand.nextFloat() + 0.65f;

        int yHeight = rand.nextInt(45) + 10;

        int test_val = 0;

        float radius;
        for (int y = yHeight; y < (yHeight + 5); y++) {
            for (BlockPos areaPos : BlockPos.getAllInBoxMutable(pos.add(-maxRadius, 0, -maxRadius), pos.add(maxRadius, 0, maxRadius))) {
                radius = (float)(maxRadius);
                BlockPos setPos = new BlockPos(areaPos.getX(), y, areaPos.getZ());
                if (UtilMethods.getHypotenuse(areaPos.getX(), areaPos.getZ(), pos.getX(), pos.getZ()) <= radius) {
                    BlockState hostBlock = reader.getBlockState(setPos);

                    if ((hostBlock.getBlock() instanceof StoneOreBlock) && (rand.nextFloat() < densityPercent)) {
                        reader.setBlockState(setPos, hostBlock.with(StoneOreBlock.ORE_TYPE, oreType), 2);
                    }
                }


            }
        }
        System.out.println(test_val);

        for (int yPole = yHeight; yPole < 120; yPole++) {
            reader.setBlockState(new BlockPos(pos.getX(), yPole, pos.getZ()), Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
        }



        return true;
    }
}
