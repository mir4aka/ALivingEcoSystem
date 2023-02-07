package eu.deltasource.internship.model;

import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;

import java.util.Random;

public class Carnivore extends Animal {
    private double attackPoints;
    private int hungerRate;
    private int hungerChange;

    public Carnivore(int maxAge, double weight, HabitatEnum mainHabitat, LivingType livingType, double reproductionRate, int hungerRate, double attackPoints) {
        super(maxAge, weight, mainHabitat, livingType, reproductionRate);
        this.hungerRate = hungerRate;
        this.attackPoints = attackPoints;
        this.hungerChange = new Random().nextInt(1, 20);
    }

    @Override
    public Carnivore reproduce() {
        return new Carnivore(getMaxAge(), getWeight(), getMainHabitat(), getLivingType(), getReproductionRate(), hungerRate, 0);
    }

    public double getAttackSuccess(Herbivore herbivore) {
        double success = (attackPoints / (attackPoints + herbivore.getEscapePoints())) * 100;

        int groupOfAttackers = isInGroup();

        if(groupOfAttackers > 1) {
            success *= 0.7;
        }

        if (herbivore.getWeight() > getWeight() && groupOfAttackers == 1) {
            success /= herbivore.getWeight() / getWeight();
        }

        return success;
    }

    private int isInGroup() {
        return new Random().nextInt(1, 15);
    }

    @Override
    public boolean isAlive() {
        return getAge() <= getMaxAge() && hungerRate < 100;
    }

    public void increaseHungerLevel(double hunger) {
        hungerRate += hunger;
    }

    public void decreaseHungerLevel(double hunger) {
        hungerRate -= hunger;
        if(hungerRate < 0) {
            hungerRate = 0;
        }
    }

    public double getHungerChange() {
        return hungerChange;
    }

    public void setAttackPoints(double attackPoints) {
        this.attackPoints = attackPoints;
    }

    public double getHungerRate() {
        return hungerRate;
    }

    public void setHungerRate(int hungerRate) {
        this.hungerRate = hungerRate;
    }
}
