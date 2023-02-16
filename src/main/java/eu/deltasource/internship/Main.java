package eu.deltasource.internship;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.model.Biome;
import eu.deltasource.internship.service.EcoSystemService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        EcoSystemService ecoSystemService = new EcoSystemService();
        
        ecoSystemService.simulateEcoSystem(BiomeEnum.SAVANNA);
        ecoSystemService.simulateEcoSystem(BiomeEnum.OCEAN);
        ecoSystemService.simulateEcoSystem(BiomeEnum.TROPICAL_RAINFOREST);
        ecoSystemService.simulateEcoSystem(BiomeEnum.TUNDRA);
        ecoSystemService.simulateEcoSystem(BiomeEnum.SWAMP);
        ecoSystemService.simulateEcoSystem(BiomeEnum.PLAINS);

        System.out.println(ecoSystemService.printAnimalsInfo());
    }
}
