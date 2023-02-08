package eu.deltasource.internship;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {
    private List<Group> animalsGroup = new ArrayList<>();
    
    @Override
    public List<Group> getCarnivoresGroup() {
        return Collections.unmodifiableList(animalsGroup);
    }
    
    @Override
    public void addToGroup(Group animal) {
        animalsGroup.add(animal);
    }
    
    @Override
    public void findInGroup(Animal animal) {
        Group group = animalsGroup.stream().filter(a -> a.getAnimals().contains(animal)).findFirst().orElse(null);
        //TODO FIND AN ANIMAL IN THE GROUP, DO A STREAM IN THE GROUP'S LIST
    }
}
