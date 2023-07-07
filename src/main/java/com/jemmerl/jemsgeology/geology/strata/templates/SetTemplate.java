package com.jemmerl.jemsgeology.geology.strata.templates;

public class SetTemplate {
    private String name;
    private String[] blocks;

    public SetTemplate(String name, String[] blocks) {
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
