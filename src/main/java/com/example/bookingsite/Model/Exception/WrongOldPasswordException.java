package com.example.bookingsite.Model.Exception;

public class WrongOldPasswordException extends RuntimeException {
    public WrongOldPasswordException() {
        super("Wrong old password");
    }
}
