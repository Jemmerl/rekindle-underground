package com.jemmerl.jemsgeology.geology.features.instances;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.geology.features.templates.BoulderTemplate;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public class BoulderEntry {

    private final BoulderTemplate boulderTemplate;

    private String name;
    private ArrayList<GeologyType> stonesList;
    private ArrayList<Biome.Category> validBiomes;

    public BoulderEntry(BoulderTemplate template) {
        this.boulderTemplate = template;
    }

    /////////////
    // SETTERS //
    /////////////

    public BoulderEntry setName(String name) {
        this.name = name;
        return this;
    }

    public BoulderEntry setStones(ArrayList<GeologyType> stonesList) {
        this.stonesList = stonesList;
        return this;
    }

    public BoulderEntry setBiomes(ArrayList<Biome.Category> validBiomes) {
        this.validBiomes = validBiomes;
        return this;
    }


    /////////////
    // GETTERS //
    /////////////

    public String getName() {
        return this.name;
    }

    public ArrayList<GeologyType> getStones() {
        return this.stonesList;
    }

    public ArrayList<Biome.Category> getBiomes() {
        return this.validBiomes;
    }

    public int getSeed() {
        return this.boulderTemplate.getSeed();
    }

    public int getChance() {
        return this.boulderTemplate.getChance();
    }

    public boolean getOnHills() {
        return this.boulderTemplate.getHillsValid();
    }

    public int getLongRadiusMax() {
        return this.boulderTemplate.getMaxLongRad();
    }

    public int getLongRadiusMin() {
        return this.boulderTemplate.getMinLongRad();
    }

    public int getShortRadiusMax() {
        return this.boulderTemplate.getMaxShortRad();
    }

    public int getShortRadiusMin() {
        return this.boulderTemplate.getMinShortRad();
    }

}
