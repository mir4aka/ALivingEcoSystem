package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

import java.util.Random;

public class Herbivore extends Animal {
    private int escapePoints;
    private final int originalEscapePoints;
    
    public Herbivore(String animalType, double maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, int groupAmount, int reproductionRate, int escapePoints) {
        super(animalType, maxAge, weight, mainHabitat, livingType, groupAmount, reproductionRate);
        this.escapePoints = escapePoints;
        this.originalEscapePoints = escapePoints;
    }
    
    public int getEscapePoints() {
        return escapePoints;
    }
    
    public void setEscapePoints(int escapePoints) {
        this.escapePoints = escapePoints;
    }
    
    public double getScaledEscapePoints() {
        return scalePoints(escapePoints);
    }
    
    public int getOriginalEscapePoints() {
        return this.originalEscapePoints;
    }
    
    @Override
    public String toString() {
        return getAnimalType() + getAge() + "\n";
    }
}
