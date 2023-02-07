package eu.deltasource.internship;

import eu.deltasource.internship.model.Biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BiomeRepository {
    private List<Biome> biomes;

    public BiomeRepository() {
        this.biomes = new ArrayList<>();
    }

    public List<Biome> getBiomes() {
        return Collections.unmodifiableList(biomes);
    }

    public void addBiome(Biome biome) {
        biomes.add(biome);
    }
}
