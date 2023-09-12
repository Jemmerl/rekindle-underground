package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.geology.features.instances.BoulderEntry;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.init.featureinit.FeatureRegistrar;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.util.lists.ModBlockLists;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.Random;

public class BoulderFeature extends Feature<NoFeatureConfig> {
    public BoulderFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        // Config disable option
        if (!JemsGeoConfig.SERVER.gen_boulders.get()) {
            return false;
        }

        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(reader.getSeed());
        }

        boolean didGen = false;
        for (BoulderEntry entry : FeatureRegistrar.getBoulderFeatures().values()) {
            //Random boulderRand = new Random(entry.getSeed());

            if (rand.nextInt(entry.getChance()) != 0) {
                continue;
            }

            // Place boulder in center of chunk +/-4 blocks in any direction (12x12 valid area vs 16x16 for buffer)
            BlockPos placePos = pos.add((8 + rand.nextInt(5)), 0, (8 + rand.nextInt(5)));
            Biome biome = reader.getBiome(placePos);
            if (!entry.getBiomes().contains(biome.getCategory())) {
                continue;
            }

            ArrayList<GeologyType> stones = entry.getStones();
            GeologyType geologyType = stones.get(rand.nextInt(stones.size()));
            if (buildBoulder(entry, reader, rand, placePos, geologyType, false)) {
                didGen = true;
                // Attempt to place up to 2 smaller adjacent boulders if enabled
                if (entry.getPlaceExtra() && rand.nextBoolean()) buildBoulder(entry, reader, rand, placePos, geologyType, true);
                if (entry.getPlaceExtra() && rand.nextBoolean()) buildBoulder(entry, reader, rand, placePos, geologyType, true);
            }
        }

        return didGen;
    }


    private boolean buildBoulder(BoulderEntry entry, ISeedReader reader, Random rand, BlockPos pos, GeologyType geologyType, boolean adjacent) {
        final BlockState boulderState = ModBlockLists.GEO_LIST.get(geologyType).getStoneOreBlock().getDefaultState();
        int rLong = rand.nextInt(entry.getLongRadiusMax() - entry.getLongRadiusMin() + 1) + entry.getLongRadiusMin();
        int rShort = rand.nextInt(entry.getShortRadiusMax() - entry.getShortRadiusMin() + 1) + entry.getShortRadiusMin();
        int embedDepth = rand.nextInt(rShort-1) + 1; // Depth the center is into the ground (1 to (rShort-1))
        float rotAngle = (float) Math.toRadians(rand.nextInt(181) - 90); // Horizontal rotation angle (-90 to 90)

        // Fast swap values if the short radius is bigger than the large radius
        if (rShort > rLong) {
            rLong = rLong ^ rShort;
            rShort = rLong ^ rShort;
            rLong = rLong ^ rShort;
        }

        // Shift placement location and shrink size if adjacent boulder
        if (adjacent) {
            pos = pos.add((rand.nextInt((rLong * 2) + 1) - rLong), 0, (rand.nextInt((rLong * 2) + 1) - rLong));
            if (rLong > 3) rLong /= 2;
            if (rShort > 3) rShort /= 2;
        }

        int centerX = pos.getX();
        int centerZ = pos.getZ();
        int centerY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX, centerZ) - embedDepth;

        // Check if the boulder can spawn on hill slopes. If not, check if it is on a hill and cancel if so
        if (!entry.getOnHills() && isOnHill(reader, centerX, centerZ)) {
            // Debug
            if (JemsGeoConfig.SERVER.debug_boulders.get()) {
                JemsGeology.getInstance().LOGGER.info("Boulder failed to place on hill at ({} ~ {})", centerX, centerZ);
            }
            return false;
        }

        float alpha = (float) Math.tan(rotAngle/2f); // flipped signs to change rotation direction
        float beta = (float) -Math.sin(rotAngle);

        // Generate planar rotation matrix
        int[][] xShears = new int[(rLong * 2) + 1][(rLong * 2) + 1];
        int[][] zShears = new int[(rLong * 2) + 1][(rLong * 2) + 1];
        for (BlockPos currPos : BlockPos.getAllInBoxMutable(pos.add(-rLong, 0, -rLong), pos.add(rLong, 0, rLong))) {
            int xRot = currPos.getX() - centerX;
            int zRot = currPos.getZ() - centerZ;

            int iX = xRot + rLong;
            int iZ = zRot + rLong;

            // First shear - horizontal
            int xShear = Math.round(Math.abs(zRot) * alpha);
            if (zRot < 0) xShear *= -1;
            xRot += xShear;

            // Second shear - vertical
            int zShear = Math.round(Math.abs(xRot) * beta);
            if (xRot < 0) zShear *= -1;
            zRot += zShear;

            // Third shear - horizontal
            xShear = Math.round(Math.abs(zRot) * alpha);
            if (zRot < 0) xShear *= -1;
            xRot += xShear;

            xShears[iX][iZ] = xRot;
            zShears[iX][iZ] = zRot;
        }

        // Rotate and place boulder
        for (BlockPos currPos : BlockPos.getAllInBoxMutable(pos.add(-rLong, (centerY - rLong), -rLong), pos.add(rLong, (centerY + rLong), rLong))) {
            int xPosAdj = currPos.getX() - centerX;
            int zPosAdj = currPos.getZ() - centerZ;

            int iX = xPosAdj + rLong;
            int iZ = zPosAdj + rLong;

            int xRot = xPosAdj + xShears[iX][iZ];
            int zRot = zPosAdj + zShears[iX][iZ];

            // Calculate vertical decrement
            int yAdjAbs = Math.abs(currPos.getY() - centerY);
            if (yAdjAbs > rShort) continue;
            float yPercent = 1 - (float) Math.pow((yAdjAbs / (float) rShort), 2);

            // Check if invalid placement
            if (isInvalidPlacement(reader.getBlockState(currPos).getBlock())) {
                continue;
            }

            float noiseVal = BlobNoise.getNoise((xRot * BlobNoise.xLength / (rShort * 4f)), (zRot * BlobNoise.zLength / (rLong * 4f)));
            if (noiseVal < -(0.43f - (0.13f * yPercent))) {
                reader.setBlockState(currPos, boulderState, 2);
            }
        }

        // Debug
        if (JemsGeoConfig.SERVER.debug_boulders.get()) {
            if (adjacent) {
                JemsGeology.getInstance().LOGGER.info(
                        "Placed Extra Boulder from gen: {} with stone: {} at ({} ~ {}) with long radius: {}, short radius {}, and embed depth {}.",
                        entry.getName(), geologyType.getName(), centerX, centerZ, rLong, rShort, embedDepth);
            } else {
                JemsGeology.getInstance().LOGGER.info(
                        "Placed Basic Boulder from gen: {} with stone: {} at ({} ~ {}) with long radius: {}, short radius {}, and embed depth {}.",
                        entry.getName(), geologyType.getName(), centerX, centerZ, rLong, rShort, embedDepth);
            }
        }

        return true;
    }

    private boolean isOnHill(ISeedReader reader, int centerX, int centerZ) {
        int yX1 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX + 1, centerZ);
        int yX2 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX - 1, centerZ);
        float mX = (yX1 - yX2) / 2f;

        if (Math.abs(mX) > 0.75f) {
            return true;
        }

        int yZ1 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX, centerZ + 1);
        int yZ2 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, centerX, centerZ - 1);
        float mZ = (yZ1 - yZ2) / 2f;

        if (Math.abs(mZ) > 0.75f) {
            return true;
        }

        return false;
    }

    private boolean isInvalidPlacement(Block block) {
        return UtilMethods.isStoneBlock(block) || (block.isIn(BlockTags.LOGS));
    }
}
