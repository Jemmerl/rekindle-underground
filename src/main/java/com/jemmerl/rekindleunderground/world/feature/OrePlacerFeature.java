package com.jemmerl.rekindleunderground.world.feature;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.blocks.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.init.depositinit.DepositRegistrar;
import com.jemmerl.rekindleunderground.geology.deposits.DepositUtil;
import com.jemmerl.rekindleunderground.geology.deposits.instances.PlacerDeposit;
import com.jemmerl.rekindleunderground.init.NoiseInit;
import com.jemmerl.rekindleunderground.init.RKUndergroundConfig;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredBlobNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.FluidTags;
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

        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(reader.getSeed());
        }

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
                continue;
            }

            // Get the weighted random grade of the ore deposit
            GradeType grade = placerDeposit.getGrades().nextElt();

            float densityPercent = ((rand.nextInt(placerDeposit.getMaxDensity() -
                    placerDeposit.getMinDensity()) + placerDeposit.getMinDensity()) / 100f);

            // Get a uniformly distributed density value for the deposit within the min and max density range
            int avgDepositRadius = placerDeposit.getAvgRadius();
            int variance = (avgDepositRadius / 3) + 1;

            // Get the random actual center for the deposit
            BlockPos centerPos = new BlockPos((pos.getX()+rand.nextInt(8)-3), pos.getY(), (pos.getZ()+rand.nextInt(8)-3));

            // Debug
            if (RKUndergroundConfig.COMMON.debug_placer_deposits.get()) {
                RekindleUnderground.getInstance().LOGGER.info("Attempting to generate placer {} at {} with properties: Density {}, Grade {}, and AvgRadius {}",
                        placerDeposit.getName(), centerPos, densityPercent, grade, avgDepositRadius);
            }

            for (int y = -3; y < 3; y++) {
                for (BlockPos areaPos : BlockPos.getAllInBoxMutable(centerPos.add(-avgDepositRadius-variance, y, -avgDepositRadius-variance),
                        centerPos.add(avgDepositRadius+variance, y, avgDepositRadius+variance)))
                {

                    // Check if the deposit must be underwater; if so and the block isn't, skip it
                    if (placerDeposit.isSubmerged() && !isUnderWater(reader, areaPos)) {
                        continue;
                    }

                    float radius = (ConfiguredBlobNoise.blobRadiusNoise((areaPos.getX() * 5), (areaPos.getY() * 6), (areaPos.getZ() * 5))
                            * variance) + avgDepositRadius;
                    //System.out.println(radius);

                    // Generate the ore block if rolls a success against the density percent and within the random radius
                    if ((rand.nextFloat() < densityPercent) &&
                            (UtilMethods.getHypotenuse(areaPos.getX(), areaPos.getZ(), centerPos.getX(), centerPos.getZ()) <= radius)) {

                        BlockState hostState = UtilMethods.convertVanillaToDetritus(reader.getBlockState(areaPos));
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
            }

            // Debug
            if (RKUndergroundConfig.COMMON.debug_placer_deposits.get()) {
                // Original placement center
                for (int i = 0; i<4; i++) {
                    reader.setBlockState(pos.up(i), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
                }

                // Shifted placement center
                for (int i = 0; i<8; i++) {
                    reader.setBlockState(centerPos.up(i), Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
                }
            }

        }

        return true;
    }

    // Checks if the block is underwater (to a depth of below 2 non-water blocks)
    private boolean isUnderWater(ISeedReader reader, BlockPos pos) {
        for (int i = 1; i < 4; i++) {
            BlockState state = reader.getBlockState(pos.up(i));
            if (state.getFluidState().isTagged(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

}