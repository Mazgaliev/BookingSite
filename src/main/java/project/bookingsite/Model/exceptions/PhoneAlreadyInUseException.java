package project.bookingsite.Model.exceptions;

public class PhoneAlreadyInUseException extends RuntimeException {
    public PhoneAlreadyInUseException() {
        super("This phone number is already in use");
    }
}
