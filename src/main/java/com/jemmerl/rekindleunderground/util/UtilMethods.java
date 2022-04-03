package com.jemmerl.rekindleunderground.util;

public class UtilMethods {

    // Returns a float over a given range mapped to a new range
    public static float remap(float oldVal, float[] oldDomain, float[] newDomain) {
        float oldRange = oldDomain[1] - oldDomain[0];
        float oldRangeValue = oldVal - oldDomain[0];
        float percentile = oldRangeValue / oldRange;

        float newRange = newDomain[1] - newDomain[0];
        float newRangeValue = percentile * newRange;
        float newVal = newRangeValue + newDomain[0];

        return newVal;
    }

    // Returns the unit vector for a given vector of any size
    public static double[] getUnitVector(double[] vals) {
        double squareSum = 0;
        for (double n:vals) {
            squareSum += n*n;
        }
        double magnitude = Math.cbrt(squareSum);
        double[] unitVec = new double[vals.length];
        for (int i = 0; i < vals.length; i++) {
            unitVec[i] = (vals[i] / magnitude);
        }
        return unitVec;
    }

}
