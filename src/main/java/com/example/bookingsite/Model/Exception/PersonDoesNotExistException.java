package com.example.bookingsite.Model.Exception;

public class PersonDoesNotExistException extends RuntimeException {
    public PersonDoesNotExistException() {
        super("This person does not exist");
    }
}
