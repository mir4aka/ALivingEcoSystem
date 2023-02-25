package eu.deltasource.internship.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.Biome;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepository;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepositoryImpl;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.service.helper.NewBornCarnivoresRepository;
import eu.deltasource.internship.service.helper.NewBornHerbivoresRepository;
import eu.deltasource.internship.service.helper.ReproductionRateHelper;
import eu.deltasource.internship.service.helper.SuccessChanceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
//TODO WRITE GIVEN WHEN THEN
class EcoSystemServiceTest {
    
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private BiomeRepository biomeRepository = new BiomeRepositoryImpl();
    private SuccessChanceCalculator successChanceCalculator = new SuccessChanceCalculator();
    private AnimalService animalService = new AnimalService(herbivoreRepository, carnivoreRepository, groupRepository, successChanceCalculator);
    private GroupService groupService = new GroupService(groupRepository, animalService);
    private BiomeService biomeService = new BiomeService(animalService, groupService, biomeRepository);
    private ReproductionRateHelper reproduceRateHelper = new ReproductionRateHelper(successChanceCalculator);
    private NewBornCarnivoresRepository newBornCarnivoresCollection = new NewBornCarnivoresRepository(carnivoreRepository);
    private NewBornHerbivoresRepository newBornHerbivoresCollection = new NewBornHerbivoresRepository(herbivoreRepository);
    private EcoSystemService ecoSystemService = new EcoSystemService(biomeService, animalService, groupService, reproduceRateHelper, successChanceCalculator, newBornCarnivoresCollection, newBornHerbivoresCollection);
    private Carnivore carnivore;
    private Herbivore herbivore;
    
    @BeforeEach
    public void setUp() {
        carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 3, 1, 15, 10);
        herbivore = new Herbivore("Deereee", 210, 2020, HabitatEnum.LAND, SocialStatus.GROUP, 51, 1, 60);
    }
    
    @Test
    public void testIfTheHungerLevelOfTheCarnivoresIsIncreasedIfNoHerbivoresAreLeftAlive() throws IOException, InterruptedException {
        JsonParser parser = new JsonParser();
        JsonObject asJsonObject = parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\animals.json")).getAsJsonObject();
        Gson gson = new GsonBuilder().create();
        BiomeEnum biomeEnum = BiomeEnum.TESTBIOME;
        
        biomeService.updateAnimalsRepositories(biomeEnum, gson, asJsonObject);
        List<Carnivore> carnivores = animalService.getCarnivores();
        ecoSystemService.simulateEcoSystem(biomeEnum);
        assertEquals(0, carnivores.size());
    }
    
    @Test
    public void testIfTheFoodIsDistributedCorrectlyBetweenTheAnimals() throws IOException, InterruptedException {
        BiomeEnum biome1 = createBiome();
        getInformationFromJsonFile(biome1);
        Carnivore carnivore = getCarnivore();
        assert carnivore != null;
        assertEquals(0, carnivore.getHungerLevel());
        ecoSystemService.simulateEcoSystem(biome1);
        assertEquals(100, carnivore.getHungerLevel());
    }
    
    private BiomeEnum createBiome() {
        Biome biome = new Biome(BiomeEnum.TESTBIOME);
        biomeRepository.addBiomeAndAnimals(biome, carnivore, herbivore);
        return biomeService.getBiome(biome);
    }
    
    private void getInformationFromJsonFile(BiomeEnum biome1) throws IOException {
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject asJsonObject = parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\animals.json")).getAsJsonObject();
        
        biomeService.updateAnimalsRepositories(biome1, gson, asJsonObject);
    }
    
    private Carnivore getCarnivore() {
        List<Carnivore> carnivores = animalService.getCarnivores();
        Carnivore carnivore = null;
        for (Carnivore carnivore1 : carnivores) {
            carnivore = carnivore1;
            break;
        }
        return carnivore;
    }
    
    private Herbivore getHerbivore() {
        List<Herbivore> herbivores = animalService.getHerbivores();
        Herbivore herbivore = null;
        for (Herbivore animal : herbivores) {
            herbivore = animal;
            break;
        }
        return herbivore;
    }
    
    @Test
    public void testIfTheCarnivoreIsRemovedAfterReachingItsMaxAge() throws IOException, InterruptedException {
        ecoSystemService.simulateEcoSystem(BiomeEnum.DESERT);
        assertEquals(0, animalService.getCarnivores().size());
    }
    
}