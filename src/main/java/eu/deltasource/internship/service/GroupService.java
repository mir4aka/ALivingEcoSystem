package eu.deltasource.internship.service;

import eu.deltasource.internship.GroupRepository.GroupRepository;
import eu.deltasource.internship.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;

import java.util.Collections;
import java.util.List;

public class GroupService {
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    
    public void addToGroup(Group group) {
        groupRepository.addGroupOfCarnivores(group);
    }
    
    public List<Carnivore> findCarnivoreInGroup(Carnivore carnivore) {
        return groupRepository.findInGroup(carnivore);
    }
    
    public List<Herbivore> findHerbivoreInGroup(Herbivore herbivore) {
        return groupRepository.findInGroup(herbivore);
    }
    
    public List<Group> getGroups() {
        return Collections.unmodifiableList(groupRepository.getCarnivoresGroup());
    }
}
