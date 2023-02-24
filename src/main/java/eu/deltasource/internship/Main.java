package eu.deltasource.internship;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepository;
import eu.deltasource.internship.repository.BiomeRepository.BiomeRepositoryImpl;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.repository.CarnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.repository.GroupRepository.GroupRepository;
import eu.deltasource.internship.repository.GroupRepository.GroupRepositoryImpl;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.repository.HerbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.service.AnimalService;
import eu.deltasource.internship.service.BiomeService;
import eu.deltasource.internship.service.EcoSystemService;
import eu.deltasource.internship.service.GroupService;
import eu.deltasource.internship.service.helper.NewBornCarnivoresCollection;
import eu.deltasource.internship.service.helper.NewBornHerbivoresCollection;
import eu.deltasource.internship.service.helper.ReproduceRateHelper;
import eu.deltasource.internship.service.helper.SuccessChanceCalculator;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        EcoSystemService ecoSystemService = ecoSystemServiceInitiation();
        System.out.println("Enter biome. You can choose savanna, swamp, plains, tundra, ocean or desert.");
        BiomeEnum biomeEnum = BiomeEnum.valueOf(scanner.nextLine().toUpperCase());
        ecoSystemService.simulateEcoSystem(biomeEnum);
        
        System.out.println(ecoSystemService.printAnimalsInfo());
    }
    
    private static EcoSystemService ecoSystemServiceInitiation() {
        CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
        HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
        GroupRepository groupRepository = new GroupRepositoryImpl();
        BiomeRepository biomeRepository = new BiomeRepositoryImpl();
        SuccessChanceCalculator successChanceCalculator = new SuccessChanceCalculator();
        AnimalService animalService = new AnimalService(herbivoreRepository, carnivoreRepository, groupRepository, successChanceCalculator);
        GroupService groupService = new GroupService(groupRepository, animalService);
        BiomeService biomeService = new BiomeService(animalService, groupService, biomeRepository);
        ReproduceRateHelper reproduceRateHelper = new ReproduceRateHelper(successChanceCalculator);
        NewBornCarnivoresCollection newBornCarnivoresCollection = new NewBornCarnivoresCollection(carnivoreRepository);
        NewBornHerbivoresCollection newBornHerbivoresCollection = new NewBornHerbivoresCollection(herbivoreRepository);
        return new EcoSystemService(biomeService, animalService, groupService, reproduceRateHelper, successChanceCalculator, newBornCarnivoresCollection, newBornHerbivoresCollection);
    }
}
