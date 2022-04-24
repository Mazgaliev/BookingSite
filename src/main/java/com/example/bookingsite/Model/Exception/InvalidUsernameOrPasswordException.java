package com.example.bookingsite.Model.Exception;

public class InvalidUsernameOrPasswordException extends RuntimeException {
    public InvalidUsernameOrPasswordException() {
        super("Invalid password or username");
    }
}
