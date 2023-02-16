package eu.deltasource.internship.service;

import eu.deltasource.internship.repository.BiomeRepository.BiomeRepository;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepositoryImpl;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;
import eu.deltasource.internship.model.*;

import java.util.List;
import java.util.Random;

public class EcoSystemService {
    private BiomeService biomeService = new BiomeService();
    
    public void simulateEcoSystem(BiomeEnum biome) throws InterruptedException {
        runEcoSystemDependingOnTheBiome(biome);
        
        List<Animal> carnivores = biomeService.getAnimalService().getCarnivores();
        List<Animal> herbivores = biomeService.getAnimalService().getHerbivores();
        List<Animal> newBornAnimals = biomeService.getAnimalService().getNewBornAnimals();
        
        System.out.println("Action happening in " + biome);
        while (isAnimalsDead(carnivores, herbivores)) {
            increaseHungerLevelOfCarnivoresIfAllHerbivoresAreDead(carnivores, herbivores);
    
            if (carnivores.size() == 0) {
                break;
            }
            
            addNewBornAnimalsToTheirGroups(newBornAnimals);
    
            carnivoreAttacksHerbivore(carnivores, herbivores);
    
            checksIfTheMembersOfTheCarnivoreGroupReachedTheMaximumHungerRate();
            
            //Increase the age of all animals
            increaseTheAgeOfTheAnimals(carnivores, herbivores);
            
            //Reproduce new animals
            animalFactory(carnivores, herbivores);
            
            System.out.println("------------------------------------------");
            Thread.sleep(1000);
        }
    }
    
    private void carnivoreAttacksHerbivore(List<Animal> carnivores, List<Animal> herbivores) {
        Animal animalCarnivore = carnivores.get(new Random().nextInt(0, carnivores.size()));
        Animal animalHerbivore = herbivores.get(new Random().nextInt(0, herbivores.size()));
        
        Carnivore carnivore = (Carnivore) animalCarnivore;
        Herbivore herbivore = (Herbivore) animalHerbivore;
        
        //Calculates the success rate of the attack
        double successRate = biomeService.getAnimalService().getAttackSuccess(carnivore, herbivore);
        int randomNumberGenerated = new Random().nextInt(0, 100);
        
        //If the attack is successful
        System.out.println(carnivore.getAnimalType() + " attacked " + herbivore.getAnimalType());
        if (successRate > randomNumberGenerated) {
            attack(carnivores, carnivore, herbivore);
        } else {
            //The program enters this scope in case the attack does not happen (if successRate is less than the randomized number).
            System.out.println(herbivore.getAnimalType() + " escaped the attack from " + carnivore.getAnimalType());
            increasingCarnivoreHungerLevel(carnivore);
        }
        carnivore.decreaseReproductionRate();
        herbivore.decreaseReproductionRate();
    }
    
    private void addNewBornAnimalsToTheirGroups(List<Animal> newBornAnimals) {
        for (Animal newBornAnimal : newBornAnimals) {
            if (newBornAnimal.getClass().getSimpleName().equals("Carnivore")) {
//                    animalService.addCarnivore(newBornAnimal);
                biomeService.getAnimalService().addCarnivore(newBornAnimal);
            } else {
//                    animalService.addHerbivore(newBornAnimal);
                biomeService.getAnimalService().addHerbivore(newBornAnimal);
            }
        }
        
        biomeService.getAnimalService().clearNewBornAnimalsList();
    }
    
    private void increaseHungerLevelOfCarnivoresIfAllHerbivoresAreDead(List<Animal> carnivores, List<Animal> herbivores) {
        if (herbivores.size() == 0) {
            while (carnivores.size() > 0) {
                for (int i = 0; i < carnivores.size(); i++) {
                    Animal currentCarnivore = carnivores.get(i);
                    Carnivore carnivore1 = (Carnivore) currentCarnivore;
                    increasingCarnivoreHungerLevel(carnivore1);
                    if (carnivore1.getHungerRate() >= 100) {
                        System.out.println(carnivore1.getAnimalType() + " died out of hunger.");
//                            animalService.removeCarnivore(carnivore1);
                        biomeService.getAnimalService().removeCarnivore(carnivore1);
                    }
                }
            }
        }
    }
    
