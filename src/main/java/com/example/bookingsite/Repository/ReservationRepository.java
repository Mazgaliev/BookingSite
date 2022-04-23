package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByPlaceIdAndStartGreaterThanEqualAndStartLessThanEqualAndRoomType
            (Place place, LocalDateTime start, LocalDateTime finish, RoomType roomType);

    List<Reservation> findByPlaceIdAndFinishGreaterThanEqualAndFinishLessThanEqualAndRoomType
            (Place place, LocalDateTime start, LocalDateTime finish, RoomType roomType);

    List<Reservation> findByPlaceIdAndStartLessThanEqualAndFinishGreaterThanEqualAndRoomType
            (Place place, LocalDateTime start, LocalDateTime finish, RoomType roomType);

    List<Reservation> findByPlaceIdAndStartGreaterThanEqualAndFinishLessThanEqualAndRoomType
            (Place place, LocalDateTime start, LocalDateTime finish, RoomType roomType);
}
