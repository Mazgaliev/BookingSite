package com.example.bookingsite.Model.Exception;

public class ReservationDoesNotExist extends RuntimeException {

    public ReservationDoesNotExist() {
        super("This reservation does not exist");
    }
}
