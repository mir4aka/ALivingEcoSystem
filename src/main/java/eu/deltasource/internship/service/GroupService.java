package eu.deltasource.internship.service;

import eu.deltasource.internship.model.Animal;
import eu.deltasource.internship.model.Herbivore;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.model.Carnivore;
import eu.deltasource.internship.model.Group;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GroupService {
    private GroupRepository groupRepository;
    private AnimalService animalService;
    
    public GroupService(GroupRepository groupRepository, AnimalService animalService) {
        this.groupRepository = groupRepository;
        this.animalService = animalService;
    }
    
    public List<Group> getCarnivoresGroup() {
        return Collections.unmodifiableList(groupRepository.getCarnivoresGroup());
    }
    
    public List<Group> getHerbivoresGroup() {
        return Collections.unmodifiableList(groupRepository.getHerbivoresGroup());
    }
    
    public void addCarnivoresGroupToRepository(Group group) {
        groupRepository.addGroupOfCarnivores(group);
    }
    
    public void addHerbivoresGroupToRepository(Group group) {
        groupRepository.addGroupOfHerbivores(group);
    }
    
    public Animal findCarnivoreInGroup(Animal carnivore) {
        List<Group> carnivoresGroup = groupRepository.getCarnivoresGroup();
        for (Group group : carnivoresGroup) {
            List<Animal> animals = group.getAnimals();
            for (Animal animal : animals) {
                if (animal.equals(carnivore)) {
                    return animal;
                }
            }
        }
        return null;
    }
    
    public void removeCarnivore(Animal carnivore) {
        List<Group> carnivoresGroup = groupRepository.getCarnivoresGroup();
        
        for (Group group : carnivoresGroup) {
            List<Animal> animals = group.getAnimals();
            for (Animal animal : animals) {
                if (animal.equals(carnivore)) {
                    group.removeAnimal(animal);
                    break;
                }
            }
        }
    }
    
    public void createGroupOfCarnivores(Carnivore carnivore) {
        Group group = new Group();
        group.addAnimal(carnivore);
        animalService.addCarnivoreToRepository(carnivore);
        for (int i = 0; i < carnivore.getGroupAmount() - 1; i++) {
            double maxAge = carnivore.getMaxAge();
            double weight = carnivore.getWeight();
            int productionRate = carnivore.getReproductionRate();
            int hungerRate = new Random().nextInt(1, 100);
            double attackPoints = new Random().nextDouble(0, carnivore.getPoints());
            int groupAmount = carnivore.getGroupAmount();
            
            Carnivore animalInGroup = new Carnivore(carnivore.getSpecie(), maxAge, weight, carnivore.getHabitat(), carnivore.getSocialStatus(), groupAmount, productionRate, hungerRate, attackPoints);
            
            group.addAnimal(animalInGroup);
            animalService.addCarnivoreToRepository(animalInGroup);
        }
        addCarnivoresGroupToRepository(group);
    }
    
    public void createGroupOfHerbivores(Herbivore herbivore) {
        Group group = new Group();
        group.addAnimal(herbivore);
        animalService.addHerbivoreToRepository(herbivore);
        for (int i = 0; i < herbivore.getGroupAmount() - 1; i++) {
            double maxAge = herbivore.getMaxAge();
            double weight = herbivore.getWeight();
            int productionRate = herbivore.getReproductionRate();
            double escapePoints = herbivore.getPoints();
            int groupAmount = herbivore.getGroupAmount();
            
            Herbivore animalInGroup = new Herbivore(herbivore.getSpecie(), maxAge, weight, herbivore.getHabitat(), herbivore.getSocialStatus(), groupAmount, productionRate, escapePoints);
            
            group.addAnimal(animalInGroup);
            animalService.addHerbivoreToRepository(animalInGroup);
        }
        addHerbivoresGroupToRepository(group);
    }
    
    public Group findCarnivoreGroup(Carnivore carnivore) {
        List<Group> carnivoresGroup = groupRepository.getCarnivoresGroup();
    
        for (Group group : carnivoresGroup) {
            if(group.getAnimals().contains(carnivore)) {
                return group;
            }
        }
        return null;
    }
}
