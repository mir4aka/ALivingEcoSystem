package eu.deltasource.internship.service;

import eu.deltasource.internship.AnimalRepository.AnimalRepository;
import eu.deltasource.internship.AnimalRepository.AnimalRepositoryImpl;
import eu.deltasource.internship.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.HerbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Herbivore;

import java.util.Collections;
import java.util.List;

public class AnimalService {
    private CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
    private HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
    private AnimalRepository animalRepository = new AnimalRepositoryImpl();
    
    public void addCarnivore(Carnivore... carnivores) {
        for (Carnivore carnivore : carnivores) {
            carnivoreRepository.addCarnivore(carnivore);
        }
    }
    
    public void removeCarnivore(Carnivore carnivore) {
        carnivoreRepository.removeCarnivore(carnivore);
    }
    
    public List<Carnivore> getCarnivores() {
        return Collections.unmodifiableList(carnivoreRepository.getCarnivores());
    }
    
    public void addHerbivore(Herbivore... herbivores) {
        for (Herbivore herbivore : herbivores) {
            herbivoreRepository.addHerbivore(herbivore);
        }
    }
    
    public void removeHerbivore(Herbivore herbivore) {
        herbivoreRepository.removeHerbivore(herbivore);
    }
    
    public List<Herbivore> getHerbivores() {
        return Collections.unmodifiableList(herbivoreRepository.getHerbivores());
    }
    
    public void addAnimal(Animal... animals) {
        for (Animal animal : animals) {
            animalRepository.addAnimal(animals);
        }
    }
    
    public void removeAnimal(Animal animal) {
        animalRepository.removeAnimal(animal);
    }
    
    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animalRepository.getAnimals());
    }
}
