package com.example.bookingsite.Model.Exception;

public class VillaRoomNotAbleToUpdate extends RuntimeException {
    public VillaRoomNotAbleToUpdate() {
        super("Hotel room can't be updated");
    }
}
