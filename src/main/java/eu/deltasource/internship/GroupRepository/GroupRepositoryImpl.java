package eu.deltasource.internship.GroupRepository;

import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GroupRepositoryImpl implements GroupRepository {
    private List<Group> animalsGroup = new ArrayList<>();
    
    @Override
    public List<Group> getAnimalsGroup() {
        return Collections.unmodifiableList(animalsGroup);
    }
    
    @Override
    public void addToGroup(Group group) {
        animalsGroup.add(group);
    }
    
    public List<Carnivore> findInGroup(Carnivore animal) {
        Group group = animalsGroup.stream()
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
        Group group = animalsGroup.stream()
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
