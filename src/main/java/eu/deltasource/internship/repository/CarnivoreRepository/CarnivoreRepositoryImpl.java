package eu.deltasource.internship.repository.CarnivoreRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CarnivoreRepositoryImpl implements CarnivoreRepository {
    private List<Carnivore> carnivoresList = new ArrayList<>();
    
    @Override
    public List<Carnivore> getCarnivores() {
        return Collections.unmodifiableList(carnivoresList);
    }
    
    @Override
    public void addCarnivore(Carnivore... animals) {
        carnivoresList.addAll(Arrays.asList(animals));
    }
    
    @Override
    public void removeCarnivore(Carnivore animal) {
        carnivoresList.remove(animal);
    }
    
    @Override
    public void addAllCarnivores(List<Carnivore> carnivores) {
        carnivoresList.addAll(carnivores);
    }
}
