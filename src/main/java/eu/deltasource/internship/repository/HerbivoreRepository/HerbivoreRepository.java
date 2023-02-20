package eu.deltasource.internship.repository.HerbivoreRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.List;

public interface HerbivoreRepository {
    List<Herbivore> getHerbivores();
    
    void addHerbivore(Herbivore... animals);
    
    void removeHerbivore(Herbivore herbivore);
}
