package eu.deltasource.internship.repository.GroupRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;

import java.util.List;

public interface GroupRepository {
    List<Group> getCarnivoresGroup();
    
    List<Group> getHerbivoresGroup();
    
    void addGroupOfCarnivores(Group group);
    
    void addGroupOfHerbivores(Group group);
    
    Carnivore findCarnivoreInGroup(Carnivore carnivore);
    
    void removeCarnivore(Animal animal);
}
