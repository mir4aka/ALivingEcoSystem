package eu.deltasource.internship.service;

import eu.deltasource.internship.enums.Habitat;
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
        // Given
        Carnivore carnivore = new Carnivore("lion", 20, 200, Habitat.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        List<Group> carnivoresGroup = groupService.getCarnivoresGroup();
        
        // When
        groupService.createGroupOfCarnivores(carnivore);
        
        // Then
        assertEquals(1, carnivoresGroup.size());
    }
    
    @Test
    public void testIfACarnivoreIsFoundInTheGroup() {
        // Given
        Carnivore carnivore = new Carnivore("lion", 20, 200, Habitat.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        
        // When
        groupService.createGroupOfCarnivores(carnivore);
        Animal carnivoreInGroup = groupService.findCarnivoreInGroup(carnivore);
        
        // Then
        assertEquals(carnivore, carnivoreInGroup);
    }
    
    @Test
    public void testIfTheGroupOfCarnivoresIsCreated() {
        
        // Given
        Carnivore carnivore = new Carnivore("lion", 20, 200, Habitat.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        Carnivore carnivore2 = new Carnivore("ko4e", 20, 200, Habitat.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        List<Group> carnivoresGroup = groupService.getCarnivoresGroup();
        
        // When
        groupService.createGroupOfCarnivores(carnivore);
        groupService.createGroupOfCarnivores(carnivore2);
        
        // Then
        assertEquals(2, carnivoresGroup.size());
    }
    
    @Test
    public void testIfTheGroupOfHerbivoresIsCreated() {
        // Given
        Herbivore herbivore = new Herbivore("Deer", 20, 200, Habitat.LAND, SocialStatus.GROUP, 5, 11, 70);
        Herbivore herbivore1 = new Herbivore("Deereee", 210, 2020, Habitat.LAND, SocialStatus.GROUP, 51, 111, 60);
        List<Group> herbivoresGroup = groupService.getHerbivoresGroup();
        
        // When
        groupService.createGroupOfHerbivores(herbivore);
        groupService.createGroupOfHerbivores(herbivore1);
        
        // Then
        assertEquals(2, herbivoresGroup.size());
    }
    
    @Test
    public void testIfTheChosenAnimalIsRemovedFromTheGroup() {
        // Given
        Carnivore carnivore = new Carnivore("lion", 20, 200, Habitat.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        
        // When
        groupService.createGroupOfCarnivores(carnivore);
        Group carnivoreGroup = groupService.findCarnivoreGroup(carnivore);
        
        // Then
        assertEquals(3, carnivoreGroup.getCarnivoresGroup().size());
        
        // When
        carnivoreGroup.removeCarnivoreFromTheGroup(carnivore);
        
        // Then
        assertEquals(2, carnivoreGroup.getCarnivoresGroup().size());
    }
    
    @Test
    public void testIfTheCarnivoreIsRemovedFromTheGroup() {
        // Given
        Carnivore carnivore = new Carnivore("lion", 20, 200, Habitat.LAND, SocialStatus.GROUP, 3, 9, 15, 10);
        List<Carnivore> carnivores = new ArrayList<>();
        List<Group> carnivoresGroup = groupService.getCarnivoresGroup();
        
        // When
        groupService.createGroupOfCarnivores(carnivore);
        for (Group group : carnivoresGroup) {
            carnivores = group.getCarnivoresGroup();
        }
        
        // Then
        assertEquals(3, carnivores.size());
        
        // When
        groupService.removeCarnivore(carnivore);
        
        // Then
        assertEquals(2, carnivores.size());
    }
}