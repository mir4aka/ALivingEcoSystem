package eu.deltasource.internship;

import eu.deltasource.internship.service.EcoSystemService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        EcoSystemService ecoSystemService = new EcoSystemService();
        ecoSystemService.simulateEcoSystem();

        System.out.println(ecoSystemService.print());
    }
}
