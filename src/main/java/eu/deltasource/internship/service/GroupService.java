package eu.deltasource.internship.service;

import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;
import eu.deltasource.internship.model.Herbivore;

import java.util.Collections;
import java.util.List;

public class GroupService {
    private GroupRepository groupRepository = new GroupRepositoryImpl();
    
    public List<Group> getGroups() {
        return Collections.unmodifiableList(groupRepository.getCarnivoresGroup());
    }
}
