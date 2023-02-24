package eu.deltasource.internship.service.helper;

import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;

import java.util.Collections;
import java.util.List;

public class NewBornHerbivoresCollection {
    private HerbivoreRepository herbivoreRepository;
    
    public NewBornHerbivoresCollection(HerbivoreRepository herbivoreRepository) {
        this.herbivoreRepository = herbivoreRepository;
    }
    
    public List<Herbivore> getNewBornHerbivores() {
        return Collections.unmodifiableList(herbivoreRepository.getNewBornHerbivores());
    }
    
    public void addNewBornHerbivore(Herbivore herbivore) {
        herbivoreRepository.addNewBornHerbivore(herbivore);
    }
    
    public void clearNewBornHerbivoresCollection() {
        herbivoreRepository.clearNewBornHerbivoresCollection();
    }
}
