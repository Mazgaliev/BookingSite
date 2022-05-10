package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByPlaceIdAndStartGreaterThanEqualAndStartLessThanEqualAndRoomType
            (Place place, LocalDate start, LocalDate finish, RoomType roomType);

    List<Reservation> findByPlaceIdAndFinishGreaterThanEqualAndFinishLessThanEqualAndRoomType
            (Place place, LocalDate start, LocalDate finish, RoomType roomType);

    List<Reservation> findByPlaceIdAndStartLessThanEqualAndFinishGreaterThanEqualAndRoomType
            (Place place, LocalDate start, LocalDate finish, RoomType roomType);

    List<Reservation> findByPlaceIdAndStartGreaterThanEqualAndFinishLessThanEqualAndRoomType
            (Place place, LocalDate start, LocalDate finish, RoomType roomType);

    Page<Reservation> findByPlaceId(Place place, Pageable pageable);

    Reservation findByPersonIdAndPlaceId(Long personId, Long placeId);

    long countByPlaceId(Place place);
}
