package project.bookingsite.Model.exceptions;

public class PlaceDoesNotExistException extends RuntimeException {
    public PlaceDoesNotExistException() {
        super("Place does not exist");
    }
}
