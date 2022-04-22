package com.example.bookingsite.Model;

import com.example.bookingsite.Model.Enum.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String name;

    String surname;

    String username;

    String password;

    String phoneNumber;
    @Enumerated(EnumType.STRING)
    Role userRole;

    @OneToMany
    List<Place> owns;

    @OneToMany(mappedBy = "personId")
    List<Reservation> reservations;

    public Person(String name, String surname, String username, String password, String phoneNumber, Role userRole) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.surname = surname;
    }

    public Person() {

    }
}
