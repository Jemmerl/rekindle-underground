package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import net.minecraft.block.FallingBlock;

public class FallingGeoBlock extends FallingBlock implements IGeoBlock {

    private final GeologyType geologyType;
    private final OreType oreType;
    private final GradeType gradeType;
    private final StoneGroupType stoneGroupType;

    public FallingGeoBlock(Properties properties, GeologyType geologyType, OreType oreType, GradeType gradeType) {
        super(properties);
        this.geologyType = geologyType;
        this.oreType = oreType;
        this.gradeType = gradeType;
        this.stoneGroupType = geologyType.getGroup();
        //this.setDefaultState(this.stateContainer.getBaseState().with(ORE_TYPE, OreType.NONE).with(GRADE_TYPE, GradeType.LOWGRADE));
    }

//    @Override
//    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
//        builder.add(ORE_TYPE);
//        builder.add(GRADE_TYPE);
//        super.fillStateContainer(builder);
//    }

    @Override
    public GeologyType getGeologyType() {
        return this.geologyType;
    }

    @Override
    public OreType getOreType() {
        return this.oreType;
    }

    @Override
    public GradeType getGradeType() {
        return this.gradeType;
    }

    @Override
    public StoneGroupType getStoneGroupType() {
        return this.stoneGroupType;
    }
}