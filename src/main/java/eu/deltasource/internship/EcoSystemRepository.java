package eu.deltasource.internship;

import eu.deltasource.internship.model.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EcoSystemRepository {
    private List<Animal> animals = new ArrayList<>();

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public void addAnimal(Animal animal) {

    }

}
