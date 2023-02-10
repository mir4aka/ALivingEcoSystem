package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

import java.util.Random;

public class Herbivore extends Animal {
    private double escapePoints;
    private final double originalEscapePoints;
    
    public Herbivore(String animalType, int maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, double reproductionRate, double escapePoints) {
        super(animalType, maxAge, weight, mainHabitat, livingType, reproductionRate);
        this.escapePoints = escapePoints;
        this.originalEscapePoints = escapePoints;
    }
    
    public Herbivore reproduce() {
        Herbivore herbivore = new Herbivore(getAnimalType(), getMaxAge(), getWeight(), getMainHabitat(), getLivingType(), getReproductionRate(), getEscapePoints());
        herbivore.setAge(0);
        return herbivore;
    }
    
    
    public double getEscapePoints() {
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
        return getAnimalType() + "\n" +
                "escapePoints = " + escapePoints + "\n";
    }
    
    public double getOriginalEscapePoints() {
        return this.originalEscapePoints;
    }
}
