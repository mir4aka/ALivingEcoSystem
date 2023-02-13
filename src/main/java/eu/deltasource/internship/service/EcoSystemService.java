package eu.deltasource.internship.service;

import eu.deltasource.internship.BiomeRepository.BiomeRepository;
import eu.deltasource.internship.BiomeRepository.BiomeRepositoryImpl;
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
        
        Animal miro = new Herbivore("miro", 85, 65, HabitatEnum.LAND, LivingType.ALONE, 8, 60);
        Animal nici = new Herbivore("nici", 85, 68, HabitatEnum.LAND, LivingType.ALONE, 9, 60);
        Animal irina = new Herbivore("irina", 85, 45, HabitatEnum.LAND, LivingType.ALONE, 11, 60);
//        Herbivore koza12 = new Herbivore("koza5334", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 24, 60);
//        Herbivore koza123 = new Herbivore("koza756756", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 43, 60);
//        Herbivore koza1234556756765 = new Herbivore("koza75675", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 14, 60);
//        Herbivore koza12534 = new Herbivore("koza545654", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 34, 60);
//        Herbivore koza12654 = new Herbivore("koza456", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 27, 60);
//        Herbivore koza12756 = new Herbivore("koza3454324", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 34, 60);
//        Herbivore koza127867 = new Herbivore("koza5467657", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 65, 60);
//        Herbivore koza1296854 = new Herbivore("koza6754", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 19, 60);
//        Herbivore koza12435654 = new Herbivore("koza7574543", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 82, 60);
//        animalService.addHerbivore(zebra, hare, gazelle, buffalo, krava, prasa, kon, koza);
        animalService.addHerbivore(miro, nici, irina);

//        Animal lion = new Carnivore("lion", 30, 150, HabitatEnum.LAND, LivingType.GROUP, 6, 20, 80);
//        Animal cheetah = new Carnivore("cheetah", 30, 60, HabitatEnum.LAND, LivingType.ALONE, 5, 15, 110);
//        Animal tiger = new Carnivore("tiger", 20, 200, HabitatEnum.LAND, LivingType.ALONE, 6, 18, 75);
//        Animal hyena = new Carnivore("hyena", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 52, 14, 80);
//        Animal kot = new Carnivore("hyena546768", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 45, 14, 80);
//        Animal ko4e = new Carnivore("hyena867543", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 35, 14, 80);
//        Animal galab = new Carnivore("hyena75645", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 32, 14, 80);
//        Animal riba = new Carnivore("hyena867546", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 87, 14, 80);
        Animal lub4o = new Carnivore("lub4o", 84, 60, HabitatEnum.LAND, LivingType.ALONE, 14, 14, 80);
        Animal topG = new Carnivore("topG", 84, 70, HabitatEnum.LAND, LivingType.ALONE, 17, 14, 80);
        Animal galinski = new Carnivore("galinski", 84, 77, HabitatEnum.LAND, LivingType.ALONE, 10, 14, 80);
//        animalService.addCarnivore(lion, cheetah, tiger, hyena, kot, ko4e, galab, riba);
        animalService.addCarnivore(lub4o, topG, galinski);
//        carnivoreRepository.addCarnivore(tiger, hyena);

//        animalService.addAnimals(zebra, hare, gazelle, buffalo, krava, prasa, kon, koza,
//                lion, cheetah, tiger, hyena, kot, ko4e, galab, riba);
//        animalRepository.addAnimal(kon, koza, tiger, hyena);

