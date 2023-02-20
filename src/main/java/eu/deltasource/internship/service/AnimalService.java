package eu.deltasource.internship.service;

import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimalService {
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private List<Carnivore> newBornCarnivores = new ArrayList<>();
    private List<Herbivore> newBornHerbivores = new ArrayList<>();
    
    public void addGroupOfCarnivores(Group group) {
        groupRepository.addGroupOfCarnivores(group);
    }
    
    public void addGroupOfHerbivores(Group group) {
        groupRepository.addGroupOfHerbivores(group);
    }
    
    public List<Group> getCarnivoresGroup() {
        return groupRepository.getCarnivoresGroup();
    }
    
    public List<Group> getHerbivoresGroup() {
        return groupRepository.getHerbivoresGroup();
    }
    
    public List<Animal> findGroup(List<Group> groups, Animal animal) {
        List<Animal> animals = new ArrayList<>();
        
        for (Group group : groups) {
            List<Animal> animalsList = group.getAnimals();
            for (Animal animalInList : animalsList) {
                if (animal.getSpecie().equals(animalInList.getSpecie())) {
                    animals.add(animalInList);
                }
            }
        }
        return animals;
    }
    
    public void addCarnivore(Carnivore... carnivores) {
        for (Carnivore carnivore : carnivores) {
            carnivoreRepository.addCarnivore(carnivore);
        }
    }
    
    public void removeCarnivore(Carnivore carnivore) {
        carnivoreRepository.removeCarnivore(carnivore);
    }
    
    public List<Carnivore> getCarnivores() {
        return Collections.unmodifiableList(carnivoreRepository.getCarnivores());
    }
    
    public void addHerbivore(Herbivore... herbivores) {
        for (Herbivore herbivore : herbivores) {
            herbivoreRepository.addHerbivore(herbivore);
        }
    }
    
    public void removeHerbivore(Herbivore herbivore) {
        herbivoreRepository.removeHerbivore(herbivore);
    }
    
    public List<Herbivore> getHerbivores() {
        return Collections.unmodifiableList(herbivoreRepository.getHerbivores());
    }
    
    public void addNewBornCarnivore(Carnivore carnivore) {
        newBornCarnivores.add(carnivore);
    }
    
    public void addNewBornHerbivore(Herbivore herbivore) {
        newBornHerbivores.add(herbivore);
    }
    
    public List<Carnivore> getNewBornCarnivores() {
        return Collections.unmodifiableList(newBornCarnivores);
    }
    
    public List<Herbivore> getNewBornHerbivores() {
        return Collections.unmodifiableList(newBornHerbivores);
    }
    
    public void clearNewBornCarnivoresList() {
        newBornCarnivores.clear();
    }
    
    public void clearNewBornHerbivoresList() {
        newBornHerbivores.clear();
    }
    
    public double getAttackSuccess(Carnivore carnivore, Herbivore herbivore) {
        double attackPoints;
        double escapePoints;
        double successRate;
        
        if (carnivore.getSocialStatus().equals(SocialStatus.GROUP)) {
            attackPoints = getScaledAttackPoints(carnivore) * carnivore.getGroupAmount();
        } else {
            attackPoints = getScaledAttackPoints(carnivore);
        }
        
        if (herbivore.getSocialStatus().equals(SocialStatus.GROUP)) {
            escapePoints = getScaledEscapePoints(herbivore) * herbivore.getGroupAmount();
        } else {
            escapePoints = getScaledEscapePoints(herbivore);
        }
        
        successRate = attackPoints / (attackPoints + escapePoints) * 100;
        
        if (carnivore.getSocialStatus().equals(SocialStatus.ALONE)) {
            successRate *= 0.5;
        } else {
            successRate = successRate + (successRate * 0.3);
        }
        
        if (herbivore.getWeight() > carnivore.getWeight() && carnivore.getSocialStatus().equals(SocialStatus.ALONE)) {
            successRate = herbivore.getWeight() / carnivore.getWeight();
        }
        
        return successRate;
    }
    
    public void increaseHungerLevel(Carnivore carnivore, double hunger) {
        int hungerRate = carnivore.getHungerRate();
        hungerRate += hunger;
        carnivore.setHungerRate(hungerRate);
        if (hungerRate >= 100) {
            carnivore.setHungerRate(100);
        }
    }
    
    public void decreaseHungerLevel(Carnivore carnivore, double hunger) {
        int hungerRate = carnivore.getHungerRate();
        hungerRate -= hunger;
        carnivore.setHungerRate(hungerRate);
        if (hungerRate <= 0) {
            carnivore.setHungerRate(1);
        }
    }
    
    public Carnivore reproduce(Carnivore carnivore) {
        return new Carnivore(carnivore.getSpecie(), carnivore.getMaxAge(), carnivore.getWeight(), carnivore.getHabitat(), carnivore.getSocialStatus(), carnivore.getGroupAmount(), 10, carnivore.getHungerRate(), getScaledAttackPoints(carnivore));
    }
    
    public Herbivore reproduce(Herbivore herbivore) {
        return new Herbivore(herbivore.getSpecie(), herbivore.getMaxAge(), herbivore.getWeight(), herbivore.getHabitat(), herbivore.getSocialStatus(), herbivore.getGroupAmount(), 10, getScaledEscapePoints(herbivore));
    }
    
    private int scalePoints(Animal animal, int points) {
        if (animal.getAge() == 0) {
            return points;
        }
        return points * (1 - (animal.getAge() / animal.getMaxAge()));
    }
    
    private int getScaledAttackPoints(Carnivore carnivore) {
        return scalePoints(carnivore, carnivore.getAttackPoints());
    }
    
    private int getScaledEscapePoints(Herbivore herbivore) {
        return scalePoints(herbivore, herbivore.getEscapePoints());
    }
    
    public void increaseAge(Animal animal) {
        int age = animal.getAge();
        age++;
        if (animal.getAge() >= animal.getMaxAge()) {
            age = animal.getMaxAge();
        }
        animal.setAge(age);
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
        animal.setReproductionRate(10);
    }
}
