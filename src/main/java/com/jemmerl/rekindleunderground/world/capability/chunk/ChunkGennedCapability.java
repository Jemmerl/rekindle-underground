package com.jemmerl.rekindleunderground.world.capability.chunk;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.concurrent.ConcurrentLinkedQueue;

// Class and related methods heavily built/cloned from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public class ChunkGennedCapability implements IChunkGennedCapability {

    @CapabilityInject(IChunkGennedCapability.class)
    public static final Capability<IChunkGennedCapability> RKU_CHUNK_GEN_CAPABILITY = null;

    private final ConcurrentLinkedQueue<ChunkPos> generatedChunks;

    public ChunkGennedCapability() {
        this.generatedChunks = new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean hasChunkGenerated(ChunkPos pos) {
        return this.generatedChunks.contains(pos);
    }

    @Override
    public void setChunkGenerated(ChunkPos pos) {
        this.generatedChunks.add(pos);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        ListNBT chunks = new ListNBT();
        this.generatedChunks.forEach(chunkPos -> {
            CompoundNBT t = new CompoundNBT();
            t.putInt("x", chunkPos.x);
            t.putInt("z", chunkPos.z);
            chunks.add(t);
        });
        compound.put("chunks", chunks);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        ListNBT chunks = compound.getList("chunks", 10);
        chunks.forEach(x -> {
            CompoundNBT comp = (CompoundNBT) x;
            ChunkPos chunkPos = new ChunkPos(comp.getInt("x"), comp.getInt("z"));
            this.generatedChunks.add(chunkPos);
        });
    }
}
