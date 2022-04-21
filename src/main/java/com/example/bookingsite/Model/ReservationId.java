package com.example.bookingsite.Model;

import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ReservationId implements Serializable {

    private Place placeId;

    private Person personId;

    public ReservationId(Place placeId, Person personId) {
        this.placeId = placeId;
        this.personId = personId;
    }
}
