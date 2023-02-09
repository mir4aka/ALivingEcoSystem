package eu.deltasource.internship.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private List<Animal> animals = new ArrayList<>();
    
    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }
    
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }
}
