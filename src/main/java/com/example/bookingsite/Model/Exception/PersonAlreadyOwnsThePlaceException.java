package com.example.bookingsite.Model.Exception;

public class PersonAlreadyOwnsThePlaceException extends RuntimeException {

    public PersonAlreadyOwnsThePlaceException() {
        super("Place is already owned by this person");
    }
}
