package com.jemmerl.jemsgeology.geology;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.geology.strata.StoneRegionBuilder;
import com.jemmerl.jemsgeology.geology.strata.VolcanicRegionBuilder;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.init.depositinit.DepositRegistrar;
import com.jemmerl.jemsgeology.geology.deposits.DepositUtil;
import com.jemmerl.jemsgeology.geology.deposits.IEnqueuedDeposit;
import com.jemmerl.jemsgeology.util.lists.ModBlockLists;
import com.jemmerl.jemsgeology.world.capability.chunk.ChunkGennedCapability;
import com.jemmerl.jemsgeology.world.capability.chunk.IChunkGennedCapability;
import com.jemmerl.jemsgeology.world.capability.deposit.DepositCapability;
import com.jemmerl.jemsgeology.world.capability.deposit.IDepositCapability;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StateMapBuilder {

    private final ChunkReader chunkReader;
    private final BlockPos blockPos; // Starting position of this chunk's generation
    private final Random rand;
    private final BlockState[][][] stoneStateMap; // Positional map of stone blockstates to be generated
    private final IDepositCapability depositCapability;
    private final IChunkGennedCapability chunkGennedCapability;

    public StateMapBuilder(ChunkReader reader, BlockPos pos, Random rand) {
        this.chunkReader = reader;
        this.blockPos = pos;
        this.rand = rand;
        this.stoneStateMap = new BlockState[16][this.chunkReader.getMaxHeight()][16];

        this.depositCapability = this.chunkReader.getSeedReader().getWorld().getCapability(DepositCapability.RKU_DEPOSIT_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("RKU deposit capability is null..."));
        this.chunkGennedCapability = this.chunkReader.getSeedReader().getWorld().getCapability(ChunkGennedCapability.RKU_CHUNK_GEN_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("RKU chunk gen capability is null..."));

        generateStateMap();
    }

    public BlockState[][][] getStoneStateMap() {
        return this.stoneStateMap;
    }

    public BlockState getStoneState(int x, int y, int z) {
        return this.stoneStateMap[x][y][z];
    }

    private void generateStateMap() {
        PopulateIgneous();
        PopulateStrata();
        PopulateOres();

        // Mark that this chunk was generated
        this.chunkGennedCapability.setChunkGenerated(this.chunkReader.getSeedReader().getChunk(this.blockPos).getPos());
    }

    /////////////////////////////////////////////////
    /////             Map Generators            /////
    /////////////////////////////////////////////////

    // Replace stones in the current state map with generated features
    // Will be used for less technical generation, such as dikes or LIPs
    public void PopulateIgneous() {
        // TODO CURRENTLY NOT IN USE. IT WILL BE IN THE FUTURE!
        // use datapack to define various dike sills and their stone
        // loop over them sequentially
        // (try randomly depending on the region order etc later to layer them)
        // as this doesnt place and override blocks, should be fast
        // by doing each dikesill for the whole chunk in order, noise seeds will not change many times
        // only need to change once for each dikesill
        // DO DIKES SILLS LAST SO THEY OVERLAP OTHER FEATURES

//        // Populate dike/sills
//        for (final DikeSillEntry dikeSillEntry : FeatureRegistrar.getDikeSillFeatures().values()) {
//            for (int x = 0; x < 16; x++) {
//                for (int z = 0; z < 16; z++) {
//                    for (int y = 0; y < this.chunkReader.getMaxHeight(); y++) {
//                        int posX = this.blockPos.getX() + x;
//                        int posZ = this.blockPos.getZ() + z;
//
//                        BlockState dikeSillState = DikeSillGen.generate(posX, y, posZ, dikeSillEntry);
//                        if (dikeSillState != null) {
//                            //System.out.println("x: " + x + " y: " + y + " z: " + z);
//                            this.stoneStateMap[x][y][z] = dikeSillState;
//                        }
//                    }
//                }
//            }
//        }



        // Generate the potential blockstate given the larger volcanic region.
        // If no block generated, return null and let the strata generate the position.
        // If not null, then there is no need to generate the strata block as the volcanic
        // spot technically is to generate on top of the strata, replacing it.
        // If a spot is to be contact metamorphosed, then it will be set as AIR
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < this.chunkReader.getMaxHeight(); y++) {
                    int posX = this.blockPos.getX() + x;
                    int posZ = this.blockPos.getZ() + z;

                    this.stoneStateMap[x][y][z] = VolcanicRegionBuilder.getVolcanicState(posX, y, posZ,
                            chunkReader.getSeedReader());
                }
            }
        }



    }


    // Fill the chunk state map with generated stones
    public void PopulateStrata() {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < this.chunkReader.getMaxHeight(); y++) {
                    int posX = this.blockPos.getX() + x;
                    int posZ = this.blockPos.getZ() + z;

                    BlockState ignState = this.stoneStateMap[x][y][z];
                    boolean contactMeta = (ignState == Blocks.AIR.getDefaultState());
                    if ((ignState == null) || contactMeta) {
                        BlockState stoneState = StoneRegionBuilder.getStoneStrataBlock(posX, y, posZ,
                                chunkReader.getSeedReader(), contactMeta);

                        // might be temporary, may move into new strata gen
                        if (contactMeta) {
                            GeologyType geoType = ((IGeoBlock)(stoneState.getBlock())).getGeologyType();
                            stoneState = ModBlockLists.CONTACT_META_MAP.getOrDefault(geoType, stoneState);
                        }

                        this.stoneStateMap[x][y][z] = stoneState;
                    }
                }
            }
        }
    }


    // Populate ore deposits
    public void PopulateOres() {

        // Fill the oremap with (or attempt to place) any previously pending blocks
        // Method adapted from Geolosys (oitsjustjose)
        // https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c
        ISeedReader reader = this.chunkReader.getSeedReader();
        ChunkPos cp = new ChunkPos(this.blockPos);
        ConcurrentLinkedQueue<DepositCapability.PendingBlock> queue = depositCapability.getPendingBlocks(cp);

        // Debug
        if (JemsGeoConfig.SERVER.debug_block_enqueuer.get()) {
            // Manual toggle. It is useful, sometimes, but it's too spammy to enable with the other debug tools
            // and likely won't be of use to any pack devs trying to understand why my spaghetti-code mod broke!
            if (false) { JemsGeology.getInstance().LOGGER.info("Trying to place queue with size {}", queue.size()); }
            if (chunkGennedCapability.hasChunkGenerated(cp) && (queue.size() > 0)) {
                JemsGeology.getInstance().LOGGER.info(
                        "Chunk [{}, {}] has already generated but attempting to place pending blocks anyways",
                        cp.x, cp.z);
            }
        }

        queue.stream().forEach(x -> DepositUtil.enqueueBlockPlacement(reader, x.getPos(), x.getOre(), x.getGrade(),
                x.getName(), this.blockPos, this, this.depositCapability, this.chunkGennedCapability));
        depositCapability.removePendingBlocksForChunk(cp);

        // Generates and enqueues the ore deposit with a one out of the deposit's weight chance
        // I.e. with a weight of 100, 1 in 100 chunks will ATTEMPT to generate the deposit
        for (IEnqueuedDeposit deposit : DepositRegistrar.getOreDeposits().values()) {
            if (this.rand.nextInt(deposit.getWeight()) == 0) {
                // Tries to update the stateMap with the generating feature
                if (!deposit.generate(this.chunkReader, this.rand, this.blockPos, this,
                        this.depositCapability, this.chunkGennedCapability) && JemsGeoConfig.SERVER.debug_block_enqueuer.get()) {
                    // Debug
                    JemsGeology.getInstance().LOGGER.warn(
                            "Failed to generate deposit at {}, {}", this.blockPos.getX(), this.blockPos.getZ());
                }
            }
        }

    }

}
