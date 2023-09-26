package com.jemmerl.jemsgeology.geology;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;

public class GeoWrapper {
    private GeologyType geologyType;
    private OreType oreType;
    private GradeType gradeType;

    public GeoWrapper(GeologyType geologyType, OreType oreType, GradeType gradeType) {
        this.geologyType = geologyType;
        this.oreType = oreType;
        this.gradeType = gradeType;
    }

    public void setGeologyType(GeologyType geologyType) { this.geologyType = geologyType; }
    public void setOreType(OreType oreType) { this.oreType = oreType; }
    public void setGradeType(GradeType gradeType) { this.gradeType = gradeType; }

    public GeologyType getGeologyType() { return geologyType; }
    public OreType getOreType() { return oreType; }
    public GradeType getGradeType() { return gradeType; }
}
