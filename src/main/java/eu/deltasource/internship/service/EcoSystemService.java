package eu.deltasource.internship.service;

import com.google.gson.*;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EcoSystemService {
    private BiomeService biomeService;
    
    public EcoSystemService(BiomeService biomeService) {
        this.biomeService = biomeService;
    }
    
    public void simulateEcoSystem(BiomeEnum ecoSystemBiome) throws InterruptedException, IOException {
        
        List<Carnivore> carnivores = biomeService.getAnimalService().getCarnivores();
        List<Herbivore> herbivores = biomeService.getAnimalService().getHerbivores();
        List<Carnivore> newBornCarnivores = biomeService.getAnimalService().getNewBornCarnivores();
        List<Herbivore> newBornHerbivores = biomeService.getAnimalService().getNewBornHerbivores();
        
        Gson gson = new GsonBuilder().create();
        JsonObject jsonObject = readingFile().getAsJsonObject();
        
        String biome = biomeService.updateAnimalsRepositories(ecoSystemBiome, gson, jsonObject);
        
        System.out.println("Action happening in " + biome);
        while (isAnimalsDead(carnivores, herbivores)) {
            increaseHungerLevelOfCarnivoresIfAllHerbivoresAreDead(carnivores, herbivores);
            
            if (carnivores.size() == 0) {
                break;
            }
            
            addNewBornAnimalsToTheirSpecies(newBornCarnivores, newBornHerbivores);
            carnivoreAttacksHerbivore(carnivores, herbivores);
            increaseTheAgeOfTheAnimals(carnivores, herbivores);
            animalFactory(carnivores, herbivores);
            
            System.out.println("------------------------------------------");
            Thread.sleep(2000);
        }
    }
    
    public void addNewBornAnimalsToTheirSpecies(List<Carnivore> newBornCarnivores, List<Herbivore> newBornHerbivores) {
        List<Carnivore> carnivores1 = addNewBornCarnivoresToTheCarnivores(newBornCarnivores);
        List<Herbivore> herbivores1 = addNewBornHerbivoresToTheHerbivores(newBornHerbivores);
        
        for (Carnivore carnivore : carnivores1) {
            biomeService.getAnimalService().addCarnivore(carnivore);
        }
        
        for (Herbivore herbivore : herbivores1) {
            biomeService.getAnimalService().addHerbivore(herbivore);
        }
        biomeService.getAnimalService().clearNewBornCarnivoresList();
        biomeService.getAnimalService().clearNewBornHerbivoresList();
    }
    
    private JsonElement readingFile() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        return parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\animals.json"));
    }
    
    public boolean isAnimalsDead(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        return carnivores.size() != 0 || herbivores.size() != 0;
    }
    
    public void increaseHungerLevelOfCarnivoresIfAllHerbivoresAreDead(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        if (herbivores.size() == 0) {
            while (carnivores.size() > 0) {
                for (int i = 0; i < carnivores.size(); i++) {
                    Carnivore carnivore = carnivores.get(i);
                    increasingCarnivoreHungerLevel(carnivore);
                    if (carnivore.getHungerRate() >= 100) {
                        System.out.println(carnivore.getSpecie() + " died out of hunger.");
                        Animal carnivoreInGroup = biomeService.getGroupService().findCarnivoreInGroup(carnivore);
                        biomeService.getAnimalService().removeCarnivore(carnivore);
                        biomeService.getGroupService().removeCarnivore(carnivoreInGroup);
                    }
                }
            }
        }
    }
    
    private void increasingCarnivoreHungerLevel(Carnivore carnivore) {
        int hungerLevel = new Random().nextInt(1, 10);
        biomeService.getAnimalService().increaseHungerLevel(carnivore, hungerLevel);
    }
    
    public List<Carnivore> addNewBornCarnivoresToTheCarnivores(List<Carnivore> newBornCarnivores) {
        return new ArrayList<>(newBornCarnivores);
    }
    
    public List<Herbivore> addNewBornHerbivoresToTheHerbivores(List<Herbivore> newBornHerbivores) {
        return new ArrayList<>(newBornHerbivores);
    }
    
    /**
     * Gets a random carnivore and a random herbivore from the lists, calculates the success chance.
     *
     * @param carnivores
     * @param herbivores
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
        foodDistributionDependingOnTheCarnivoresSocialStatus(carnivores, carnivore, herbivore);
        System.out.println(carnivore.getSpecie() + " killed " + herbivore.getSpecie());
        biomeService.getAnimalService().removeHerbivore(herbivore);
    }
    
    /**
     * Distributing the food of the carnivores whether the carnivore is living in a group or alone.
     */
    public void foodDistributionDependingOnTheCarnivoresSocialStatus(List<Carnivore> carnivores, Carnivore carnivore, Herbivore herbivore) {
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
    
    /**
     * After the attack is successful, hunger rate of each member of the group is being decreased, the main attacker of the group receives two portions.
     */
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
    
    /**
     * If the carnivore reaches its max age, it dies.
     *
     * @param carnivore
     */
    private void checkIfTheAnimalHasReachedItsMaxAge(Carnivore carnivore) {
        if (carnivore.getAge() > carnivore.getMaxAge()) {
            System.out.println("Animal " + carnivore.getSpecie() + " died old." + carnivore.getAge());
            biomeService.getAnimalService().removeCarnivore(carnivore);
        }
    }
    
    /**
     * If the herbivore reaches its max age, it dies.
     *
     * @param herbivore
     */
    private void checkIfTheAnimalHasReachedItsMaxAge(Herbivore herbivore) {
        if (herbivore.getAge() > herbivore.getMaxAge()) {
            System.out.println("Animal " + herbivore.getSpecie() + " died old." + herbivore.getAge());
            biomeService.getAnimalService().removeHerbivore(herbivore);
        }
    }
    
    /**
     * Iterates the herbivores and carnivores and checks if their reproduction rate is equal to 0, if it is - an animal is being reproduced.
     */
    public void animalFactory(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        for (Carnivore carnivore : carnivores) {
            double reproductionRate = carnivore.getReproductionRate();
            if (reproductionRate == 0) {
                Carnivore newBornCarnivore = biomeService.getAnimalService().reproduce(carnivore);
                biomeService.getAnimalService().addNewBornCarnivore(newBornCarnivore);
                biomeService.getAnimalService().resetReproductionRate(carnivore);
                System.out.println("New carnivore " + newBornCarnivore.getSpecie() + " is born.");
            }
        }
        for (Herbivore herbivore : herbivores) {
            double reproductionRate = herbivore.getReproductionRate();
            if (reproductionRate <= 0) {
                Herbivore newBornHerbivore = biomeService.getAnimalService().reproduce(herbivore);
                biomeService.getAnimalService().addNewBornHerbivore(newBornHerbivore);
                biomeService.getAnimalService().resetReproductionRate(herbivore);
                System.out.println("New herbivore " + newBornHerbivore.getSpecie() + " is born.");
            }
        }
    }
    
    /**
     * Straight forward prints animals' information, for example specie and age.
     * The goal of the application is not to reach this method, so it is not really used, unless all the animals are dead.
     */
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
