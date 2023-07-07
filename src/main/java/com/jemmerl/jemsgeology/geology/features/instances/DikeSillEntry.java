package com.jemmerl.jemsgeology.geology.features.instances;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.geology.features.templates.DikeSillTemplate;

public class DikeSillEntry {

    private final DikeSillTemplate dikeSillTemplate;

    private String name;
    private GeologyType genStone;


    public DikeSillEntry(DikeSillTemplate template) {
        this.dikeSillTemplate = template;
    }

    /////////////
    // SETTERS //
    /////////////

    public DikeSillEntry setName(String name) {
        this.name = name;
        return this;
    }

    public DikeSillEntry setStone(GeologyType genStone) {
        this.genStone = genStone;
        return this;
    }


    /////////////
    // GETTERS //
    /////////////

    public String getName() {
        return this.name;
    }

    public GeologyType getStone() {
        return this.genStone;
    }

    public int getSeed() {
        return this.dikeSillTemplate.getSeed();
    }



}
