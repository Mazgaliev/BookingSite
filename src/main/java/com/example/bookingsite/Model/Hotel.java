package com.example.bookingsite.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Hotel extends Place{

    Integer vipRooms;
    Integer standardRooms;
    Integer priceVipRoom;
    Integer priceStandardRoom;

    public Hotel(String name, String location, String contactNumber,
                 Person owner, Integer vipRooms, Integer standardRooms, Integer priceVipRoom,
                 Integer priceStandardRoom) {
        super(name, location, contactNumber, owner);
        this.vipRooms = vipRooms;
        this.standardRooms = standardRooms;
        this.priceVipRoom = priceVipRoom;
        this.priceStandardRoom = priceStandardRoom;
    }

    public Hotel() {

    }
}
