package com.example.bookingsite.Model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String name;

    String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "Images")
    List<String> images;

    String location;

    String contactNumber;
    @OneToMany(mappedBy = "placeId")
    List<Reservation> reservations;

    @ManyToOne
    Person owner;

    public Place(String name, String location, String description, String contactNumber, Person owner) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.contactNumber = contactNumber;
        this.owner = owner;

    }

    public Place() {

    }
}
