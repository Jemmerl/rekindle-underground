package com.jemmerl.jemsgeology.geology.deposits.instances;

import com.jemmerl.jemsgeology.data.enums.ore.DepositType;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.geology.deposits.IDeposit;
import com.jemmerl.jemsgeology.geology.deposits.templates.PlacerTemplate;
import com.jemmerl.jemsgeology.util.WeightedProbMap;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public class PlacerDeposit implements IDeposit {

    private final PlacerTemplate placerTemplate;

    private String name;
    private WeightedProbMap<OreType> ores;
    private WeightedProbMap<GradeType> gradesMap;
    private ArrayList<GeologyType> validList;
    private ArrayList<Biome.Category> validBiomes;

    public PlacerDeposit(PlacerTemplate template) {
        this.placerTemplate = template;
    }

    /////////////
    // SETTERS //
    /////////////

    @Override
    public PlacerDeposit setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public PlacerDeposit setOres(WeightedProbMap<OreType> oreMap) {
        this.ores = oreMap;
        return this;
    }

    @Override
    public PlacerDeposit setGrades(WeightedProbMap<GradeType> gradesMap) {
        this.gradesMap = gradesMap;
        return this;
    }

    @Override
    public PlacerDeposit setValid(ArrayList<GeologyType> validList) {
        this.validList = validList;
        return this;
    }

    @Override
    public PlacerDeposit setBiomes(ArrayList<Biome.Category> validBiomes) {
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
        return this.placerTemplate.getWeight();
    }

    public int getAvgRadius() {
        return this.placerTemplate.getAvgRadius();
    }

    public int getMinDensity() {
        return this.placerTemplate.getMinDensity();
    }

    public int getMaxDensity() {
        return this.placerTemplate.getMaxDensity();
    }

    public boolean isSubmerged() {
        return this.placerTemplate.isSubmerged();
    }

}