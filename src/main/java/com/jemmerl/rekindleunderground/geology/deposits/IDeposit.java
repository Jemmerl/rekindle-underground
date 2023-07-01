package com.jemmerl.rekindleunderground.geology.deposits;

import com.jemmerl.rekindleunderground.data.enums.ore.DepositType;
import com.jemmerl.rekindleunderground.data.enums.GeologyType;
import com.jemmerl.rekindleunderground.data.enums.ore.GradeType;
import com.jemmerl.rekindleunderground.data.enums.ore.OreType;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public interface IDeposit {

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
