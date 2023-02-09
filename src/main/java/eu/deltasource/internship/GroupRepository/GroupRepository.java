package eu.deltasource.internship.GroupRepository;

import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;

import java.util.List;

public interface GroupRepository {
    List<Group> getAnimalsGroup();
    void addToGroup(Group group);
    List<Carnivore> findInGroup(Carnivore animal);
    List<Herbivore> findInGroup(Herbivore animal);
}
