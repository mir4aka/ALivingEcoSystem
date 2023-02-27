package eu.deltasource.internship.service.helper;

import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.service.AnimalService;

import java.util.List;

public class HungerLevelCalculator {
    private AnimalService animalService;
    
    public HungerLevelCalculator(AnimalService animalService) {
        this.animalService = animalService;
    }
    
    public void increaseHungerLevel(Carnivore carnivore) {
        int hungerLevel = calculateHungerLevel(carnivore);
        carnivore.setHungerLevel(hungerLevel);
        if (hungerLevel >= 100) {
            carnivore.setHungerLevel(100);
        }
    }
    
    public void decreaseHungerLevel(Carnivore carnivore, double food) {
        int hungerLevel = carnivore.getHungerLevel();
        hungerLevel -= food;
        carnivore.setHungerLevel(hungerLevel);
        if (hungerLevel <= 0) {
            carnivore.setHungerLevel(0);
        }
    }
    
    public void calculateFoodDistributionBetweenTheGroupMembers(List<Carnivore> carnivores, Carnivore carnivore, double foodInKg) {
        double foodForMainAttacker;
        double foodForTheRestOfTheGroup;
        List<Group> groups = animalService.getCarnivoresGroup();
        List<Carnivore> groupOfCarnivores = animalService.findGroup(groups, carnivore);
        int attackersAmount = groupOfCarnivores.size();
        foodInKg /= attackersAmount + 1;
        foodForMainAttacker = foodInKg * 2;
        foodForTheRestOfTheGroup = foodInKg;
        decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(carnivores, carnivore, foodForMainAttacker, foodForTheRestOfTheGroup);
    }
    
    /**
     * After the attack is successful, hunger rate of each member of the group is being decreased, the main attacker of the group receives two portions.
     */
    public void decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(List<Carnivore> carnivores, Carnivore carnivore, double foodForMainAttacker, double foodForTheRestOfTheGroup) {
        for (Animal groupMember : carnivores) {
            if (groupMember.equals(carnivore)) {
                List<Group> groups = animalService.getCarnivoresGroup();
                List<Carnivore> groupOfCarnivores = animalService.findGroup(groups, carnivore);
                for (Carnivore animal : groupOfCarnivores) {
                    if (animal.equals(carnivore)) {
                        //The main attacker receives two portions of food
                        decreaseHungerLevel(animal, foodForMainAttacker);
                    } else {
                        //Each groupMember that is not the attacker should receive a single portion of the food.
                        decreaseHungerLevel(animal, foodForTheRestOfTheGroup);
                    }
                }
            }
        }
    }
    
    /**
     * Distributing the food of the carnivores whether the carnivore is living in a group or alone.
     */
    public void foodDistributionDependingOnTheCarnivoresSocialStatus(List<Carnivore> carnivores, Carnivore carnivore, Herbivore herbivore) {
        double foodInKg = herbivore.getWeight();
        double foodForMainAttacker;
        
        if (carnivore.getSocialStatus().equals(SocialStatus.GROUP)) {
            calculateFoodDistributionBetweenTheGroupMembers(carnivores, carnivore, foodInKg);
        } else {
            foodForMainAttacker = herbivore.getWeight();
            decreaseHungerLevel(carnivore, foodForMainAttacker);
        }
    }
    
    private int calculateHungerLevel(Carnivore carnivore) {
        int hungerLevel = carnivore.getHungerLevel();
        int hungerRate = carnivore.getHungerRate();
        hungerLevel += hungerRate;
        return hungerLevel;
    }
}
