package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.HabitatEnum;

public class Biome {
    private int biomeId;
    private HabitatEnum mainHabitat;
    private BiomeEnum biome;

    public Biome(HabitatEnum mainHabitat, BiomeEnum biome) {
        this.mainHabitat = mainHabitat;
        this.biome = biome;
        this.biomeId++;
    }

    public HabitatEnum getMainHabitat() {
        return mainHabitat;
    }

    public BiomeEnum getBiome() {
        return biome;
    }
    
    @Override
    public String toString() {
        return biome.toString();
    }
}
