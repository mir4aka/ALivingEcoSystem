package eu.deltasource.internship.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private List<Carnivore> carnivores = new ArrayList<>();
    private List<Herbivore> herbivores = new ArrayList<>();
    
    public List<Carnivore> getCarnivoresGroup() {
        return Collections.unmodifiableList(carnivores);
    }
    
    public void addCarnivoreToTheGroup(Carnivore carnivore) {
        carnivores.add(carnivore);
    }
    
    public void addHerbivoreToTheGroup(Herbivore herbivore) {
        herbivores.add(herbivore);
    }
    
    public void removeCarnivoreFromTheGroup(Carnivore carnivore) {
        carnivores.remove(carnivore);
    }
}
