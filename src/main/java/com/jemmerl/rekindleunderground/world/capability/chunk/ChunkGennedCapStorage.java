package com.jemmerl.rekindleunderground.world.capability.chunk;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

// Class and related methods heavily built/cloned from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public class ChunkGennedCapStorage implements Capability.IStorage<IChunkGennedCapability> {

    @Override
    public void readNBT(Capability<IChunkGennedCapability> capability, IChunkGennedCapability instance, Direction side,
                        INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            instance.deserializeNBT(((CompoundNBT) nbt));
        }
    }

    @Nullable
    @Override
    public INBT writeNBT(Capability<IChunkGennedCapability> capability, IChunkGennedCapability instance, Direction side) {
        return instance.serializeNBT();
    }
}
