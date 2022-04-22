package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.CompositeKey.ReservationId;
import com.example.bookingsite.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {

}
