package com.example.bookingsite.Model.Exception;

public class PlaceHasNoReservationsException extends RuntimeException {
    public PlaceHasNoReservationsException() {
        super("Place has no reviews");
    }
}
