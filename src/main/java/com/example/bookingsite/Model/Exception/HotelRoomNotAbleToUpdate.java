package com.example.bookingsite.Model.Exception;

public class HotelRoomNotAbleToUpdate extends RuntimeException{
    public HotelRoomNotAbleToUpdate() {
        super("Hotel room can't be updated");
    }
}
