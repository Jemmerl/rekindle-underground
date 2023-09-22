package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.sun.org.apache.xpath.internal.operations.Or;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BaseGeoBlock extends Block implements IGeoBlock {

    private final GeologyType geologyType;
    private final OreType oreType;
    private final GradeType gradeType;
    private final StoneGroupType stoneGroupType; // is this even useful now? maybe replace with tags

    public BaseGeoBlock(AbstractBlock.Properties properties, GeologyType geologyType, OreType oreType, GradeType gradeType) {
        super(properties);
        this.geologyType = geologyType;
        this.oreType = oreType;
        this.gradeType = gradeType;
        this.stoneGroupType = geologyType.getGroup();
        // Blockstates may be useful for determining natural vs placed stones, if I want to do custom cavein stuff.
        // Keeping the infrastructure
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
        return this.oreType
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
