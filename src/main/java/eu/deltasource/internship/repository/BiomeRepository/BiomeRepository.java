package eu.deltasource.internship.repository.BiomeRepository;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Biome;

public interface BiomeRepository {
    
    void addBiomeAndAnimals(Biome biome, Animal... animals);
    
    BiomeEnum findBiome(Biome biome);
}
