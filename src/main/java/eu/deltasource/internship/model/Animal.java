package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal {
    private int age;
    private int maxAge;
    private int reproductionRate;
    private int groupAmount;
    private double weight;
    private String specie;
    private HabitatEnum habitat;
    private SocialStatus socialStatus;
    
    public Animal(String specie, int maxAge, double weight, HabitatEnum mainHabitat, SocialStatus socialStatus, int groupAmount, int reproductionRate) {
        this.specie = specie;
        this.maxAge = maxAge;
        this.weight = weight;
        this.habitat = mainHabitat;
        this.socialStatus = socialStatus;
        this.groupAmount = groupAmount;
        this.reproductionRate = reproductionRate;
        this.age = 0;
    }
    
    public Animal() {
    }
    
    public String getSpecie() {
        return specie;
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
    
    public HabitatEnum getHabitat() {
        return habitat;
    }
    
    public int getReproductionRate() {
        return reproductionRate;
    }
    
    public void setReproductionRate(int reproductionRate) {
        this.reproductionRate = reproductionRate;
    }
    
    public SocialStatus getSocialStatus() {
        return socialStatus;
    }
    
    public int getGroupAmount() {
        return groupAmount;
    }
    
    public abstract int getPoints();
    @Override
    public String toString() {
        return "Type of animal = " + getClass().getSimpleName() + "\n" +
                "Animal = " + specie + "\n" +
                "Age = " + age + "\n" +
                "----------------\n";
    }
}
