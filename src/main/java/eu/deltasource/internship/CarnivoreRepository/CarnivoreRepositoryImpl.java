package eu.deltasource.internship.CarnivoreRepository;

import eu.deltasource.internship.model.Carnivore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CarnivoreRepositoryImpl implements CarnivoreRepository {
    private List<Carnivore> carnivores = new ArrayList<>();
    
    @Override
    public List<Carnivore> getCarnivores() {
        return Collections.unmodifiableList(carnivores);
    }
    
    @Override
    public void addCarnivore(Carnivore... animals) {
        carnivores.addAll(Arrays.asList(animals));
    }
    
    @Override
    public void removeCarnivore(Carnivore animal) {
        carnivores.remove(animal);
    }
}
