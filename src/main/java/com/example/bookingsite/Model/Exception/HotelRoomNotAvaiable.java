package com.example.bookingsite.Model.Exception;

public class HotelRoomNotAvaiable extends RuntimeException {
    public HotelRoomNotAvaiable() {
        super("Current selection of rooms are full for these dates");
    }
}
