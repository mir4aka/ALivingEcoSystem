package eu.deltasource.internship.service;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.enums.HabitatEnum;
import eu.deltasource.internship.enums.LivingType;
import eu.deltasource.internship.model.*;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepository;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepositoryImpl;

import java.util.Random;

public class BiomeService {
    private AnimalService animalService = new AnimalService();
    private BiomeRepository biomeRepository = new BiomeRepositoryImpl();
    private GroupService groupService = new GroupService();
    
    public void updateRepositoriesForSavanna() {
        Biome savanna = new Biome(HabitatEnum.LAND, BiomeEnum.SAVANNA);
        
        Animal miro = new Herbivore("miro", 85, 65, HabitatEnum.LAND, LivingType.GROUP, 11, 80, 60);
        Animal nici = new Herbivore("nici", 85, 68, HabitatEnum.LAND, LivingType.ALONE, 1, 90, 60);
        Animal irina = new Herbivore("irina", 85, 45, HabitatEnum.LAND, LivingType.ALONE, 1, 111, 60);
        Animal zebra = new Herbivore("zebra", 16, 45, HabitatEnum.LAND, LivingType.GROUP, 12, 121, 60);
        Animal hare = new Herbivore("hare", 9, 45, HabitatEnum.LAND, LivingType.ALONE, 1, 110, 60);
        Animal gazelle = new Herbivore("gazelle", 11, 45, HabitatEnum.LAND, LivingType.GROUP, 9, 121, 60);
        Animal buffalo = new Herbivore("byffallo", 18, 45, HabitatEnum.LAND, LivingType.GROUP, 28, 171, 60);
        animalService.addHerbivore(zebra, hare, gazelle, buffalo, miro, nici, irina);
        
        createGroup(miro);
        createGroup(zebra);
        createGroup(gazelle);
        createGroup(buffalo);
        
        Animal kot = new Carnivore("kot", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 1, 145, 14, 80);
        Animal ko4e = new Carnivore("ko4e", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 8, 135, 14, 80);
        Animal galab = new Carnivore("galab", 24, 50, HabitatEnum.LAND, LivingType.ALONE, 1, 132, 14, 80);
        Animal riba = new Carnivore("riba", 24, 50, HabitatEnum.LAND, LivingType.GROUP, 24, 287, 14, 80);
        Animal lub4o = new Carnivore("lub4o", 84, 60, HabitatEnum.LAND, LivingType.ALONE, 1, 214, 14, 80);
        Animal topG = new Carnivore("topG", 84, 70, HabitatEnum.LAND, LivingType.ALONE, 1, 217, 14, 80);
        Animal galinski = new Carnivore("galinski", 84, 77, HabitatEnum.LAND, LivingType.GROUP, 16, 310, 14, 80);
        animalService.addCarnivore(kot, ko4e, galab, riba, lub4o, topG, galinski);
        
        createGroup(ko4e);
        createGroup(riba);
        createGroup(galinski);
        
        biomeRepository.addBiomeAndAnimals(savanna, miro, nici, irina, zebra, hare, gazelle, buffalo, kot, ko4e, galab, riba, lub4o, topG, galinski);
    }
    
    public void updateRepositoriesForOcean() {
        Biome ocean = new Biome(HabitatEnum.WATER, BiomeEnum.OCEAN);
        
        Animal clownfish = new Herbivore("riba kloun", 8, 0.05, HabitatEnum.WATER, LivingType.ALONE, 1, 6, 60);
        Animal whale = new Herbivore("kit", 11, 40000, HabitatEnum.WATER, LivingType.ALONE, 1, 8, 60);
        Animal salmon = new Herbivore("siomga", 10, 0.01, HabitatEnum.WATER, LivingType.GROUP, 19, 15, 60);
        animalService.addHerbivore(clownfish, whale, salmon);
        
        createGroup(salmon);
        
        Animal shark = new Carnivore("akula", 44, 700, HabitatEnum.WATER, LivingType.ALONE, 1, 14, 14, 80);
        Animal swordfish = new Carnivore("ribame4", 24, 670, HabitatEnum.WATER, LivingType.ALONE, 1, 17, 14, 80);
        Animal barracuda = new Carnivore("barakuda", 44, 57, HabitatEnum.WATER, LivingType.GROUP, 16, 31, 14, 80);
        animalService.addCarnivore(shark, swordfish, barracuda);
        
        biomeRepository.addBiomeAndAnimals(ocean, clownfish, whale, salmon, shark, swordfish, barracuda);
        
        createGroup(barracuda);
    }
    
