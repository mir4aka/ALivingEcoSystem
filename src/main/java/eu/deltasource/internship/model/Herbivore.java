package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

import java.util.Random;

public class Herbivore extends Animal {
    private double escapePoints;
    private double grouping;

    public Herbivore(String animalType, int maxAge, int weight, HabitatEnum mainHabitat, LivingType livingType, double reproductionRate, int escapePoints) {
        super(animalType, maxAge, weight, mainHabitat, livingType, reproductionRate);
        this.escapePoints = escapePoints;
        this.grouping = 1;
    }

    public double getEscapePoints() {
        return escapePoints;
    }

    public void setEscapePoints(int escapePoints) {
        this.escapePoints = escapePoints;
    }

    public void decreaseEscapePoints(double points) {
        escapePoints -= points;
        if(escapePoints < 0) {
            escapePoints = 0;
        }
    }
    
    public void increaseEscapePoints(double escapePoints) {
        this.escapePoints += escapePoints;
    }
    
    double getScaledEscapePoints() {
        return scalePoints(escapePoints);
    }
    
    public double getGrouping() {
        return grouping;
    }
    
    public void changeGroupingFactor() {
        this.grouping = new Random().nextDouble(1, 3);
    }
}
