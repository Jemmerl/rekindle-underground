package com.jemmerl.jemsgeology.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public static class Common {

        private static final int HARDNESS_DEPTH_FACTOR = 3; // 1 - 10 scale; 1 is no change by depth
        private static final int STONE_HARDNESS = 20; // Multiply relative stone hardnesses
        private static final int STONE_RESISTANCE = 6; // Multiply relative stone resistances
        private static final boolean DET_SCALING = false; // Apply hardness depth scaling to detritus blocks

        public final ForgeConfigSpec.ConfigValue<Integer> hardnessDepthFactor;
        public final ForgeConfigSpec.ConfigValue<Integer> stoneHardness;
        public final ForgeConfigSpec.ConfigValue<Integer> stoneResistance;
        public final ForgeConfigSpec.ConfigValue<Boolean> detritusScaling;

        public Common(ForgeConfigSpec.Builder builder) {
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
        }

    }

}
