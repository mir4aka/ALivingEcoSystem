package eu.deltasource.internship.HerbivoreRepository;

import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HerbivoreRepositoryImpl implements HerbivoreRepository {
    private List<Herbivore> herbivores = new ArrayList<>();
    
    @Override
    public List<Herbivore> getHerbivores() {
        return Collections.unmodifiableList(herbivores);
    }
    
    @Override
    public void addHerbivore(Herbivore... animals) {
        herbivores.addAll(Arrays.asList(animals));
    }
    
    @Override
    public void removeHerbivore(Herbivore animal) {
        herbivores.remove(animal);
    }
}
