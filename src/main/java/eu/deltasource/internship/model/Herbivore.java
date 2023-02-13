package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

import java.util.Random;

public class Herbivore extends Animal {
    private int escapePoints;
    private final int originalEscapePoints;
    
    public Herbivore(String animalType, double maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, double reproductionRate, int escapePoints) {
        super(animalType, maxAge, weight, mainHabitat, livingType, reproductionRate);
        this.escapePoints = escapePoints;
        this.originalEscapePoints = escapePoints;
    }
    
    public Herbivore reproduce() {
        Herbivore herbivore = new Herbivore(getAnimalType(), getMaxAge(), getWeight(), getMainHabitat(), getLivingType(), getReproductionRate(), getEscapePoints());
        herbivore.setAge(0);
        return herbivore;
    }
    
    
    public int getEscapePoints() {
        return escapePoints;
    }
    
    public void setEscapePoints(int escapePoints) {
        this.escapePoints = escapePoints;
    }
    
    public void decreaseEscapePoints(double points) {
        escapePoints -= points;
        if (escapePoints < 0) {
            escapePoints = 0;
        }
    }
    
    public void increaseEscapePoints(double escapePoints) {
        this.escapePoints += escapePoints;
    }
    
    double getScaledEscapePoints() {
        return scalePoints(escapePoints);
    }
    
    @Override
    public String toString() {
        return getAnimalType() + getAge() + "\n";
    }
    
    public int getOriginalEscapePoints() {
        return this.originalEscapePoints;
    }
    
    public void increaseReproductionRate(double originalReproductionRate) {
        super.increaseReproductionRate(originalReproductionRate);
    }
}
