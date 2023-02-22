package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;

public class Carnivore extends Animal {
    private int attackPoints;
    private int hungerRate;
    
    public Carnivore(String specie, int maxAge, double weight, HabitatEnum habitat, SocialStatus livingType, int groupAmount, int reproductionRate, int hungerRate, int attackPoints) {
        super(specie, maxAge, weight, habitat, livingType, groupAmount, reproductionRate);
        this.hungerRate = hungerRate;
        this.attackPoints = attackPoints;
    }
    
    public Carnivore() {
    }
    
    @Override
    public int getPoints() {
        return attackPoints;
    }
    
    public int getHungerRate() {
        return hungerRate;
    }
    
    public void setHungerRate(int hungerRate) {
        this.hungerRate = hungerRate;
    }
    
    @Override
    public String toString() {
        return getSpecie() + " age " + getAge() + "\n";
    }
    
}
