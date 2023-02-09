package eu.deltasource.internship.AnimalRepository;

import eu.deltasource.internship.model.Animal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnimalRepositoryImpl implements AnimalRepository {
    private List<Animal> animalsList = new ArrayList<>();
    
    @Override
    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animalsList);
    }
    
    @Override
    public void addAnimal(Animal... animals) {
        animalsList.addAll(Arrays.asList(animals));
    }
    
    @Override
    public void removeAnimal(Animal animal) {
        animalsList.remove(animal);
    }
}
