package eu.deltasource.internship.GroupRepository;

import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GroupRepositoryImpl implements GroupRepository {
    private List<Group> carnivoresGroup = new ArrayList<>();
    private List<Group> herbivoresGroup = new ArrayList<>();
    
    @Override
    public List<Group> getCarnivoresGroup() {
        return carnivoresGroup;
    }
    
    @Override
    public List<Group> getHerbivoresGroup() {
        return herbivoresGroup;
    }
    
    @Override
    public void addGroupOfCarnivores(Group group) {
        carnivoresGroup.add(group);
    }
    
    @Override
    public void addGroupOfHerbivores(Group group) {
        herbivoresGroup.add(group);
    }
    
    public List<Carnivore> findInGroup(Carnivore animal) {
        Group group = carnivoresGroup.stream()
                .filter(a -> a.getAnimals().contains(animal))
                .findFirst()
                .orElse(null);
        if (group == null) {
            return null;
        }
        
        return group.getAnimals().stream()
                .filter(c -> c instanceof Carnivore
                        && c.getAnimalType().equals(animal.getAnimalType())
                        && c.getClass().getSimpleName().equals(animal.getClass().getSimpleName()))
                .map(Carnivore.class::cast)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Herbivore> findInGroup(Herbivore animal) {
        Group group = herbivoresGroup.stream()
                .filter(a -> a.getAnimals().contains(animal))
                .findFirst()
                .orElse(null);
        
        if (group == null) {
            return null;
        }
    
        return group.getAnimals().stream()
                .filter(c -> c instanceof Carnivore
                        && c.getAnimalType().equals(animal.getAnimalType())
                        && c.getClass().getSimpleName().equals(animal.getClass().getSimpleName()))
                .map(Herbivore.class::cast)
                .collect(Collectors.toList());
    }
    
}
