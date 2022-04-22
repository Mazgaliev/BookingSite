package com.example.bookingsite.Model.Exception;

public class HotelRoomNotAvaiable extends RuntimeException{
    public HotelRoomNotAvaiable() {
        super("Hotel rooms of are full");
    }
}
