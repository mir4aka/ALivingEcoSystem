package eu.deltasource.internship.repository.NewBornAnimalsRepository;

import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;

import java.util.Collections;
import java.util.List;

public class NewBornHerbivoresRepository {
    private HerbivoreRepository herbivoreRepository;
    
    public NewBornHerbivoresRepository(HerbivoreRepository herbivoreRepository) {
        this.herbivoreRepository = herbivoreRepository;
    }
    
    public List<Herbivore> getNewBornHerbivores() {
        return Collections.unmodifiableList(herbivoreRepository.getNewBornHerbivores());
    }
    
    public void addNewBornHerbivore(Herbivore herbivore) {
        herbivoreRepository.addNewBornHerbivore(herbivore);
    }
    
    public void clearNewBornHerbivoresRepository() {
        herbivoreRepository.clearNewBornHerbivoresRepository();
    }
}
