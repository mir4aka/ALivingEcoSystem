package eu.deltasource.internship;

import eu.deltasource.internship.model.Animal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnimalRepository {
    private List<Animal> animalsList = new ArrayList<>();

    public List<Animal> getAnimals() {
        return animalsList;
    }

    public void addAnimal(Animal... animals) {
        animalsList.addAll(Arrays.asList(animals));
    }
}
