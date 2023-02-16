package eu.deltasource.internship.repository.HerbivoreRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.List;

public interface HerbivoreRepository {
    List<Animal> getHerbivores();
    
    void addHerbivore(Animal... animals);
    
    void removeHerbivore(Animal herbivore);
}
