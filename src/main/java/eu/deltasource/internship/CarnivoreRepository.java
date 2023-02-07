package eu.deltasource.internship;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CarnivoreRepository {
    private List<Carnivore> carnivores;

    public CarnivoreRepository() {
        this.carnivores = new ArrayList<>();
    }

    public List<Carnivore> getCarnivores() {
        return Collections.unmodifiableList(carnivores);
    }

    public void addCarnivore(Carnivore... animals) {
        carnivores.addAll(Arrays.asList(animals));
    }
}
