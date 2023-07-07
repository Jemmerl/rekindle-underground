package com.jemmerl.jemsgeology.data.enums.igneous;

public enum DikeType {
    NONE,
    ONE, // One dike type
    TWO, // Two dikes, one overlaid on another (newer/older)
    LINEAR_SWARM, // Eroded out "roots" of a flood basalt
    LINEAR_SWARM_ONE; // Linear swarm with one newer dike overlaid
    //RADIAL_SWARM; // Implement if doable later -- Need to find center of cellular noise reason
}
