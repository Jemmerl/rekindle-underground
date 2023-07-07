package com.jemmerl.jemsgeology.world.capability.chunk;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.util.INBTSerializable;

// Class and related methods heavily built/cloned from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public interface IChunkGennedCapability extends INBTSerializable<CompoundNBT> {
    boolean hasChunkGenerated(ChunkPos pos);

    void setChunkGenerated(ChunkPos pos);
}
