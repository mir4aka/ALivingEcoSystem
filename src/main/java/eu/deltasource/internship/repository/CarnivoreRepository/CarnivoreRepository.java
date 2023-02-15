package eu.deltasource.internship.repository.CarnivoreRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;

import java.util.ArrayList;
import java.util.List;

public interface CarnivoreRepository {
    public List<Animal> getCarnivores();
    
    public void addCarnivore(Animal... animals);
    
    public void removeCarnivore(Animal animal);
}
