package com.example.bookingsite.Model;

import com.example.bookingsite.Model.CompositeKey.ReservationId;
import com.example.bookingsite.Model.Enum.RoomType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@IdClass(ReservationId.class)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Id
    @ManyToOne
    private Place placeId;

    @Id
    @ManyToOne
    private Person personId;

    private LocalDateTime start;

    private LocalDateTime finish;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    public Reservation(LocalDateTime start, LocalDateTime finish, Person person, Place place) {
        this.placeId = place;
        this.personId = person;
        this.start = start;
        this.finish = finish;
    }

    public Reservation() {

    }
}
