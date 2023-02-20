package eu.deltasource.internship.service;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupService {
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    private List<Animal> animals = new ArrayList<>();
    
    public List<Group> getCarnivoresGroup() {
        return Collections.unmodifiableList(groupRepository.getCarnivoresGroup());
    }
    
    public Carnivore findCarnivoreInGroup(Carnivore carnivore) {
        return groupRepository.findCarnivoreInGroup(carnivore);
    }
    
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }
    
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }
    
    public void findFirstAnimalToRemove(Carnivore carnivore) {
        Animal animal = animals.stream().filter(a -> a.getSpecie().equals(carnivore.getSpecie())).findFirst().orElse(null);
        animals.remove(animal);
    }
}
