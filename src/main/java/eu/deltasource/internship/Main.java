package eu.deltasource.internship;

import eu.deltasource.internship.enums.BiomeEnum;
import eu.deltasource.internship.service.EcoSystemService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        EcoSystemService ecoSystemService = new EcoSystemService();
    
        System.out.println("Enter biome. You can choose savanna, swamp, plains, tundra or ocean");
    
        //TODO IMPLEMENT A WHILE LOOP UNTIL A VALID BIOME IS ENTERED
        BiomeEnum biomeEnum = BiomeEnum.valueOf(scanner.nextLine().toUpperCase());

        ecoSystemService.simulateEcoSystem(biomeEnum);

        System.out.println(ecoSystemService.printAnimalsInfo());
    }
}
