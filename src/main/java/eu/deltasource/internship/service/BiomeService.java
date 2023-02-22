package eu.deltasource.internship.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.model.*;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepository;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepositoryImpl;

import java.util.List;
import java.util.Random;

public class BiomeService {
    private AnimalService animalService = new AnimalService();
    private GroupService groupService = new GroupService();
    private BiomeRepository biomeRepository = new BiomeRepositoryImpl();
    
    public String updateAnimalsRepositories(BiomeEnum ecoSystemBiome, Gson gson, JsonObject JSONObject) {
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
                    List<Carnivore> groupOfCarnivores = groupService.createGroupOfCarnivores(carnivore);
                    for (Carnivore animal : groupOfCarnivores) {
                        animalService.addCarnivore(animal);
                    }
                    break;
                }
                animalService.addCarnivore(carnivore);
            }
            
            for (JsonElement jsonElement : herbivoresInJson) {
                Herbivore herbivore = gson.fromJson(jsonElement.toString(), Herbivore.class);
                if(herbivore.getSocialStatus().equals(SocialStatus.GROUP)) {
                    List<Herbivore> groupOfHerbivores = groupService.createGroupOfHerbivores(herbivore);
                    for (Herbivore animal : groupOfHerbivores) {
                        animalService.addHerbivore(animal);
                    }
                    break;
                }
                animalService.addHerbivore(herbivore);
            }
        } else {
            System.out.println("No such biome in this ecosystem.");
        }
        return biome;
    }
    
    public AnimalService getAnimalService() {
        return animalService;
    }
    
    public GroupService getGroupService() {
        return groupService;
    }
    
    public BiomeEnum getBiome(Biome biome) {
        return biomeRepository.findBiome(biome);
    }
}
