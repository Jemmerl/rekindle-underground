package com.jemmerl.jemsgeology.geology.deposits.templates;

public class ConstantScatterTemplate {

    private final int chunk_spawn_tries;
    private final boolean accurate_size;
    private final int max_height;
    private final int min_height;
    private final int size;

    public ConstantScatterTemplate(int chunk_spawn_tries, int size, int max_height, int min_height, boolean accurate_size) {
        this.chunk_spawn_tries = chunk_spawn_tries;
        this.accurate_size = accurate_size;
        this.max_height = max_height;
        this.min_height = min_height;

        if (accurate_size) {
            this.size = adjustSize(size);
        } else {
            this.size = size;
        }
    }

    public int getSpawnTries() { return chunk_spawn_tries; }
    public int getSize() { return size; }
    public int getMaxHeight() { return max_height; }
    public int getMinHeight() { return min_height; }
    public boolean isAccurateSize () { return accurate_size; }


    /*
        TODO needs testing with a tool. Can be refined for sure, as current model is based on 1.12 data
        Can adjust the input size to be approximately accurate, compared to
        Minecraft's default that increases exponentially in error past size ~ 15.
        Accurate to ~+/-5 for 1-150, and ~+/-10 for 151-300
        >300 overshoots worse than Minecraft into the 1000's
    */
    private int adjustSize(int size) {
        int adjSize = (int)Math.ceil(
                ((0.000003 * Math.pow(size, 3)) - (0.0018 * Math.pow(size, 2)) + (0.4265 * size) + 5.667) * 0.95);

        if (adjSize > 293) {
            // 294 to inf: -2
            adjSize -= 2;
        } else if (adjSize > 277) {
            // 278 to 293: -1
            adjSize -= 1;
        } else if (adjSize > 165) {
            // 166 to 277: 0
        } else if (adjSize > 95) {
            // 96 to 165: -1
            adjSize -= 1;
        } else if (adjSize > 68) {
            // 69 to 95: 0
        } else if (adjSize > 55) {
            // 56 to 68: +1
            adjSize += 1;
        } else if (adjSize > 14) {
            // 15 to 55: +2
            adjSize += 2;
        } else {
            // TODO not yet handled (except 1)
        }

        return adjSize;
    }
}