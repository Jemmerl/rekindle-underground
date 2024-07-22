package com.jemmerl.jemsgeology.geology;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GeoMapBuilder {

    private final ChunkReader chunkReader;
    private final BlockPos cornerPos; // Starting position of this chunk's generation
    private final Random rand;
    private final GeoWrapper[][][] geoWrapperArray; // Positional map of stone blockstates to be generated
    private final int[][] batholithHeights;
    private final IDepositCapability depCap;
    private final IChunkGennedCapability cpCap;

    public GeoMapBuilder(ChunkReader reader, BlockPos pos, Random rand) {
        this.chunkReader = reader;
        this.cornerPos = pos;
        this.rand = rand;
        this.geoWrapperArray = new GeoWrapper[16][this.chunkReader.getMaxHeight()][16];
        this.batholithHeights = new int[16][16];

        this.depCap = this.chunkReader.getSeedReader().getWorld().getCapability(DepositCapability.JEMGEO_DEPOSIT_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo deposit capability is null..."));
        this.cpCap = this.chunkReader.getSeedReader().getWorld().getCapability(ChunkGennedCapability.JEMGEO_CHUNK_GEN_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo chunk gen capability is null..."));

        genGeoMap();
    }

    public GeoWrapper[][][] getGeoWrapperArray() {
        return this.geoWrapperArray;
    }

    public GeoWrapper getGeoWrapper(int x, int y, int z) {
        return this.geoWrapperArray[x][y][z];
    }

    private void genGeoMap() {
        PopulateIgneous();
        PopulateStrata();
        PopulateOres();

        // Mark that this chunk was generated
        this.cpCap.setChunkGenerated(this.chunkReader.getSeedReader().getChunk(this.cornerPos).getPos());
        //System.out.println("??? GENNED");
        // WHAT IN THE HELL WHYYYYYY
        //519 ~ 183
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
        VolcanicRegionBuilder.getVolcanicBlocks(this.geoWrapperArray, this.batholithHeights, this.chunkReader, this.cornerPos);

//        int[][] bathHeights =
//        for(int i = 0; i < bathHeights.length; i++) {
//            int[] aMatrix = bathHeights[i];
//            int aLength = aMatrix.length;
//            this.batholithHeights[i] = new int[aLength];
//            System.arraycopy(aMatrix, 0, this.batholithHeights[i], 0, aLength);
//        }

//        for (int x = 0; x < 16; x++) {
//            for (int z = 0; z < 16; z++) {
//                for (int y = 0; y < this.chunkReader.getMaxHeight(); y++) {
//                    int posX = this.cornerPos.getX() + x;
//                    int posZ = this.cornerPos.getZ() + z;
//
//                    this.geoWrapperArray[x][y][z] = VolcanicRegionBuilder.getVolcanicBlock(posX, y, posZ,
//                            chunkReader.getSeedReader());
//                }
//            }
//        }
    }


    // Fill the chunk state map with generated stones
    public void PopulateStrata() {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < this.chunkReader.getMaxHeight(); y++) {
                    int posX = this.cornerPos.getX() + x;
                    int posZ = this.cornerPos.getZ() + z;

                    GeoWrapper ignBlock = this.geoWrapperArray[x][y][z];
                    GeologyType geologyType = ignBlock.getGeologyType();
                    boolean contactMeta = (ignBlock.getOreType() == null);
                    if ((geologyType == null) || contactMeta) {
                        GeologyType stoneGeoType = StoneRegionBuilder.getStoneStrataBlock(posX, (y - batholithHeights[x][z]), posZ,
                                chunkReader.getSeedReader(), contactMeta);

                        // might be temporary, may move into new strata gen
                        if (contactMeta) {
                            stoneGeoType = ModBlockLists.CONTACT_META_MAP.getOrDefault(stoneGeoType, stoneGeoType);
                        }

                        this.geoWrapperArray[x][y][z] = new GeoWrapper(stoneGeoType, OreType.NONE, GradeType.NONE);
                    }
                }
            }
        }
    }


    // Populate ore deposits
    public void PopulateOres() {

        // Fill the oremap with (or attempt to place) any previously immediate pending blocks
        // Method adapted from Geolosys (oitsjustjose)
        // https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c
        ISeedReader reader = this.chunkReader.getSeedReader();
        ChunkPos cp = new ChunkPos(this.cornerPos);
        ConcurrentLinkedQueue<DepositCapability.PendingBlock> queue = depCap.getImmediatePendingBlocks(cp);

        // Debug
        if (JemsGeoConfig.SERVER.debug_block_enqueuer.get()) {
            // Manual toggle. It is useful, sometimes, but it's too spammy to enable with the other debug tools
            // and likely won't be of use to any pack devs trying to understand why my spaghetti-code mod broke!
            if (false) { JemsGeology.getInstance().LOGGER.info("Trying to place queue with size {}", queue.size()); }
            if (cpCap.hasChunkGenerated(cp) && (queue.size() > 0)) {
                JemsGeology.getInstance().LOGGER.info(
                        "Chunk [{}, {}] has already generated, trying to place immediately pending blocks anyways",
                        cp.x, cp.z);
            }
        }

        queue.stream().forEach(x -> DepositUtil.processGeoMapEnqueue(reader, x.getPos(), x.getOre(), x.getGrade(),
                x.getName(), this.cornerPos, this));
        depCap.removeImmediatePendingBlocksForChunk(cp);

        // Generates and enqueues the ore deposit with a one out of the deposit's weight chance
        // I.e. with a weight of 100, 1 in 100 chunks will ATTEMPT to generate the deposit
        for (IEnqueuedDeposit deposit : DepositRegistrar.getEnqOreDeposits().values()) {
            if (this.rand.nextInt(deposit.getWeight()) == 0) {
                // Tries to update the stateMap with the generating feature
                if (!deposit.generate(this.chunkReader, this.rand, this.cornerPos, this) &&
                        JemsGeoConfig.SERVER.debug_block_enqueuer.get()) {
                    // Debug
                    JemsGeology.getInstance().LOGGER.warn(
                            "Failed to generate deposit at {}, {}", this.cornerPos.getX(), this.cornerPos.getZ());
                }
            }
        }

    }

}
