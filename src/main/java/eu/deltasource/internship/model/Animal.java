package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

import java.util.Random;

public abstract class Animal {
    private int age;
    private int maxAge;
    private double weight;
    private HabitatEnum mainHabitat;
    private double reproductionRate;
    private LivingType livingType;

    public Animal(int maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, double reproductionRate) {
        this.maxAge = maxAge;
        this.weight = weight;
        this.mainHabitat = mainHabitat;
        this.livingType = livingType;
        this.reproductionRate = reproductionRate;
        this.age = 1;
    }

    public Carnivore reproduce() {
        return new Carnivore(getMaxAge(), getWeight(), getMainHabitat(), getLivingType(), getReproductionRate(), 20, 0);
    }

    public void increaseAge() {
        this.age++;
    }

    public boolean isAlive() {
        return this.age <= this.maxAge;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public double getWeight() {
        return weight;
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
}
