package eu.deltasource.internship.HerbivoreRepository;

import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.List;

public interface HerbivoreRepository {
    List<Herbivore> herbivores = new ArrayList<>();
    
    List<Herbivore> getHerbivores();
    
    void addHerbivore(Herbivore... animals);
    
    void removeHerbivore(Herbivore herbivore);
}
