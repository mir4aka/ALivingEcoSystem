package eu.deltasource.internship.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.SocialStatus;
import eu.deltasource.internship.exception.NoSuchBiomeException;
import eu.deltasource.internship.model.*;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepository;

public class BiomeService {
    private AnimalService animalService;
    private GroupService groupService;
    private BiomeRepository biomeRepository;
    
    public BiomeService(AnimalService animalService, GroupService groupService, BiomeRepository biomeRepository) {
        this.animalService = animalService;
        this.groupService = groupService;
        this.biomeRepository = biomeRepository;
    }
    
    /**
     * Adds each animal from the json file to the repositories.
     * */
    public String updateAnimalsRepositories(BiomeEnum ecoSystemBiome, Gson gson, JsonObject JSONObject) {
        String biome = String.valueOf(ecoSystemBiome);
        if (JSONObject.has(biome)) {
            JsonElement jsonElements = JSONObject.get(biome);
            JsonArray asJsonArray = jsonElements.getAsJsonArray();
            JsonElement membersInBiome = asJsonArray.iterator().next();
            JsonObject asJsonObject = membersInBiome.getAsJsonObject();
            JsonArray carnivoresInJson = asJsonObject.get("Carnivores").getAsJsonArray();
            JsonArray herbivoresInJson = asJsonObject.get("Herbivores").getAsJsonArray();
            
            addCarnivoresToRepository(gson, carnivoresInJson);
            addHerbivoresToRepository(gson, herbivoresInJson);
        } else {
            throw new NoSuchBiomeException();
        }
        return biome;
    }
    
    public BiomeEnum getBiome(Biome biome) {
        return biomeRepository.findBiome(biome);
    }
    
    private void addCarnivoresToRepository(Gson gson, JsonArray carnivoresInJson) {
        for (JsonElement jsonElement : carnivoresInJson) {
            Carnivore carnivore = gson.fromJson(jsonElement.toString(), Carnivore.class);
            if (carnivore.getSocialStatus().equals(SocialStatus.GROUP)) {
                groupService.createGroupOfCarnivores(carnivore);
            } else {
                animalService.addCarnivoreToRepository(carnivore);
            }
        }
    }
    
    private void addHerbivoresToRepository(Gson gson, JsonArray herbivoresInJson) {
        for (JsonElement jsonElement : herbivoresInJson) {
            Herbivore herbivore = gson.fromJson(jsonElement.toString(), Herbivore.class);
            if (herbivore.getSocialStatus().equals(SocialStatus.GROUP)) {
                groupService.createGroupOfHerbivores(herbivore);
            } else {
                animalService.addHerbivoreToRepository(herbivore);
            }
        }
    }
    
}