    public void updateRepositoriesForPlains() {
        Biome plains = new Biome(HabitatEnum.LAND, BiomeEnum.PLAINS);
        
        Animal impala = new Herbivore("impala", 12, 45, HabitatEnum.LAND, LivingType.ALONE, 1, 176, 60);
        Animal bison = new Herbivore("bizon", 11, 45, HabitatEnum.LAND, LivingType.GROUP, 19, 285, 60);
        Animal deer = new Herbivore("elen", 11, 45, HabitatEnum.LAND, LivingType.GROUP, 19, 285, 60);
        animalService.addHerbivore(impala, bison, deer);
        
        createGroup(bison);
        createGroup(deer);
        
        Animal leopard = new Carnivore("leopard", 84, 60, HabitatEnum.LAND, LivingType.ALONE, 1, 214, 14, 80);
        Animal cheetah = new Carnivore("chita", 84, 70, HabitatEnum.LAND, LivingType.ALONE, 1, 217, 14, 80);
        Animal wildDog = new Carnivore("div ku4", 84, 77, HabitatEnum.LAND, LivingType.GROUP, 16, 310, 14, 80);
        animalService.addCarnivore(leopard, cheetah, wildDog);
        
        createGroup(wildDog);
        
        biomeRepository.addBiomeAndAnimals(plains, impala, bison, deer, leopard, cheetah, wildDog);
    }
    
    public void updateRepositoriesForSwamp() {
        Biome swamp = new Biome(HabitatEnum.LAND, BiomeEnum.SWAMP);
        
        Animal moose = new Herbivore("los", 12, 45, HabitatEnum.LAND, LivingType.ALONE, 1, 176, 60);
        Animal beaver = new Herbivore("bobar", 11, 45, HabitatEnum.WATER, LivingType.GROUP, 19, 285, 60);
        Animal duck = new Herbivore("patka", 11, 45, HabitatEnum.WATER, LivingType.GROUP, 19, 285, 60);
        animalService.addHerbivore(moose, beaver, duck);
        
        createGroup(beaver);
        createGroup(duck);
        
        Animal trout = new Carnivore("pastarva", 84, 60, HabitatEnum.WATER, LivingType.ALONE, 1, 214, 14, 80);
        Animal weasel = new Carnivore("qzovec", 84, 70, HabitatEnum.LAND, LivingType.ALONE, 1, 217, 14, 80);
        Animal bullFrog = new Carnivore("bik riba", 84, 77, HabitatEnum.LAND, LivingType.GROUP, 16, 310, 14, 80);
        animalService.addCarnivore(trout, weasel, bullFrog);
        
        createGroup(bullFrog);
        
        biomeRepository.addBiomeAndAnimals(swamp, moose, beaver, duck, trout, weasel, bullFrog);
    }
    
    public void updateRepositoriesForTropicalForest() {
        Biome tropicalForest = new Biome(HabitatEnum.LAND, BiomeEnum.TROPICAL_RAINFOREST);
        
        Animal sloth = new Herbivore("lenivec", 12, 35, HabitatEnum.LAND, LivingType.ALONE, 1, 176, 60);
        Animal turtle = new Herbivore("kostanurka", 11, 15, HabitatEnum.LAND, LivingType.ALONE, 1, 285, 60);
        Animal elk = new Herbivore("mujki elen", 11, 85, HabitatEnum.LAND, LivingType.GROUP, 19, 285, 60);
        animalService.addHerbivore(sloth, turtle, elk);
        
        createGroup(elk);
        
        Animal harpyEagles = new Carnivore("pastarva", 84, 20, HabitatEnum.LAND, LivingType.ALONE, 1, 214, 14, 80);
        Animal jaguar = new Carnivore("qzovec", 84, 70, HabitatEnum.LAND, LivingType.ALONE, 1, 217, 14, 80);
        Animal piranha = new Carnivore("bik riba", 84, 6, HabitatEnum.LAND, LivingType.GROUP, 16, 310, 14, 80);
        animalService.addCarnivore(harpyEagles, jaguar, piranha);
        
        createGroup(piranha);
        
        biomeRepository.addBiomeAndAnimals(tropicalForest, sloth, turtle, elk, harpyEagles, jaguar, piranha);
    }
    
