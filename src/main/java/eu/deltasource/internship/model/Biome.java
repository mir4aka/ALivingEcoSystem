package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.BiomeList;

public class Biome {
    private BiomeList biome;
    
    public Biome(BiomeList biome) {
        this.biome = biome;
    }
    
    @Override
    public String toString() {
        return biome.toString();
    }
}
