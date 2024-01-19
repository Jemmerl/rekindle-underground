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

public class DiatremeDiaUtilDeposit implements IEnqueuedDeposit {

    private static DiatremeDiaUtilDeposit INSTANCE = null;

    private final String name;
    private final WeightedProbMap<GradeType> gradesMap;
    private final WeightedProbMap<OreType> oresMap;
    private final ArrayList<GeologyType> validList;
    private final ArrayList<Biome.Category> validBiomes;

    // Utility deposit for diatreme-maar diamond and olivine placement
    private DiatremeDiaUtilDeposit() {
        this.name = "diatreme_maar_diamond";

        ArrayList<Pair<Integer, GradeType>> gradeElts = new ArrayList<>();
        gradeElts.add( new Pair<>(1, GradeType.HIGHGRADE));
        gradeElts.add( new Pair<>(9, GradeType.MIDGRADE));
        gradeElts.add( new Pair<>(90, GradeType.LOWGRADE));
        this.gradesMap = new WeightedProbMap<>(gradeElts);

        ArrayList<Pair<Integer, OreType>> oreElts = new ArrayList<>();
        oreElts.add( new Pair<>(100, OreType.DIAMOND));
        this.oresMap = new WeightedProbMap<>(oreElts);

        this.validList = new ArrayList<>(Arrays.asList(GeologyType.values()));
        this.validBiomes = new ArrayList<>(Arrays.asList(Biome.Category.values()));
    }

    // Singleton pattern
    public static DiatremeDiaUtilDeposit getDepositInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DiatremeDiaUtilDeposit();
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
        return oresMap;
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

    // maybe rework to be the DIAMONDS diatreme util deposit, adding second for olivine... or combine somehow
    public boolean enqDiamondOre(ISeedReader reader, BlockPos pos) {
        DepositUtil.processOreEnqueue(reader, pos, OreType.DIAMOND, gradesMap.nextElt(), true, this);
        return true;
    }
}
