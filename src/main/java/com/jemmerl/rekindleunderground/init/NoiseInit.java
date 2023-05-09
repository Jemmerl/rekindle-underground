package com.jemmerl.rekindleunderground.init;

import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.BlobNoise;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.DikeSillNoise;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.RegionNoise;
import com.jemmerl.rekindleunderground.util.noise.GenerationNoise.StrataNoise;

public class NoiseInit {

    public static boolean configured = false;

    // Initialize and configure the various noise generators that are used in more than one class
    public static void init(long seed) {
        BlobNoise.configNoise(seed);
        RegionNoise.configNoise(seed);
        StrataNoise.configNoise(seed);
        DikeSillNoise.configNoise(seed);
        configured = true;
    }
}
