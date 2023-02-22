package eu.deltasource.internship.service;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalServiceTest {
    private AnimalService animalService;
    
    @BeforeEach
    public void setUp() {
        animalService = new AnimalService();
    }
    
    @Test
    public void testAddGroupOfCarnivores() {
        Group carnivoreGroup = new Group();
        animalService.addGroupOfCarnivores(carnivoreGroup);
        List<Group> carnivoresGroups = animalService.getCarnivoresGroup();
        assertEquals(carnivoreGroup, carnivoresGroups.get(0));
    }
    
    @Test
    public void testAddGroupOfHerbivores() {
        Group herbivoreGroup = new Group();
        animalService.addGroupOfHerbivores(herbivoreGroup);
        List<Group> herbivoresGroups = animalService.getHerbivoresGroup();
        assertEquals(herbivoreGroup, herbivoresGroups.get(0));
    }
    
    @Test
    public void testFindGroup() {
        Group group1 = new Group();
        Group group2 = new Group();
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        group1.addAnimal(carnivore);
        List<Animal> animals = animalService.findGroup(groups, carnivore);
        assertTrue(animals.contains(carnivore));
    }
    
    @Test
    public void testAddCarnivore() {
        Carnivore carnivore1 = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        Carnivore carnivore2 = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        animalService.addCarnivore(carnivore1, carnivore2);
        List<Carnivore> carnivores = animalService.getCarnivores();
        assertTrue(carnivores.contains(carnivore1));
        assertTrue(carnivores.contains(carnivore2));
    }
    
    @Test
    public void testGetAttackSuccessWithNullCarnivore() {
        Herbivore herbivore = new Herbivore("Deer", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 5, 11, 70);
        assertThrows(NullPointerException.class, () -> animalService.getAttackSuccess(null, herbivore));
    }
    
    // Verify that the herbivore is not null
    @Test
    public void testGetAttackSuccessWithNullHerbivore() {
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        assertThrows(NullPointerException.class, () -> animalService.getAttackSuccess(carnivore, null));
    }
    
    // Verify that the success rate returned is a positive value
    @Test
    public void testGetAttackSuccessWithValidValues() {
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 110);
        Herbivore herbivore = new Herbivore("Deer", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 5, 11, 70);
        double successRate = animalService.getAttackSuccess(carnivore, herbivore);
        assertTrue(successRate >= 0.0);
    }
    
    // Verify that the success rate returned is 0 when the herbivore's escape points are greater than the carnivore's attack points
    @Test
    public void testGetAttackSuccessWithHerbivoreEscapePointsGreaterThanCarnivoreAttackPoints() {
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 10);
        Herbivore herbivore = new Herbivore("Deer", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 5, 11, 60);
        double successRate = animalService.getAttackSuccess(carnivore, herbivore);
        assertEquals(0.0, successRate);
    }
    
    // Adds the newborn carnivore to the repository and clears the newborn carnivores list.
    @Test
    public void testIfNewBornCarnivoresAreAddedToTheRepository() {
        List<Carnivore> newBornCarnivores = new ArrayList<>();
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.ALONE, 1, 9, 15, 10);
        newBornCarnivores.add(carnivore);
        
        for (Carnivore newBornCarnivore : newBornCarnivores) {
            animalService.addCarnivore(newBornCarnivore);
        }
        
        newBornCarnivores.clear();
        
        List<Carnivore> carnivores = animalService.getCarnivores();
        
        assertEquals(1, carnivores.size());
    }
    
    // Adds the newborn carnivore to the repository and clears the newborn carnivores list.
    @Test
    public void testIfNewBornHerbivoresAreAddedToTheRepository() {
        List<Herbivore> newBornHerbivores = new ArrayList<>();
        Herbivore herbivore = new Herbivore("Deer", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 5, 11, 60);
        newBornHerbivores.add(herbivore);
        
        for (Herbivore newbornHerbivore : newBornHerbivores) {
            animalService.addHerbivore(newbornHerbivore);
        }
        
        newBornHerbivores.clear();
        
        List<Herbivore> herbivores = animalService.getHerbivores();
        
        assertEquals(1, herbivores.size());
    }
    
    
    
}