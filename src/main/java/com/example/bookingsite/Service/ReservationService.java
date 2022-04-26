package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationService {

    Optional<Reservation> createHotelReservation(LocalDate start, LocalDate finish, Long personId, Long hotelId, RoomType roomType);

    Optional<Reservation> createVillaReservation(LocalDate start, LocalDate finish, Long personId, Long villaId);

    Optional<Reservation> updateHotelReservation(Long Id, LocalDate start, LocalDate finish, RoomType roomType);

    Optional<Reservation> updateVillaReservation(Long Id, LocalDate start, LocalDate finish);

    Optional<Reservation> deleteReservation(Long Id);

    Page<Reservation> findReservationPage(Long placeId, Pageable pageable);

    long countPlaceReservations(Long placeId);

    Reservation findById(Long id);
}
