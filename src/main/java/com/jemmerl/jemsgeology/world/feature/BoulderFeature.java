package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.UtilMethods;
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

        // temporary constants, will be instantialized by config'd boulder instances (like dike-sills)
        final int RADIUS = 5; // can be between 2 and 8 (4 to 16 wide)
        final int RADIUS_SQ = RADIUS * RADIUS; // used for fast distance check without sqrt
        final BlockState BOULDER_STATE = ModBlocks.GRANITE_STONE.get().getDefaultState();
        final int EMBED_DEPTH = 3; // depth the center is into the ground (min 0)
        // valid biomes

        // Config disable option
        if (!JemsGeoConfig.SERVER.gen_boulders.get()) {
            return false;
        }

        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(reader.getSeed());
        }

        int centerX = pos.getX();
        int centerZ = pos.getZ();
        int centerY = reader.getHeight(Heightmap.Type.WORLD_SURFACE, centerX, centerZ) - EMBED_DEPTH;

        for (BlockPos currPos : BlockPos.getAllInBoxMutable(pos.add(-RADIUS, (centerY - RADIUS), -RADIUS), pos.add(RADIUS, (centerY + RADIUS), RADIUS))) {
            float distanceSq = UtilMethods.getSquareDistance3D(currPos.getX(), currPos.getY(), currPos.getZ(), centerX, centerY, centerZ);

            if (distanceSq < RADIUS_SQ) {
                reader.setBlockState(currPos, BOULDER_STATE, 2);
            }
        }

        // Debug
        if (JemsGeoConfig.SERVER.debug_boulders.get()) {
            JemsGeology.getInstance().LOGGER.info("Placed Boulder at X: {}, Y: {}, Z: {}", centerX, centerY, centerZ);
        }

        return true;
    }
}
