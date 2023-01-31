package com.jemmerl.rekindleunderground.world.feature.ores;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.deposit.DepositRegistrar;
import com.jemmerl.rekindleunderground.deposit.IDeposit;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class OrePlacerFeature extends Feature<NoFeatureConfig>{
    public OrePlacerFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        final double PLACER_CHANCE = RKUndergroundConfig.COMMON.placerChance.get();

//        System.out.println("attempted placer at");
//        System.out.println(pos);

        if (rand.nextFloat() > PLACER_CHANCE) {
            return false;
        }


        for (IDeposit placerDeposit : DepositRegistrar.getPlacerDeposits().values()) {
            //System.out.println("trying to placer at");
            //System.out.println(pos);
            // get deposit properties


            // Check for valid biome placement. If not in a valid biome, cancel generation
            if (!placerDeposit.getBiomes().contains(reader.getBiome(pos).getCategory())) {
                // Debug
                if (RKUndergroundConfig.COMMON.debug.get()) {
                    RekindleUnderground.getInstance().LOGGER.info("Invalid biome for layer deposit at {}, failed to generate.", pos);
                }
                //System.out.println("skipped placer: bad biome");
                continue;
            }

            //System.out.println("tried to place blocks");

            reader.setBlockState(pos, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
            reader.setBlockState(pos.up(), Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
            reader.setBlockState(pos.up(2), Blocks.DIAMOND_BLOCK.getDefaultState(), 2);


        }


        //return false;
        return true;
    }

}