    private void attack(List<Animal> carnivores, Carnivore carnivore, Herbivore herbivore) {
        //Distributing the food depending on the carnivore's way of living (GROUP, ALONE).
        foodDistributionDependingOnTheCarnivoresWayOfLiving(carnivores, carnivore, herbivore);
        
        System.out.println(carnivore.getAnimalType() + " killed " + herbivore.getAnimalType());
        biomeService.getAnimalService().removeHerbivore(herbivore);
    }
    
    private void runEcoSystemDependingOnTheBiome(BiomeEnum biome) {
        if (biome.equals(BiomeEnum.SAVANNA)) {
            biomeService.updateRepositoriesForSavanna();
        } else if (biome.equals(BiomeEnum.OCEAN)) {
            biomeService.updateRepositoriesForOcean();
        } else if (biome.equals(BiomeEnum.PLAINS)) {
            biomeService.updateRepositoriesForPlains();
        } else if (biome.equals(BiomeEnum.SWAMP)) {
            biomeService.updateRepositoriesForSwamp();
        } else if (biome.equals(BiomeEnum.TROPICAL_RAINFOREST)) {
            biomeService.updateRepositoriesForTropicalForest();
        } else if (biome.equals(BiomeEnum.TUNDRA)) {
            biomeService.updateRepositoriesForTundra();
        }
    }
    
    private boolean isAnimalsDead(List<Animal> carnivores, List<Animal> herbivores) {
        if (carnivores.size() == 0 && herbivores.size() == 0) {
            return false;
        }
        return true;
    }
    
    private void foodDistributionDependingOnTheCarnivoresWayOfLiving(List<Animal> carnivores, Animal animalCarnivore, Animal herbivore) {
        double foodInKg = herbivore.getWeight();
        double foodForMainAttacker;
        double foodForTheRestOfTheGroup;
        
        Carnivore carnivore = (Carnivore) animalCarnivore;
        
        if (carnivore.getLivingType().equals(LivingType.GROUP)) {
//            List<Group> groups = animalService.getGroups();
            List<Group> groups = biomeService.getAnimalService().getGroups();
//            List<Animal> groupOfAnimals = animalService.findGroup(groups, carnivore);
            List<Animal> groupOfAnimals = biomeService.getAnimalService().findGroup(groups, carnivore);
            
            int attackersAmount = groupOfAnimals.size();
            foodInKg /= attackersAmount + 1;
            foodForMainAttacker = foodInKg * 2;
            foodForTheRestOfTheGroup = foodInKg;
            decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(carnivores, carnivore, foodForMainAttacker, foodForTheRestOfTheGroup);
        } else {
            foodForMainAttacker = herbivore.getWeight();
//            animalService.decreaseHungerLevel(carnivore, foodForMainAttacker);
            biomeService.getAnimalService().decreaseHungerLevel(carnivore, foodForMainAttacker);
        }
    }
    
    private void decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(List<Animal> carnivores, Carnivore carnivore, double foodForMainAttacker, double foodForTheRestOfTheGroup) {
        for (Animal groupMember : carnivores) {
            if (groupMember.equals(carnivore)) {
//                List<Group> groups = animalService.getGroups();
                List<Group> groups = biomeService.getAnimalService().getGroups();
//                List<Animal> groupOfAnimals = animalService.findGroup(groups, carnivore);
                List<Animal> groupOfAnimals = biomeService.getAnimalService().findGroup(groups, carnivore);
                
                for (Animal animal : groupOfAnimals) {
                    if (animal.equals(carnivore)) {
                        //The main attacker receives two portions of food
//                        animalService.decreaseHungerLevel(carnivore, foodForMainAttacker);
                        biomeService.getAnimalService().decreaseHungerLevel(carnivore, foodForMainAttacker);
                    } else {
                        //Each groupMember that is not the attacker should receive a single portion of the food.
                        biomeService.getAnimalService().decreaseHungerLevel(carnivore, foodForTheRestOfTheGroup);
//                        animalService.decreaseHungerLevel(carnivore, foodForTheRestOfTheGroup);
                    }
                }
            }
        }
    }
    
    
    private void increasingCarnivoreHungerLevel(Animal animal) {
        Carnivore carnivore = (Carnivore) animal;
        int hungerLevel = new Random().nextInt(1, 10);
//        animalService.increaseHungerLevel(carnivore, hungerLevel);
        biomeService.getAnimalService().increaseHungerLevel(carnivore, hungerLevel);
    }
    
