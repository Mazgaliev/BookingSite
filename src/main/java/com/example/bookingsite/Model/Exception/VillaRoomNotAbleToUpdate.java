package com.example.bookingsite.Model.Exception;

public class VillaRoomNotAbleToUpdate extends RuntimeException {
    public VillaRoomNotAbleToUpdate() {
        super("Villa can't be updated");
    }
}
