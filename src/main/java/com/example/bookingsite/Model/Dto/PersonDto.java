package com.example.bookingsite.Model.Dto;

import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;
import lombok.Data;

import javax.management.relation.Role;
import java.util.List;

@Data
public class PersonDto {

    private Long id;

    private String name;

    private String surname;

    private String username;

    private String phoneNumber;

    private Role role;

    private List<Place> owns;

    private List<Reservation> reservations;

    public PersonDto(Long id, String name, String username, String phoneNumber, Role role, List<Place> owns, List<Reservation> reservations) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.owns = owns;
        this.reservations = reservations;
    }

    public PersonDto() {
    }
}
