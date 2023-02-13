package eu.deltasource.internship.BiomeRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Biome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BiomeRepository {
     Map<Biome, List<Animal>> animalsInBiome = new HashMap<>();
     
     void addBiomeAndAnimals(Biome biome, Animal... animals);
     Map<Biome, List<Animal>> getAnimalsInBiome();
     String getBiome();
}
