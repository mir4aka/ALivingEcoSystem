package eu.deltasource.internship.exception;

public class NoSuchBiomeException extends RuntimeException{
    public NoSuchBiomeException() {
        super("Biome does not exist.");
    }
}
