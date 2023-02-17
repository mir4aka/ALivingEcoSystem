package eu.deltasource.internship.repository.GroupRepository;

import eu.deltasource.internship.model.Group;

import java.util.ArrayList;
import java.util.List;
public class GroupRepositoryImpl implements GroupRepository {
    private List<Group> carnivoresGroup = new ArrayList<>();
    private List<Group> herbivoresGroup = new ArrayList<>();
    
    @Override
    public List<Group> getCarnivoresGroup() {
        return carnivoresGroup;
    }
    
    @Override
    public void addGroupOfCarnivores(Group group) {
        carnivoresGroup.add(group);
    }
    
    @Override
    public void addGroupOfHerbivores(Group group) {
        herbivoresGroup.add(group);
    }
    
}
