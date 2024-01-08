package com.jemmerl.jemsgeology.geology.deposits;

import com.jemmerl.jemsgeology.geology.ChunkReader;
import com.jemmerl.jemsgeology.geology.GeoMapBuilder;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public interface IEnqueuedDeposit extends IDeposit {

    boolean generate(ChunkReader reader, Random rand, BlockPos pos, GeoMapBuilder geoMapBuilder);

}

// deposit _> placer or vein -> subcategories of each

/*
Shapes:
 disk to blobby-disk
 LIKE: coals
 - can be layered over top of eachother

 spherical to blobular

vein (thick to thin)
 LIKE: hydrothermal
    - singular to cluster

 */
// TODO TODO TODO
// generate with radius using random blob like the diatrememaar
// REWORK LAYER WAVYNESS TO USE A SINGLE, GLOBAL HEIGHT MORPHER!!
// ALLOW ORES TO ACCESS IT! WILL LET ORES FOLLOW THE EFFECTS!

// TODO
// check if block being set is within the active chunk
// if so, continue. if not, enqueue!
// TEMP: just make it get ignored :P
