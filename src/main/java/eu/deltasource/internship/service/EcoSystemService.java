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
    
    private AnimalService animalService = new AnimalService();
    private GroupService groupService = new GroupService();
    private BiomeRepository biomeRepository = new BiomeRepositoryImpl();
    
    private void updateRepositories() {
        Biome savanna = new Biome(HabitatEnum.LAND, BiomeEnum.SAVANNA);
        
        Animal miro = new Herbivore("miro", 85, 65, HabitatEnum.LAND, LivingType.GROUP, 11, 80, 60);
        Animal nici = new Herbivore("nici", 85, 68, HabitatEnum.LAND, LivingType.ALONE, 1, 90, 60);
        Animal irina = new Herbivore("irina", 85, 45, HabitatEnum.LAND, LivingType.ALONE, 1, 111, 60);
        Animal zebra = new Herbivore("zebra", 16, 45, HabitatEnum.LAND, LivingType.GROUP, 12, 121, 60);
        Animal hare = new Herbivore("hare", 9, 45, HabitatEnum.LAND, LivingType.ALONE, 1, 110, 60);
        Animal gazelle = new Herbivore("gazelle", 11, 45, HabitatEnum.LAND, LivingType.GROUP, 9, 121, 60);
        Animal buffalo = new Herbivore("byffallo", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 28, 171, 60);
        Animal krava = new Herbivore("krava", 8, 45, HabitatEnum.LAND, LivingType.GROUP, 5, 114, 60);
        Animal prasa = new Herbivore("pras", 6, 45, HabitatEnum.LAND, LivingType.GROUP, 14, 134, 60);
        Animal kon = new Herbivore("kon", 12, 45, HabitatEnum.LAND, LivingType.ALONE, 1, 176, 60);
        Animal koza = new Herbivore("koza", 11, 45, HabitatEnum.LAND, LivingType.GROUP, 19, 285, 60);
        animalService.addHerbivore(zebra, hare, gazelle, buffalo, krava, prasa, kon, koza, miro, nici, irina);
        
        Animal lion = new Carnivore("lion", 30, 150, HabitatEnum.LAND, LivingType.GROUP, 4, 79, 20, 80);
        Animal cheetah = new Carnivore("cheetah", 30, 60, HabitatEnum.LAND, LivingType.ALONE, 1, 105, 15, 110);
        Animal tiger = new Carnivore("tiger", 20, 200, HabitatEnum.LAND, LivingType.ALONE, 1, 189, 18, 75);
        Animal hyena = new Carnivore("hyena", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 7, 152, 14, 80);
        Animal kot = new Carnivore("hyena546768", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 1, 145, 14, 80);
        Animal ko4e = new Carnivore("hyena867543", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 8, 135, 14, 80);
        Animal galab = new Carnivore("hyena75645", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 1, 132, 14, 80);
        Animal riba = new Carnivore("hyena867546", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 24, 287, 14, 80);
        Animal lub4o = new Carnivore("lub4o", 84, 60, HabitatEnum.LAND, LivingType.ALONE, 1, 214, 14, 80);
        Animal topG = new Carnivore("topG", 84, 70, HabitatEnum.LAND, LivingType.ALONE, 1, 217, 14, 80);
        Animal galinski = new Carnivore("galinski", 84, 77, HabitatEnum.LAND, LivingType.GROUP, 16, 310, 14, 80);
        animalService.addCarnivore(lion, cheetah, tiger, hyena, kot, ko4e, galab, riba, lub4o, topG, galinski);
        
        biomeRepository.addBiomeAndAnimals(savanna, zebra, hare, gazelle, buffalo, krava, prasa, kon, koza,
                lion, cheetah, tiger, hyena, kot, ko4e, galab);
    }
    
    public void simulateEcoSystem() throws InterruptedException {
        updateRepositories();
        
        List<Animal> carnivores = animalService.getCarnivores();
        List<Animal> herbivores = animalService.getHerbivores();
        List<Animal> newBornAnimals = animalService.getNewBornAnimals();
        String biome = biomeRepository.getBiome();
        
        System.out.println("Action happening in the " + biome);
        //TODO THINK OF HOW TO IMPLEMENT THE CODE TO NEVER STOP THE ECO SYSTEM, BECAUSE NOW IT STOPS ONCE THE HERBIVORES ARE DEAD
         START_OF_SIMULATION:
        while (isAnimalsDead(carnivores, herbivores)) {
            
            if (herbivores.size() == 0) {
                while (carnivores.size() != 0) {
                    for (int i = 0; i < carnivores.size(); i++) {
                        Animal currentCarnivore = carnivores.get(i);
                        Carnivore carnivore1 = (Carnivore) currentCarnivore;
                        carnivore1.decreaseReproductionRate();
                        increasingCarnivoreHungerLevel(carnivore1);
                        
                        if (carnivore1.getHungerRate() >= 100) {
                            System.out.println(carnivore1.getAnimalType() + " died out of hunger.");
                            animalService.removeCarnivore(carnivore1);
                        }
                    }
                    if (carnivores.size() == 0) {
                        break START_OF_SIMULATION;
                    }
                }
                
            }
            
            for (Animal newBornAnimal : newBornAnimals) {
                if (newBornAnimal.getClass().getSimpleName().equals("Carnivore")) {
                    animalService.addCarnivore(newBornAnimal);
                } else {
                    animalService.addHerbivore(newBornAnimal);
                }
            }
            
            animalService.clearNewBornAnimalsList();
            
            Animal animalCarnivore = carnivores.get(new Random().nextInt(0, carnivores.size()));
            Animal animalHerbivore = herbivores.get(new Random().nextInt(0, herbivores.size()));
            
            Carnivore carnivore = (Carnivore) animalCarnivore;
            Herbivore herbivore = (Herbivore) animalHerbivore;
            
            //Calculates the success rate of the attack
            double successRate = animalService.getAttackSuccess(carnivore, herbivore);
            
            if (herbivore.getLivingType().equals(LivingType.GROUP)) {
                successRate = successRate - (successRate * 0.3);
            }
            
            int randomCoefficient = new Random().nextInt(0, 100);
            
            //If the attack is successful
            System.out.println(carnivore.getAnimalType() + " attacked " + herbivore.getAnimalType());
            if (successRate > randomCoefficient) {
                
                //Distributing the food depending on the carnivore's way of living (GROUP, ALONE).
                foodDistributionDependingOnTheCarnivoresWayOfLiving(carnivores, carnivore, herbivore);
                checksIfTheHerbivoreLivesInAGroup(herbivore);
                
                System.out.println(carnivore.getAnimalType() + " killed " + herbivore.getAnimalType());
                animalService.removeHerbivore(herbivore);
                
                //The program enters the scope below in case the attack does not happen (if successRate is less than the randomized number).
            } else {
                System.out.println(herbivore.getAnimalType() + " escaped the attack from " + carnivore.getAnimalType());
                
                increasingCarnivoreHungerLevel(carnivore);
            }
            
            checksIfTheMembersOfTheCarnivoreGroupReachedTheMaximumHungerRate();
            
            //Increase the age of all animals
            increaseTheAgeOfTheAnimals(carnivores, herbivores);
            
            //Reproduce new animals
            animalFactory(carnivores, herbivores);
            
            System.out.println("------------------------------------------");
            Thread.sleep(2000);
        }
    }
    
    private boolean isAnimalsDead(List<Animal> carnivores, List<Animal> herbivores) {
        if (carnivores.size() == 0 && herbivores.size() == 0) {
            return false;
        }
        return true;
    }
    
    private void foodDistributionDependingOnTheCarnivoresWayOfLiving(List<Animal> carnivores, Animal carnivore, Animal herbivore) {
        double foodInKg = herbivore.getWeight();
        double foodForMainAttacker;
        double foodForTheRestOfTheGroup = 0;
        
        Carnivore carnivore1 = (Carnivore) carnivore;
        Herbivore herbivore1 = (Herbivore) herbivore;
        
        if (carnivore.getLivingType().equals(LivingType.GROUP)) {
            //TODO EVERY CARNIVORE'S ATTACK POINTS SUM INTO ONE AND CALCULATE THE SUCCESS RATE
            //TODO IF THE HERBIVORE IS ALONE AND IS ATTACKED BY A GROUP OF CARNIVORES, THE SUCCESS RATE OF THE ATTACK INCREASES RAPIDLY, BECAUSE
            //TODO THE HERBIVORE'S CHANCE TO ESCAPE FROM A NUMBER OF CARNIVORES IS VERY LOW.
            createGroupOfCarnivores(carnivore1);
            List<Group> groups = animalService.getGroups();
            List<Animal> groupOfAnimals = animalService.findGroup(groups, carnivore);
            
            int attackersAmount = groupOfAnimals.size();
            foodInKg /= attackersAmount + 1;
            foodForMainAttacker = foodInKg * 2;
            foodForTheRestOfTheGroup = foodInKg;
            decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(carnivores, carnivore1, foodForMainAttacker, foodForTheRestOfTheGroup);
        } else {
            foodForMainAttacker = herbivore.getWeight();
            animalService.decreaseHungerLevel(carnivore1, foodForMainAttacker);
        }
    }
    
    private void decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(List<Animal> carnivores, Carnivore carnivore, double foodForMainAttacker, double foodForTheRestOfTheGroup) {
        for (Animal groupMember : carnivores) {
            if (groupMember.equals(carnivore)) {
                List<Group> groups = animalService.getGroups();
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
    
    private void checksIfTheHerbivoreLivesInAGroup(Herbivore herbivore) {
        if (herbivore.getLivingType().equals(LivingType.GROUP)) {
            createGroupOfHerbivores(herbivore);
        }
    }
    
    private void increasingCarnivoreHungerLevel(Animal animal) {
        Carnivore carnivore = (Carnivore) animal;
        int hungerLevel = new Random().nextInt(1, 10);
        animalService.increaseHungerLevel(carnivore, hungerLevel);
    }
    
    private void checksIfTheMembersOfTheCarnivoreGroupReachedTheMaximumHungerRate() {
        List<Group> groups = groupService.getGroups();
        for (Group group : groups) {
            List<Animal> listOfAnimals = group.getAnimals();
            
            for (Animal animal : listOfAnimals) {
                if (animal.getClass().getSimpleName().equals("Carnivore")) {
                    Carnivore newCarnivore = (Carnivore) animal;
                    if (newCarnivore.getHungerRate() >= 100) {
                        System.out.println(newCarnivore.getAnimalType() + " died out of hunger.");
                        animalService.removeCarnivore(newCarnivore);
                    }
                }
            }
        }
    }
    
    private void increaseTheAgeOfTheAnimals(List<Animal> carnivores, List<Animal> herbivores) {
        for (Animal carnivore : carnivores) {
            carnivore.increaseAge();
            carnivore.decreaseReproductionRate();
            checkIfTheAnimalHasReachedItsMaxAge(carnivore);
        }
        
        for (Animal herbivore : herbivores) {
            herbivore.increaseAge();
            herbivore.decreaseReproductionRate();
            checkIfTheAnimalHasReachedItsMaxAge(herbivore);
        }
    }
    
    private void checkIfTheAnimalHasReachedItsMaxAge(Animal animal) {
        if (animal.getAge() > animal.getMaxAge()) {
            System.out.println("Animal " + animal.getAnimalType() + " died old." + animal.getAge());
            if (animal.getClass().getSimpleName().equals("Carnivore")) {
                Carnivore carnivoreToRemove = (Carnivore) animal;
                animalService.removeCarnivore(carnivoreToRemove);
            } else {
                Herbivore herbivoreToRemove = (Herbivore) animal;
                animalService.removeHerbivore(herbivoreToRemove);
            }
        }
    }
    
    private void animalFactory(List<Animal> carnivores, List<Animal> herbivores) {
        for (Animal carnivore : carnivores) {
            if (carnivore.getClass().getSimpleName().equals("Carnivore")) {
                if (carnivore.getReproductionRate() == 0) {
                    carnivore.increaseReproductionRate();
                    Carnivore newBornCarnivore = animalService.reproduce((Carnivore) carnivore);
                    animalService.addNewBorn(newBornCarnivore);
                    System.out.println("New carnivore " + newBornCarnivore.getAnimalType() + " is born.");
                }
            }
        }
        //TODO CHECK LOGIC AROUND THE REPRODUCING
        for (Animal herbivore : herbivores) {
            if (herbivore.getClass().getSimpleName().equals("Herbivore")) {
                if (herbivore.getReproductionRate() == 0) {
                    herbivore.increaseReproductionRate();
                    Herbivore newBornHerbivore = animalService.reproduce((Herbivore) herbivore);
                    animalService.addNewBorn(newBornHerbivore);
                    System.out.println("New herbivore " + newBornHerbivore.getAnimalType() + " is born.");
                }
            }
        }
    }
    
    public String printAnimals() {
        StringBuilder sb = new StringBuilder();
        
        List<Animal> carnivores = animalService.getCarnivores();
        List<Animal> herbivores = animalService.getHerbivores();
        
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
    
    private void createGroupOfCarnivores(Carnivore carnivore) {
        Group groupOfCarnivores = new Group();
        
        groupOfCarnivores.addAnimal(carnivore);
        for (int i = 0; i < carnivore.getGroupAmount(); i++) {
            double maxAge = carnivore.getMaxAge();
            double weight = new Random().nextDouble(0, carnivore.getWeight());
            double productionRate = carnivore.getOriginalReproductionRate();
            int hungerRate = new Random().nextInt(1, 100);
            int attackPoints = new Random().nextInt(0, carnivore.getOriginalAttackPoints());
            int groupAmount = carnivore.getGroupAmount();
            
            Carnivore animalInGroup = new Carnivore(carnivore.getAnimalType(), maxAge, weight, carnivore.getMainHabitat(), carnivore.getLivingType(), groupAmount, productionRate, hungerRate, attackPoints);
            
            animalService.addCarnivore(animalInGroup);
            groupOfCarnivores.addAnimal(animalInGroup);
        }
        animalService.addGroupOfCarnivores(groupOfCarnivores);
    }
    
    private void createGroupOfHerbivores(Herbivore herbivore) {
        Group groupOfHerbivores = new Group();
        
        groupOfHerbivores.addAnimal(herbivore);
        for (int i = 0; i < herbivore.getGroupAmount(); i++) {
            double maxAge = herbivore.getOriginalMaxAge();
            double weight = new Random().nextDouble(0, herbivore.getOriginalWeight());
            double productionRate = herbivore.getOriginalReproductionRate();
            int escapePoints = herbivore.getOriginalEscapePoints();
            int groupAmount = herbivore.getGroupAmount();
            
            Herbivore animalInGroup = new Herbivore(herbivore.getAnimalType(), maxAge, weight, herbivore.getMainHabitat(), herbivore.getLivingType(), groupAmount, productionRate, escapePoints);
            
            animalService.addHerbivore(animalInGroup);
            groupOfHerbivores.addAnimal(animalInGroup);
        }
        animalService.addGroupOfHerbivores(groupOfHerbivores);
    }
    
}
