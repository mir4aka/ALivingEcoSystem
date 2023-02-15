package eu.deltasource.internship.repository.BiomeRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Biome;

import java.util.*;

public class BiomeRepositoryImpl implements BiomeRepository {
    private Map<Biome, List<Animal>> animalsInBiome = new HashMap<>();
    private String biome;
    
    @Override
    public void addBiomeAndAnimals(Biome biome, Animal... animals) {
        this.biome = String.valueOf(biome);
        for (Animal animal : animals) {
            if (!animalsInBiome.containsKey(biome)) {
                animalsInBiome.put(biome, new ArrayList<>());
            } else {
                animalsInBiome.get(biome).add(animal);
            }
        }
    }
    
    public Map<Biome, List<Animal>> getAnimalsInBiome() {
        return Collections.unmodifiableMap(animalsInBiome);
    }
    
    public String getBiome() {
        return biome;
    }
}
