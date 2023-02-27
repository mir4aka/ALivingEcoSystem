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
    
    public List<Carnivore> findGroup(List<Group> groups, Animal animal) {
        List<Carnivore> animals = new ArrayList<>();
        for (Group group : groups) {
            List<Carnivore> animalsList = group.getCarnivoresGroup();
            for (Carnivore animalInList : animalsList) {
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
    
    public void increaseAge(Animal animal) {
        int age = animal.getAge();
        age++;
        if (animal.getAge() >= animal.getMaxAge()) {
            age = animal.getMaxAge();
        }
        animal.setAge(age);
    }
}
