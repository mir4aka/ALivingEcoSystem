package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

public class Herbivore extends Animal {
    private double escapePoints;

    public Herbivore(int maxAge, int weight, HabitatEnum mainHabitat, LivingType livingType, double reproductionRate, double escapePoints) {
        super(maxAge, weight, mainHabitat, livingType, reproductionRate);
        this.escapePoints = escapePoints;
    }

    public double getEscapePoints() {
        return escapePoints;
    }

    public void setEscapePoints(double escapePoints) {
        this.escapePoints = escapePoints;
    }
}
