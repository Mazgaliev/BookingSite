package com.example.bookingsite.Model.Exception;

public class VipRoomNotAvaiable extends RuntimeException{
    public VipRoomNotAvaiable() {
        super("Vip rooms are full at that time");
    }
}
