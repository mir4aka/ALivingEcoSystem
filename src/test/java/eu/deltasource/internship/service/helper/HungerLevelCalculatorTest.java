package eu.deltasource.internship.service.helper;

import eu.deltasource.internship.enums.Habitat;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.service.AnimalService;
import eu.deltasource.internship.service.GroupService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HungerLevelCalculatorTest {
    
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    //TODO GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH GIT PUSH
    
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private SuccessChanceCalculator successChanceCalculator = new SuccessChanceCalculator();
    private AnimalService animalService = new AnimalService(herbivoreRepository, carnivoreRepository, groupRepository, successChanceCalculator);
    private HungerLevelCalculator hungerLevelCalculator = new HungerLevelCalculator(animalService);
    private GroupService groupService = new GroupService(groupRepository, animalService);
    
    @Test
    public void testIfTheFoodIsDistributedBetweenTheMembersCorrectly() {
        // Given
        Carnivore carnivore = new Carnivore("lion", 20, 200, Habitat.LAND, SocialStatus.GROUP, 4, 9, 15, 120);
        carnivore.setHungerLevel(80);
        groupService.createGroupOfCarnivores(carnivore);
        List<Carnivore> carnivores = new ArrayList<>();
        carnivores.add(carnivore);
        double sampleHerbivoreWeight = 100; //kg
        
        // Then
        assertEquals(80, carnivore.getHungerLevel());
        
        // When
        hungerLevelCalculator.calculateFoodDistributionBetweenTheGroupMembers(carnivores, carnivore, sampleHerbivoreWeight);
        
        // Then
        assertEquals(40, carnivore.getHungerLevel());
    }
    
}