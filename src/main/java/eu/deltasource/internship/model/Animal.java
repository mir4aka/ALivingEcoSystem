package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal {
    private double age;
    private double maxAge;
    private int reproductionLevel;
    private int reproductionRate;
    private int groupAmount;
    private double weight;
    private String specie;
    private HabitatEnum habitat;
    private SocialStatus socialStatus;
    
    public Animal(String specie, double maxAge, double weight, HabitatEnum habitat, SocialStatus socialStatus, int groupAmount, int reproductionRate) {
        setSpecie(specie);
        setMaxAge(maxAge);
        setWeight(weight);
        setHabitat(habitat);
        setSocialStatus(socialStatus);
        setGroupAmount(groupAmount);
        setReproductionLevel(100);
        setReproductionRate(reproductionRate);
        setAge(0);
    }
    
    public Animal() {
    }
    
    public String getSpecie() {
        return specie;
    }
    
    public void setSpecie(String specie) {
        this.specie = specie;
    }
    
    public double getAge() {
        return age;
    }
    
    public void setAge(double age) {
        this.age = age;
    }
    
    public double getMaxAge() {
        return maxAge;
    }
    
    public void setMaxAge(double maxAge) {
        this.maxAge = maxAge;
    }
    
    public void setGroupAmount(int groupAmount) {
        this.groupAmount = groupAmount;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public void setHabitat(HabitatEnum habitat) {
        this.habitat = habitat;
    }
    
    public void setSocialStatus(SocialStatus socialStatus) {
        this.socialStatus = socialStatus;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public HabitatEnum getHabitat() {
        return habitat;
    }
    
    public int getReproductionLevel() {
        return reproductionLevel;
    }
    
    public void setReproductionLevel(int reproductionLevel) {
        this.reproductionLevel = reproductionLevel;
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
    
    public abstract double getPoints();
    @Override
    public String toString() {
        return "Type of animal = " + getClass().getSimpleName() + "\n" +
                "Animal = " + specie + "\n" +
                "Age = " + age + "\n" +
                "----------------\n";
    }
}
