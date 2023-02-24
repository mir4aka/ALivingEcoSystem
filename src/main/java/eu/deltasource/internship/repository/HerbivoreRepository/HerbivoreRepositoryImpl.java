package eu.deltasource.internship.repository.HerbivoreRepository;

import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HerbivoreRepositoryImpl implements HerbivoreRepository {
    private List<Herbivore> herbivoresList = new ArrayList<>();
    private List<Herbivore> newBornHerbivores = new ArrayList<>();
    
    @Override
    public List<Herbivore> getHerbivoresList() {
        return Collections.unmodifiableList(herbivoresList);
    }
    
    @Override
    public List<Herbivore> getNewBornHerbivores() {
        return newBornHerbivores;
    }
    
    @Override
    public void addHerbivore(Herbivore... animals) {
        herbivoresList.addAll(Arrays.asList(animals));
    }
    
    @Override
    public void addNewBornHerbivore(Herbivore herbivore) {
        newBornHerbivores.add(herbivore);
    }
    
    @Override
    public void removeHerbivore(Herbivore animal) {
        herbivoresList.remove(animal);
    }
    
    @Override
    public void clearNewBornHerbivoresCollection() {
        newBornHerbivores.clear();
    }
}
