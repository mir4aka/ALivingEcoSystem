package eu.deltasource.internship.repository.CarnivoreRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.List;

public interface CarnivoreRepository {
    public List<Carnivore> getCarnivores();
    
    public void addCarnivore(Carnivore... animals);
    
    public void removeCarnivore(Carnivore animal);
    
    void addAllCarnivores(List<Carnivore> carnivores);
}
