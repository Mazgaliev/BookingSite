package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationService {

    Optional<Reservation> createHotelReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long hotelId, RoomType roomType);

    Optional<Reservation> createVillaReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long villaId);

    Optional<Reservation> updateHotelReservation(Long Id, LocalDateTime start, LocalDateTime finish, Long personId, Long hotelId, RoomType roomType);

    Optional<Reservation> updateVillaReservation(Long Id, LocalDateTime start, LocalDateTime finish, Long personId, Long villaId);

    Optional<Reservation> deleteReservation(Long Id);
}
