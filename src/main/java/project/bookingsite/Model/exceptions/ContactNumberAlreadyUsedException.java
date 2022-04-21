package project.bookingsite.Model.exceptions;

public class ContactNumberAlreadyUsedException extends RuntimeException {
    public ContactNumberAlreadyUsedException() {
        super("Contact number is used another business");
    }
}
