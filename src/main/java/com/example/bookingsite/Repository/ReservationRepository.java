package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.CompositeKey.ReservationId;
import com.example.bookingsite.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
}
