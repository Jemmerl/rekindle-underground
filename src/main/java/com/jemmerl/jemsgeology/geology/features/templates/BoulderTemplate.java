package com.jemmerl.jemsgeology.geology.features.templates;

public class BoulderTemplate {

    private final int seed;
    private final int chance;
    private final int max_long_radius;
    private final int min_long_radius;
    private final int max_short_radius;
    private final int min_short_radius;
    private final boolean on_hills;
    private final boolean place_extra;

    public BoulderTemplate(int seed, int chance, int max_long_radius, int min_long_radius, int max_short_radius, int min_short_radius, boolean on_hills, boolean place_extra) {
        this.seed = seed;
        this.chance = chance;
        this.on_hills = on_hills;
        this.place_extra = place_extra;

        // I would rather just handle accidentally swapped values instead of making the user hunt down the error
        // Fast swap values if the long radius min is bigger than the large radius max
        if (min_long_radius > max_long_radius) {
            max_long_radius = max_long_radius ^ min_long_radius;
            min_long_radius = max_long_radius ^ min_long_radius;
            max_long_radius = max_long_radius ^ min_long_radius;
        }

        // Fast swap values if the short radius min is bigger than the short radius max
        if (min_short_radius > max_short_radius) {
            max_short_radius = max_short_radius ^ min_short_radius;
            min_short_radius = max_short_radius ^ min_short_radius;
            max_short_radius = max_short_radius ^ min_short_radius;
        }

        // Clamp values from 2 to 12
        this.max_long_radius = Math.max(2, Math.min(12, max_long_radius));
        this.min_long_radius = Math.max(2, Math.min(12, min_long_radius));
        this.max_short_radius = Math.max(2, Math.min(12, max_short_radius));
        this.min_short_radius = Math.max(2, Math.min(12, min_short_radius));
    }

    public int getSeed() {
        return this.seed;
    }

    public int getChance() {
        return this.chance;
    }

    public boolean getHillsValid() {
        return this.on_hills;
    }

    public boolean getPlaceExtra() {
        return this.place_extra;
    }

    public int getMaxLongRad() {
        return this.max_long_radius;
    }

    public int getMinLongRad() {
        return this.min_long_radius;
    }

    public int getMaxShortRad() {
        return this.max_short_radius;
    }

    public int getMinShortRad() {
        return this.min_short_radius;
    }

}
