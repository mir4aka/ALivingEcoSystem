package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

import java.util.Random;

public class Carnivore extends Animal {
    private int attackPoints;
    private int hungerRate;
    private int hungerChange;
    
    public Carnivore(String animalType, int maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, double reproductionRate, int hungerRate, int attackPoints) {
        super(animalType, maxAge, weight, mainHabitat, livingType, reproductionRate);
        this.hungerRate = hungerRate;
        this.attackPoints = attackPoints;
        this.hungerChange = new Random().nextInt(1, 20);
    }
    
    public Carnivore reproduce() {
        Carnivore carnivore = new Carnivore(getAnimalType(), getMaxAge(), getWeight(), getMainHabitat(), getLivingType(), getReproductionRate(), hungerRate, 0);
        carnivore.setAge(0);
        return carnivore;
    }
    
    public double getAttackSuccess(Herbivore herbivore) {
        double attackPoints = getScaledAttackPoints();
        double escapePoints = herbivore.getScaledEscapePoints();
        double successRate = attackPoints / (attackPoints + escapePoints) * 100;
        
        if (getLivingType().equals(LivingType.ALONE)) {
            successRate *= 0.5;
        }
        
        if (herbivore.getWeight() > getWeight() && getLivingType().equals(LivingType.ALONE)) {
            successRate = herbivore.getWeight() / getWeight();
        }
        
        return successRate;
    }
    
    @Override
    public boolean isAlive() {
        return getAge() > getMaxAge() || hungerRate >= 100;
    }
    
    double getScaledAttackPoints() {
        return scalePoints(attackPoints);
    }
    
    public void increaseHungerLevel(double hunger) {
        hungerRate += hunger;
        if (hungerRate >= 100) {
            hungerRate = 100;
        }
    }
    
    public void decreaseHungerLevel(double hunger) {
        hungerRate -= hunger;
        if (hungerRate <= 0) {
            hungerRate = 1;
        }
    }
    
    public double getHungerChange() {
        return hungerChange;
    }
    
    public int getAttackPoints() {
        return attackPoints;
    }
    
    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }
    
    public int getHungerRate() {
        return hungerRate;
    }
    
    public void setHungerRate(int hungerRate) {
        this.hungerRate = hungerRate;
    }
    
    @Override
    public String toString() {
        return getAnimalType() + "\n" +
                "hungerRate = " + hungerRate + "\n";
    }
}