    private void checksIfTheMembersOfTheCarnivoreGroupReachedTheMaximumHungerRate() {
//        List<Group> groups = groupService.getGroups();
        List<Group> groups = biomeService.getGroupService().getGroups();
        
        for (Group group : groups) {
            List<Animal> listOfAnimals = group.getAnimals();
            
            for (Animal animal : listOfAnimals) {
                if (animal.getClass().getSimpleName().equals("Carnivore")) {
                    Carnivore newCarnivore = (Carnivore) animal;
                    if (newCarnivore.getHungerRate() >= 100) {
                        System.out.println(newCarnivore.getAnimalType() + " died out of hunger.");
                        biomeService.getAnimalService().removeCarnivore(newCarnivore);
//                        animalService.removeCarnivore(newCarnivore);
                    }
                }
            }
        }
    }
    
    private void increaseTheAgeOfTheAnimals(List<Animal> carnivores, List<Animal> herbivores) {
        for (Animal carnivore : carnivores) {
            carnivore.increaseAge();
            checkIfTheAnimalHasReachedItsMaxAge(carnivore);
        }
        
        for (Animal herbivore : herbivores) {
            herbivore.increaseAge();
            checkIfTheAnimalHasReachedItsMaxAge(herbivore);
        }
    }
    
    private void checkIfTheAnimalHasReachedItsMaxAge(Animal animal) {
        if (animal.getAge() > animal.getMaxAge()) {
            System.out.println("Animal " + animal.getAnimalType() + " died old." + animal.getAge());
            if (animal.getClass().getSimpleName().equals("Carnivore")) {
                biomeService.getAnimalService().removeCarnivore(animal);
//                animalService.removeCarnivore(animal);
            } else {
                biomeService.getAnimalService().removeHerbivore(animal);
//                animalService.removeHerbivore(animal);
            }
        }
    }
    
    /**
     * Takes care of the reproducing of the animals
     */
    private void animalFactory(List<Animal> carnivores, List<Animal> herbivores) {
        for (Animal carnivore : carnivores) {
            if (carnivore.getClass().getSimpleName().equals("Carnivore")) {
                double reproductionRate = carnivore.getReproductionRate();
                if (reproductionRate == 0) {
                    Carnivore newBornCarnivore = biomeService.getAnimalService().reproduce((Carnivore) carnivore);
                    biomeService.getAnimalService().addNewBorn(newBornCarnivore);
                    carnivore.increaseReproductionRate();
                    System.out.println("New carnivore " + newBornCarnivore.getAnimalType() + " is born.");
                }
            }
        }
        //TODO CHECK LOGIC AROUND THE REPRODUCING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for (Animal herbivore : herbivores) {
            if (herbivore.getClass().getSimpleName().equals("Herbivore")) {
                double reproductionRate = herbivore.getReproductionRate();
                if (reproductionRate <= 0) {
                    Herbivore newBornHerbivore = biomeService.getAnimalService().reproduce((Herbivore) herbivore);
                    biomeService.getAnimalService().addNewBorn(newBornHerbivore);
                    herbivore.increaseReproductionRate();
                    System.out.println("New herbivore " + newBornHerbivore.getAnimalType() + " is born.");
                }
            }
        }
    }
    
    public String printAnimalsInfo() {
        StringBuilder sb = new StringBuilder();

//        List<Animal> carnivores = animalService.getCarnivores();
//        List<Animal> herbivores = animalService.getHerbivores();
        
        List<Animal> carnivores = biomeService.getAnimalService().getCarnivores();
        List<Animal> herbivores = biomeService.getAnimalService().getHerbivores();
        
        if (carnivores.isEmpty() && herbivores.isEmpty()) {
            sb.append("No more animals alive left.");
            return sb.toString();
        }
        
        sb.append("Animals left to live: \n");
        
        for (Animal carnivore : carnivores) {
            sb.append(carnivore);
        }
        
        for (Animal herbivore : herbivores) {
            sb.append(herbivore);
        }
        
        return sb.toString();
    }
}
