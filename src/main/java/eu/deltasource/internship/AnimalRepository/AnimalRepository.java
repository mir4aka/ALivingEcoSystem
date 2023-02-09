package eu.deltasource.internship.AnimalRepository;

import eu.deltasource.internship.model.Animal;

import java.util.List;

public interface AnimalRepository {
    
    List<Animal> getAnimals();
    
    void addAnimal(Animal... animal);
    
    void removeAnimal(Animal animal);
}
