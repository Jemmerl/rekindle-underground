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

        public final ForgeConfigSpec.ConfigValue<Integer> hardnessDepthFactor;
        public final ForgeConfigSpec.ConfigValue<Integer> regionSize;
        public final ForgeConfigSpec.ConfigValue<Double> regionVariation;
        public final ForgeConfigSpec.ConfigValue<Integer> faultSize;
        public final ForgeConfigSpec.ConfigValue<Double> faultVariation;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("General Config");
            this.hardnessDepthFactor = builder.comment("Sets the overall hardness scaling factor of stone from y = 50 to y = 0; Recommended Default is 3")
                    .worldRestart()
                    .defineInRange("Hardness Depth Factor", HARDNESS_DEPTH_FACTOR, 1, 10);

            builder.pop();

            builder.push("Stone Generation");
            this.regionSize = builder.comment("Sets average stone region area in chunks; Recommended Default is 128")
                    .worldRestart()
                    .defineInRange("Region Size", REGION_SIZE, 16, 2048);

            this.regionVariation = builder.comment("Sets general difference between regions; Recommended Default is 0.50")
                    .worldRestart()
                    .defineInRange("Regional Variation", REGIONAL_VARIATION, 0.0, 1.0);

            this.faultSize = builder.comment("Sets average plate area in chunks; Recommended Default is 64")
                    .worldRestart()
                    .defineInRange("Fault Size", FAULT_SIZE, 16, 2048);

            this.faultVariation = builder.comment("Sets the degree of range for fault shift; Recommended Default is 0.20")
                    .worldRestart()
                    .defineInRange("Fault Variation", FAULT_VARIATION, 0.0, 1.0);

            builder.pop();
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
