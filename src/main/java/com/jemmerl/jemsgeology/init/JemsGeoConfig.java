package com.jemmerl.jemsgeology.init;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class JemsGeoConfig {
    public static class Common
    {
        private static final int HARDNESS_DEPTH_FACTOR = 3; // 1 - 10 scale; 1 is no change by depth
        private static final int REGION_SIZE = 512; // Rough average, in chunks (OG IS 128)
        private static final double REGIONAL_VARIATION = 0.50; // 0.00 - 1.00 scale
        private static final int FAULT_SIZE = 128; // Rough average, in chunks (OG IS 64)
        private static final double FAULT_VARIATION = 0.20; // 0.00 - 1.00 scale; 0 is no fault shift

        // 0 to 100 scale, setting min = max will ensure that value will always be used
        private static final int THICK_MIN = 10;
        private static final int THICK_MAX = 40;
        private static final int WARP_MIN = 10;
        private static final int WARP_MAX = 75;
        private static final int TILT_MIN = 20;
        private static final int TILT_MAX = 100;

        private static final int STONE_HARDNESS = 20; // Multiply relative stone hardnesses
        private static final int STONE_RESISTANCE = 6; // Multiply relative stone resistances
        private static final boolean DET_SCALING = false; // Apply hardness depth scaling to detritus blocks

        private static final double PLACER_CHANCE = 1.00; // 0.00 - 1.00 scale, chance of placer deposit attempt in chunk; 0 is no placers

        private static final boolean GEN_MAAR_DIATREMES = true; // Generate maar-diatremes? TODO why tf is a '?' here
        private static final boolean GEN_BATHOLITHS = true; // Generate batholiths

        // Debug options
        private static final boolean DEBUG_DIATREME_MAAR = false;
        private static final boolean DEBUG_BATHOLITHS = false;
        private static final boolean DEBUG_TEST_GENFEATURES = true;
        private static final boolean DEBUG_GENFEATURES_READER = false;
        private static final boolean DEBUG_TEST_DEPOSITS = true;
        private static final boolean DEBUG_DEPOSIT_READER = false;
        private static final boolean DEBUG_LAYER_DEPOSITS = false;
        private static final boolean DEBUG_PLACER_DEPOSITS = false;
        private static final boolean DEBUG_BLOCK_ENQUEUER = false;

        // TODO make placers and maar spawn in every chunk in registry, let config determine internal chance
        // solves issue of registration with configs

        public final ForgeConfigSpec.ConfigValue<Integer> hardnessDepthFactor;
        public final ForgeConfigSpec.ConfigValue<Integer> regionSize;
        public final ForgeConfigSpec.ConfigValue<Double> regionVariation;
        public final ForgeConfigSpec.ConfigValue<Integer> faultSize;
        public final ForgeConfigSpec.ConfigValue<Double> faultVariation;

        public final ForgeConfigSpec.ConfigValue<Integer> thickMin;
        public final ForgeConfigSpec.ConfigValue<Integer> thickMax;
        public final ForgeConfigSpec.ConfigValue<Integer> warpMin;
        public final ForgeConfigSpec.ConfigValue<Integer> warpMax;
        public final ForgeConfigSpec.ConfigValue<Integer> tiltMin;
        public final ForgeConfigSpec.ConfigValue<Integer> tiltMax;

        public final ForgeConfigSpec.ConfigValue<Integer> stoneHardness;
        public final ForgeConfigSpec.ConfigValue<Integer> stoneResistance;
        public final ForgeConfigSpec.ConfigValue<Boolean> detritusScaling;

        public final ForgeConfigSpec.ConfigValue<Double> placerChance;

        public final ForgeConfigSpec.ConfigValue<Boolean> gen_maar_diatremes;
        public final ForgeConfigSpec.ConfigValue<Boolean> gen_batholiths;

        public final ForgeConfigSpec.ConfigValue<Boolean> debug_diatreme_maar;
        public final ForgeConfigSpec.ConfigValue<Boolean> debug_batholiths;
        public final ForgeConfigSpec.ConfigValue<Boolean> debug_test_genfeatures;
        public final ForgeConfigSpec.ConfigValue<Boolean> debug_genfeature_reader;
        public final ForgeConfigSpec.ConfigValue<Boolean> debug_test_deposits;
        public final ForgeConfigSpec.ConfigValue<Boolean> debug_deposit_reader;
        public final ForgeConfigSpec.ConfigValue<Boolean> debug_layer_deposits;
        public final ForgeConfigSpec.ConfigValue<Boolean> debug_placer_deposits;
        public final ForgeConfigSpec.ConfigValue<Boolean> debug_block_enqueuer;

        public Common(ForgeConfigSpec.Builder builder)
        {
//            // General Config
//            builder.push("General Config");
//
//            builder.pop();
//            // End General Config


            // Ore Config
            builder.push("Ore Generation");
            this.placerChance = builder.comment("Sets chance of a chunk attempting to spawn placer deposits; Recommended Default is 0.25")
                    .worldRestart()
                    .defineInRange("Placer Chance", PLACER_CHANCE, 0.0, 1.0);

            builder.pop();
            // End Ore Config


            // Igneous Config
            builder.push("Igneous Features Generation");
            this.gen_maar_diatremes = builder.comment("Enable Maar-Diatreme generation; Default true")
                    .worldRestart()
                    .define("Generate Maar-Diatremes", GEN_MAAR_DIATREMES);
            this.gen_batholiths = builder.comment("Enable Batholith generation; Default true")
                    .worldRestart()
                    .define("Generate Batholiths", GEN_BATHOLITHS);
            builder.pop();
            // End Igneous Config


            // Layer Config
            builder.push("Layer Generation");

            builder.push("Region Config");
            this.regionSize = builder.comment("Sets average stone region area in chunks; Recommended Default is 128")
                    .worldRestart()
                    .defineInRange("Region Size", REGION_SIZE, 16, 2048);

            this.regionVariation = builder.comment("Sets general difference between regions; Recommended Default is 0.50")
                    .worldRestart()
                    .defineInRange("Regional Variation", REGIONAL_VARIATION, 0.0, 1.0);
            builder.pop();

            builder.push("Fault Config");
            this.faultSize = builder.comment("Sets average plate area in chunks; Recommended Default is 64")
                    .worldRestart()
                    .defineInRange("Fault Size", FAULT_SIZE, 16, 2048);

            this.faultVariation = builder.comment("Sets the degree of range for fault shift; Recommended Default is 0.20")
                    .worldRestart()
                    .defineInRange("Fault Variation", FAULT_VARIATION, 0.0, 1.0);
            builder.pop();

            builder.comment("Maximum config values must be greater than or equal to the minimum values, and vice versa.\n" +
                    "Else, they will be dramatically corrected, resulting in potentially unexpected results").push("Strata Config");

            builder.push("Thickness");
            this.thickMin = builder.comment("Define the the minimum possible degree of strata thickness; Recommended Default is 10")
                    .worldRestart()
                    .defineInRange("Minimum Thickness", THICK_MIN, 0, 100);
            this.thickMax = builder.comment("Define the the maximum possible degree of strata thickness; Recommended Default is 40")
                    .worldRestart()
                    .defineInRange("Maximum Thickness", THICK_MAX, 0, 100);
            builder.pop();

            builder.push("Warp");
            this.warpMin = builder.comment("Define the the minimum possible degree of strata warp; Recommended Default is 10")
                    .worldRestart()
                    .defineInRange("Minimum Warp", WARP_MIN, 0, 100);
            this.warpMax = builder.comment("Define the the maximum possible degree of strata warp; Recommended Default is 75")
                    .worldRestart()
                    .defineInRange("Maximum Warp", WARP_MAX, 0, 100);
            builder.pop();

            builder.push("Tilt");
            this.tiltMin = builder.comment("Define the the minimum possible degree of strata tilt; Recommended Default is 20")
                    .worldRestart()
                    .defineInRange("Minimum Tilt", TILT_MIN, 0, 100);
            this.tiltMax = builder.comment("Define the the maximum possible degree of strata tilt; Recommended Default is 100")
                    .worldRestart()
                    .defineInRange("Maximum Tilt", TILT_MAX, 0, 100);
            builder.pop();

            builder.pop(2);
            // End Layer Config


            // Stone Block Config
            builder.push("Stone Property Config");
            this.hardnessDepthFactor = builder.comment("Sets the overall hardness scaling factor of stone from y = 50 to y = 0; Recommended Default is 3")
                    .worldRestart()
                    .defineInRange("Hardness Depth Factor", HARDNESS_DEPTH_FACTOR, 1, 10);
            this.detritusScaling = builder.comment("Toggle detritus depth scaling with other stones; Default is False")
                    .worldRestart()
                    .define("Detritus Depth Scaling", DET_SCALING);
            this.stoneHardness = builder.comment("Set the multiplier for relative stone hardnesses; Recommended Default is 20")
                    .worldRestart()
                    .defineInRange("Hardness Multiplier", STONE_HARDNESS, 1, 50);
            this.stoneResistance = builder.comment("Set the multiplier for relative stone resistances; Recommended Default is 6")
                    .worldRestart()
                    .defineInRange("Resistance Multiplier", STONE_RESISTANCE, 1, 30);
            builder.pop();
            // End Stone Block Config


            // Debug Config
            builder.push("Debug Options");
            this.debug_diatreme_maar = builder.comment("Enable diatreme-maar debug mode")
                    .worldRestart()
                    .define("Debug Diatreme-Maars", DEBUG_DIATREME_MAAR);
            this.debug_batholiths = builder.comment("Enable batholith debug mode")
                    .worldRestart()
                    .define("Debug Diatreme-Maars", DEBUG_BATHOLITHS);
            this.debug_test_genfeatures = builder.comment("Enable generated features for testing (have \"test\" in their name) debug mode")
                    .worldRestart()
                    .define("Debug Test Generated Features", DEBUG_TEST_GENFEATURES);
            this.debug_genfeature_reader = builder.comment("Enable generated feature config reader debug mode")
                    .worldRestart()
                    .define("Debug Generated Features Reader", DEBUG_GENFEATURES_READER);
            this.debug_test_deposits = builder.comment("Enable deposits for testing (have \"test\" in their name) debug mode")
                    .worldRestart()
                    .define("Debug Test Deposits", DEBUG_TEST_DEPOSITS);
            this.debug_deposit_reader = builder.comment("Enable deposit config reader debug mode")
                    .worldRestart()
                    .define("Debug Deposit Reader", DEBUG_DEPOSIT_READER);
            this.debug_layer_deposits = builder.comment("Enable layer deposit debug mode")
                    .worldRestart()
                    .define("Debug Layer Deposits", DEBUG_LAYER_DEPOSITS);
            this.debug_placer_deposits = builder.comment("Enable placer deposit debug mode")
                    .worldRestart()
                    .define("Debug Placers Deposits", DEBUG_PLACER_DEPOSITS);
            this.debug_block_enqueuer = builder.comment("Enable block enqueuer debug mode")
                    .worldRestart()
                    .define("Debug Block Enqueuer", DEBUG_BLOCK_ENQUEUER);
            builder.pop();
            // End Debug Config

        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static //constructor
    {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }
}
