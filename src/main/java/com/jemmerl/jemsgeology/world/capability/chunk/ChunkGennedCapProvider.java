package com.jemmerl.jemsgeology.world.capability.chunk;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// Class and related methods heavily built/cloned from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public class ChunkGennedCapProvider implements ICapabilitySerializable<CompoundNBT> {
    private final IChunkGennedCapability impl = new ChunkGennedCapability();
    private final LazyOptional<IChunkGennedCapability> cap = LazyOptional.of(() -> impl);

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capIn, final @Nullable Direction side) {
        if (capIn == ChunkGennedCapability.RKU_CHUNK_GEN_CAPABILITY) {
            return cap.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return impl.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        impl.deserializeNBT(nbt);
    }
}
