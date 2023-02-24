package eu.deltasource.internship.service.helper;

import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;

import java.util.Collections;
import java.util.List;

public class NewBornCarnivoresCollection {
    private CarnivoreRepository carnivoreRepository;
    
    public NewBornCarnivoresCollection(CarnivoreRepository carnivoreRepository) {
        this.carnivoreRepository = carnivoreRepository;
    }
    
    public List<Carnivore> getNewBornCarnivores() {
        return Collections.unmodifiableList(carnivoreRepository.getNewBornCarnivores());
    }
    
    public void addNewBornCarnivore(Carnivore carnivore) {
        carnivoreRepository.addNewBornCarnivore(carnivore);
    }
    
    public void clearNewBornCarnivoresCollection() {
        carnivoreRepository.clearNewBornCarnivoresCollection();
    }
}
