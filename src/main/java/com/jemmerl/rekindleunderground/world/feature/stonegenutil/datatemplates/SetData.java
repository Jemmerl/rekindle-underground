package com.jemmerl.rekindleunderground.world.feature.stonegenutil.datatemplates;

public class SetData {
    private String name;
    private String[] blocks;

    public SetData(String name, String[] blocks) {
        this.name = name;
        this.blocks = blocks;
    }

    public String getName() {
        return this.name;
    }
    public String[] getBlocks() {
        return this.blocks;
    }

    public void setName(String name) { this.name = name; }
    public void setBlocks(String[] blocks) {
        this.blocks = blocks;
    }
}
