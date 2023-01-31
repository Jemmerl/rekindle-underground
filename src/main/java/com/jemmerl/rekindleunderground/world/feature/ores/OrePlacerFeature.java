package com.jemmerl.rekindleunderground.world.feature.ores;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.blocks.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.deposit.DepositRegistrar;
import com.jemmerl.rekindleunderground.deposit.DepositUtil;
import com.jemmerl.rekindleunderground.deposit.generators.PlacerDeposit;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredBlobNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
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

        if (rand.nextFloat() > PLACER_CHANCE) {
            return false;
        }

        for (PlacerDeposit placerDeposit : DepositRegistrar.getPlacerDeposits().values()) {

            // Check against the weight; if fails, skip deposit
            if (rand.nextInt(placerDeposit.getWeight()) != 0) {
                continue;
            }

            // Check for valid biome placement; if not in a valid biome, skip deposit
            if (!placerDeposit.getBiomes().contains(reader.getBiome(pos).getCategory())) {
                // Debug
                if (RKUndergroundConfig.COMMON.debug.get()) {
                    RekindleUnderground.getInstance().LOGGER.info("Invalid biome for layer deposit at {}, failed to generate.", pos);
                }
                //System.out.println("skipped placer: bad biome");
                continue;
            }

            System.out.println("Attempting to place placer at");
            System.out.println(pos);

            // Get the weighted random grade of the ore deposit
            GradeType grade = placerDeposit.getGrades().nextElt();

            float densityPercent = ((rand.nextInt(placerDeposit.getMaxDensity() -
                    placerDeposit.getMinDensity()) + placerDeposit.getMinDensity()) / 100f);


            // Get a uniformly distributed density value for the deposit within the min and max density range
            int avgDepositRadius = placerDeposit.getAvgRadius();
            int variance = (avgDepositRadius / 4) + 1;

            // Get the random actual center for the deposit
            System.out.println(pos);
            BlockPos centerPos = new BlockPos((pos.getX()+rand.nextInt(8)-3), pos.getY(), (pos.getZ()+rand.nextInt(8)-3));
            System.out.println(centerPos);
            System.out.println(avgDepositRadius);
            System.out.println(variance);

            //(BlockPos areaPos : BlockPos.getAllInBoxMutable(
            //                    new BlockPos((centerPos.getX() - avgDepositRadius), centerPos.getY(), (centerPos.getZ() - avgDepositRadius)),
            //                    new BlockPos((centerPos.getX() + avgDepositRadius), centerPos.getY(), (centerPos.getZ() + avgDepositRadius))))

            // TODO CURRENTLY DOES ONE LAYER ONLY
            for (BlockPos areaPos : BlockPos.getAllInBoxMutable(centerPos.add(-avgDepositRadius-variance, 0, -avgDepositRadius-variance),
                    centerPos.add(avgDepositRadius+variance, 0, avgDepositRadius+variance)))
            {


                float radius = (ConfiguredBlobNoise.blobRadiusNoise((areaPos.getX() * 5), (centerPos.getY() * 6), (areaPos.getZ() * 5))
                    * variance) + avgDepositRadius;

                // Generate the ore block if rolls a success against the density percent and within the random radius
                if ((rand.nextFloat() < densityPercent) &&
                        (UtilMethods.getHypotenuse(areaPos.getX(), areaPos.getZ(), centerPos.getX(), centerPos.getZ()) <= radius)) {

                    BlockState hostState = UtilMethods.convertToDetritus(reader.getBlockState(areaPos));
                    if (DepositUtil.isValidStone(hostState.getBlock(), placerDeposit.getValid())) {
                        // Check if the block already has an ore in it; if so, roll to replace
                        if (hostState.hasProperty(StoneOreBlock.ORE_TYPE) && (hostState.get(StoneOreBlock.ORE_TYPE) != OreType.NONE)) {
                            // TODO Currently set to 40% chance to NOT replace
                            if (rand.nextFloat() > 0.60f) { continue; }
                        }

                        reader.setBlockState(areaPos,hostState
                                .with(StoneOreBlock.ORE_TYPE, placerDeposit.getOres().nextElt())
                                .with(StoneOreBlock.GRADE_TYPE, grade), 2);

                    }

                }
            }

            //System.out.println("tried to place blocks");

            for (int i = 0; i<8; i++) {
                reader.setBlockState(centerPos.up(i), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
            }

            for (int i = 0; i<4; i++) {
                reader.setBlockState(pos.up(i), Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
            }

        }


        //return false;
        return true;
    }

}