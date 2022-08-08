package com.jemmerl.rekindleunderground.world.feature.stonegeneration.datatemplates;

public class PresetTemplate {
    private String name;
    private String[] sets;
    private String[] individuals;

    public PresetTemplate(String name, String[] sets, String[] individuals) {
        this.name = name;
        this.sets = sets;
        this.individuals = individuals;
    }

    public String getName() { return this.name; }
    public String[] getSets() { return this.sets; }
    public String[] getIndividuals() { return this.individuals; }

    public void setName(String name) { this.name = name; }
    public void setSets(String[] sets) { this.sets = sets; }
    public void setIndividuals(String[] individuals) { this.individuals = individuals; }
}
