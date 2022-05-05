package com.example.bookingsite.Model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String description;

    Short rating;

    @ManyToOne
    Person person;

    @ManyToOne
    Place place;

    public Review(String description, Person person, Place place, Short rating) {
        this.description = description;
        this.person = person;
        this.place = place;
        if (rating > 5 || rating < 0)
            throw new IllegalArgumentException();
        this.rating = rating;
    }

    public Review() {

    }


}
