package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.blocks.StoneGeoBlock;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.depositinit.DepositRegistrar;
import com.jemmerl.jemsgeology.geology.deposits.DepositUtil;
import com.jemmerl.jemsgeology.geology.deposits.instances.PlacerDeposit;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
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

        final double PLACER_CHANCE = JemsGeoConfig.SERVER.placerChance.get();

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
            if (JemsGeoConfig.SERVER.debug_placer_deposits.get()) {
                JemsGeology.getInstance().LOGGER.info("Attempting to generate placer {} at {} with properties: Density {}, Grade {}, and AvgRadius {}",
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

                    float radius = (BlobNoise.blobWarpRadiusNoise((areaPos.getX() * 5), (areaPos.getY() * 6), (areaPos.getZ() * 5))
                            * variance) + avgDepositRadius;
                    //System.out.println(radius);

                    // Generate the ore block if rolls a success against the density percent and within the random radius
                    if ((rand.nextFloat() < densityPercent) &&
                            (UtilMethods.getDistance2D(areaPos.getX(), areaPos.getZ(), centerPos.getX(), centerPos.getZ()) <= radius)) {

                        BlockState hostState = UtilMethods.convertVanillaToDetritus(reader.getBlockState(areaPos));
                        if (DepositUtil.isValidStone(hostState.getBlock(), placerDeposit.getValid())) {
                            IGeoBlock hostBlock = (IGeoBlock) hostState.getBlock();
                            // Check if the block already has an ore in it; if so, roll to replace
                            if (hostBlock.getOreType().hasOre()) {
                                // TODO Currently set to 40% chance to NOT replace
                                // add this to other deposits? if so, add to the placeDepositOre method in DepositUtils
                                if (rand.nextFloat() > 0.60f) { continue; }
                            }

                            Block placeBlock;
                            if (UtilMethods.isGeoBlockRegolith(hostState.getBlock())) {
                                placeBlock = ModBlocks.GEOBLOCKS.get(hostBlock.getGeologyType())
                                        .getRegolithOre(placerDeposit.getOres().nextElt(), grade);
                            } else {
                                placeBlock = ModBlocks.GEOBLOCKS.get(hostBlock.getGeologyType())
                                        .getStoneOre(placerDeposit.getOres().nextElt(), grade);
                            }

                            reader.setBlockState(areaPos, placeBlock.getDefaultState(), 2);

                        }

                    }
                }
            }

            // Debug
            if (JemsGeoConfig.SERVER.debug_placer_deposits.get()) {
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