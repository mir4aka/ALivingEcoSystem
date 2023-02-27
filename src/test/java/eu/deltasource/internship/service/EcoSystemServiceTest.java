package eu.deltasource.internship.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.deltasource.internship.enums.BiomeList;
import eu.deltasource.internship.enums.Habitat;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.Biome;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.repository.NewBornAnimalsRepository.NewBornCarnivoresRepository;
import eu.deltasource.internship.repository.NewBornAnimalsRepository.NewBornHerbivoresRepository;
import eu.deltasource.internship.service.helper.HungerLevelCalculator;
import eu.deltasource.internship.service.helper.ReproductionRateHelper;
import eu.deltasource.internship.service.helper.SuccessChanceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EcoSystemServiceTest {
    
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private SuccessChanceCalculator successChanceCalculator = new SuccessChanceCalculator();
    private AnimalService animalService = new AnimalService(herbivoreRepository, carnivoreRepository, groupRepository, successChanceCalculator);
    private GroupService groupService = new GroupService(groupRepository, animalService);
    private BiomeService biomeService = new BiomeService(animalService, groupService);
    private ReproductionRateHelper reproduceRateHelper = new ReproductionRateHelper(successChanceCalculator);
    private NewBornCarnivoresRepository newBornCarnivoresCollection = new NewBornCarnivoresRepository(carnivoreRepository);
    private NewBornHerbivoresRepository newBornHerbivoresCollection = new NewBornHerbivoresRepository(herbivoreRepository);
    private HungerLevelCalculator hungerLevelCalculator = new HungerLevelCalculator(animalService);
    private EcoSystemService ecoSystemService = new EcoSystemService(biomeService, animalService, groupService, reproduceRateHelper, successChanceCalculator, newBornCarnivoresCollection, newBornHerbivoresCollection, hungerLevelCalculator);
    private Carnivore carnivore;
    private Herbivore herbivore;
    
    @BeforeEach
    public void setUp() {
        carnivore = new Carnivore("lion", 20, 200, Habitat.LAND, SocialStatus.GROUP, 3, 1, 15, 10);
        herbivore = new Herbivore("Deereee", 210, 2020, Habitat.LAND, SocialStatus.GROUP, 51, 1, 60);
    }
    
    @Test
    public void testIfTheHungerLevelOfTheCarnivoresIsIncreasedIfNoHerbivoresAreLeftAlive() throws IOException, InterruptedException {
        // Given
        JsonParser parser = new JsonParser();
        JsonObject asJsonObject = parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\animals.json")).getAsJsonObject();
        Gson gson = new GsonBuilder().create();
        BiomeList biomeEnum = BiomeList.TESTBIOME;
        List<Carnivore> carnivores = animalService.getCarnivores();
        
        // When
        biomeService.updateAnimalsRepositories(biomeEnum, gson, asJsonObject);
        ecoSystemService.simulateEcoSystem(biomeEnum);
        
        // Then
        assertEquals(0, carnivores.size());
    }
    
    @Test
    public void testIfTheCarnivoreIsRemovedAfterReachingItsMaxAge() throws IOException, InterruptedException {
        // When
        ecoSystemService.simulateEcoSystem(BiomeList.DESERT);
        
        // Then
        assertEquals(0, animalService.getCarnivores().size());
    }
}