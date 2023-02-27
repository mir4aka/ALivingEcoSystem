package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.Habitat;
import eu.deltasource.internship.enums.SocialStatus;

public abstract class Animal {
    private int age;
    private int maxAge;
    private int reproductionLevel;
    private int reproductionRate;
    private int groupAmount;
    private int weight;
    private String specie;
    private Habitat habitat;
    private SocialStatus socialStatus;
    
    public Animal(String specie, int maxAge, int weight, Habitat habitat, SocialStatus socialStatus, int groupAmount, int reproductionRate) {
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
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getMaxAge() {
        return maxAge;
    }
    
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
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
    
    public int getGroupAmount() {
        return groupAmount;
    }
    
    public void setGroupAmount(int groupAmount) {
        this.groupAmount = groupAmount;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public String getSpecie() {
        return specie;
    }
    
    public void setSpecie(String specie) {
        this.specie = specie;
    }
    
    public Habitat getHabitat() {
        return habitat;
    }
    
    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }
    
    public SocialStatus getSocialStatus() {
        return socialStatus;
    }
    
    public void setSocialStatus(SocialStatus socialStatus) {
        this.socialStatus = socialStatus;
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
