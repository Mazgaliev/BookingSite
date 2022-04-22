package com.example.bookingsite.Model.Exception;

public class PlaceAlreadyExistsException extends RuntimeException {
    public PlaceAlreadyExistsException() {

        super("This place already exists");

    }
}
