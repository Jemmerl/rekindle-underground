package com.jemmerl.rekindleunderground.deposit.templates;

public class LayerTemplate {

    private final int weight;
    private final int avg_radius;
    private final int max_layers;
    private final int avg_layer_thickness;
    private final int min_density;
    private final int max_density;
    private final int min_yheight;
    private final int max_yheight;

    public LayerTemplate(int weight, int avg_radius, int max_layers, int avg_layer_thickness,
                         int min_density, int max_density, int min_yheight, int max_yheight) {
        this.weight = weight;
        this.avg_radius = Math.max(avg_radius, 3);
        this.max_layers = Math.max(max_layers, 1);
        this.avg_layer_thickness = Math.max(avg_layer_thickness, 1);
        this.min_density = min_density;
        this.max_density = max_density;
        this.min_yheight = min_yheight;
        this.max_yheight = max_yheight;
    }

    public int getWeight() { return this.weight; }
    public int getAvgRadius() { return this.avg_radius; }
    public int getMaxLayers() { return this.max_layers; }
    public int getAvgLayerThick() { return this.avg_layer_thickness; }
    public int getMinDensity() { return this.min_density; }
    public int getMaxDensity() { return this.max_density; }
    public int getMinYHeight() { return this.min_yheight; }
    public int getMaxYHeight() { return this.max_yheight; }

}
