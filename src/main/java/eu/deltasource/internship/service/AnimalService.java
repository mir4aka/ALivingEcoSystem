package eu.deltasource.internship.service;

import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.service.helper.SuccessChanceCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AnimalService {
    private HerbivoreRepository herbivoreRepository;
    private CarnivoreRepository carnivoreRepository;
    private GroupRepository groupRepository;
    private List<Carnivore> newBornCarnivores;
    private List<Herbivore> newBornHerbivores;
    private SuccessChanceCalculator successChanceCalculator;
    
    public AnimalService(HerbivoreRepository herbivoreRepository, CarnivoreRepository carnivoreRepository, GroupRepository groupRepository, SuccessChanceCalculator successChanceCalculator) {
        this.herbivoreRepository = herbivoreRepository;
        this.carnivoreRepository = carnivoreRepository;
        this.groupRepository = groupRepository;
        this.successChanceCalculator = successChanceCalculator;
        this.newBornCarnivores = new ArrayList<>();
        this.newBornHerbivores = new ArrayList<>();
    }
    
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
    
    public void addCarnivoreToRepository(Carnivore... carnivores) {
        for (Carnivore carnivore : carnivores) {
            carnivoreRepository.addCarnivore(carnivore);
        }
    }
    
    public void removeCarnivoreFromRepository(Carnivore carnivore) {
        carnivoreRepository.removeCarnivore(carnivore);
    }
    
    public void addHerbivoreToRepository(Herbivore... herbivores) {
        for (Herbivore herbivore : herbivores) {
            herbivoreRepository.addHerbivore(herbivore);
        }
    }
    
    public void removeHerbivoreFromRepository(Herbivore herbivore) {
        herbivoreRepository.removeHerbivore(herbivore);
    }
    
    public List<Carnivore> getCarnivores() {
        return Collections.unmodifiableList(carnivoreRepository.getCarnivores());
    }
    
    public List<Herbivore> getHerbivores() {
        return Collections.unmodifiableList(herbivoreRepository.getHerbivoresList());
    }
    
    public void increaseHungerLevel(Carnivore carnivore) {
        int hungerLevel = carnivore.getHungerLevel();
        int hungerRate = carnivore.getHungerRate();
        hungerLevel += hungerRate;
        carnivore.setHungerLevel(hungerLevel);
        if (hungerLevel >= 100) {
            carnivore.setHungerLevel(100);
        }
    }
    
    public void decreaseHungerLevel(Carnivore carnivore, double food) {
        int hungerLevel = carnivore.getHungerLevel();
        hungerLevel-=food;
        carnivore.setHungerLevel(hungerLevel);
        if (hungerLevel <= 0) {
            carnivore.setHungerLevel(0);
        }
    }
    
    public void increaseAge(Animal animal) {
        double age = animal.getAge();
        age++;
        if (animal.getAge() >= animal.getMaxAge()) {
            age = animal.getMaxAge();
        }
        animal.setAge(age);
    }
}
