package eu.deltasource.internship.CarnivoreRepository;

import eu.deltasource.internship.model.Carnivore;

import java.util.ArrayList;
import java.util.List;

public interface CarnivoreRepository {
    List<Carnivore> carnivores = new ArrayList<>();
    
    public List<Carnivore> getCarnivores();
    
    public void addCarnivore(Carnivore... animals);
    
    public void removeCarnivore(Carnivore animal);
}
