package com.jemmerl.rekindleunderground.world.capability.deposit;

import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.OreType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.concurrent.ConcurrentLinkedQueue;

// Class and related methods heavily built/cloned from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public interface IDepositCapability extends INBTSerializable<CompoundNBT> {
    boolean hasDepositGenerated(ChunkPos pos);

    void setDepositGenerated(ChunkPos pos);

    void putPendingOre(BlockPos pos, OreType oreType, GradeType gradeType, String name);

    void removePendingBlocksForChunk(ChunkPos cp);

    ConcurrentLinkedQueue<DepositCapability.PendingBlock> getPendingBlocks(ChunkPos cp);

    int getPendingBlockCount();

    ConcurrentLinkedQueue<ChunkPos> getGenMap();
}
