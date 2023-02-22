package eu.deltasource.internship.service;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalServiceTest {
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private AnimalService animalService = new AnimalService(herbivoreRepository, carnivoreRepository, groupRepository);
    private Carnivore carnivore1;
    private Carnivore carnivore2;
    private Herbivore herbivore;
    
    @BeforeEach
    public void setUp() {
        carnivore1 = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        carnivore2 = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        herbivore = new Herbivore("Deer", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 5, 11, 70);
    }
    
    @Test
    public void testAddGroupOfCarnivores() {
        Group carnivoreGroup = new Group();
        animalService.addGroupOfCarnivores(carnivoreGroup);
        List<Group> carnivoresGroups = animalService.getCarnivoresGroup();
        assertEquals(1, carnivoresGroups.size());
    }
    
    @Test
    public void testAddGroupOfHerbivores() {
        Group herbivoreGroup = new Group();
        animalService.addGroupOfHerbivores(herbivoreGroup);
        List<Group> herbivoresGroups = animalService.getHerbivoresGroup();
        assertEquals(1, herbivoresGroups.size());
    }
    
    @Test
    public void testFindGroup() {
        Group group1 = new Group();
        Group group2 = new Group();
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        group1.addAnimal(carnivore1);
        List<Animal> animals = animalService.findGroup(groups, carnivore1);
        assertTrue(animals.contains(carnivore1));
    }
    
    @Test
    public void testAddCarnivore() {
        animalService.addCarnivore(carnivore1, carnivore2);
        List<Carnivore> carnivores = animalService.getCarnivores();
        assertTrue(carnivores.contains(carnivore1));
        assertTrue(carnivores.contains(carnivore2));
    }
    
    @Test
    public void testRemoveCarnivore() {
        Carnivore carnivore1 = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        Carnivore carnivore2 = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        animalService.addCarnivore(carnivore1, carnivore2);
    
        List<Carnivore> carnivores = animalService.getCarnivores();
        
        assertEquals(2, carnivores.size());
        animalService.removeCarnivore(carnivore1);
        assertEquals(1, carnivores.size());
    }
    
    @Test
    public void testGetAttackSuccessWithNullCarnivore() {
        assertThrows(NullPointerException.class, () -> animalService.getAttackSuccess(null, herbivore));
    }
    
    // Verify that the herbivore is not null
    @Test
    public void testGetAttackSuccessWithNullHerbivore() {
        assertThrows(NullPointerException.class, () -> animalService.getAttackSuccess(carnivore1, null));
    }
    
    // Verify that the success rate returned is a positive value
    @Test
    public void testGetAttackSuccessWithValidValues() {
        double successRate = animalService.getAttackSuccess(carnivore1, herbivore);
        assertTrue(successRate >= 0.0);
    }
    
    // Verify that the success rate returned is 0 when the herbivore's escape points are greater than the carnivore's attack points
    @Test
    public void testGetAttackSuccessWithHerbivoreEscapePointsGreaterThanCarnivoreAttackPoints() {
        double successRate = animalService.getAttackSuccess(carnivore1, herbivore);
        assertEquals(0.0, successRate);
    }
    
    // Adds the newborn carnivore to the repository and clears the newborn carnivores list.
    @Test
    public void testIfNewBornCarnivoresAreAddedToTheRepository() {
        List<Carnivore> newBornCarnivores = new ArrayList<>();
        newBornCarnivores.add(carnivore1);
        
        for (Carnivore newBornCarnivore : newBornCarnivores) {
            animalService.addNewBornCarnivore(newBornCarnivore);
        }
        
        newBornCarnivores.clear();
        List<Carnivore> carnivores = animalService.getNewBornCarnivores();
        
        assertEquals(1, carnivores.size());
        animalService.clearNewBornCarnivoresList();
        assertEquals(0, carnivores.size());
    }
    
    // Adds the newborn carnivore to the repository and clears the newborn carnivores list.
    @Test
    public void testIfNewBornHerbivoresAreAddedToTheRepository() {
        List<Herbivore> newBornHerbivores = new ArrayList<>();
        newBornHerbivores.add(herbivore);
        
        for (Herbivore newbornHerbivore : newBornHerbivores) {
            animalService.addNewBornHerbivore(newbornHerbivore);
        }
        
        newBornHerbivores.clear();
        List<Herbivore> herbivores = animalService.getNewBornHerbivores();
        
        assertEquals(1, herbivores.size());
        animalService.clearNewBornHerbivoresList();
        assertEquals(0, herbivores.size());
    }
    
    @Test
    public void testIfTheHungerRateIsIncreasedOfTheCarnivore() {
        assertEquals(15, carnivore1.getHungerRate());
        animalService.increaseHungerLevel(carnivore1, 5);
        assertEquals(20, carnivore1.getHungerRate());
    }
    
    @Test
    public void testIfAgeOfTheAnimalIsIncreased() {
        assertEquals(0, carnivore1.getAge());
        assertEquals(0, herbivore.getAge());
    
        animalService.increaseAge(carnivore1);
        animalService.increaseAge(herbivore);
        
        assertEquals(1, carnivore1.getAge());
        assertEquals(1, herbivore.getAge());
    }
    
    @Test
    public void testIfTheReproductionRateOfTheAnimalIsDecreased() {
        assertEquals(9, carnivore1.getReproductionRate());
        assertEquals(11, herbivore.getReproductionRate());
        
        animalService.decreaseReproductionRate(carnivore1);
        animalService.decreaseReproductionRate(herbivore);
        
        assertEquals(8, carnivore1.getReproductionRate());
        assertEquals(10, herbivore.getReproductionRate());
    }
    
    @Test
    public void testIfTheReproductionRateOfTheAnimalIsResetTo10() {
        assertEquals(9, carnivore1.getReproductionRate());
        animalService.resetReproductionRate(carnivore1);
        assertEquals(10, carnivore1.getReproductionRate());
    }
    
    @Test
    public void testIfReproducingOfTheCarnivoreFunctionsAsItShould() {
        animalService.addCarnivore(carnivore1);
        List<Carnivore> carnivores = animalService.getCarnivores();
        
        assertEquals(1, carnivores.size());
        Carnivore reproducedCarnivore = animalService.reproduce(carnivore1);
        
        animalService.addCarnivore(reproducedCarnivore);
        assertEquals(2, carnivores.size());
    }
    
    @Test
    public void testIfReproducingOfTheHerbivoreFunctionsAsItShould() {
        animalService.addHerbivore(herbivore);
        List<Herbivore> herbivores = animalService.getHerbivores();
        
        assertEquals(1, herbivores.size());
        Herbivore reproducedHerbivore = animalService.reproduce(herbivore);
        
        animalService.addHerbivore(reproducedHerbivore);
        assertEquals(2, herbivores.size());
    }
    
    @Test
    public void testIfTheAttackPointsOfTheCarnivoreAreScaledCorrectly() {
        double attackPoints = carnivore1.getPoints();
        
        assertEquals(10, attackPoints);
        double scaledAttackPoints = animalService.scalePoints(carnivore1, attackPoints);
        
        assertEquals(10, scaledAttackPoints);
        carnivore1.setAge(5);
    
        double scaledAttackPointsAfterAgeIsIncreased = animalService.scalePoints(carnivore1, attackPoints);
        assertEquals(7.5, scaledAttackPointsAfterAgeIsIncreased);
    }
    
    @Test
    public void testIfTheReproductionRateIsReset() {
        assertEquals(9, carnivore1.getReproductionRate());
        animalService.decreaseReproductionRate(carnivore1);
        assertEquals(8, carnivore1.getReproductionRate());
        animalService.resetReproductionRate(carnivore1);
        assertEquals(9, carnivore1.getReproductionRate());
    }
}