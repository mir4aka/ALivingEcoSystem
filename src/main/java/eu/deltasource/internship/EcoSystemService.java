package eu.deltasource.internship;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;
import eu.deltasource.internship.model.*;

import java.util.List;
import java.util.Random;

public class EcoSystemService {
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepository();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepository();
    private AnimalRepository animalRepository = new AnimalRepository();
    private BiomeRepository biomeRepository = new BiomeRepository();
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    
    private void updateRepositories() {
        Biome savanna = new Biome(HabitatEnum.LAND, BiomeEnum.SAVANNA);
        
        EcoSystem ecoSystem = new EcoSystem(savanna);

//        Herbivore zebra = new Herbivore("zebra", 50, 300, HabitatEnum.LAND, LivingType.GROUP, 10, 80);
//        Herbivore hare = new Herbivore("hare", 24, 5, HabitatEnum.LAND, LivingType.ALONE, 3, 100);
//        Herbivore gazelle = new Herbivore("gazelle", 25, 25, HabitatEnum.LAND, LivingType.GROUP, 5, 80);
//        Herbivore buffalo = new Herbivore("buffalo", 24, 5, HabitatEnum.LAND, LivingType.GROUP, 9, 40);
//        Herbivore krava = new Herbivore("krava", 11, 50, HabitatEnum.LAND, LivingType.GROUP, 11, 10);
//        Herbivore prasa = new Herbivore("prasa", 14, 80, HabitatEnum.LAND, LivingType.GROUP, 5, 20);
        Herbivore kon = new Herbivore("kon", 4, 100, HabitatEnum.LAND, LivingType.GROUP, 6, 90);
        Herbivore koza = new Herbivore("koza", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
//        herbivoreRepository.addHerbivore(zebra, hare, gazelle, buffalo, krava, prasa, kon, koza);
        herbivoreRepository.addHerbivore(kon, koza);

//        Carnivore lion = new Carnivore("lion", 30, 150, HabitatEnum.LAND, LivingType.GROUP, 6, 20, 80);
//        Carnivore cheetah = new Carnivore("cheetah", 30, 60, HabitatEnum.LAND, LivingType.ALONE, 5, 15, 110);
        Carnivore tiger = new Carnivore("tiger", 20, 200, HabitatEnum.LAND, LivingType.ALONE, 6, 18, 75);
        Carnivore hyena = new Carnivore("hyena", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 5, 14, 80);
//        carnivoreRepository.addCarnivore(lion, cheetah, tiger, hyena);
        carnivoreRepository.addCarnivore(tiger, hyena);

//        animalRepository.addAnimal(zebra, hare, gazelle, buffalo, krava, prasa, kon, koza, lion, cheetah, tiger, hyena);
        animalRepository.addAnimal(kon, koza, tiger, hyena);
        
        biomeRepository.addBiome(savanna);
    }
    
    public void simulateIteration() {
        List<Carnivore> carnivores = carnivoreRepository.getCarnivores();
        List<Herbivore> herbivores = herbivoreRepository.getHerbivores();
        List<Animal> animals = animalRepository.getAnimals();
        List<Group> carnivoresGroup = groupRepository.getCarnivoresGroup();
        
        updateRepositories();
        
        List<Biome> biomes = biomeRepository.getBiomes();
        System.out.println("Action happening in the " + biomes.get(0));
        while (carnivores.size() != 0 && herbivores.size() != 0) {
            Carnivore carnivore = carnivores.get(new Random().nextInt(carnivores.size()));
            Herbivore herbivore = herbivores.get(new Random().nextInt(herbivores.size()));
            herbivore.changeGroupingFactor();
            
            if (carnivore.getHungerRate() >= 100) {
                System.out.println(carnivore.getAnimalType() + " died out of hunger.");
                carnivores.remove(carnivore);
                animals.remove(carnivore);
                break;
            }
            
            if (carnivore.isAlive()) {
                animals.remove(carnivore);
                carnivores.remove(carnivore);
                break;
            }
            
            //calculates the success rate of the attack
            double successRate = carnivore.getAttackSuccess(herbivore);
            
            int random = new Random().nextInt(1, 100);
            
            double food = 0;
            if (carnivore.getLivingType().equals(LivingType.GROUP)) {
                createGroup(carnivore);
                int randomAttackers = carnivoresGroup.size();
                food = (herbivore.getWeight() / randomAttackers);
            }
            
            //if the attack is successful
            if (successRate > random) {
                System.out.println(carnivore.getAnimalType() + " attacked " + herbivore.getAnimalType());
                for (Carnivore groupMember : carnivores) {
                    
                    if (groupMember.equals(carnivore)) {
                        carnivore.decreaseHungerLevel(food * 2);
                    }
                    
                    for (Group groups : carnivoresGroup) {
                        List<Animal> animals1 = groups.getAnimals();
                        if (animals1.contains(carnivore)) {
                        
                        }
                    }
                }
                
                
                //the carnivore that performed the attack receives two portions
                herbivores.remove(herbivore);
                animals.remove(herbivore);
            } else {
                double escapePoints = 7;
                herbivore.increaseEscapePoints(escapePoints);
                carnivore.increaseHungerLevel(food);
            }

//            }
            
            //checks if any animal has reached its maximum age
            for (int i = 0; i < animals.size() - 1; i++) {
                Animal currentAnimal = animals.get(i);
                if (currentAnimal.isAlive()) {
                    animals.remove(currentAnimal);
                }
            }
            
            //increase the age of all animals
            for (Animal animal : animals) {
                if (animal.getAge() > animal.getMaxAge()) {
                    animals.remove(animal);
                    break;
                }
                animal.increaseAge();
            }
        }
        //reproduce new animals
        for (Animal animal : animals) {
            animal.reproduce();
        }
    }
    
    public String print() {
        StringBuilder sb = new StringBuilder();
        
        List<Animal> animals = animalRepository.getAnimals();
        
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
    
    private void createGroup(Carnivore carnivore) {
        Group group = new Group();
        List<Animal> animals = animalRepository.getAnimals();
        group.addAnimal(carnivore);
        for (int i = 0; i <= 4; i++) {
            int maxAge = new Random().nextInt(0, carnivore.getMaxAge());
            double weight = new Random().nextDouble(0, carnivore.getWeight());
            double productionRate = new Random().nextDouble(1, carnivore.getReproductionRate());
            int hungerRate = new Random().nextInt(0, carnivore.getHungerRate());
            int attackPoints = new Random().nextInt(0, carnivore.getAttackPoints());
            
            Carnivore animalInGroup = new Carnivore(carnivore.getAnimalType(), maxAge, weight, carnivore.getMainHabitat(), carnivore.getLivingType(), productionRate, hungerRate, attackPoints);
            animals.add(animalInGroup);
            group.addAnimal(animalInGroup);
        }
        groupRepository.addToGroup(group);
    }
    
}
