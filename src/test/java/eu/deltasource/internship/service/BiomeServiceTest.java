package eu.deltasource.internship.service;

import com.google.gson.*;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepository;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BiomeServiceTest {
    private AnimalService animalService;
    private GroupService groupService;
    private BiomeRepository biomeRepository;
    private BiomeService biomeService;
    
    @BeforeEach
    public void setUp() {
        animalService = new AnimalService();
        groupService = new GroupService();
        biomeRepository = new BiomeRepositoryImpl();
        biomeService = new BiomeService();
    }

    @Test
    public void testIfAnimalRepositoriesAreUpdatedCorrectly() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonObject asJsonObject = parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\testAnimals.json")).getAsJsonObject();
        Gson gson = new GsonBuilder().create();
        BiomeEnum biomeEnum = BiomeEnum.DESERT;
        
        biomeService.updateAnimalsRepositories(biomeEnum, gson, asJsonObject);
    
        List<Carnivore> carnivores = biomeService.getAnimalService().getCarnivores();
        List<Herbivore> herbivores = biomeService.getAnimalService().getHerbivores();
        
        assertEquals(21, herbivores.size());
        assertEquals(8, carnivores.size());
    }
}