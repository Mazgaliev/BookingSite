package project.bookingsite.Model.exceptions;

public class ReservationDoesNotExistException extends RuntimeException {
    public ReservationDoesNotExistException() {
        super("This reservation does not exist");
    }
}
