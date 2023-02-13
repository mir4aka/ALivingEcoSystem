package eu.deltasource.internship.service;

import eu.deltasource.internship.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.GroupRepository.GroupRepository;
import eu.deltasource.internship.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.HerbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.model.Animal;
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
    
    public void removeHerbivore(Herbivore herbivore) {
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
}
