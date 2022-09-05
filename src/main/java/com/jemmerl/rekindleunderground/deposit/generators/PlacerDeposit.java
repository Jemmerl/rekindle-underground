package com.jemmerl.rekindleunderground.deposit.generators;

import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.deposit.IDeposit;
import com.jemmerl.rekindleunderground.deposit.templates.LayerTemplate;
import com.jemmerl.rekindleunderground.util.WeightedProbMap;
import com.jemmerl.rekindleunderground.world.capability.chunk.IChunkGennedCapability;
import com.jemmerl.rekindleunderground.world.capability.deposit.IDepositCapability;
import com.jemmerl.rekindleunderground.world.feature.stonegeneration.ChunkReader;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Random;

public class PlacerDeposit implements IDeposit {

    private final LayerTemplate layerTemplate;

    private String name;
    private WeightedProbMap<OreType> ores;
    private ArrayList<Integer> grades;
    private ArrayList<StoneType> validList;
    private ArrayList<Biome.Category> validBiomes;

    public PlacerDeposit(LayerTemplate template) {
        this.layerTemplate = template;
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
    public PlacerDeposit setGrades(ArrayList<Integer> grades) {
        this.grades = grades;
        return this;
    }

    @Override
    public PlacerDeposit setValid(ArrayList<StoneType> validList) {
        this.validList = validList;
        return this;
    }

    @Override
    public IDeposit setBiomes(ArrayList<Biome.Category> validBiomes) {
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
    public WeightedProbMap<OreType> getOres() {
        return this.ores;
    }

    @Override
    public ArrayList<Integer> getGrades() {
        return this.grades;
    }

    @Override
    public ArrayList<StoneType> getValid() {
        return this.validList;
    }

    @Override
    public ArrayList<Biome.Category> getBiomes() {
        return this.validBiomes;
    }

    @Override
    public int getWeight() {
        return this.layerTemplate.getWeight();
    }


    //////////////////////////
    //  DEPOSIT GENERATION  //
    //////////////////////////

    @Override
    public boolean generate(ChunkReader reader, Random rand, BlockPos pos, BlockState[][][] stateMap,
                            IDepositCapability depositCapability, IChunkGennedCapability chunkGennedCapability) {
        return false;
    }

}
