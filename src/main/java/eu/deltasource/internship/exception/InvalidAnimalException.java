package eu.deltasource.internship.exception;

public class InvalidAnimalException extends RuntimeException {
    public InvalidAnimalException() {
        super("Carnivore or herbivore cannot be null.");
    }
}
