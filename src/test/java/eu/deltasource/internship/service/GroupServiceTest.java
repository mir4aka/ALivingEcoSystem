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
import eu.deltasource.internship.service.helper.SuccessChanceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupServiceTest {
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private SuccessChanceCalculator successChanceCalculator = new SuccessChanceCalculator();
    private AnimalService animalService = new AnimalService(herbivoreRepository, carnivoreRepository, groupRepository, successChanceCalculator);
    private GroupService groupService = new GroupService(groupRepository, animalService);
    
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
        
        Group carnivoreGroup = groupService.findCarnivoreGroup(carnivore);
        
        assertEquals(3, carnivoreGroup.getAnimals().size());
        
        carnivoreGroup.removeAnimal(carnivore);
        
        assertEquals(2, carnivoreGroup.getAnimals().size());
    }
    
    @Test
    public void testIfTheCarnivoreIsRemovedFromTheGroup() {
        Carnivore carnivore = new Carnivore("lion", 20, 200, HabitatEnum.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        groupService.createGroupOfCarnivores(carnivore);
        
        List<Animal> animals = new ArrayList<>();
        
        List<Group> carnivoresGroup = groupService.getCarnivoresGroup();
        for (Group group : carnivoresGroup) {
            animals = group.getAnimals();
        }
        
        assertEquals(3, animals.size());
        groupService.removeCarnivore(carnivore);
        assertEquals(2, animals.size());
    }
}