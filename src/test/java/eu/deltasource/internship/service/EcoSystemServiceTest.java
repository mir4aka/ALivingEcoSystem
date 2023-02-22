package eu.deltasource.internship.service;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Biome;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepository;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EcoSystemServiceTest {
    
    private EcoSystemService ecoSystemService;
    private BiomeRepository biomeRepository;
    private BiomeService biomeService;
    private AnimalService animalService;
    
    @BeforeEach
    public void setUp() {
        ecoSystemService = new EcoSystemService();
        biomeRepository = new BiomeRepositoryImpl();
        biomeService = new BiomeService();
        animalService = new AnimalService();
    }

    
    @Test
    public void testIfAppStopsWhenCarnivoresAreDead() throws IOException, InterruptedException {
        Biome biome = new Biome(BiomeEnum.DESERT);
        
        Herbivore zebra = new Herbivore("zebra", 20, 100, HabitatEnum.LAND, SocialStatus.ALONE, 1, 10, 60);
        Carnivore lion = new Carnivore("lion", 20, 150, HabitatEnum.LAND, SocialStatus.ALONE, 1, 11, 40, 80);
        
        animalService.addHerbivore(zebra);
        animalService.addCarnivore(lion);
        
        biomeRepository.addBiomeAndAnimals(biome, zebra, lion);
    
        List<Carnivore> carnivores = animalService.getCarnivores();
        List<Herbivore> herbivores = animalService.getHerbivores();
    
        //TODO FIND OUT WHY THIS METHOD DOESN'T RETURN THAT IT SHOULD(THE DESERT METHOD)
        BiomeEnum biome1 = biomeService.getBiome(biome);
    
        ecoSystemService.simulateEcoSystem(biome1);
        
        assertEquals(0, carnivores.size());
        assertEquals(0, herbivores.size());
    }
}