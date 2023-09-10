package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.blocks.StoneGeoBlock;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobNoise;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class BoulderFeature extends Feature<NoFeatureConfig> {
    public BoulderFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        final BlockState BOULDER_STATE = ModBlocks.GRANITE_STONE.get().getDefaultState();
        int rLong = rand.nextInt(6) + 3; // Long radius of the boulder (3 to 8)
        int rShort = rand.nextInt(5) + 2; // Short radius of the boulder (2 to 6)
        int embedDepth = rand.nextInt(rShort-1) + 1; // Depth the center is into the ground (1 to (rShort-1))
        float rotAngle = (float) Math.toRadians(rand.nextInt(181) - 90); // Horizontal rotation angle (-90 to 90)
        // valid biomes

        // Config disable option
        if (!JemsGeoConfig.SERVER.gen_boulders.get()) {
            return false;
        }

        // Check if valid biome



        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(reader.getSeed());
        }

        int centerX = pos.getX();
        int centerZ = pos.getZ();
        int centerY = reader.getHeight(Heightmap.Type.WORLD_SURFACE, centerX, centerZ) - embedDepth;



        // Fast swap values if the short radius is bigger than the large radius
        if (rShort > rLong) {
            rLong = rLong ^ rShort;
            rShort = rLong ^ rShort;
            rLong = rLong ^ rShort;
        }


        //float rotAngle = 0f;


        float alpha = (float) Math.tan(rotAngle/2f); // flipped signs to change rotation direction
        float beta = (float) -Math.sin(rotAngle);

        // Calculate shear13 movements
        int[] shear13 = new int[rLong + 1];
        for (int z = 0; z < (rLong + 1); z++) {
            shear13[z] = Math.round(z * alpha);
        }

        // Calculate shear2 movements
        int[] shear2 = new int[rLong + 1];
        for (int x = 0; x < (rLong + 1); x++) {
            shear2[x] = Math.round(x * beta);
        }

        // rewrite to do once and make a 2 layer matrix of the final rotation movements

        for (BlockPos currPos : BlockPos.getAllInBoxMutable(pos.add(-rLong, (centerY - rLong), -rLong), pos.add(rLong, (centerY + rLong), rLong))) {
            int xPos = currPos.getX();
            int yPos = currPos.getY();
            int zPos = currPos.getZ();

            int xPosAdj = xPos - centerX;
            int yPosAdj = yPos - centerY;
            int zPosAdj = zPos - centerZ;

            int xRot = xPosAdj;
            int zRot = zPosAdj;

            // First shear - horizontal
            int xShear = shear13[Math.abs(zRot)];
            if (zRot < 0) xShear *= -1;
            xRot += xShear;
            if ((Math.abs(xRot)) > rLong) {
                continue;
            }

            // Second shear - vertical
            int zShear = shear2[Math.abs(xRot)];
            if (xRot < 0) zShear *= -1;
            zRot += zShear;
            if (Math.abs(zRot) > rLong) {
                continue;
            }

            // Third shear - horizontal
            xShear = shear13[Math.abs(zRot)];
            if (zRot < 0) xShear *= -1;
            xRot += xShear;
            if (Math.abs(xRot) > rLong) {
                continue;
            }

            // Calculate vertical decrement
            int yAdjAbs = Math.abs(yPosAdj);
            if (yAdjAbs > rShort) continue;
            float yPercent = 1 - (float) Math.pow((yAdjAbs / (float) rShort), 2);
            //float yPercent = 0f;

            // Check if valid placement
            if (reader.getBlockState(currPos).getBlock() instanceof StoneGeoBlock) {
                continue;
            }

            float noiseVal = BlobNoise.getNoise((xRot * BlobNoise.xLength / (rShort * 2f)), (zRot * BlobNoise.zLength / (rLong * 2f)));
            if (noiseVal < -(0.43f - (0.13f * yPercent))) {
                reader.setBlockState(currPos, BOULDER_STATE, 2);
            }
        }


        // Debug
        if (JemsGeoConfig.SERVER.debug_boulders.get()) {
            JemsGeology.getInstance().LOGGER.info("Placed Boulder at ({} {} {}) with ", centerX, centerY, centerZ);
        }

        return true;
    }

    private boolean buildBoulder() {
        return true;
    }
}
