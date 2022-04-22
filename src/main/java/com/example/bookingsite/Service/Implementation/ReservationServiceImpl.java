package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Override
    public Optional<Reservation> createReservation(LocalDateTime start, LocalDateTime finish, Person person, Place place) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> deleteReservation(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> updateReservation(Long id, LocalDateTime start, LocalDateTime finish, Person person, Place place) {
        return Optional.empty();
    }
}
