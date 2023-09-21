package com.jemmerl.jemsgeology.geology.deposits.instances;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.DepositType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.geology.deposits.IScatterDeposit;
import com.jemmerl.jemsgeology.geology.deposits.templates.ConstantScatterTemplate;
import com.jemmerl.jemsgeology.util.WeightedProbMap;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public class ConstantScatterDeposit implements IScatterDeposit {

    private final ConstantScatterTemplate template;

    private String name;
    private WeightedProbMap<OreType> ores;
    private WeightedProbMap<GradeType> gradesMap;
    private ArrayList<GeologyType> validList;
    private ArrayList<Biome.Category> validBiomes;

    public ConstantScatterDeposit(ConstantScatterTemplate constantScatterTemplate) {
        this.template = constantScatterTemplate;
    }

    /////////////
    // SETTERS //
    /////////////

    @Override
    public ConstantScatterDeposit setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ConstantScatterDeposit setOres(WeightedProbMap<OreType> oreMap) {
        this.ores = oreMap;
        return this;
    }

    @Override
    public ConstantScatterDeposit setGrades(WeightedProbMap<GradeType> gradesMap) {
        this.gradesMap = gradesMap;
        return this;
    }

    @Override
    public ConstantScatterDeposit setValid(ArrayList<GeologyType> validList) {
        this.validList = validList;
        return this;
    }

    @Override
    public ConstantScatterDeposit setBiomes(ArrayList<Biome.Category> validBiomes) {
        this.validBiomes = validBiomes;
        return this;
    }

    /////////////
    // GETTERS //
    /////////////

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public DepositType getType() {
        return DepositType.PLACER;
    }

    @Override
    public WeightedProbMap<OreType> getOres() {
        return this.ores;
    }

    @Override
    public WeightedProbMap<GradeType> getGrades() {
        return this.gradesMap;
    }

    @Override
    public ArrayList<GeologyType> getValid() {
        return this.validList;
    }

    @Override
    public ArrayList<Biome.Category> getBiomes() {
        return this.validBiomes;
    }

    @Override
    public int getWeight() {
        return 1; // Always generates in a chunk [rand.nextInt(1) always == 0]
    }

    @Override
    public int getSpawnTries() {
        return template.getSpawnTries();
    }

    @Override
    public int getSize() {
        return template.getSize();
    }

    public int getMaxHeight() {
        return template.getMaxHeight();
    }

    public int getMinHeight() {
        return template.getMinHeight();
    }

    public boolean isAccurateSize() {
        return template.isAccurateSize();
    }

}