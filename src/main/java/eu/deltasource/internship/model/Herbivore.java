package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.Habitat;
import eu.deltasource.internship.enums.SocialStatus;

public class Herbivore extends Animal {
    private double escapePoints;
    
    public Herbivore(String specie, int maxAge, int weight, Habitat habitat, SocialStatus socialStatus, int groupAmount, int reproductionRate, double escapePoints) {
        super(specie, maxAge, weight, habitat, socialStatus, groupAmount, reproductionRate);
        setEscapePoints(escapePoints);
    }
    
    public Herbivore() {
    }
    
    public void setEscapePoints(double escapePoints) {
        this.escapePoints = escapePoints;
    }
    
    /**
     * Gets the escape points of the herbivore.
     *
     * @return
     */
    @Override
    public double getPoints() {
        return escapePoints;
    }
    
    @Override
    public String toString() {
        return getSpecie() + " age " + getAge() + "\n";
    }
}
