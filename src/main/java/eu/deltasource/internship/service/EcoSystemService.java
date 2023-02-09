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
    private Group group = new Group();
    
    private void updateRepositories() {
        Biome savanna = new Biome(HabitatEnum.LAND, BiomeEnum.SAVANNA);
        
        EcoSystem ecoSystem = new EcoSystem(savanna);

//        Herbivore zebra = new Herbivore("zebra", 50, 300, HabitatEnum.LAND, LivingType.GROUP, 10, 80);
//        Herbivore hare = new Herbivore("hare", 24, 5, HabitatEnum.LAND, LivingType.ALONE, 3, 100);
//        Herbivore gazelle = new Herbivore("gazelle", 25, 25, HabitatEnum.LAND, LivingType.GROUP, 5, 80);
//        Herbivore buffalo = new Herbivore("buffalo", 24, 5, HabitatEnum.LAND, LivingType.GROUP, 9, 40);
//        Herbivore krava = new Herbivore("krava", 11, 50, HabitatEnum.LAND, LivingType.GROUP, 11, 10);
        Herbivore prasa = new Herbivore("prasa", 14, 80, HabitatEnum.LAND, LivingType.GROUP, 5, 20);
        Herbivore kon = new Herbivore("kon", 4, 100, HabitatEnum.LAND, LivingType.GROUP, 6, 90);
        Herbivore koza = new Herbivore("koza", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        animalService.addHerbivore(prasa, kon, koza);
//        herbivoreRepository.addHerbivore(kon, koza);
        
        Carnivore lion = new Carnivore("lion", 30, 150, HabitatEnum.LAND, LivingType.GROUP, 6, 20, 80);
        Carnivore cheetah = new Carnivore("cheetah", 30, 60, HabitatEnum.LAND, LivingType.ALONE, 5, 15, 110);
        Carnivore tiger = new Carnivore("tiger", 20, 200, HabitatEnum.LAND, LivingType.ALONE, 6, 18, 75);
        Carnivore hyena = new Carnivore("hyena", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 5, 14, 80);
        animalService.addCarnivore(lion, cheetah, tiger, hyena);
//        carnivoreRepository.addCarnivore(tiger, hyena);
        
        animalService.addAnimals(prasa, kon, koza, lion, cheetah, tiger, hyena);
//        animalRepository.addAnimal(kon, koza, tiger, hyena);
        
        biomeRepository.addBiomeAndAnimals(savanna, prasa, kon, koza, lion, cheetah, tiger, hyena);
    }
    
    public void simulateEcoSystem() {
        List<Carnivore> carnivores = animalService.getCarnivores();
        List<Herbivore> herbivores = animalService.getHerbivores();
        List<Animal> animals = animalService.getAnimals();
    
        List<Animal> newBornAnimals = animalService.getNewBornAnimals();
    
        for (Animal newBornAnimal : newBornAnimals) {
            animalService.addAnimals(newBornAnimal);
        }
    
        updateRepositories();
        
        String biome = biomeRepository.getBiome();
        
        System.out.println("Action happening in the " + biome);
        while (animals.size() != 0) {
            if (carnivores.size() == 0) {
                break;
            }
            
            if (herbivores.size() == 0) {
                for (Carnivore carnivore : carnivores) {
                    int hungerLevel = new Random().nextInt(1, 10);
                    carnivore.increaseHungerLevel(hungerLevel);
                    if (carnivore.isAlive()) {
                        System.out.println(carnivore.getAnimalType() + " died.");
                        animalService.removeCarnivore(carnivore);
                        animalService.removeAnimal(carnivore);
                    }
                }
            }
            
            Carnivore carnivore = carnivores.get(new Random().nextInt(0, carnivores.size()));
            Herbivore herbivore = herbivores.get(new Random().nextInt(0, herbivores.size()));
            
            //calculates the success rate of the attack
            double successRate = carnivore.getAttackSuccess(herbivore);
            
            int random = new Random().nextInt(1, 100);
            
            //if the attack is successful
            if (successRate > random) {
                System.out.println(carnivore.getAnimalType() + " attacked " + herbivore.getAnimalType());
                
                double foodInKg = herbivore.getWeight();
                if (carnivore.getLivingType().equals(LivingType.GROUP)) {
                    createGroupOfCarnivores(carnivore);
                    int attackersAmount = group.getAnimals().size();
                    foodInKg /= attackersAmount + 1;
                }
                
                if (herbivore.getLivingType().equals(LivingType.GROUP)) {
                    createGroupOfHerbivores(herbivore);
                }
                
                double foodForMainAttacker = foodInKg * 2;
                double foodForTheRestOfTheGroup = foodInKg;
                
                for (Carnivore groupMember : carnivores) {
                    if (groupMember.equals(carnivore)) {
                        List<Carnivore> animalsGroup = groupService.findCarnivoreInGroup(carnivore);
                        if (animalsGroup == null) {
                            continue;
                        }
                        for (Carnivore animal : animalsGroup) {
                            if (animal.equals(carnivore)) {
                                //The main attacker receives two portions of food
                                carnivore.decreaseHungerLevel(foodForMainAttacker);
                            } else {
                                //Each groupMember that is not the attacker should receive a single portion of the food.
                                animal.decreaseHungerLevel(foodForTheRestOfTheGroup);
                            }
                        }
                    }
                    
                }
                animalService.removeHerbivore(herbivore);
                animalService.removeAnimal(herbivore);
            } else {
                double escapePoints = carnivore.getAttackPoints() - herbivore.getEscapePoints();
                herbivore.increaseEscapePoints(escapePoints);
                
                int hungerLevel = new Random().nextInt(1, 10);
                carnivore.increaseHungerLevel(hungerLevel);
            }
            
            if (carnivore.getHungerRate() >= 100) {
                System.out.println(carnivore.getAnimalType() + " died out of hunger.");
                animalService.removeAnimal(carnivore);
                animalService.removeCarnivore(carnivore);
                break;
            }
            
            if (carnivores.size() == 0 && herbivores.size() == 0) {
                break;
            }
            //increase the age of all animals
            for (Animal animal : animals) {
                if (animal.getAge() > animal.getMaxAge()) {
                    System.out.println("Animal " + animal.getAnimalType() + " died naturally." + animal.getAge());
                    animalService.removeAnimal(animal);
                    break;
                }
                animal.increaseAge();
                animal.decreaseReproductionRate();
            }
            
            //reproduce new animals
            for (Carnivore animal : carnivores) {
                if (animal.getReproductionRate() == 0) {
                    Carnivore newCarnivore = animal.reproduce();
                    animalService.addNewBorn(newCarnivore);
                    animalService.addAnimals(newCarnivore);
                }
            }
            
            for (Herbivore animal : herbivores) {
                if (animal.getReproductionRate() == 0) {
                    Herbivore newHerbivore = animal.reproduce();
                    animalService.addNewBorn(newHerbivore);
                    animalService.addAnimals(newHerbivore);
                }
            }
        }
        
    }
    
    public String print() {
        StringBuilder sb = new StringBuilder();
        
        List<Animal> animals = animalService.getAnimals();
        
        if (animals.isEmpty()) {
            sb.append("No more animals alive left.");
            return sb.toString();
        }
        
        sb.append("Animals left to live: \n");
        
        for (Animal animal : animals) {
            sb.append(animal);
        }
        
        return sb.toString();
    }
    
    private void createGroupOfCarnivores(Carnivore carnivore) {
        List<Carnivore> carnivores = animalService.getCarnivores();
        group.addAnimal(carnivore);
        for (int i = 0; i < 3; i++) {
            int maxAge = new Random().nextInt(0, carnivore.getMaxAge());
            double weight = new Random().nextDouble(0, carnivore.getWeight());
            double productionRate = carnivore.getOriginalReproductionRate();
            int hungerRate = new Random().nextInt(0, carnivore.getHungerRate());
            int attackPoints = new Random().nextInt(0, carnivore.getAttackPoints());
            
            Carnivore animalInGroup = new Carnivore(carnivore.getAnimalType(), maxAge, weight, carnivore.getMainHabitat(), carnivore.getLivingType(), productionRate, hungerRate, attackPoints);
            animalService.addCarnivore(animalInGroup);
            group.addAnimal(animalInGroup);
        }
        groupService.addToGroup(group);
    }
    
    private void createGroupOfHerbivores(Herbivore herbivore) {
        List<Herbivore> herbivores = animalService.getHerbivores();
        group.addAnimal(herbivore);
        for (int i = 0; i < 3; i++) {
            int maxAge = new Random().nextInt(0, herbivore.getMaxAge());
            double weight = new Random().nextDouble(0, herbivore.getWeight());
            double productionRate = herbivore.getReproductionRate();
            double escapePoints = herbivore.getOriginalEscapePoints();
            
            Herbivore animalInGroup = new Herbivore(herbivore.getAnimalType(), maxAge, weight, herbivore.getMainHabitat(), herbivore.getLivingType(), productionRate, escapePoints);
            animalService.addHerbivore(animalInGroup);
            group.addAnimal(animalInGroup);
        }
        groupService.addToGroup(group);
    }
    
}
