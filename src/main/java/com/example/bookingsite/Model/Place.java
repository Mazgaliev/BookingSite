package com.example.bookingsite.Model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Place {
    //TODO implementiranje na lajk komentari rating
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String name;

    String description;

    Double rating;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "Images")
    List<String> images;

    String location;

    String contactNumber;
    @OneToMany(mappedBy = "placeId", fetch = FetchType.LAZY)
    List<Reservation> reservations;

    @ManyToOne
    Person owner;

    @OneToMany(mappedBy = "place")
    List<Review> reviews;

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
