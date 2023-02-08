package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

import java.util.Random;

public abstract class Animal {
    private String animalType;
    private int age;
    private int maxAge;
    private double weight;
    private HabitatEnum mainHabitat;
    private double reproductionRate;
    private LivingType livingType;

    public Animal(String animalType, int maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, double reproductionRate) {
        this.animalType = animalType;
        this.maxAge = maxAge;
        this.weight = weight;
        this.mainHabitat = mainHabitat;
        this.livingType = livingType;
        this.reproductionRate = reproductionRate;
        this.age = new Random().nextInt(1, 5);
    }

    public void reproduce() {
        new Carnivore(getAnimalType(), getMaxAge(), getWeight(), getMainHabitat(), getLivingType(), getReproductionRate(), 20, 0);
    }

    public void increaseAge() {
        this.age++;
        if(getAge() > getMaxAge()) {
            this.age = getMaxAge();
        }
    }

    public boolean isAlive() {
        return this.age < this.maxAge;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
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

    @Override
    public String toString() {
        return "Type of animal = " + getClass().getSimpleName() + "\n" +
                "Animal = " + animalType + "\n" +
                "Age = " + age + "\n"+
                "----------------\n";
    }
    
    double scalePoints(double points) {
        return points * (1 - (double)age / maxAge);
    }
}
