package com.jemmerl.jemsgeology.geology;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;

public class GeoWrapper {
    private GeologyType geologyType;
    private OreType oreType;
    private GradeType gradeType;
    //private boolean isRegolith;

    public GeoWrapper(GeologyType geologyType, OreType oreType, GradeType gradeType) {
        this.geologyType = geologyType;
        this.oreType = oreType;
        this.gradeType = gradeType;
        //this.isRegolith = isRegolith;
    }

    public void setGeologyType(GeologyType geologyType) { this.geologyType = geologyType; }
    public void setOreType(OreType oreType) { this.oreType = oreType; }
    public void setGradeType(GradeType gradeType) { this.gradeType = gradeType; }
    //public void setRegolith(boolean isRegolith) { this.isRegolith = isRegolith; }

    public GeologyType getGeologyType() { return geologyType; }
    public OreType getOreType() { return oreType; }
    public GradeType getGradeType() { return gradeType; }
    //public GradeType isRegolith() { return isRegolith; }
}
