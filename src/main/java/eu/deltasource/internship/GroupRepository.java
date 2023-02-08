package eu.deltasource.internship;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;

import java.util.List;

public interface GroupRepository {
    List<Group> getCarnivoresGroup();
    void addToGroup(Group animal);
    
    void findInGroup(Animal animal);
}
