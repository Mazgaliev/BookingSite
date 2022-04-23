package com.example.bookingsite.Model.Exception;

public class StandardRoomNotAvaiable extends RuntimeException{
    public StandardRoomNotAvaiable() {
        super("Standard rooms are full at that time");
    }
}
