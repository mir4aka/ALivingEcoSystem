package eu.deltasource.internship.repository.NewBornAnimalsRepository;

import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;

import java.util.Collections;
import java.util.List;

public class NewBornCarnivoresRepository {
    private CarnivoreRepository carnivoreRepository;
    
    public NewBornCarnivoresRepository(CarnivoreRepository carnivoreRepository) {
        this.carnivoreRepository = carnivoreRepository;
    }
    
    public List<Carnivore> getNewBornCarnivores() {
        return Collections.unmodifiableList(carnivoreRepository.getNewBornCarnivores());
    }
    
    public void addNewBornCarnivore(Carnivore carnivore) {
        carnivoreRepository.addNewBornCarnivore(carnivore);
    }
    
    public void clearNewBornCarnivoresRepository() {
        carnivoreRepository.clearNewBornCarnivoresRepository();
    }
}
