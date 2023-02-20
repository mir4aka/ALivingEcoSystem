package eu.deltasource.internship.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.*;

import java.util.List;
import java.util.Random;

public class BiomeService {
    private AnimalService animalService = new AnimalService();
    private GroupService groupService = new GroupService();
    
    public String updateAnimalsRepositories(BiomeEnum ecoSystemBiome, Gson gson, JsonObject JSONObject, List<Carnivore> carnivores, List<Herbivore> herbivores) {
        String biome = String.valueOf(ecoSystemBiome);
        
        if (JSONObject.has(biome)) {
            JsonElement jsonElements = JSONObject.get(biome);
            
            JsonArray asJsonArray = jsonElements.getAsJsonArray();
            JsonElement membersInBiome = asJsonArray.iterator().next();
            JsonObject asJsonObject = membersInBiome.getAsJsonObject();
            
            JsonArray carnivoresInJson = asJsonObject.get("Carnivores").getAsJsonArray();
            JsonArray herbivoresInJson = asJsonObject.get("Herbivores").getAsJsonArray();
            
            for (JsonElement jsonElement : carnivoresInJson) {
                Carnivore carnivore = gson.fromJson(jsonElement.toString(), Carnivore.class);
                if(carnivore.getSocialStatus().equals(SocialStatus.GROUP)) {
                    createGroup(carnivore);
                }
                animalService.addCarnivore(carnivore);
            }
            
            for (JsonElement jsonElement : herbivoresInJson) {
                Herbivore herbivore = gson.fromJson(jsonElement.toString(), Herbivore.class);
                if(herbivore.getSocialStatus().equals(SocialStatus.GROUP)) {
                    createGroup(herbivore);
                }
                animalService.addHerbivore(herbivore);
            }
        } else {
            System.out.println("No such biome in this ecosystem.");
        }
        return biome;
    }
    
    private void createGroup(Animal animal) {
        if (animal.getClass().getSimpleName().equals("Carnivore")) {
            Group group = new Group();
            groupService.addAnimal(animal);
            for (int i = 0; i < animal.getGroupAmount(); i++) {
                int maxAge = animal.getMaxAge();
                double weight = new Random().nextDouble(0, animal.getWeight());
                int productionRate = new Random().nextInt(0, animal.getReproductionRate());
                int hungerRate = new Random().nextInt(1, 100);
                int attackPoints = new Random().nextInt(0, animal.getAttackPoints());
                int groupAmount = animal.getGroupAmount();
                
                Carnivore animalInGroup = new Carnivore(animal.getSpecie(), maxAge, weight, animal.getHabitat(), animal.getSocialStatus(), groupAmount, productionRate, hungerRate, attackPoints);
                
                animalService.addCarnivore(animalInGroup);
                groupService.addAnimal(animalInGroup);
            }
            animalService.addGroupOfCarnivores(group);
        } else {
            Group group = new Group();
            groupService.addAnimal(animal);
            for (int i = 0; i < animal.getGroupAmount(); i++) {
                int maxAge = animal.getMaxAge();
                double weight = new Random().nextDouble(0, animal.getWeight());
                int productionRate = new Random().nextInt(0, animal.getReproductionRate());
                //TODO FIGURE OUT HOW TO ACCESS THE ESCAPE POINTS OF THE ANIMAL
                int escapePoints = getEscapePoints(animal);
                int groupAmount = animal.getGroupAmount();
                
                Herbivore animalInGroup = new Herbivore(animal.getSpecie(), maxAge, weight, animal.getHabitat(), animal.getSocialStatus(), groupAmount, productionRate, escapePoints);
                
                animalService.addHerbivore(animalInGroup);
                groupService.addAnimal(animalInGroup);
            }
            animalService.addGroupOfHerbivores(group);
        }
    }
    
    public AnimalService getAnimalService() {
        return animalService;
    }
    
    public GroupService getGroupService() {
        return groupService;
    }
}
