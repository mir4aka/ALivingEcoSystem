package eu.deltasource.internship.service;

import com.google.gson.*;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.*;
import eu.deltasource.internship.service.helper.NewBornCarnivoresRepository;
import eu.deltasource.internship.service.helper.NewBornHerbivoresRepository;
import eu.deltasource.internship.service.helper.ReproductionRateHelper;
import eu.deltasource.internship.service.helper.SuccessChanceCalculator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EcoSystemService {
    private BiomeService biomeService;
    private AnimalService animalService;
    private GroupService groupService;
    private ReproductionRateHelper reproduceRateHelper;
    private SuccessChanceCalculator successChanceCalculator;
    private NewBornCarnivoresRepository newBornCarnivoresCollection;
    private NewBornHerbivoresRepository newBornHerbivoresCollection;
    
    public EcoSystemService(BiomeService biomeService, AnimalService animalService, GroupService groupService, ReproductionRateHelper reproduceRateHelper, SuccessChanceCalculator successChanceCalculator, NewBornCarnivoresRepository newBornCarnivoresCollection, NewBornHerbivoresRepository newBornHerbivoresCollection) {
        this.biomeService = biomeService;
        this.animalService = animalService;
        this.groupService = groupService;
        this.reproduceRateHelper = reproduceRateHelper;
        this.successChanceCalculator = successChanceCalculator;
        this.newBornCarnivoresCollection = newBornCarnivoresCollection;
        this.newBornHerbivoresCollection = newBornHerbivoresCollection;
    }
    
    public void simulateEcoSystem(BiomeEnum ecoSystemBiome) throws IOException, InterruptedException {
        
        List<Carnivore> carnivores = animalService.getCarnivores();
        List<Herbivore> herbivores = animalService.getHerbivores();
        List<Carnivore> newBornCarnivores = newBornCarnivoresCollection.getNewBornCarnivores();
        List<Herbivore> newBornHerbivores = newBornHerbivoresCollection.getNewBornHerbivores();
        
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
            increaseEachCarnivoresHungerLevel(carnivores);
    
            System.out.println("------------------------------------------");
            Thread.sleep(2000);
        }
    }
    
    private void increaseEachCarnivoresHungerLevel(List<Carnivore> carnivores) {
        for (Carnivore carnivore : carnivores) {
            animalService.increaseHungerLevel(carnivore);
        }
    }
    
    private void addNewBornAnimalsToTheirSpecies(List<Carnivore> newBornCarnivores, List<Herbivore> newBornHerbivores) {
        List<Carnivore> carnivores1 = addNewBornCarnivoresToTheCarnivores(newBornCarnivores);
        List<Herbivore> herbivores1 = addNewBornHerbivoresToTheHerbivores(newBornHerbivores);
        
        for (Carnivore carnivore : carnivores1) {
            animalService.addCarnivoreToRepository(carnivore);
        }
        
        for (Herbivore herbivore : herbivores1) {
            animalService.addHerbivoreToRepository(herbivore);
        }
        newBornCarnivoresCollection.clearNewBornCarnivoresRepository();
        newBornHerbivoresCollection.clearNewBornHerbivoresRepository();
    }
    
    private JsonElement readingFile() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        return parser.parse(new FileReader("C:\\Users\\mirchakis\\IdeaProjects\\ProjectsDeltaSource\\ALivingEcoSystem\\ALivingEcoSystem\\src\\main\\resources\\animals.json"));
    }
    
    private boolean isAnimalsDead(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        return carnivores.size() != 0 || herbivores.size() != 0;
    }
    
    private void increaseHungerLevelOfCarnivoresIfAllHerbivoresAreDead(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        if (herbivores.size() == 0) {
            while (carnivores.size() > 0) {
                for (int i = 0; i < carnivores.size(); i++) {
                    Carnivore carnivore = carnivores.get(i);
                    increasingCarnivoreHungerLevel(carnivore);
                    if (carnivore.getHungerLevel() >= 100) {
                        System.out.println(carnivore.getSpecie() + " died out of hunger.");
                        Animal carnivoreInGroup = groupService.findCarnivoreInGroup(carnivore);
                        animalService.removeCarnivoreFromRepository(carnivore);
                        groupService.removeCarnivore(carnivoreInGroup);
                    }
                }
            }
        }
    }
    
    private void increasingCarnivoreHungerLevel(Carnivore carnivore) {
        animalService.increaseHungerLevel(carnivore);
    }
    
    private List<Carnivore> addNewBornCarnivoresToTheCarnivores(List<Carnivore> newBornCarnivores) {
        return new ArrayList<>(newBornCarnivores);
    }
    
    private List<Herbivore> addNewBornHerbivoresToTheHerbivores(List<Herbivore> newBornHerbivores) {
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
        double successRate = successChanceCalculator.getAttackSuccess(carnivore, herbivore);
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
        if (checkIfTheAnimalHasReachedItsMaxAge(carnivore) ||
                checkIfTheAnimalHasReachedItsMaxAge(herbivore)) {
            animalService.removeCarnivoreFromRepository(carnivore);
            animalService.removeHerbivoreFromRepository(herbivore);
        }
    }
    
    private void attack(List<Carnivore> carnivores, Carnivore carnivore, Herbivore herbivore) {
        //Distributing the food depending on the carnivore's social status (GROUP, ALONE).
        foodDistributionDependingOnTheCarnivoresSocialStatus(carnivores, carnivore, herbivore);
        System.out.println(carnivore.getSpecie() + " killed " + herbivore.getSpecie());
        animalService.removeHerbivoreFromRepository(herbivore);
    }
    
    /**
     * Distributing the food of the carnivores whether the carnivore is living in a group or alone.
     */
    private void foodDistributionDependingOnTheCarnivoresSocialStatus(List<Carnivore> carnivores, Carnivore carnivore, Herbivore herbivore) {
        double foodInKg = herbivore.getWeight();
        double foodForMainAttacker;
        
        if (carnivore.getSocialStatus().equals(SocialStatus.GROUP)) {
            calculateFoodDistributionBetweenTheGroupMembers(carnivores, carnivore, foodInKg);
        } else {
            foodForMainAttacker = herbivore.getWeight();
            animalService.decreaseHungerLevel(carnivore, foodForMainAttacker);
        }
    }
    
    private void calculateFoodDistributionBetweenTheGroupMembers(List<Carnivore> carnivores, Carnivore carnivore, double foodInKg) {
        double foodForMainAttacker;
        double foodForTheRestOfTheGroup;
        List<Group> groups = animalService.getCarnivoresGroup();
        List<Animal> groupOfAnimals = animalService.findGroup(groups, carnivore);
        int attackersAmount = groupOfAnimals.size();
        foodInKg /= attackersAmount + 1;
        foodForMainAttacker = foodInKg * 2;
        foodForTheRestOfTheGroup = foodInKg;
        decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(carnivores, carnivore, foodForMainAttacker, foodForTheRestOfTheGroup);
    }
    
    /**
     * After the attack is successful, hunger rate of each member of the group is being decreased, the main attacker of the group receives two portions.
     */
    private void decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(List<Carnivore> carnivores, Carnivore carnivore, double foodForMainAttacker, double foodForTheRestOfTheGroup) {
        for (Animal groupMember : carnivores) {
            if (groupMember.equals(carnivore)) {
                List<Group> groups = animalService.getCarnivoresGroup();
                List<Animal> groupOfAnimals = animalService.findGroup(groups, carnivore);
                for (Animal animal : groupOfAnimals) {
                    if (animal.equals(carnivore)) {
                        //The main attacker receives two portions of food
                        animalService.decreaseHungerLevel(carnivore, foodForMainAttacker);
                    } else {
                        //Each groupMember that is not the attacker should receive a single portion of the food.
                        animalService.decreaseHungerLevel(carnivore, foodForTheRestOfTheGroup);
                    }
                }
            }
        }
    }
    
    private void increaseTheAgeOfTheAnimals(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        for (Carnivore carnivore : carnivores) {
            animalService.increaseAge(carnivore);
        }
        
        for (Herbivore herbivore : herbivores) {
            animalService.increaseAge(herbivore);
        }
    }
    
    /**
     * If the carnivore reaches its max age, it dies.
     *
     * @param carnivore
     */
    private boolean checkIfTheAnimalHasReachedItsMaxAge(Carnivore carnivore) {
        if (carnivore.getAge() >= carnivore.getMaxAge()) {
            System.out.println("Animal " + carnivore.getSpecie() + " died old." + carnivore.getAge());
            return true;
        }
        return false;
    }
    
    /**
     * If the herbivore reaches its max age, it dies.
     *
     * @param herbivore
     */
    private boolean checkIfTheAnimalHasReachedItsMaxAge(Herbivore herbivore) {
        if (herbivore.getAge() >= herbivore.getMaxAge()) {
            System.out.println("Animal " + herbivore.getSpecie() + " died old." + herbivore.getAge());
            return true;
        }
        return false;
    }
    
    /**
     * Iterates the herbivores and carnivores and checks if their reproduction rate is equal to 0, if it is - an animal is being reproduced.
     */
    private void animalFactory(List<Carnivore> carnivores, List<Herbivore> herbivores) {
        carnivoreFactory(carnivores);
        herbivoreFactory(herbivores);
    }
    
    private void carnivoreFactory(List<Carnivore> carnivores) {
        for (Carnivore carnivore : carnivores) {
            int reproductionLevel = carnivore.getReproductionLevel();
            if (reproductionLevel == 0) {
                Carnivore newBornCarnivore = reproduceRateHelper.reproduce(carnivore);
                newBornCarnivoresCollection.addNewBornCarnivore(newBornCarnivore);
                reproduceRateHelper.resetReproductionLevel(carnivore);
                System.out.println("New carnivore " + newBornCarnivore.getSpecie() + " is born.");
            } else {
                reproduceRateHelper.decreaseReproductionLevel(carnivore);
            }
        }
    }
    
    private void herbivoreFactory(List<Herbivore> herbivores) {
        for (Herbivore herbivore : herbivores) {
            int reproductionLevel = herbivore.getReproductionLevel();
            if (reproductionLevel <= 0) {
                Herbivore newBornHerbivore = reproduceRateHelper.reproduce(herbivore);
                newBornHerbivoresCollection.addNewBornHerbivore(newBornHerbivore);
                reproduceRateHelper.resetReproductionLevel(herbivore);
                System.out.println("New herbivore " + newBornHerbivore.getSpecie() + " is born.");
            } else {
                reproduceRateHelper.decreaseReproductionLevel(herbivore);
            }
        }
    }
    
    /**
     * Straight forward prints animals' information, for example specie and age.
     * The goal of the application is not to reach this method, so it is not really used, unless all the animals are dead.
     */
    public String printAnimalsInfo() {
        StringBuilder sb = new StringBuilder();
        List<Carnivore> carnivores = animalService.getCarnivores();
        List<Herbivore> herbivores = animalService.getHerbivores();
        
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
