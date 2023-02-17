package eu.deltasource.internship.service;

import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.enums.LivingType;
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
    private List<Animal> newBornAnimals = new ArrayList<>();
    
    public void addGroupOfCarnivores(Group group) {
        groupRepository.addGroupOfCarnivores(group);
    }
    
    public void addGroupOfHerbivores(Group group) {
        groupRepository.addGroupOfHerbivores(group);
    }
    
    public List<Group> getGroups() {
        return groupRepository.getCarnivoresGroup();
    }
    
    public List<Animal> findGroup(List<Group> groups, Animal animal) {
        List<Animal> animals = new ArrayList<>();
        
        for (Group group : groups) {
            List<Animal> animals1 = group.getAnimals().stream().filter(g -> g.getAnimalType().equals(animal.getAnimalType())).toList();
            animals.addAll(animals1);
        }
        return animals;
    }
    
    public void addCarnivore(Animal... carnivores) {
        for (Animal carnivore : carnivores) {
            carnivoreRepository.addCarnivore(carnivore);
        }
    }
    
    public void removeCarnivore(Animal carnivore) {
        carnivoreRepository.removeCarnivore(carnivore);
    }
    
    public List<Animal> getCarnivores() {
        return Collections.unmodifiableList(carnivoreRepository.getCarnivores());
    }
    
    public void addHerbivore(Animal... herbivores) {
        for (Animal herbivore : herbivores) {
            herbivoreRepository.addHerbivore(herbivore);
        }
    }
    
    public void removeHerbivore(Animal herbivore) {
        herbivoreRepository.removeHerbivore(herbivore);
    }
    
    public List<Animal> getHerbivores() {
        return Collections.unmodifiableList(herbivoreRepository.getHerbivores());
    }
    
    public void addNewBorn(Animal animal) {
        newBornAnimals.add(animal);
    }
    
    public List<Animal> getNewBornAnimals() {
        return Collections.unmodifiableList(newBornAnimals);
    }
    
    public void clearNewBornAnimalsList() {
        newBornAnimals.clear();
    }
    
    public double getAttackSuccess(Carnivore carnivore, Herbivore herbivore) {
        double attackPoints;
        double escapePoints;
        double successRate;
        
        if (carnivore.getLivingType().equals(LivingType.GROUP)) {
            attackPoints = carnivore.getScaledAttackPoints() * carnivore.getGroupAmount();
        } else {
            attackPoints = carnivore.getScaledAttackPoints();
        }
        
        if (herbivore.getLivingType().equals(LivingType.GROUP)) {
            escapePoints = herbivore.getScaledEscapePoints() * herbivore.getGroupAmount();
        } else {
            escapePoints = herbivore.getScaledEscapePoints();
        }
        
        successRate = attackPoints / (attackPoints + escapePoints) * 100;
        
        if (carnivore.getLivingType().equals(LivingType.ALONE)) {
            successRate *= 0.5;
        } else {
            successRate = successRate + (successRate * 0.3);
        }
        
        if (herbivore.getWeight() > carnivore.getWeight() && carnivore.getLivingType().equals(LivingType.ALONE)) {
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
        Carnivore newCarnivore = new Carnivore(carnivore.getAnimalType(), carnivore.getMaxAge(), carnivore.getWeight(), carnivore.getMainHabitat(), carnivore.getLivingType(), carnivore.getGroupAmount(), 10, carnivore.getHungerRate(), carnivore.getAttackPoints());
        newCarnivore.setAge(0);
        return newCarnivore;
    }
    
    public Herbivore reproduce(Herbivore herbivore) {
        Herbivore newHerbivore = new Herbivore(herbivore.getAnimalType(), herbivore.getMaxAge(), herbivore.getWeight(), herbivore.getMainHabitat(), herbivore.getLivingType(), herbivore.getGroupAmount(), 10, herbivore.getEscapePoints());
        newHerbivore.setAge(0);
        return newHerbivore;
    }
}
