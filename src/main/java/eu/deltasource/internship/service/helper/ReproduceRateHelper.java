package eu.deltasource.internship.service.helper;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;

import java.util.Random;

public class ReproduceRateHelper {
    private SuccessChanceCalculator successChanceCalculator;
    
    public ReproduceRateHelper(SuccessChanceCalculator successChanceCalculator) {
        this.successChanceCalculator = successChanceCalculator;
    }
    
    public void decreaseReproductionRate(Animal animal) {
        int reproductionRate = animal.getReproductionRate();
        reproductionRate--;
        if (reproductionRate <= 0) {
            reproductionRate = 0;
        }
        animal.setReproductionRate(reproductionRate);
    }
    
    public void resetReproductionRate(Animal animal) {
        int randomReproductionRate = new Random().nextInt(10, 25);
        animal.setReproductionRate(randomReproductionRate);
    }
    
    public Carnivore reproduce(Carnivore carnivore) {
        return new Carnivore(carnivore.getSpecie(), carnivore.getMaxAge(), carnivore.getWeight(), carnivore.getHabitat(), carnivore.getSocialStatus(), carnivore.getGroupAmount(), 10, carnivore.getHungerRate(), successChanceCalculator.getScaledAttackPoints(carnivore));
    }
    
    public Herbivore reproduce(Herbivore herbivore) {
        return new Herbivore(herbivore.getSpecie(), herbivore.getMaxAge(), herbivore.getWeight(), herbivore.getHabitat(), herbivore.getSocialStatus(), herbivore.getGroupAmount(), 10, successChanceCalculator.getScaledEscapePoints(herbivore));
    }
}
