package eu.deltasource.internship.service;

import eu.deltasource.internship.enums.Habitat;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.exception.InvalidAnimalException;
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
import eu.deltasource.internship.repository.NewBornAnimalsRepository.NewBornCarnivoresRepository;
import eu.deltasource.internship.repository.NewBornAnimalsRepository.NewBornHerbivoresRepository;
import eu.deltasource.internship.service.helper.HungerLevelCalculator;
import eu.deltasource.internship.service.helper.ReproductionRateHelper;
import eu.deltasource.internship.service.helper.SuccessChanceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalServiceTest {
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private NewBornCarnivoresRepository newBornCarnivoresRepository = new NewBornCarnivoresRepository(carnivoreRepository);
    private NewBornHerbivoresRepository newBornHerbivoresRepository = new NewBornHerbivoresRepository(herbivoreRepository);
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private SuccessChanceCalculator successChanceCalculator = new SuccessChanceCalculator();
    private ReproductionRateHelper reproduceRateHelper = new ReproductionRateHelper(successChanceCalculator);
    private AnimalService animalService = new AnimalService(herbivoreRepository, carnivoreRepository, groupRepository, successChanceCalculator);
    private HungerLevelCalculator hungerLevelCalculator = new HungerLevelCalculator(animalService);
    private Carnivore carnivore1;
    private Carnivore carnivore2;
    private Herbivore herbivore;
    
    @BeforeEach
    public void setUp() {
        carnivore1 = new Carnivore("lion", 20, 200, Habitat.LAND, SocialStatus.ALONE, 1, 9, 15, 120);
        carnivore2 = new Carnivore("lion", 20, 200, Habitat.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        herbivore = new Herbivore("Deer", 20, 200, Habitat.LAND, SocialStatus.GROUP, 5, 11, 170);
    }
    
    @Test
    public void testAddGroupOfCarnivores() {
        // Given
        Group carnivoreGroup = new Group();
        
        // When
        animalService.addGroupOfCarnivores(carnivoreGroup);
        List<Group> carnivoresGroups = animalService.getCarnivoresGroup();
        
        // Then
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
        // Given
        Group group1 = new Group();
        Group group2 = new Group();
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        group1.addCarnivoreToTheGroup(carnivore1);
        
        // When
        List<Carnivore> carnivores = animalService.findGroup(groups, carnivore1);
        
        // Then
        assertTrue(carnivores.contains(carnivore1));
    }
    
    @Test
    public void testAddCarnivore() {
        // When
        animalService.addCarnivoreToRepository(carnivore1, carnivore2);
        List<Carnivore> carnivores = animalService.getCarnivores();
        
        // Then
        assertTrue(carnivores.contains(carnivore1));
        assertTrue(carnivores.contains(carnivore2));
    }
    
    @Test
    public void testRemoveCarnivore() {
        // When
        animalService.addCarnivoreToRepository(carnivore1, carnivore2);
        List<Carnivore> carnivores = animalService.getCarnivores();
        
        // Then
        assertEquals(2, carnivores.size());
        
        // When
        animalService.removeCarnivoreFromRepository(carnivore1);
        
        // Then
        assertEquals(1, carnivores.size());
    }
    
    @Test
    public void testGetAttackSuccessWithNullCarnivore() {
        // WHEN trying to calculate the attack success rate with a null carnivore
        // THEN an InvalidAnimalException should be thrown
        assertThrows(InvalidAnimalException.class, () -> successChanceCalculator.getAttackSuccess(null, herbivore));
    }
    
    @Test
    public void testGetAttackSuccessWithNullHerbivore() {
        // WHEN trying to calculate the attack success rate with a null herbivore
        // THEN an InvalidAnimalException should be thrown
        assertThrows(InvalidAnimalException.class, () -> successChanceCalculator.getAttackSuccess(carnivore1, null));
    }
    
    @Test
    public void testGetAttackSuccessWithValidValues() {
        // WHEN calculating the attack success rate with valid animals
        double successRate = successChanceCalculator.getAttackSuccess(carnivore1, herbivore);
        
        // THEN the success rate should be greater than or equal to 0.0
        assertTrue(successRate >= 0.0);
    }
    
    @Test
    public void testGetAttackSuccessWithHerbivoreEscapePointsGreaterThanCarnivoreAttackPoints() {
        // WHEN calculating the attack success rate
        double successRate = successChanceCalculator.getAttackSuccess(carnivore1, herbivore);
        
        // THEN the success rate should be 0.0
        assertEquals(0.0, successRate);
    }
    
    @Test
    public void testIfNewBornCarnivoresAreAddedToTheRepository() {
        // Given
        List<Carnivore> newBornCarnivores = newBornCarnivoresRepository.getNewBornCarnivores();
        List<Carnivore> carnivores = carnivoreRepository.getCarnivores();
        
        // When
        newBornCarnivoresRepository.addNewBornCarnivore(carnivore1);
        for (Carnivore newBornCarnivore : newBornCarnivores) {
            carnivoreRepository.addCarnivore(newBornCarnivore);
        }
        
        // Then
        assertEquals(1, carnivores.size());
        
        // When
        newBornCarnivoresRepository.clearNewBornCarnivoresRepository();
        
        // Then
        assertEquals(0, newBornCarnivores.size());
    }
    
    @Test
    public void testIfNewBornHerbivoresAreAddedToTheRepository() {
        // Given
        List<Herbivore> newBornHerbivores = new ArrayList<>();
        newBornHerbivores.add(herbivore);
        
        // When
        for (Herbivore newbornHerbivore : newBornHerbivores) {
            newBornHerbivoresRepository.addNewBornHerbivore(newbornHerbivore);
        }
        newBornHerbivores.clear();
        List<Herbivore> herbivores = newBornHerbivoresRepository.getNewBornHerbivores();
        
        // Then
        assertEquals(1, herbivores.size());
        newBornHerbivoresRepository.clearNewBornHerbivoresRepository();
        assertEquals(0, herbivores.size());
    }
    
    @Test
    public void testIfTheHungerLevelIsIncreasedOfTheCarnivore() {
        // When
        int initialHungerLevel = carnivore1.getHungerLevel();
        
        // Then
        assertEquals(0, initialHungerLevel);
        
        // When
        hungerLevelCalculator.increaseHungerLevel(carnivore1);
        int updatedHungerLevel = carnivore1.getHungerLevel();
        
        // Then
        assertEquals(15, updatedHungerLevel);
    }
    
    @Test
    public void testIfAgeOfTheAnimalIsIncreased() {
        // When
        int initialCarnivoreAge = carnivore1.getAge();
        int initialHerbivoreAge = herbivore.getAge();
        
        // Then
        assertEquals(0, initialCarnivoreAge);
        assertEquals(0, initialHerbivoreAge);
        
        // When
        animalService.increaseAge(carnivore1);
        animalService.increaseAge(herbivore);
        int updatedCarnivoreAge = carnivore1.getAge();
        int updatedHerbivoreAge = herbivore.getAge();
        
        // Then
        assertEquals(1, updatedCarnivoreAge);
        assertEquals(1, updatedHerbivoreAge);
    }
    
    @Test
    public void testIfTheReproductionLevelOfTheAnimalIsDecreased() {
        // When
        int initialCarnivoreReproductionLevel = carnivore1.getReproductionLevel();
        int initialHerbivoreReproductionLevel = herbivore.getReproductionLevel();
        
        // Then
        assertEquals(100, initialCarnivoreReproductionLevel);
        assertEquals(100, initialHerbivoreReproductionLevel);
        
        // When
        reproduceRateHelper.decreaseReproductionLevel(carnivore1);
        reproduceRateHelper.decreaseReproductionLevel(herbivore);
        int updatedCarnivoreReproductionLevel = carnivore1.getReproductionLevel();
        int updatedHerbivoreReproductionLevel = herbivore.getReproductionLevel();
        
        // Then
        assertEquals(91, updatedCarnivoreReproductionLevel);
        assertEquals(89, updatedHerbivoreReproductionLevel);
    }
    
    @Test
    public void testIfTheReproductionLevelOfTheAnimalIsResetTo100() {
        // GIVEN: An animal with a reproduction level of 100
        assertEquals(100, carnivore1.getReproductionLevel());
        
        // WHEN: Decrease the reproduction level of the animal by 4 times
        reproduceRateHelper.decreaseReproductionLevel(carnivore1);
        reproduceRateHelper.decreaseReproductionLevel(carnivore1);
        reproduceRateHelper.decreaseReproductionLevel(carnivore1);
        reproduceRateHelper.decreaseReproductionLevel(carnivore1);
        
        // THEN: The reproduction level of the animal should be 64
        assertEquals(64, carnivore1.getReproductionLevel());
        
        // WHEN: Reset the reproduction level of the animal to 100
        reproduceRateHelper.resetReproductionLevel(carnivore1);
        
        // THEN: The reproduction level of the animal should be reset to 100
        assertEquals(100, carnivore1.getReproductionLevel());
    }
    
    @Test
    public void testIfReproducingOfTheCarnivoreFunctionsAsItShould() {
        // GIVEN: A carnivore in the repository
        animalService.addCarnivoreToRepository(carnivore1);
        List<Carnivore> carnivores = animalService.getCarnivores();
        
        // WHEN: Reproduce the carnivore
        Carnivore reproducedCarnivore = reproduceRateHelper.reproduce(carnivore1);
        
        // THEN: The repository should have two carnivores
        animalService.addCarnivoreToRepository(reproducedCarnivore);
        assertEquals(2, carnivores.size());
    }
    
    @Test
    public void testIfReproducingOfTheHerbivoreFunctionsAsItShould() {
        // Given
        animalService.addHerbivoreToRepository(herbivore);
        List<Herbivore> herbivores = animalService.getHerbivores();
        
        // Then
        assertEquals(1, herbivores.size());
        
        // When
        Herbivore reproducedHerbivore = reproduceRateHelper.reproduce(herbivore);
        animalService.addHerbivoreToRepository(reproducedHerbivore);
        
        // Then
        assertEquals(2, herbivores.size());
    }
    
    @Test
    public void testIfTheAttackPointsOfTheCarnivoreAreScaledCorrectly() {
        // Given
        double attackPoints = carnivore1.getPoints();
        
        // Then
        assertEquals(120, attackPoints);
        
        // When
        double scaledAttackPoints = successChanceCalculator.scalePoints(carnivore1, attackPoints);
        
        // Then
        assertEquals(120, scaledAttackPoints);
        
        // When
        carnivore1.setAge(5);
        double scaledAttackPointsAfterAgeIsIncreased = successChanceCalculator.scalePoints(carnivore1, attackPoints);
        
        // Then
        assertEquals(90, scaledAttackPointsAfterAgeIsIncreased);
    }
    
    @Test
    public void testIfTheReproductionLevelIsReset() {
        // Given
        assertEquals(100, carnivore1.getReproductionLevel());
        
        // When
        reproduceRateHelper.decreaseReproductionLevel(carnivore1);
        reproduceRateHelper.resetReproductionLevel(carnivore1);
        
        // Then
        assertEquals(100, carnivore1.getReproductionLevel());
    }
    
}