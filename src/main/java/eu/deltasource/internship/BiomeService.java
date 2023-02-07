package eu.deltasource.internship;

import eu.deltasource.internship.model.Biome;

public class BiomeService {
    private BiomeRepository biomeRepository;

    public void addBiome(Biome biome) {
        biomeRepository.addBiome(biome);
    }
}
