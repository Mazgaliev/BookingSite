package project.bookingsite.Model.exceptions;

public class PlaceNameTakenException extends RuntimeException {
    public PlaceNameTakenException() {
        super("Place name is taken");
    }
}