    public void updateRepositoriesForTundra() {
        Biome tundra = new Biome(HabitatEnum.LAND, BiomeEnum.TUNDRA);
        
        Animal squirrel = new Herbivore("katerica", 12, 4, HabitatEnum.LAND, LivingType.GROUP, 6, 176, 60);
        Animal vole = new Herbivore("mishka", 9, 2, HabitatEnum.LAND, LivingType.ALONE, 1, 285, 60);
        Animal lemming = new Herbivore("leming", 4, 11, HabitatEnum.LAND, LivingType.GROUP, 19, 285, 60);
        animalService.addHerbivore(squirrel, vole, lemming);
        
        createGroup(squirrel);
        createGroup(lemming);
        
        Animal arcticFox = new Carnivore("lisica", 19, 15, HabitatEnum.LAND, LivingType.ALONE, 1, 214, 14, 80);
        Animal snowyOwl = new Carnivore("buhal", 14, 9, HabitatEnum.LAND, LivingType.ALONE, 1, 217, 14, 80);
        Animal polarBear = new Carnivore("polqrna me4ka", 24, 120, HabitatEnum.LAND, LivingType.GROUP, 4, 310, 14, 80);
        animalService.addCarnivore(arcticFox, snowyOwl, polarBear);
        
        createGroup(polarBear);
        
        biomeRepository.addBiomeAndAnimals(tundra, squirrel, vole, lemming, arcticFox, snowyOwl, polarBear);
    }
    
    private void createGroup(Animal animal) {
        if (animal.getClass().getSimpleName().equals("Carnivore")) {
            Group group = new Group();
            group.addAnimal(animal);
            Carnivore carnivore = (Carnivore) animal;
            for (int i = 0; i < carnivore.getGroupAmount() - 1; i++) {
                double maxAge = carnivore.getMaxAge();
                double weight = new Random().nextDouble(0, carnivore.getWeight());
                int productionRate = new Random().nextInt(0, carnivore.getOriginalReproductionRate());
                int hungerRate = new Random().nextInt(1, 100);
                int attackPoints = new Random().nextInt(0, carnivore.getOriginalAttackPoints());
                int groupAmount = carnivore.getGroupAmount();
                
                Carnivore animalInGroup = new Carnivore(animal.getAnimalType(), maxAge, weight, animal.getMainHabitat(), animal.getLivingType(), groupAmount, productionRate, hungerRate, attackPoints);

                getAnimalService().addCarnivore(animalInGroup);
                group.addAnimal(animalInGroup);
            }
            getAnimalService().addGroupOfCarnivores(group);
        } else {
            Group group = new Group();
            group.addAnimal(animal);
            Herbivore herbivore = (Herbivore) animal;
            for (int i = 0; i < herbivore.getGroupAmount() - 1; i++) {
                double maxAge = herbivore.getOriginalMaxAge();
                double weight = new Random().nextDouble(0, herbivore.getOriginalWeight());
                int productionRate = new Random().nextInt(0, herbivore.getOriginalReproductionRate());
                int escapePoints = herbivore.getOriginalEscapePoints();
                int groupAmount = herbivore.getGroupAmount();
                
                Herbivore animalInGroup = new Herbivore(herbivore.getAnimalType(), maxAge, weight, herbivore.getMainHabitat(), herbivore.getLivingType(), groupAmount, productionRate, escapePoints);

                getAnimalService().addHerbivore(animalInGroup);
                group.addAnimal(animalInGroup);
            }
            getAnimalService().addGroupOfHerbivores(group);
        }
    }
    
    public AnimalService getAnimalService() {
        return animalService;
    }
    
    public GroupService getGroupService() {
        return groupService;
    }
}
