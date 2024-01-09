package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.deposits.DepositUtil;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.world.capability.chunk.ChunkGennedCapability;
import com.jemmerl.jemsgeology.world.capability.chunk.IChunkGennedCapability;
import com.jemmerl.jemsgeology.world.capability.deposit.DepositCapability;
import com.jemmerl.jemsgeology.world.capability.deposit.IDepositCapability;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DelayedOreFeature extends Feature<NoFeatureConfig> {

    private static IDepositCapability depCap;
    private static IChunkGennedCapability cgCap;

    public DelayedOreFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (depCap == null) {
            depCap = reader.getWorld().getCapability(DepositCapability.JEMGEO_DEPOSIT_CAPABILITY)
                    .orElseThrow(() -> new RuntimeException("JemsGeo deposit capability is null..."));
        }

        if (cgCap == null) {
            cgCap = reader.getWorld().getCapability(ChunkGennedCapability.JEMGEO_CHUNK_GEN_CAPABILITY)
                    .orElseThrow(() -> new RuntimeException("JemsGeo chunk gen capability is null..."));
        }

        // Fill the chunk with (or attempt to place) any previously delayed pending blocks
        // Method adapted from Geolosys (oitsjustjose)
        // https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c
        ChunkPos cp = new ChunkPos(pos);
        ConcurrentLinkedQueue<DepositCapability.PendingBlock> queue = depCap.getDelayedPendingBlocks(cp);

        // Debug
        if (JemsGeoConfig.SERVER.debug_block_enqueuer.get()) {
            // Manual toggle. It is useful, sometimes, but it's too spammy to enable with the other debug tools
            // and likely won't be of use to any pack devs trying to understand why my spaghetti-code mod broke!
            if (false) { JemsGeology.getInstance().LOGGER.info("Trying to place queue with size {}", queue.size()); }

            // Unlike the immediate ore enqueueing, delayed placement should always already be "generated"
            if (!cgCap.hasChunkGenerated(cp) && (queue.size() > 0)) {
                JemsGeology.getInstance().LOGGER.info(
                        "Chunk [{}, {}] is not generated, trying to place delayed pending blocks anyways",
                        cp.x, cp.z);
            }
        }

        queue.stream().forEach(x ->
                DepositUtil.processOreEnqueue(reader, x.getPos(), x.getOre(), x.getGrade(), x.getDelayed(), x.getName()));
        depCap.removeDelayedPendingBlocksForChunk(cp);

        return true;
    }
}
