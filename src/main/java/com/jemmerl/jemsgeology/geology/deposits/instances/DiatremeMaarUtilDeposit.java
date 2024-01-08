package com.jemmerl.jemsgeology.geology.deposits.instances;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.DepositType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.geology.ChunkReader;
import com.jemmerl.jemsgeology.geology.GeoMapBuilder;
import com.jemmerl.jemsgeology.geology.deposits.DepositUtil;
import com.jemmerl.jemsgeology.geology.deposits.IDeposit;
import com.jemmerl.jemsgeology.geology.deposits.IEnqueuedDeposit;
import com.jemmerl.jemsgeology.util.Pair;
import com.jemmerl.jemsgeology.util.WeightedProbMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DiatremeMaarUtilDeposit implements IEnqueuedDeposit {

    private static DiatremeMaarUtilDeposit INSTANCE = null;

    private final String name;
    private final WeightedProbMap<GradeType> gradesMap;
    private final ArrayList<GeologyType> validList;
    private final ArrayList<Biome.Category> validBiomes;

    // Utility deposit for diatreme-maar diamond and olivine placement
    private DiatremeMaarUtilDeposit() {
        this.name = "diatreme_maar";

        ArrayList<Pair<Integer, GradeType>> elts = new ArrayList<>();
        elts.add( new Pair<>(1, GradeType.HIGHGRADE));
        elts.add( new Pair<>(9, GradeType.MIDGRADE));
        elts.add( new Pair<>(90, GradeType.LOWGRADE));
        this.gradesMap = new WeightedProbMap<>(elts);

        this.validList = new ArrayList<>(Arrays.asList(GeologyType.values()));
        this.validBiomes = new ArrayList<>(Arrays.asList(Biome.Category.values()));
    }

    // Singleton pattern
    public static DiatremeMaarUtilDeposit getDepositInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DiatremeMaarUtilDeposit();
        }
        return INSTANCE;
    }


    /////////////
    // SETTERS //
    /////////////

    @Override
    public IDeposit setName(String name) { return this; }

    @Override
    public IDeposit setOres(WeightedProbMap<OreType> oreMap) { return this; }

    @Override
    public IDeposit setGrades(WeightedProbMap<GradeType> grades) { return this; }

    @Override
    public IDeposit setValid(ArrayList<GeologyType> validList) { return this; }

    @Override
    public IDeposit setBiomes(ArrayList<Biome.Category> validBiomes) { return this; }


    /////////////
    // GETTERS //
    /////////////

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DepositType getType() {
        return DepositType.UTIL;
    }

    @Override
    public WeightedProbMap<OreType> getOres() {
        return null;
    }

    @Override
    public WeightedProbMap<GradeType> getGrades() {
        return gradesMap;
    }

    @Override
    public ArrayList<GeologyType> getValid() {
        return validList;
    }

    @Override
    public ArrayList<Biome.Category> getBiomes() {
        return validBiomes;
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public boolean generate(ChunkReader reader, Random rand, BlockPos pos, GeoMapBuilder geoMapBuilder) {
        return false;
    }

    public boolean enqDiamondOre(ISeedReader reader, BlockPos pos) {
        DepositUtil.processOreEnqueue(reader, pos, OreType.DIAMOND, gradesMap.nextElt(), this);
        return true;
    }
}
