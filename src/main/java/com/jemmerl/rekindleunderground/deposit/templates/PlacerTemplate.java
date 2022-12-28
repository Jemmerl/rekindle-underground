package com.jemmerl.rekindleunderground.deposit.templates;

public class PlacerTemplate {

    private final int weight;
    private final int avg_radius;
    private final int min_density;
    private final int max_density;

    public PlacerTemplate(int weight, int avg_radius, int min_density, int max_density) {
        this.weight = weight;
        this.avg_radius = Math.max(avg_radius, 3);
        this.min_density = min_density;
        this.max_density = max_density;
    }

    public int getWeight() { return this.weight; }
    public int getAvgRadius() { return this.avg_radius; }
    public int getMinDensity() { return this.min_density; }
    public int getMaxDensity() { return this.max_density; }

}