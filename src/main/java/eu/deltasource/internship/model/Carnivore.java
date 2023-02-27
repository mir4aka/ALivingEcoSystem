package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.Habitat;
import eu.deltasource.internship.enums.SocialStatus;

public class Carnivore extends Animal {
    private double attackPoints;
    private int hungerLevel;
    private int hungerRate;
    
    public Carnivore(String specie, int maxAge, int weight, Habitat habitat, SocialStatus socialStatus, int groupAmount, int reproductionRate, int hungerRate, double attackPoints) {
        super(specie, maxAge, weight, habitat, socialStatus, groupAmount, reproductionRate);
        setHungerRate(hungerRate);
        setAttackPoints(attackPoints);
        setHungerLevel(0);
    }
    
    public Carnivore() {
    }
    
    public void setAttackPoints(double attackPoints) {
        this.attackPoints = attackPoints;
    }
    
    public int getHungerRate() {
        return hungerRate;
    }
    
    public void setHungerRate(int hungerRate) {
        this.hungerRate = hungerRate;
    }
    
    public int getHungerLevel() {
        return hungerLevel;
    }
    
    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }
    
    /**
     * Gets the attack points of the carnivore.
     * @return
     */
    @Override
    public double getPoints() {
        return attackPoints;
    }
    
    @Override
    public String toString() {
        return getSpecie() + " age " + getAge() + "\n";
    }
    
}
