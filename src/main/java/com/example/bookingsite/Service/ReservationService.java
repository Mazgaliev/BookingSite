package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationService {

    Optional<Reservation> createHotelReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long hotelId);

    Optional<Reservation> createVillaReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long villaId);

    Optional<Reservation> updateHotelReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long hotelId);

    Optional<Reservation> updateVillaReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long villaId);

    Optional<Reservation> deleteReservation(Long personId, Long placeId);
}
