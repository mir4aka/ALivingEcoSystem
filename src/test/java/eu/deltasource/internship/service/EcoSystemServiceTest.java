package eu.deltasource.internship.service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EcoSystemServiceTest {
    
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private BiomeRepository biomeRepository = new BiomeRepositoryImpl();
    private AnimalService animalService = new AnimalService(herbivoreRepository, carnivoreRepository, groupRepository);
    private GroupService groupService = new GroupService(groupRepository);
    private BiomeService biomeService = new BiomeService(animalService, groupService, biomeRepository);
    private EcoSystemService ecoSystemService = new EcoSystemService(biomeService);
    private Carnivore carnivore;
    private Herbivore herbivore;
    
    @BeforeEach
    public void setUp() {
        carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 3, 1, 15, 10);
        herbivore = new Herbivore("Deereee", 210, 2020, HabitatEnum.LAND, SocialStatus.GROUP, 51, 1, 60);
    }
    
    @Test
    public void testIfAppEndsWhenAllAnimalsAreDead() throws IOException, InterruptedException {
        JsonParser parser = new JsonParser();
        JsonObject asJsonObject = parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\animals.json")).getAsJsonObject();
        Gson gson = new GsonBuilder().create();
        BiomeEnum biomeEnum = BiomeEnum.DESERT;
    
        biomeService.updateAnimalsRepositories(biomeEnum, gson, asJsonObject);
    
        List<Carnivore> carnivores = biomeService.getAnimalService().getCarnivores();
        List<Herbivore> herbivores = biomeService.getAnimalService().getHerbivores();
    
        assertTrue(ecoSystemService.isAnimalsDead(carnivores, herbivores));
        ecoSystemService.simulateEcoSystem(biomeEnum);
        assertFalse(ecoSystemService.isAnimalsDead(carnivores, herbivores));
    }
    
    @Test
    public void testIfTheNewBornAnimalsAreAddedToTheirGroups() {
        biomeService.getAnimalService().addCarnivore(carnivore);
        biomeService.getAnimalService().addHerbivore(herbivore);
        
        Carnivore reproducedCarnivore = biomeService.getAnimalService().reproduce(carnivore);
        Herbivore reproducedHerbivore = biomeService.getAnimalService().reproduce(herbivore);
    
        biomeService.getAnimalService().addNewBornCarnivore(reproducedCarnivore);
        biomeService.getAnimalService().addNewBornHerbivore(reproducedHerbivore);
    
        List<Carnivore> newBornCarnivores = biomeService.getAnimalService().getNewBornCarnivores();
        List<Herbivore> newBornHerbivores = biomeService.getAnimalService().getNewBornHerbivores();
        
        assertEquals(1, biomeService.getAnimalService().getCarnivores().size());
        assertEquals(1, biomeService.getAnimalService().getHerbivores().size());
    
        /**
         * The logic behind this method lies on the bottom of the class.
         */
        addsNewBornAnimalsToTheirSpecies(newBornCarnivores, newBornHerbivores);
    
        assertEquals(2, biomeService.getAnimalService().getCarnivores().size());
        assertEquals(2, biomeService.getAnimalService().getHerbivores().size());
    }
    
    @Test
    public void testIfTheHungerLevelOfTheCarnivoresIsIncreasedIfNoHerbivoresAreLeftAlive() throws IOException, InterruptedException {
        JsonParser parser = new JsonParser();
        JsonObject asJsonObject = parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\animals.json")).getAsJsonObject();
        Gson gson = new GsonBuilder().create();
        BiomeEnum biomeEnum = BiomeEnum.SWAMP;

        biomeService.updateAnimalsRepositories(biomeEnum, gson, asJsonObject);
    
        List<Carnivore> carnivores = biomeService.getAnimalService().getCarnivores();
        List<Herbivore> herbivores = new ArrayList<>();

        ecoSystemService.increaseHungerLevelOfCarnivoresIfAllHerbivoresAreDead(carnivores, herbivores);

        assertEquals(0, carnivores.size());
    }
    
    @Test
    public void testIfTheFoodIsDistributedCorrectlyBetweenTheAnimals() {
        animalService.addCarnivore(carnivore);
        animalService.addHerbivore(herbivore);
        
        groupService.createGroupOfCarnivores(carnivore);
    
        List<Carnivore> carnivores = animalService.getCarnivores();
        
        assertEquals(15, carnivore.getHungerRate());
        
        ecoSystemService.foodDistributionDependingOnTheCarnivoresSocialStatus(carnivores, carnivore, herbivore);
        
        assertEquals(0, carnivore.getHungerRate());
    }
    
    @Test
    public void testIfAnimalsAreReproducedCorrectlyAndAddedToRepositories() {
        biomeService.getAnimalService().addCarnivore(carnivore);
        biomeService.getAnimalService().addHerbivore(herbivore);
    
        List<Carnivore> carnivores = biomeService.getAnimalService().getCarnivores();
        List<Herbivore> herbivores = biomeService.getAnimalService().getHerbivores();
        List<Carnivore> newBornCarnivores = biomeService.getAnimalService().getNewBornCarnivores();
        List<Herbivore> newBornHerbivores = biomeService.getAnimalService().getNewBornHerbivores();
    
        biomeService.getAnimalService().decreaseReproductionRate(carnivore);
        biomeService.getAnimalService().decreaseReproductionRate(herbivore);
        
        ecoSystemService.animalFactory(carnivores, herbivores);
        ecoSystemService.addNewBornAnimalsToTheirSpecies(newBornCarnivores, newBornHerbivores);
        
        assertEquals(2, carnivores.size());
        assertEquals(2, herbivores.size());
    }
    
    private void addsNewBornAnimalsToTheirSpecies(List<Carnivore> newBornCarnivores, List<Herbivore> newBornHerbivores) {
        List<Carnivore> carnivores = ecoSystemService.addNewBornCarnivoresToTheCarnivores(newBornCarnivores);
        for (Carnivore carnivore1 : carnivores) {
            biomeService.getAnimalService().addCarnivore(carnivore1);
        }
        
        List<Herbivore> herbivores = ecoSystemService.addNewBornHerbivoresToTheHerbivores(newBornHerbivores);
        for (Herbivore herbivore : herbivores) {
            biomeService.getAnimalService().addHerbivore(herbivore);
        }
        
        biomeService.getAnimalService().clearNewBornCarnivoresList();
        biomeService.getAnimalService().clearNewBornHerbivoresList();
    }
    
}