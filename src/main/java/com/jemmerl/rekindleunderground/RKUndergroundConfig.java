package com.jemmerl.rekindleunderground;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class RKUndergroundConfig {
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

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("General Config");
            this.hardnessDepthFactor = builder.comment("Sets the overall hardness scaling factor of stone from y = 50 to y = 0; Recommended Default is 3")
                    .worldRestart()
                    .defineInRange("Hardness Depth Factor", HARDNESS_DEPTH_FACTOR, 1, 10);
            builder.pop();

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

            builder.push("Stone Property Config");
            this.stoneHardness = builder.comment("Set the multiplier for relative stone hardnesses; Recommended Default is 10")
                    .worldRestart()
                    .defineInRange("Hardness Multiplier", STONE_HARDNESS, 1, 50);
            this.stoneResistance = builder.comment("Set the multiplier for relative stone resistances; Recommended Default is 6")
                    .worldRestart()
                    .defineInRange("Resistance Multiplier", STONE_RESISTANCE, 1, 30);
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
