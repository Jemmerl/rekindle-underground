package com.jemmerl.rekindleunderground.init;

import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.ConfiguredBlobNoise;

public class NoiseInit {

    public static boolean configured = false;

    // Initialize and configure the various noise generators that are used in more than one class
    public static void init(long seed) {
        ConfiguredBlobNoise.configNoise(seed);
        configured = true;
    }
}
