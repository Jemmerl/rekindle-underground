package com.jemmerl.rekindleunderground.world.capability.deposit;

import com.jemmerl.rekindleunderground.data.types.OreType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

// Class and related methods heavily built/cloned from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public class DepositCapability implements IDepositCapability {

    @CapabilityInject(IDepositCapability.class)
    public static final Capability<IDepositCapability> RKU_DEPOSIT_CAPABILITY = null;

    private final ConcurrentLinkedQueue<ChunkPos> oreGenMap;
    private final ConcurrentHashMap<ChunkPos, ConcurrentLinkedQueue<PendingBlock>> pendingBlocks;

    public DepositCapability() {
        this.oreGenMap = new ConcurrentLinkedQueue<>();
        this.pendingBlocks = new ConcurrentHashMap<>();
    }

    @Override
    public ConcurrentLinkedQueue<ChunkPos> getGenMap() {
        return this.oreGenMap;
    }

    @Override
    public void putPendingOre(BlockPos pos, OreType oreType, String name) {
        PendingBlock p = new PendingBlock(pos, oreType, name);
        ChunkPos cp = new ChunkPos(pos);
        this.pendingBlocks.putIfAbsent(cp, new ConcurrentLinkedQueue<>());
        this.pendingBlocks.get(cp).add(p);
    }

    @Override
    public void removePendingBlocksForChunk(ChunkPos cp) {
        this.pendingBlocks.remove(cp);
    }

    @Override
    public ConcurrentLinkedQueue<PendingBlock> getPendingBlocks(ChunkPos chunkPos) {
        return this.pendingBlocks.getOrDefault(chunkPos, new ConcurrentLinkedQueue<>());
    }

    @Override
    public int getPendingBlockCount() {
        return (int) this.pendingBlocks.values().stream().collect(Collectors.summarizingInt(x -> x.size())).getSum();
    }

    @Override
    public void setDepositGenerated(ChunkPos pos) {
        this.oreGenMap.add(pos);
    }

    @Override
    public boolean hasDepositGenerated(ChunkPos pos) {
        return this.oreGenMap.contains(pos);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        compound.put("WorldOreDeposits", new CompoundNBT());
        compound.put("PendingBlocks", new CompoundNBT());

        CompoundNBT oreDeposits = compound.getCompound("WorldOreDeposits");
        CompoundNBT pendingBlocks = compound.getCompound("PendingBlocks");

        this.getGenMap().forEach(cp -> oreDeposits.putBoolean(serializeChunkPos(cp), true));
        this.pendingBlocks.entrySet().forEach(e -> {
            ListNBT p = new ListNBT();
            String key = e.getKey().x + "_" + e.getKey().z;
            e.getValue().forEach(pb -> p.add(pb.serialize()));
            pendingBlocks.put(key, p);
        });

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        // Check to see if the old storage is still there
        if (compound.contains("PendingBlocks")) {
            CompoundNBT pendingBlocks = compound.getCompound("PendingBlocks");
            pendingBlocks.keySet().forEach(key -> {
                BlockPos pos = deSerializeBlockPos(key);
                OreType type = OreType.valueOf(((CompoundNBT) Objects.requireNonNull(pendingBlocks.get(key))).getString("ore"));
                String name = ((CompoundNBT) Objects.requireNonNull(pendingBlocks.get(key))).getString("name");
                this.putPendingOre(pos, type, name);
            });
        }

        if (compound.contains("PendingBlocksNew")) {
            CompoundNBT pendingBlocks = compound.getCompound("PendingBlocksNew");
            compound.keySet().forEach(chunkPosAsString -> {
                String[] parts = chunkPosAsString.split("_");
                ChunkPos cp = new ChunkPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

                ListNBT pending = compound.getList(chunkPosAsString, 10);
                ConcurrentLinkedQueue<PendingBlock> lq = new ConcurrentLinkedQueue<>();
                pending.forEach(x -> {
                    PendingBlock pb = PendingBlock.deserialize(x);
                    if (pb != null) {
                        lq.add(pb);
                    }
                });
                this.pendingBlocks.put(cp, lq);
            });
        }

        CompoundNBT oreDeposits = compound.getCompound("WorldOreDeposits");
        oreDeposits.keySet().forEach(key -> this.setDepositGenerated(deSerializeChunkPos(key)));
    }

    private String serializeChunkPos(ChunkPos pos) {
        return pos.x + "," + pos.z;
    }

    private String serializeBlockPos(BlockPos pos) {
        return pos.getX() + "," + pos.getY() + "," + pos.getZ();
    }

    private ChunkPos deSerializeChunkPos(String asStr) {
        String[] parts = asStr.split(",");
        return new ChunkPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    private BlockPos deSerializeBlockPos(String asStr) {
        String[] parts = asStr.split(",");
        return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public static class PendingBlock {
        private BlockPos pos;
        private OreType type;
        private String name;

        public PendingBlock(BlockPos pos, OreType type, String name) {
            this.pos = pos;
            this.type = type;
            this.name = name;
        }

        public BlockPos getPos() {
            return this.pos;
        }

        public OreType getOre() {
            return this.type;
        }

        public String getName() {
            return this.name;
        }

        public CompoundNBT serialize() {
            CompoundNBT tag = new CompoundNBT();
            CompoundNBT posTag = NBTUtil.writeBlockPos(this.pos);
            tag.put("pos", posTag);
            tag.putString("ore", this.type.getString());
            tag.putString("name", this.name);
            return tag;
        }

        @Nullable
        public static PendingBlock deserialize(INBT nbt) {
            if (nbt instanceof CompoundNBT) {
                CompoundNBT tag = (CompoundNBT) nbt;
                BlockPos pos = NBTUtil.readBlockPos(tag.getCompound("pos"));
                String type = tag.getCompound("ore").toString();
                String name = tag.getCompound("name").toString();
                return new PendingBlock(pos, OreType.valueOf(type), name);
            }

            return null;
        }
    }
}