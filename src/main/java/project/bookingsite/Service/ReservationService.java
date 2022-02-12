package project.bookingsite.Service;

import project.bookingsite.Model.Hotel;
import project.bookingsite.Model.Reservation;
import project.bookingsite.Model.RoomType;

import java.time.LocalDateTime;

public interface ReservationService {

    public Reservation makeReservation(LocalDateTime pocetok, LocalDateTime kraj, Long hotelId, RoomType type);

    public boolean cancelReservation(Long reservationId);


}
