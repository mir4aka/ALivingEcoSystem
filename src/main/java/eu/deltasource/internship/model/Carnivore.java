package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

public class Carnivore extends Animal {
    private int attackPoints;
    private int hungerRate;
    private final int originalAttackPoints;
    private final int originalHungerRate;
    
    public Carnivore(String animalType, double maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, int groupAmount, int reproductionRate, int hungerRate, int attackPoints) {
        super(animalType, maxAge, weight, mainHabitat, livingType, groupAmount, reproductionRate);
        this.hungerRate = hungerRate;
        this.attackPoints = attackPoints;
        this.originalAttackPoints = attackPoints;
        this.originalHungerRate = hungerRate;
    }
    
    public double getScaledAttackPoints() {
        return scalePoints(attackPoints);
    }
    
    public int getHungerRate() {
        return hungerRate;
    }
    
    public void setHungerRate(int hungerRate) {
        this.hungerRate = hungerRate;
    }
    
    public int getOriginalAttackPoints() {
        return originalAttackPoints;
    }
    
    @Override
    public String toString() {
        return getAnimalType() + " age " + getAge() + "\n";
    }
    
    public int getAttackPoints() {
        return attackPoints;
    }
}
