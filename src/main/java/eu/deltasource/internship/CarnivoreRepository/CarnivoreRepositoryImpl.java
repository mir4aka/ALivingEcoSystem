package eu.deltasource.internship.CarnivoreRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CarnivoreRepositoryImpl implements CarnivoreRepository {
    private List<Animal> carnivores = new ArrayList<>();
    
    @Override
    public List<Animal> getCarnivores() {
        return Collections.unmodifiableList(carnivores);
    }
    
    @Override
    public void addCarnivore(Animal... animals) {
        carnivores.addAll(Arrays.asList(animals));
    }
    
    @Override
    public void removeCarnivore(Animal animal) {
        carnivores.remove(animal);
    }
}
