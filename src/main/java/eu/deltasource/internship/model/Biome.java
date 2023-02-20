package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.HabitatEnum;

public class Biome {
    private HabitatEnum habitat;
    private BiomeEnum biome;
    
    public Biome(HabitatEnum habitat, BiomeEnum biome) {
        this.habitat = habitat;
        this.biome = biome;
    }
    
    @Override
    public String toString() {
        return biome.toString();
    }
}
