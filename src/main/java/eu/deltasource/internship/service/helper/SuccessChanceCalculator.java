package eu.deltasource.internship.service.helper;

import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;

public class SuccessChanceCalculator {
    /**
     * Calculates the success chance of the attack, based on the carnivore/herbivore's social status.
     * */
    public double getAttackSuccess(Carnivore carnivore, Herbivore herbivore) {
        double attackPoints;
        double escapePoints;
        double successRate;
        
        if (herbivore.getPoints() > carnivore.getPoints()) {
            successRate = 0;
            return successRate;
        }
        
        attackPoints = calculateAttackPointsOfTheCarnivore(carnivore);
        escapePoints = calculateEscapePointsOfTheHerbivore(herbivore);
        successRate = attackPoints / (attackPoints + escapePoints) * 100;
        successRate = calculateSuccessChanceIfTheCarnivoreAttacksAlone(carnivore, successRate);
        successRate = calculatesTheSuccessChanceIfTheHerbivoreWeighsMoreThanTheCarnivore(carnivore, herbivore, successRate);
        
        return successRate;
    }
    
    public double getScaledAttackPoints(Carnivore carnivore) {
        return scalePoints(carnivore, carnivore.getPoints());
    }
    
    public double getScaledEscapePoints(Herbivore herbivore) {
        return scalePoints(herbivore, herbivore.getPoints());
    }
    
    public double scalePoints(Animal animal, double points) {
        if (animal.getAge() == 0) {
            return points;
        }
        return points * (1 - (animal.getAge() / animal.getMaxAge()));
    }
    
    private double calculatesTheSuccessChanceIfTheHerbivoreWeighsMoreThanTheCarnivore(Carnivore carnivore, Herbivore herbivore, double successRate) {
        if (herbivore.getWeight() > carnivore.getWeight() && carnivore.getSocialStatus().equals(SocialStatus.ALONE)) {
            successRate = herbivore.getWeight() / carnivore.getWeight();
        }
        return successRate;
    }
    
    private double calculateSuccessChanceIfTheCarnivoreAttacksAlone(Carnivore carnivore, double successRate) {
        if (carnivore.getSocialStatus().equals(SocialStatus.ALONE)) {
            successRate *= 0.5;
        } else {
            successRate = successRate + (successRate * 0.3);
        }
        return successRate;
    }
    
    private double calculateEscapePointsOfTheHerbivore(Herbivore herbivore) {
        double escapePoints;
        if (herbivore.getSocialStatus().equals(SocialStatus.GROUP)) {
            escapePoints = getScaledEscapePoints(herbivore) * herbivore.getGroupAmount();
        } else {
            escapePoints = getScaledEscapePoints(herbivore);
        }
        return escapePoints;
    }
    
    private double calculateAttackPointsOfTheCarnivore(Carnivore carnivore) {
        double attackPoints;
        if (carnivore.getSocialStatus().equals(SocialStatus.GROUP)) {
            attackPoints = getScaledAttackPoints(carnivore) * carnivore.getGroupAmount();
        } else {
            attackPoints = getScaledAttackPoints(carnivore);
        }
        return attackPoints;
    }
}
