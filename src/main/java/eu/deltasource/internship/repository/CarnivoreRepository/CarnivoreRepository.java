package eu.deltasource.internship.repository.CarnivoreRepository;

import eu.deltasource.internship.model.Carnivore;

import java.util.List;

public interface CarnivoreRepository {
    public List<Carnivore> getCarnivores();
    List<Carnivore> getNewBornCarnivores();
    
    public void addCarnivore(Carnivore... animals);
    void addNewBornCarnivore(Carnivore carnivore);
    
    public void removeCarnivore(Carnivore animal);
    void clearNewBornCarnivoresRepository();
}
