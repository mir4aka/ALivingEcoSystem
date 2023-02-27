package eu.deltasource.internship.service.helper;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;

public class ReproductionRateHelper {
    private SuccessChanceCalculator successChanceCalculator;
    
    public ReproductionRateHelper(SuccessChanceCalculator successChanceCalculator) {
        this.successChanceCalculator = successChanceCalculator;
    }
    
    /**
     * Decreasing the animal's reproduction rate by one at the end of each iteration
     */
    public void decreaseReproductionLevel(Animal animal) {
        int reproductionLevel = animal.getReproductionLevel();
        int reproductionRate = animal.getReproductionRate();
        reproductionLevel-=reproductionRate;
        if (reproductionLevel <= 0) {
            animal.setReproductionLevel(0);
        }
        animal.setReproductionLevel(reproductionLevel);
    }
    
    /**
     * Resets the reproduction rate back to it's first value.
     */
    public void resetReproductionLevel(Animal animal) {
        int reproductionLevel = 100;
        animal.setReproductionLevel(reproductionLevel);
    }
    
    /**
     * Carnivore reproduction, the returned carnivore is added to its species.
     */
    public Carnivore reproduce(Carnivore carnivore) {
        return new Carnivore(carnivore.getSpecie(), carnivore.getMaxAge(), carnivore.getWeight(), carnivore.getHabitat(), carnivore.getSocialStatus(), carnivore.getGroupAmount(), 10, carnivore.getHungerRate(), successChanceCalculator.getScaledAttackPoints(carnivore));
    }
    
    /**
     * Herbivore reproduction, the returned herbivore is added to its species.
     */
    public Herbivore reproduce(Herbivore herbivore) {
        return new Herbivore(herbivore.getSpecie(), herbivore.getMaxAge(), herbivore.getWeight(), herbivore.getHabitat(), herbivore.getSocialStatus(), herbivore.getGroupAmount(), 10, successChanceCalculator.getScaledEscapePoints(herbivore));
    }
}
