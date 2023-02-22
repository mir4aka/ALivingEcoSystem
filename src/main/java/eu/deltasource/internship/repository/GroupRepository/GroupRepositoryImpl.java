package eu.deltasource.internship.repository.GroupRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {
    private List<Group> carnivoresGroup = new ArrayList<>();
    private List<Group> herbivoresGroup = new ArrayList<>();
    
    @Override
    public List<Group> getCarnivoresGroup() {
        return Collections.unmodifiableList(carnivoresGroup);
    }
    
    @Override
    public List<Group> getHerbivoresGroup() {
        return Collections.unmodifiableList(herbivoresGroup);
    }
    
    @Override
    public void addGroupOfCarnivores(Group group) {
        carnivoresGroup.add(group);
    }
    
    @Override
    public void addGroupOfHerbivores(Group group) {
        herbivoresGroup.add(group);
    }
    
    public Carnivore findCarnivoreInGroup(Carnivore carnivore) {
        for (Group group : carnivoresGroup) {
            List<Animal> animals = group.getAnimals();
            if (animals.contains(carnivore)) {
                return carnivore;
            }
        }
        return carnivore;
    }
    
    @Override
    public void removeCarnivore(Animal animal) {
        for (Group group : carnivoresGroup) {
            List<Animal> animals = group.getAnimals();
            for (Animal animal1 : animals) {
                if(animal1.equals(animal)) {
                    animals.remove(animal);
                }
            }
        }
    }
    
}
