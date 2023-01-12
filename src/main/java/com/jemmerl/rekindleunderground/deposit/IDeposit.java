package com.jemmerl.rekindleunderground.deposit;

import com.jemmerl.rekindleunderground.data.types.DepositType;
import com.jemmerl.rekindleunderground.data.types.GeologyType;
import com.jemmerl.rekindleunderground.data.types.GradeType;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import com.jemmerl.rekindleunderground.world.capability.chunk.IChunkGennedCapability;
import com.jemmerl.rekindleunderground.world.capability.deposit.IDepositCapability;
import com.jemmerl.rekindleunderground.world.feature.stonegeneration.ChunkReader;
import com.jemmerl.rekindleunderground.world.feature.stonegeneration.StateMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Random;

public interface IDeposit {

    boolean generate(ChunkReader reader, Random rand, BlockPos pos, StateMap stateMap,
                     IDepositCapability depositCapability, IChunkGennedCapability chunkGennedCapability);

    IDeposit setName(String name);

    IDeposit setOres(WeightedProbMap<OreType> oreMap);

    IDeposit setGrades(WeightedProbMap<GradeType> grades);

    IDeposit setValid(ArrayList<GeologyType> validList);

    IDeposit setBiomes(ArrayList<Biome.Category> validBiomes);

    String getName();

    DepositType getType();

    WeightedProbMap<OreType> getOres();

    WeightedProbMap<GradeType> getGrades();

    ArrayList<GeologyType> getValid();

    ArrayList<Biome.Category> getBiomes();

    int getWeight();

}

// deposit _> placer or vein -> subcategories of each

/*
Shapes:
 disk to blobby-disk (ala diatrememaar)
 LIKE: coals
 - can be layered over top of eachother

 spherical to blobular

vein (thick to thin)
 LIKE: hydrothermal
    - singular to cluster

 */
// TODO TODO TODO
// generate with radius using random blob like the diatrememaar
// REWORK LAYER WAVYNESS TO USE A SINGLE, GLOBAL HEIGHT MORPHER!!
// ALLOW ORES TO ACCESS IT! WILL LET ORES FOLLOW THE EFFECTS!

// TODO
// check if block being set is within the active chunk
// if so, continue. if not, enqueue!
// TEMP: just make it get ignored :P
