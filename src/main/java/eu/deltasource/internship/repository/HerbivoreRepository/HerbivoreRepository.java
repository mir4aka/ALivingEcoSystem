package eu.deltasource.internship.repository.HerbivoreRepository;

import eu.deltasource.internship.model.Herbivore;

import java.util.List;

public interface HerbivoreRepository {
    List<Herbivore> getHerbivoresList();
    List<Herbivore> getNewBornHerbivores();
    
    void addHerbivore(Herbivore... animals);
    void addNewBornHerbivore(Herbivore herbivore);
    
    void removeHerbivore(Herbivore herbivore);
    void clearNewBornHerbivoresRepository();
}
