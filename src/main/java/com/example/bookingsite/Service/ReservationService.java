package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationService {

    Optional<Reservation> createReservation(LocalDateTime start, LocalDateTime finish, Person person, Place place);

    Optional<Reservation> deleteReservation(Long id);

    Optional<Reservation> updateReservation(Long id, LocalDateTime start, LocalDateTime finish, Person person, Place place);
}
