package com.example.bookingsite.Model.CompositeKey;

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

    private Long id;

    private Place placeId;

    private Person personId;

    public ReservationId(Long id, Place placeId, Person personId) {
        this.id=id;
        this.placeId = placeId;
        this.personId = personId;
    }
}
