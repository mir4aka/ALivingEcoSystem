package eu.deltasource.internship;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Herbivore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HerbivoreRepository {
    private List<Herbivore> herbivores;

    public HerbivoreRepository() {
        this.herbivores = new ArrayList<>();
    }

    public List<Herbivore> getHerbivores() {
        return herbivores;
    }

    public void addHerbivore(Herbivore... animals) {
        herbivores.addAll(Arrays.asList(animals));
    }

    public void removeHerbivore(Herbivore herbivore) {
        herbivores.remove(herbivore);
    }
}
