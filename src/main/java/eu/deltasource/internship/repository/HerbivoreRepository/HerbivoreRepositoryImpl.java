package eu.deltasource.internship.repository.HerbivoreRepository;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HerbivoreRepositoryImpl implements HerbivoreRepository {
    private List<Animal> herbivores = new ArrayList<>();
    
    @Override
    public List<Animal> getHerbivores() {
        return Collections.unmodifiableList(herbivores);
    }
    
    @Override
    public void addHerbivore(Animal... animals) {
        herbivores.addAll(Arrays.asList(animals));
    }
    
    @Override
    public void removeHerbivore(Animal animal) {
        herbivores.remove(animal);
    }
}
