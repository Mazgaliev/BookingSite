package com.example.bookingsite.Model.Dto;

import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;
import lombok.Data;

import java.util.List;

@Data
public class PersonDto {

    private String name;

    private String surname;

    private String username;

    private String phoneNumber;

    private List<Place> owns;

    private List<Reservation> reservations;

    public PersonDto(String name, String username, String phoneNumber, List<Place> owns, List<Reservation> reservations) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.owns = owns;
        this.reservations = reservations;
    }
}
