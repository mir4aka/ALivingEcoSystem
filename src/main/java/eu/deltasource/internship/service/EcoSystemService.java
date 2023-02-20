package eu.deltasource.internship.service;

import com.google.gson.*;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class EcoSystemService {
    private BiomeService biomeService = new BiomeService();
    
    public void simulateEcoSystem(BiomeEnum ecoSystemBiome) throws InterruptedException, IOException {
        
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonElement parse = parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\animals.json"));
        JsonObject JSONObject = (JsonObject) parse;
    
        List<Carnivore> carnivores = biomeService.getAnimalService().getCarnivores();
        List<Herbivore> herbivores = biomeService.getAnimalService().getHerbivores();
    
        String biome = biomeService.updateAnimalsRepositories(ecoSystemBiome, gson, JSONObject, carnivores, herbivores);
    
        List<Carnivore> newBornCarnivores = biomeService.getAnimalService().getNewBornCarnivores();
        List<Herbivore> newBornHerbivores = biomeService.getAnimalService().getNewBornHerbivores();
        
        System.out.println("Action happening in " + biome);
        while (isAnimalsDead(carnivores, herbivores)) {
            increaseHungerLevelOfCarnivoresIfAllHerbivoresAreDead(carnivores, herbivores);
            
            
            addNewBornAnimalsToTheirGroups(newBornCarnivores, newBornHerbivores);
            
            carnivoreAttacksHerbivore(carnivores, herbivores);
            
            increaseTheAgeOfTheAnimals(carnivores, herbivores);
            
            //Reproduce new animals
            animalFactory(carnivores, herbivores);
            
            System.out.println("------------------------------------------");
            Thread.sleep(2000);
        }
    }

//    private void runEcoSystemDependingOnTheBiome(BiomeEnum biome) {
//        if (biome.equals(BiomeEnum.SAVANNA)) {
//            biomeService.updateRepositoriesForSavanna();
//        } else if (biome.equals(BiomeEnum.OCEAN)) {
//            biomeService.updateRepositoriesForOcean();
//        } else if (biome.equals(BiomeEnum.PLAINS)) {
//            biomeService.updateRepositoriesForPlains();
//        } else if (biome.equals(BiomeEnum.SWAMP)) {
//            biomeService.updateRepositoriesForSwamp();
//        } else if (biome.equals(BiomeEnum.TROPICAL_RAINFOREST)) {
//            biomeService.updateRepositoriesForTropicalForest();
//        } else if (biome.equals(BiomeEnum.TUNDRA)) {
//            biomeService.updateRepositoriesForTundra();
//        }
//    }
    
    private boolean isAnimalsDead(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        return carnivores.size() != 0 && herbivores.size() != 0;
    }
    
    private void increaseHungerLevelOfCarnivoresIfAllHerbivoresAreDead(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        if (herbivores.size() == 0) {
            while (carnivores.size() > 0) {
                for (int i = 0; i < carnivores.size(); i++) {
                    Carnivore carnivore = carnivores.get(i);
                    increasingCarnivoreHungerLevel(carnivore);
                    if (carnivore.getHungerRate() >= 100) {
                        System.out.println(carnivore.getSpecie() + " died out of hunger.");
                        biomeService.getAnimalService().removeCarnivore(carnivore);
                        Carnivore carnivoreInGroup = biomeService.getGroupService().findCarnivoreInGroup(carnivore);
                        
                        biomeService.getGroupService().removeAnimal(carnivoreInGroup);
                        biomeService.getGroupService().findFirstAnimalToRemove(carnivoreInGroup);
                    }
                }
            }
        }
    }
    
    private void increasingCarnivoreHungerLevel(Carnivore carnivore) {
        int hungerLevel = new Random().nextInt(1, 10);
        biomeService.getAnimalService().increaseHungerLevel(carnivore, hungerLevel);
    }
    
    /**
     * If there are any newborn animals, they are being added to all carnivores/herbivores
     */
    private void addNewBornAnimalsToTheirGroups(List<Carnivore> newBornCarnivores, List<Herbivore> newBornHerbivores) {
        for (Carnivore newBornCarnivore : newBornCarnivores) {
            biomeService.getAnimalService().addCarnivore(newBornCarnivore);
        }
        
        for (Herbivore newBornHerbivore : newBornHerbivores) {
            biomeService.getAnimalService().addHerbivore(newBornHerbivore);
        }
        
        biomeService.getAnimalService().clearNewBornCarnivoresList();
        biomeService.getAnimalService().clearNewBornHerbivoresList();
    }
    
    /**
     * Attacking method that calculates the success chance of the attack.
     */
    private void carnivoreAttacksHerbivore(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        Carnivore carnivore = carnivores.get(new Random().nextInt(0, carnivores.size()));
        Herbivore herbivore = herbivores.get(new Random().nextInt(0, herbivores.size()));
        
        //Calculates the success rate of the attack
        double successRate = biomeService.getAnimalService().getAttackSuccess(carnivore, herbivore);
        
        double randomNumberGenerated = new Random().nextDouble(0, 100);
        
        System.out.println(carnivore.getSpecie() + " attacked " + herbivore.getSpecie());
        if (successRate > randomNumberGenerated) {
            //If the attack is successful
            attack(carnivores, carnivore, herbivore);
        } else {
            //The program enters this scope in case the attack does not happen (if successRate is less than the randomized number).
            System.out.println(herbivore.getSpecie() + " escaped the attack from " + carnivore.getSpecie());
            increasingCarnivoreHungerLevel(carnivore);
        }
        
        biomeService.getAnimalService().decreaseReproductionRate(carnivore);
        biomeService.getAnimalService().decreaseReproductionRate(herbivore);
    }
    
    private void attack(List<Carnivore> carnivores, Carnivore carnivore, Herbivore herbivore) {
        //Distributing the food depending on the carnivore's social status (GROUP, ALONE).
        foodDistributionDependingOnTheCarnivoresWayOfLiving(carnivores, carnivore, herbivore);
        
        System.out.println(carnivore.getSpecie() + " killed " + herbivore.getSpecie());
        biomeService.getAnimalService().removeHerbivore(herbivore);
    }
    
    private void foodDistributionDependingOnTheCarnivoresWayOfLiving(List<Carnivore> carnivores, Carnivore carnivore, Herbivore herbivore) {
        double foodInKg = herbivore.getWeight();
        double foodForMainAttacker;
        double foodForTheRestOfTheGroup;
        
        if (carnivore.getSocialStatus().equals(SocialStatus.GROUP)) {
            List<Group> groups = biomeService.getAnimalService().getCarnivoresGroup();
            List<Animal> groupOfAnimals = biomeService.getAnimalService().findGroup(groups, carnivore);
            
            int attackersAmount = groupOfAnimals.size();
            foodInKg /= attackersAmount + 1;
            foodForMainAttacker = foodInKg * 2;
            foodForTheRestOfTheGroup = foodInKg;
            decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(carnivores, carnivore, foodForMainAttacker, foodForTheRestOfTheGroup);
        } else {
            foodForMainAttacker = herbivore.getWeight();
            biomeService.getAnimalService().decreaseHungerLevel(carnivore, foodForMainAttacker);
        }
    }
    
    private void decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(List<Carnivore> carnivores, Carnivore carnivore, double foodForMainAttacker, double foodForTheRestOfTheGroup) {
        for (Animal groupMember : carnivores) {
            if (groupMember.equals(carnivore)) {
                List<Group> groups = biomeService.getAnimalService().getCarnivoresGroup();
                List<Animal> groupOfAnimals = biomeService.getAnimalService().findGroup(groups, carnivore);
                
                for (Animal animal : groupOfAnimals) {
                    if (animal.equals(carnivore)) {
                        //The main attacker receives two portions of food
                        biomeService.getAnimalService().decreaseHungerLevel(carnivore, foodForMainAttacker);
                    } else {
                        //Each groupMember that is not the attacker should receive a single portion of the food.
                        biomeService.getAnimalService().decreaseHungerLevel(carnivore, foodForTheRestOfTheGroup);
                    }
                }
            }
        }
    }
    
    private void increaseTheAgeOfTheAnimals(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        for (Carnivore carnivore : carnivores) {
            biomeService.getAnimalService().increaseAge(carnivore);
            checkIfTheAnimalHasReachedItsMaxAge(carnivore);
        }
        
        for (Herbivore herbivore : herbivores) {
            biomeService.getAnimalService().increaseAge(herbivore);
            checkIfTheAnimalHasReachedItsMaxAge(herbivore);
        }
    }
    
    private void checkIfTheAnimalHasReachedItsMaxAge(Carnivore carnivore) {
        if (carnivore.getAge() > carnivore.getMaxAge()) {
            System.out.println("Animal " + carnivore.getSpecie() + " died old." + carnivore.getAge());
            biomeService.getAnimalService().removeCarnivore(carnivore);
        }
    }
    
    private void checkIfTheAnimalHasReachedItsMaxAge(Herbivore herbivore) {
        if (herbivore.getAge() > herbivore.getMaxAge()) {
            System.out.println("Animal " + herbivore.getSpecie() + " died old." + herbivore.getAge());
            biomeService.getAnimalService().removeHerbivore(herbivore);
        }
    }
    
    /**
     * Takes care of the reproducing of the animals
     */
    private void animalFactory(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        for (Carnivore carnivore : carnivores) {
            if (carnivore.getClass().getSimpleName().equals("Carnivore")) {
                double reproductionRate = carnivore.getReproductionRate();
                if (reproductionRate == 0) {
                    Carnivore newBornCarnivore = biomeService.getAnimalService().reproduce(carnivore);
                    biomeService.getAnimalService().addNewBornCarnivore(newBornCarnivore);
                    biomeService.getAnimalService().resetReproductionRate(carnivore);
                    System.out.println("New carnivore " + newBornCarnivore.getSpecie() + " is born.");
                }
            }
        }
        for (Herbivore herbivore : herbivores) {
            if (herbivore.getClass().getSimpleName().equals("Herbivore")) {
                double reproductionRate = herbivore.getReproductionRate();
                if (reproductionRate <= 0) {
                    Herbivore newBornHerbivore = biomeService.getAnimalService().reproduce(herbivore);
                    biomeService.getAnimalService().addNewBornHerbivore(newBornHerbivore);
                    biomeService.getAnimalService().resetReproductionRate(herbivore);
                    System.out.println("New herbivore " + newBornHerbivore.getSpecie() + " is born.");
                }
            }
        }
    }
    
    
    public String printAnimalsInfo() {
        StringBuilder sb = new StringBuilder();
        
        List<Carnivore> carnivores = biomeService.getAnimalService().getCarnivores();
        List<Herbivore> herbivores = biomeService.getAnimalService().getHerbivores();
        
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
