package com.example.bookingsite.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Villa extends Place {


    Integer pricePerNight;

    public Villa(String name, String location, String description, String contactNumber, Person owner, Integer pricePerNight) {
        super(name, location, description, contactNumber, owner);
        this.pricePerNight = pricePerNight;
    }

    public Villa() {

    }
}
