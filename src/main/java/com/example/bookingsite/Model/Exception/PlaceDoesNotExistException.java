package com.example.bookingsite.Model.Exception;

public class PlaceDoesNotExistException extends RuntimeException {

    public PlaceDoesNotExistException() {
        super("This place doesnt exist");
    }
}