//        biomeRepository.addBiomeAndAnimals(savanna, zebra, hare, gazelle, buffalo, krava, prasa, kon, koza,
//                lion, cheetah, tiger, hyena, kot, ko4e, galab);
        biomeRepository.addBiomeAndAnimals(savanna, miro, nici, irina, lub4o, topG, galinski);
    }
    
    public void simulateEcoSystem() throws InterruptedException {
        updateRepositories();
        
        List<Animal> carnivores = animalService.getCarnivores();
        List<Animal> herbivores = animalService.getHerbivores();
        List<Animal> newBornAnimals = animalService.getNewBornAnimals();
        String biome = biomeRepository.getBiome();
        
        System.out.println("Action happening in the " + biome);
        //TODO THINK OF HOW TO IMPLEMENT THE CODE TO NEVER STOP THE ECO SYSTEM, BECAUSE NOW IT STOPS ONCE THE HERBIVORES ARE DEAD
        while (isAnimalsDead(carnivores, herbivores)) {
            
            if (herbivores.size() == 0) {
                for (Animal carnivore : carnivores) {
                    Carnivore carnivore1 = (Carnivore) carnivore;
                    carnivore1.decreaseReproductionRate();
                    increasingCarnivoreHungerLevel(carnivore1);
                    
                    if (carnivore1.getHungerRate() >= 100) {
                        System.out.println(carnivore1 + " died out of hunger.");
                        animalService.removeCarnivore(carnivore1);
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
            double successRate = carnivore.getAttackSuccess(herbivore);
            
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
        return carnivores.size() != 0 && herbivores.size() != 0;
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
            decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(carnivores, carnivore1, herbivore1, foodForMainAttacker, foodForTheRestOfTheGroup);
        } else {
            foodForMainAttacker = herbivore.getWeight();
            carnivore1.decreaseHungerLevel(foodForMainAttacker);
        }
    }
    
    private void decreasingTheHungerLevelOfEachMemberOfTheAttackersGroup(List<Animal> carnivores, Carnivore carnivore, Herbivore herbivore, double foodForMainAttacker, double foodForTheRestOfTheGroup) {
        for (Animal groupMember : carnivores) {
            if (groupMember.equals(carnivore)) {
                List<Group> groups = animalService.getGroups();
                List<Animal> groupOfAnimals = animalService.findGroup(groups, carnivore);
                
                for (Animal animal : groupOfAnimals) {
                    if (animal.equals(carnivore)) {
                        //The main attacker receives two portions of food
                        carnivore.decreaseHungerLevel(foodForMainAttacker);
                    } else {
                        //Each groupMember that is not the attacker should receive a single portion of the food.
                        animal.decreaseHungerLevel(carnivore, foodForTheRestOfTheGroup);
                    }
                }
                System.out.println(carnivore.getAnimalType() + " killed " + herbivore.getAnimalType());
            }
        }
    }
    
    private void increaseHungerLevelOfAllCarnivoresIfNoHerbivoresLeftAlive(List<Animal> carnivores, List<Animal> herbivores) {
        if (herbivores.size() == 0) {
            for (Animal carnivore : carnivores) {
                increasingCarnivoreHungerLevel(carnivore);
                if (!carnivore.isAlive()) {
                    System.out.println(carnivore.getAnimalType() + " died of hunger.");
                    animalService.removeCarnivore(carnivore);
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
        carnivore.increaseHungerLevel(hungerLevel);
    }
    
    private boolean noMoreSpeciesLeftAlive(boolean carnivores) {
        if (carnivores) {
            return true;
        }
        return false;
    }
    
    private void checksIfTheMembersOfTheCarnivoreGroupReachedTheMaximumHungerRate() {
        List<Group> groups = groupService.getGroups();
        for (Group group : groups) {
            List<Animal> animals1 = group.getAnimals();
            
            for (Animal animal : animals1) {
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
                Carnivore animalCarnivore = (Carnivore) carnivore;
                if (animalCarnivore.getReproductionRate() == 0) {
                    Carnivore newBornCarnivore = animalCarnivore.reproduce();
                    animalService.addNewBorn(newBornCarnivore);
                    System.out.println("New carnivore " + newBornCarnivore.getAnimalType() + " is born.");
                }
            }
        }
        
        for (Animal herbivore : herbivores) {
            Herbivore animalHerbivore = (Herbivore) herbivore;
            if (animalHerbivore.getReproductionRate() == 0) {
                Herbivore newBornHerbivore = animalHerbivore.reproduce();
                animalService.addNewBorn(newBornHerbivore);
                System.out.println("New herbivore " + newBornHerbivore.getAnimalType() + " is born.");
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
        for (int i = 0; i < 3; i++) {
            double maxAge = carnivore.getMaxAge();
            double weight = new Random().nextDouble(0, carnivore.getWeight());
            double productionRate = carnivore.getOriginalReproductionRate();
            int hungerRate = new Random().nextInt(1, 100);
            int attackPoints = new Random().nextInt(0, carnivore.getOriginalAttackPoints());
            
            Carnivore animalInGroup = new Carnivore(carnivore.getAnimalType(), maxAge, weight, carnivore.getMainHabitat(), carnivore.getLivingType(), productionRate, hungerRate, attackPoints);
            
            animalService.addCarnivore(animalInGroup);
            groupOfCarnivores.addAnimal(animalInGroup);
        }
        animalService.addGroupOfCarnivores(groupOfCarnivores);
    }
    
    private void createGroupOfHerbivores(Herbivore herbivore) {
        Group groupOfHerbivores = new Group();
        
        groupOfHerbivores.addAnimal(herbivore);
        for (int i = 0; i < 3; i++) {
            double maxAge = herbivore.getOriginalMaxAge();
            double weight = new Random().nextDouble(0, herbivore.getOriginalWeight());
            double productionRate = herbivore.getOriginalReproductionRate();
            int escapePoints = herbivore.getOriginalEscapePoints();
            
            Herbivore animalInGroup = new Herbivore(herbivore.getAnimalType(), maxAge, weight, herbivore.getMainHabitat(), herbivore.getLivingType(), productionRate, escapePoints);
            
            animalService.addHerbivore(animalInGroup);
            groupOfHerbivores.addAnimal(animalInGroup);
        }
        animalService.addGroupOfHerbivores(groupOfHerbivores);
    }
    
}
