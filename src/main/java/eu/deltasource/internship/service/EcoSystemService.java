package eu.deltasource.internship.service;

import eu.deltasource.internship.BiomeRepository.BiomeRepository;
import eu.deltasource.internship.BiomeRepository.BiomeRepositoryImpl;
import eu.deltasource.internship.GroupRepository.GroupRepository;
import eu.deltasource.internship.GroupRepository.GroupRepositoryImpl;
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
        
        EcoSystem ecoSystem = new EcoSystem(savanna);
        
        Herbivore zebra = new Herbivore("zebra", 50, 300, HabitatEnum.LAND, LivingType.GROUP, 10, 80);
        Herbivore hare = new Herbivore("hare", 24, 5, HabitatEnum.LAND, LivingType.ALONE, 3, 100);
        Herbivore gazelle = new Herbivore("gazelle", 25, 25, HabitatEnum.LAND, LivingType.GROUP, 5, 80);
        Herbivore buffalo = new Herbivore("buffalo", 24, 5, HabitatEnum.LAND, LivingType.GROUP, 9, 40);
        Herbivore krava = new Herbivore("krava", 11, 50, HabitatEnum.LAND, LivingType.GROUP, 11, 10);
        Herbivore prasa = new Herbivore("prasa", 14, 80, HabitatEnum.LAND, LivingType.GROUP, 5, 20);
        Herbivore kon = new Herbivore("kon", 4, 100, HabitatEnum.LAND, LivingType.GROUP, 6, 90);
        Herbivore koza = new Herbivore("koza", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        Herbivore koza12 = new Herbivore("koza5334", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        Herbivore koza123 = new Herbivore("koza756756", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        Herbivore koza1234556756765 = new Herbivore("koza75675", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        Herbivore koza12534 = new Herbivore("koza545654", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        Herbivore koza12654 = new Herbivore("koza456", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        Herbivore koza12756 = new Herbivore("koza3454324", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        Herbivore koza127867 = new Herbivore("koza5467657", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        Herbivore koza1296854 = new Herbivore("koza6754", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        Herbivore koza12435654 = new Herbivore("koza7574543", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        animalService.addHerbivore(zebra, hare, gazelle, buffalo, krava, prasa, kon, koza, koza12, koza123, koza1234556756765, koza12534, koza12654, koza12756, koza127867, koza1296854, koza12435654);
        
        Carnivore lion = new Carnivore("lion", 30, 150, HabitatEnum.LAND, LivingType.GROUP, 6, 20, 80);
        Carnivore cheetah = new Carnivore("cheetah", 30, 60, HabitatEnum.LAND, LivingType.ALONE, 5, 15, 110);
        Carnivore tiger = new Carnivore("tiger", 20, 200, HabitatEnum.LAND, LivingType.ALONE, 6, 18, 75);
        Carnivore hyena = new Carnivore("hyena", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 5, 14, 80);
        Carnivore kot = new Carnivore("hyena546768", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 5, 14, 80);
        Carnivore ko4e = new Carnivore("hyena867543", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 5, 14, 80);
        Carnivore galab = new Carnivore("hyena75645", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 5, 14, 80);
        Carnivore riba = new Carnivore("hyena867546", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 5, 14, 80);
        animalService.addCarnivore(lion, cheetah, tiger, hyena, kot, ko4e, galab, riba);
//        carnivoreRepository.addCarnivore(tiger, hyena);
        
        animalService.addAnimals(zebra, hare, gazelle, buffalo, krava, prasa, kon, koza, koza12, koza123, koza1234556756765, koza12534, koza12654, koza12756, koza127867, koza1296854, koza12435654,
                lion, cheetah, tiger, hyena, kot, ko4e, galab, riba);
//        animalRepository.addAnimal(kon, koza, tiger, hyena);
        
        biomeRepository.addBiomeAndAnimals(savanna, zebra, hare, gazelle, buffalo, krava, prasa, kon, koza, koza12, koza123, koza1234556756765, koza12534, koza12654, koza12756, koza127867, koza1296854, koza12435654,
                lion, cheetah, tiger, hyena, kot, ko4e, galab);
    }
    
    //TODO RUN A PROPER DEBUG SESSION
    public void simulateEcoSystem() throws InterruptedException {
        updateRepositories();
        
        List<Carnivore> carnivores = animalService.getCarnivores();
        List<Herbivore> herbivores = animalService.getHerbivores();
        List<Animal> animals = animalService.getAnimals();
        
        List<Animal> newBornAnimals = animalService.getNewBornAnimals();
        
        for (Animal newBornAnimal : newBornAnimals) {
            animalService.addAnimals(newBornAnimal);
        }
        
        animalService.clearNewBornAnimalsList();
        
        String biome = biomeRepository.getBiome();
        
        System.out.println("Action happening in the " + biome);
        while (animals.size() != 0) {
            if (carnivores.size() == 0) {
                break;
            }
            
            if (herbivores.size() == 0) {
                for (Carnivore carnivore : carnivores) {
                    int hungerLevel = new Random().nextInt(1, 10);
                    //TODO CHECK PROPERLY HUNGER LEVEL
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
                    List<Group> groups = animalService.getGroups();
                    List<Animal> groupOfAnimals = animalService.findGroup(groups, carnivore);
                    
                    int attackersAmount = groupOfAnimals.size();
                    foodInKg /= attackersAmount + 1;
                }
                
                if (herbivore.getLivingType().equals(LivingType.GROUP)) {
                    createGroupOfHerbivores(herbivore);
                }
                
                double foodForMainAttacker = foodInKg * 2;
                double foodForTheRestOfTheGroup = foodInKg;
                
                for (Carnivore groupMember : carnivores) {
                    if (groupMember.equals(carnivore)) {
//                        List<Carnivore> carnivoresGroup = groupService.findCarnivoreInGroup(carnivore);
                        
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
                animalService.removeHerbivore(herbivore);
                animalService.removeAnimal(herbivore);
            } else {
                double escapePoints = Math.abs(carnivore.getAttackPoints() - herbivore.getEscapePoints());
                herbivore.increaseEscapePoints(escapePoints);
                System.out.println(herbivore.getAnimalType() + " escaped the attack from " + carnivore.getAnimalType());
                int hungerLevel = new Random().nextInt(1, 10);
                carnivore.increaseHungerLevel(hungerLevel);
            }
            
            List<Group> groups = groupService.getGroups();
            for (Group group : groups) {
                List<Animal> animals1 = group.getAnimals();
                for (Animal animal : animals1) {
                    if (animal instanceof Carnivore) {
                        if (((Carnivore) animal).getHungerRate() >= 100) {
                            System.out.println(carnivore.getAnimalType() + " died out of hunger.");
                            animalService.removeAnimal(carnivore);
                            animalService.removeCarnivore(carnivore);
                        }
                    }
                }
            }
            
            if (carnivores.size() == 0 && herbivores.size() == 0) {
                break;
            }
            Thread.sleep(1000);
        }
        
        //increase the age of all animals
        for (Animal animal : animals) {
            
            animal.increaseAge();
            animal.decreaseReproductionRate();
            if (animal.getAge() > animal.getMaxAge()) {
                System.out.println("Animal " + animal.getAnimalType() + " died naturally." + animal.getAge());
                animalService.removeAnimal(animal);
            }
        }
        
        //reproduce new animals
        for (Carnivore animal : carnivores) {
            if (animal.getReproductionRate() == 0) {
                Carnivore newCarnivore = animal.reproduce();
                System.out.println("New carnivore " + newCarnivore + " is born.");
                animalService.addNewBorn(newCarnivore);
                animalService.addAnimals(newCarnivore);
                animal.increaseReproductionRate(animal.getOriginalReproductionRate());
            }
        }
        
        for (Herbivore animal : herbivores) {
            if (animal.getReproductionRate() == 0) {
                Herbivore newHerbivore = animal.reproduce();
                System.out.println("New herbivore " + newHerbivore + " is born.");
                animalService.addNewBorn(newHerbivore);
                animalService.addAnimals(newHerbivore);
                animal.increaseReproductionRate(animal.getOriginalReproductionRate());
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
        Group groupOfCarnivores = new Group();
        
        groupOfCarnivores.addAnimal(carnivore);
        for (int i = 0; i < 3; i++) {
            int maxAge = carnivore.getMaxAge();
            double weight = new Random().nextDouble(0, carnivore.getWeight());
            double productionRate = carnivore.getOriginalReproductionRate();
            int hungerRate = new Random().nextInt(1, 100);
            int attackPoints = new Random().nextInt(0, carnivore.getOriginalAttackPoints());
            
            Carnivore animalInGroup = new Carnivore(carnivore.getAnimalType(), maxAge, weight, carnivore.getMainHabitat(), carnivore.getLivingType(), productionRate, hungerRate, attackPoints);
            
            animalService.addCarnivore(animalInGroup);
            animalService.addAnimals(animalInGroup);
            groupOfCarnivores.addAnimal(animalInGroup);
        }
        animalService.addGroupOfCarnivores(groupOfCarnivores);
    }
    
    private void createGroupOfHerbivores(Herbivore herbivore) {
        Group groupOfHerbivores = new Group();
        
        groupOfHerbivores.addAnimal(herbivore);
        for (int i = 0; i < 3; i++) {
            int maxAge = herbivore.getOriginalMaxAge();
            double weight = new Random().nextDouble(0, herbivore.getOriginalWeight());
            double productionRate = herbivore.getOriginalReproductionRate();
            double escapePoints = herbivore.getOriginalEscapePoints();
            
            Herbivore animalInGroup = new Herbivore(herbivore.getAnimalType(), maxAge, weight, herbivore.getMainHabitat(), herbivore.getLivingType(), productionRate, escapePoints);
            
            animalService.addHerbivore(animalInGroup);
            animalService.addAnimals(animalInGroup);
            groupOfHerbivores.addAnimal(animalInGroup);
        }
        animalService.addGroupOfHerbivores(groupOfHerbivores);
    }
    
}
