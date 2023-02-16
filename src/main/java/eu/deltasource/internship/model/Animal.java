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
    private int reproductionRate;
    private LivingType livingType;
    private int groupAmount;
    private int originalReproductionRate;
    private final double originalMaxAge;
    private final double originalWeight;
    private List<Biome> biomes;
    
    public Animal(String animalType, double maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, int groupAmount, int reproductionRate) {
        this.animalType = animalType;
        this.maxAge = maxAge;
        this.weight = weight;
        this.mainHabitat = mainHabitat;
        this.livingType = livingType;
        this.groupAmount = groupAmount;
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
    
    public void decreaseReproductionRate() {
        this.reproductionRate--;
        if(reproductionRate <= 0) {
            reproductionRate = 0;
        }
    }
    
    public String getAnimalType() {
        return animalType;
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
    
    public int getGroupAmount() {
        return groupAmount;
    }
    
    double scalePoints(double points) {
        if(age == 0) {
            return points;
        }
        return points * (1 - (age / maxAge));
    }
    
    public int getOriginalReproductionRate() {
        return originalReproductionRate;
    }
    
    protected void increaseReproductionRate(double originalReproductionRate) {
        reproductionRate += originalReproductionRate;
    }
    
    @Override
    public String toString() {
        return "Type of animal = " + getClass().getSimpleName() + "\n" +
                "Animal = " + animalType + "\n" +
                "Age = " + age + "\n" +
                "----------------\n";
    }
}
