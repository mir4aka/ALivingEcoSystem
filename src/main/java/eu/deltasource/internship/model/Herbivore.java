package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;

public class Herbivore extends Animal {
    private double escapePoints;
    
    public Herbivore(String specie, double maxAge, double weight, HabitatEnum habitat, SocialStatus livingType, int groupAmount, int reproductionRate, double escapePoints) {
        super(specie, maxAge, weight, habitat, livingType, groupAmount, reproductionRate);
        this.escapePoints = escapePoints;
    }
    
    public Herbivore() {
    }
    
    @Override
    public double getPoints() {
        return escapePoints;
    }
    
    
    @Override
    public String toString() {
        return getSpecie() + getAge() + "\n";
    }
}
