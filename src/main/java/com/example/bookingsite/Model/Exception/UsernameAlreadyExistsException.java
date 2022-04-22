package com.example.bookingsite.Model.Exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException() {
        super("This username is already in use");
    }
}
