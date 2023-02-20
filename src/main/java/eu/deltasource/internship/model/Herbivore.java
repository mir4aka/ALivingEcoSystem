package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.SocialStatus;

public class Herbivore extends Animal {
    private int escapePoints;
    
    public Herbivore(String animalType, int maxAge, double weight, HabitatEnum mainHabitat, SocialStatus livingType, int groupAmount, int reproductionRate, int escapePoints) {
        super(animalType, maxAge, weight, mainHabitat, livingType, groupAmount, reproductionRate);
        this.escapePoints = escapePoints;
    }
    
    public Herbivore() {
    }
    
    public int getEscapePoints() {
        return escapePoints;
    }
    
    public void setEscapePoints(int escapePoints) {
        this.escapePoints = escapePoints;
    }
    
    @Override
    public String toString() {
        return getSpecie() + getAge() + "\n";
    }
}
