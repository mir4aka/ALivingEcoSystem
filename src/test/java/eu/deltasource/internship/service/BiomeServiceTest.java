package eu.deltasource.internship.service;

import com.google.gson.*;
import eu.deltasource.internship.enums.BiomeList;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.service.helper.SuccessChanceCalculator;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BiomeServiceTest {
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private SuccessChanceCalculator successChanceCalculator = new SuccessChanceCalculator();
    private AnimalService animalService = new AnimalService(herbivoreRepository, carnivoreRepository, groupRepository, successChanceCalculator);
    private GroupService groupService = new GroupService(groupRepository, animalService);
    private BiomeService biomeService = new BiomeService(animalService, groupService);

    @Test
    public void testIfAnimalRepositoriesAreUpdatedCorrectly() throws FileNotFoundException {
        // Given
        JsonParser parser = new JsonParser();
        JsonObject asJsonObject = parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\animals.json")).getAsJsonObject();
        Gson gson = new GsonBuilder().create();
        BiomeList biomeEnum = BiomeList.DESERT;
        List<Carnivore> carnivores = animalService.getCarnivores();
        List<Herbivore> herbivores = animalService.getHerbivores();
    
        // When
        biomeService.updateAnimalsRepositories(biomeEnum, gson, asJsonObject);
        
        // Then
        assertEquals(3, herbivores.size());
        assertEquals(3, carnivores.size());
    }
}