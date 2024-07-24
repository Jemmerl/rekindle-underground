package com.jemmerl.jemsgeology.data.enums.igneous;

public enum IgnBodyType {
    NONE,
    VERT_DIKE, // A standard, mostly vertical dike
    DIKE_SWARM, // Eroded out "roots" of a flood basalt, many, many vertical dikes
    //RADIAL_DIKE_SWARM, // Implement if doable later -- use center of noise (works for volcanic shape well iirc)
    //RING_DIKE, // Sheet-like cone dike, almost always on top of a magma chamber (stock)
    //STOCK, // A magma chamber
    //SILL,
    LACCOLITH, // Form at shallower depths (could be deeper if formed then overlaid); smoothly deform strata above
    PUNCHED_LACCOLITH, // Laccoliths that are more like plugs, sharply deform above strata like a upwards fault
    LOPOLITH // Form at deeper depths (could be shallower if formed then eroded); deform strata below

}
