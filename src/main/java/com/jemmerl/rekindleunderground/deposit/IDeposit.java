package com.jemmerl.rekindleunderground.deposit;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;

import java.util.Random;

public interface IDeposit {

    boolean generate(ISeedReader reader, Random rand, BlockPos pos, BlockState[][][] stateMap);

    int getWeight();

    // Oreblocks it can generate in

    //

    //
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