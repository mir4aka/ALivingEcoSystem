package eu.deltasource.internship;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EcoSystemService ecoSystemService = new EcoSystemService();
        ecoSystemService.simulateIteration();

        System.out.println(ecoSystemService.print());
    }
}
