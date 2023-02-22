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

class GroupServiceTest {
    private GroupService groupService;
    
    @BeforeEach
    public void setUp() {
        groupService = new GroupService();
    }
    
    @Test
    public void testIfCarnivoresGroupSizeIsCorrect() {
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        groupService.createGroupOfCarnivores(carnivore);
        
        List<Group> carnivoresGroup = groupService.getCarnivoresGroup();
        
        assertEquals(1, carnivoresGroup.size());
    }
    
    @Test
    public void testIfACarnivoreIsFoundInTheGroup() {
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        groupService.createGroupOfCarnivores(carnivore);
        
        Animal carnivoreInGroup = groupService.findCarnivoreInGroup(carnivore);
        
        assertEquals(carnivore, carnivoreInGroup);
    }
    
    @Test
    public void testIfTheGroupOfCarnivoresIsCreated() {
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        Carnivore carnivore2 = new Carnivore("ko4e", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        groupService.createGroupOfCarnivores(carnivore);
        groupService.createGroupOfCarnivores(carnivore2);
        List<Group> carnivoresGroup = groupService.getCarnivoresGroup();
        assertEquals(2, carnivoresGroup.size());
    }
    
    @Test
    public void testIfTheGroupOfHerbivoresIsCreated() {
        Herbivore herbivore = new Herbivore("Deer", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 5, 11, 70);
        Herbivore herbivore1 = new Herbivore("Deereee", 210, 2020, HabitatEnum.LAND, SocialStatus.GROUP, 51, 111, 60);
        groupService.createGroupOfHerbivores(herbivore);
        groupService.createGroupOfHerbivores(herbivore1);
    
        List<Group> herbivoresGroup = groupService.getHerbivoresGroup();
        assertEquals(2, herbivoresGroup.size());
    }
    
    @Test
    public void testIfTheChosenAnimalIsRemovedFromTheGroup() {
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        groupService.createGroupOfCarnivores(carnivore);
    
        List<Group> carnivoresGroup = groupService.getCarnivoresGroup();
        
        List<Animal> animals = new ArrayList<>();
        for (Group group : carnivoresGroup) {
            animals = group.getAnimals();
        }
    
        assertEquals(4, animals.size());
        
        groupService.removeCarnivore(carnivore);
        
        assertEquals(3, animals.size());
    }
}