package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.util.noise.GenerationNoise.*;

public class NoiseInit {

    public static boolean configured = false;

    // Initialize and configure the various noise generators that are used in more than one class
    public static void init(long seed) {
        BlobNoise.configNoise(seed);
        BlobWarpNoise.configNoise(seed);
        RegionNoise.configNoise(seed);
        StrataNoise.configNoise(seed);
        DikeSillNoise.configNoise(seed);
        configured = true;
    }
}
