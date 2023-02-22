package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.BiomeEnum;

public class Biome {
    private BiomeEnum biome;
    
    public Biome(BiomeEnum biome) {
        this.biome = biome;
    }
    
    @Override
    public String toString() {
        return biome.toString();
    }
}
