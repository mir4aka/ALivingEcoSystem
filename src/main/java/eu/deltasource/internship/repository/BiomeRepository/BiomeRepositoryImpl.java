package eu.deltasource.internship.repository.BiomeRepository;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Biome;

import java.util.*;

public class BiomeRepositoryImpl implements BiomeRepository {
    private Map<Biome, List<Animal>> animalsInBiome = new HashMap<>();
    
    @Override
    public void addBiomeAndAnimals(Biome biome, Animal... animals) {
        for (Animal animal : animals) {
            if (!animalsInBiome.containsKey(biome)) {
                animalsInBiome.put(biome, new ArrayList<>());
            } else {
                animalsInBiome.get(biome).add(animal);
            }
        }
    }
    
    @Override
    public BiomeEnum findBiome(Biome biome) {
        if(!animalsInBiome.containsKey(biome)) {
            throw new IllegalArgumentException("No such biome exception.");
        }
        String biom = biome.toString();
        return BiomeEnum.valueOf(biom);
    }
}
