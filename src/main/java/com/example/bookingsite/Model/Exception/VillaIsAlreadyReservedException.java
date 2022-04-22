package com.example.bookingsite.Model.Exception;

public class VillaIsAlreadyReservedException extends RuntimeException {

    public VillaIsAlreadyReservedException() {
        super("Villa is reserved in this date");
    }
}
