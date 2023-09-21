package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.geology.deposits.DepositUtil;
import com.jemmerl.jemsgeology.geology.deposits.instances.ConstantScatterDeposit;
import com.jemmerl.jemsgeology.init.depositinit.DepositRegistrar;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class OreConstantScatterFeature extends Feature<NoFeatureConfig>{

    public OreConstantScatterFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    // TODO can impliment the scatter blob idea with ta noise function.
    // the feature finds the position, the Util places the ore
    // so make new deposit type with that capability! ez. dont over think this. really, it all makes sense

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        // Configure noise if not done so
        if (!NoiseInit.configured) {
            NoiseInit.init(reader.getSeed());
        }

        // Iterate over each scatter deposit
        for (ConstantScatterDeposit depositEntry : DepositRegistrar.getConstScatterDeposits().values()) {

            // Attempt to generate each ore blob
            int spawnTries = depositEntry.getSpawnTries();
            for (int tries = 0; tries < spawnTries; tries++) {

                // Get position in chunk within valid height range
                int yPos = rand.nextInt(depositEntry.getMaxHeight() - depositEntry.getMinHeight() + 1) + depositEntry.getMinHeight();
                BlockPos startPos = pos.add(rand.nextInt(16), yPos, rand.nextInt(16));

                // Check for valid biome placement; if not in a valid biome, skip placement try
                if (!depositEntry.getBiomes().contains(reader.getBiome(pos).getCategory())) {
                    continue;
                }

//                TODO this may be needed for handling 2-14(15?)
//                if (depositEntry.isAccurateSize()) {
//
//                } else {
//
//                }

                if (depositEntry.getSize() == 1) {
                    GradeType grade = depositEntry.getGrades().nextElt();
                    DepositUtil.placeDepositOre(reader, depositEntry, grade, startPos);
                } else {
                    DepositUtil.genMinecraftOre(reader, generator, rand, startPos, depositEntry);
                }
            }
        }

        return true;
    }
}

//TODO can easily implement this if desired
//https://www.reddit.com/r/minecraftsuggestions/comments/lrpmhm/ore_generation_variation_between_chunks/

