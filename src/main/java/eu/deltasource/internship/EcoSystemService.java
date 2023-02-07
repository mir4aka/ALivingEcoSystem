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

    private List<Carnivore> carnivores = carnivoreRepository.getCarnivores();
    private List<Herbivore> herbivores = herbivoreRepository.getHerbivores();
    private List<Animal> animals = animalRepository.getAnimals();

    private void updateRepositories() {
        Biome savanna = new Biome(HabitatEnum.LAND, BiomeEnum.SAVANNA);

        EcoSystem ecoSystem = new EcoSystem(savanna);

        Herbivore zebra = new Herbivore("zebra",50, 300, HabitatEnum.LAND, LivingType.GROUP, 10, 80);
        Herbivore hare = new Herbivore(24, 5, HabitatEnum.LAND, LivingType.ALONE, 3, 100);
        Herbivore gazelle = new Herbivore(25, 25, HabitatEnum.LAND, LivingType.GROUP, 5, 80);
        Herbivore buffalo = new Herbivore(24, 5, HabitatEnum.LAND, LivingType.GROUP, 9, 40);
        Herbivore krava = new Herbivore(11, 50, HabitatEnum.LAND, LivingType.GROUP, 11, 10);
        Herbivore prasa = new Herbivore(14, 80, HabitatEnum.LAND, LivingType.GROUP, 5, 20);
        Herbivore kon = new Herbivore(4, 100, HabitatEnum.LAND, LivingType.GROUP, 6, 90);
        Herbivore koza = new Herbivore(18, 45, HabitatEnum.LAND, LivingType.GROUP, 4, 60);
        herbivoreRepository.addHerbivore(zebra, hare, gazelle, buffalo, krava, prasa, kon, koza);

        Carnivore lion = new Carnivore(30, 150, HabitatEnum.LAND, LivingType.GROUP, 6, 20, 80);
        Carnivore cheetah = new Carnivore(30, 60, HabitatEnum.LAND, LivingType.ALONE, 5, 15, 110);
        Carnivore tiger = new Carnivore(20, 200, HabitatEnum.LAND, LivingType.ALONE, 6, 18, 75);
        Carnivore hyena = new Carnivore(24, 50, HabitatEnum.LAND, LivingType.GROUP, 5, 14, 80);
        carnivoreRepository.addCarnivore(lion, cheetah, tiger, hyena);

        animalRepository.addAnimal(zebra, hare, gazelle, buffalo, lion, cheetah, tiger, hyena);

        biomeRepository.addBiome(savanna);
    }

    public void simulateIteration() {
        updateRepositories();

        for (Carnivore carnivore : carnivores) {
            if (carnivore.getHungerRate() >= 100) {
                carnivores.remove(carnivore);
                break;
            }
            while (carnivore.isAlive()) {
                // increase the hunger level
                carnivore.increaseHungerLevel(carnivore.getHungerChange());

                // check if the carnivore is still alive
                if (carnivore.isAlive()) {
                    if (herbivores.size() == 0) {
                        break;
                    }
                    // find a target herbivore to attack
                    Herbivore target = herbivores.get(new Random().nextInt(herbivores.size()));

                    // calculate the success rate of the attack
                    double successRate = carnivore.getAttackSuccess(target);

                    int random = new Random().nextInt(1, 100);
                    // if the attack is successful
                    if (successRate > random) {
                        double food = target.getWeight() / carnivores.size();
                        for (Carnivore groupMember : carnivores) {
                            groupMember.decreaseHungerLevel(food);
                        }
                        // the carnivore that performed the attack receives two portions
                        carnivore.decreaseHungerLevel(food * 2);
//                    herbivores.remove(target);
                        herbivores.remove(target);
                    }
                }
            }

            // increase the age of all animals
            for (Animal animal : animals) {
                animal.increaseAge();
            }

            // check if any animal has reached its maximum age
            for (int i = 0; i < animals.size() - 1; i++) {
                Animal currentAnimal = animals.get(i);
                if (currentAnimal.isAlive()) {
                    animals.remove(currentAnimal);
                }
            }

            // reproduce new animals
            for (Animal animal : animals) {
                if (new Random().nextDouble(animal.getReproductionRate()) == 0) {
                    animals.add(animal.reproduce());
                }
            }
        }
    }

    public String print() {
        StringBuilder sb = new StringBuilder();

        if(animals.isEmpty()) {
            sb.append("No more animals alive left.");
            return sb.toString();
        }

        sb.append("Animals left to live: \n");

        for (Animal animal : animals) {
            sb.append(animal);
        }

        return sb.toString();
    }

}
