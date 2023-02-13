package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Animal {
    private String animalType;
    private double age;
    private double maxAge;
    private double weight;
    private HabitatEnum mainHabitat;
    private double reproductionRate;
    private LivingType livingType;
    private double originalReproductionRate;
    private final double originalMaxAge;
    private final double originalWeight;
    private List<Biome> biomes;
    
    public Animal(String animalType, double maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, double reproductionRate) {
        this.animalType = animalType;
        this.maxAge = maxAge;
        this.weight = weight;
        this.mainHabitat = mainHabitat;
        this.livingType = livingType;
        this.reproductionRate = reproductionRate;
        this.age = new Random().nextInt(1, 5);
        this.originalReproductionRate = reproductionRate;
        this.originalMaxAge = maxAge;
        this.originalWeight = weight;
        this.biomes = new ArrayList<>();
    }

    public void increaseAge() {
        this.age++;
        if(getAge() >= getMaxAge()) {
            this.age = getMaxAge();
        }
    }

    public boolean isAlive() {
        return this.age < this.maxAge;
    }

    public void decreaseReproductionRate() {
        this.reproductionRate--;
    }
    
    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public double getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getMaxAge() {
        return maxAge;
    }
    
    public double getOriginalMaxAge() {
        return originalMaxAge;
    }

    public double getWeight() {
        return weight;
    }
    
    public double getOriginalWeight() {
        return originalWeight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public HabitatEnum getMainHabitat() {
        return mainHabitat;
    }

    public double getReproductionRate() {
        return reproductionRate;
    }

    public LivingType getLivingType() {
        return livingType;
    }

    
    double scalePoints(double points) {
        if(age == 0) {
            return points;
        }
        return points * (1 - (age / maxAge));
    }
    
    public double getOriginalReproductionRate() {
        return originalReproductionRate;
    }
    
    @Override
    public String toString() {
        return "Type of animal = " + getClass().getSimpleName() + "\n" +
                "Animal = " + animalType + "\n" +
                "Age = " + age + "\n" +
                "----------------\n";
    }
    
    public void decreaseHungerLevel(Carnivore carnivore, double hungerLevel) {
        carnivore.decreaseHungerLevel(hungerLevel);
    }
    
    protected void increaseReproductionRate(double originalReproductionRate) {
        reproductionRate += originalReproductionRate;
    }
    
    public double getHungerRate(Carnivore carnivore) {
        return carnivore.getHungerRate();
    }
    
    public List<Biome> getBiomes() {
        return Collections.unmodifiableList(biomes);
    }
    
}
