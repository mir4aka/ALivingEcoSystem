package eu.deltasource.internship.repository.BiomeRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Biome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BiomeRepository {
    Map<Biome, List<Animal>> animalsInBiome = new HashMap<>();
    
    void addBiomeAndAnimals(String biome, Animal... animals);
    
    Map<String, List<Animal>> getAnimalsInBiome();
    
    String getBiome();
}
