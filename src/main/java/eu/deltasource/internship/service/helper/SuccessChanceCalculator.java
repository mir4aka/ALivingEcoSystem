package eu.deltasource.internship.service.helper;

import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.exception.InvalidAnimalException;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;

public class SuccessChanceCalculator {
    /**
     * Calculates the success chance of the attack, based on the carnivore/herbivore's social status.
     * */
    public double getAttackSuccess(Carnivore carnivore, Herbivore herbivore) {
        checkIfAnimalIsNull(carnivore, herbivore);
        double successRate;
        if (herbivore.getPoints() > carnivore.getPoints()) {
            successRate = 0;
            return successRate;
        }
        successRate = calculationOfSuccessRate(carnivore, herbivore);
        return successRate;
    }
    
    private double calculationOfSuccessRate(Carnivore carnivore, Herbivore herbivore) {
        double escapePoints;
        double attackPoints;
        double successRate;
        attackPoints = calculateAttackPointsOfTheCarnivore(carnivore);
        escapePoints = calculateEscapePointsOfTheHerbivore(herbivore);
        successRate = attackPoints / (attackPoints + escapePoints) * 100;
        successRate = calculateSuccessChanceIfTheCarnivoreAttacksAlone(carnivore, successRate);
        successRate = calculatesTheSuccessChanceIfTheHerbivoreWeighsMoreThanTheCarnivore(carnivore, herbivore, successRate);
        return successRate;
    }
    
    private void checkIfAnimalIsNull(Carnivore carnivore, Herbivore herbivore) {
        if(carnivore == null || herbivore == null) {
            throw new InvalidAnimalException();
        }
    }
    
    /**
     * Returns scaled attack points based on the formula from scalePoints().
     */
    public double getScaledAttackPoints(Carnivore carnivore) {
        return scalePoints(carnivore, carnivore.getPoints());
    }
    
    /**
     * Returns scaled escape points based on the formula from scalePoints().
     */
    public double getScaledEscapePoints(Herbivore herbivore) {
        return scalePoints(herbivore, herbivore.getPoints());
    }
    
    /**
     * Scales the attack/escape points of the animal based on their current age and max age.
     */
    public double scalePoints(Animal animal, double points) {
        if (animal.getAge() == 0) {
            return points;
        }
        return points * (1 - (animal.getAge() * 1.0 / animal.getMaxAge()));
    }
    
    private double calculatesTheSuccessChanceIfTheHerbivoreWeighsMoreThanTheCarnivore(Carnivore carnivore, Herbivore herbivore, double successRate) {
        if (herbivore.getWeight() > carnivore.getWeight() && carnivore.getSocialStatus().equals(SocialStatus.ALONE)) {
            successRate = herbivore.getWeight() * 1.0 / carnivore.getWeight();
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
