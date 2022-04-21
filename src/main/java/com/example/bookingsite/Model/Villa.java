package com.example.bookingsite.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
public class Villa extends Place {

    Boolean reserved;

    Integer pricePerNight;

    public Villa(String name, String location, String description, String contactNumber, Person owner, Integer pricePerNight) {
        super(name, location, description, contactNumber, owner);
        this.pricePerNight = pricePerNight;
        this.reserved = false;
    }

    public Villa() {

    }
}
