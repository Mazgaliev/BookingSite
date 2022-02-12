package project.bookingsite.Service.implementation;

import project.bookingsite.Model.Hotel;
import project.bookingsite.Model.Reservation;
import project.bookingsite.Model.RoomType;
import project.bookingsite.Service.ReservationService;

import java.time.LocalDateTime;

public class ReservationServiceImpl implements ReservationService {
    @Override
    public Reservation makeReservation(LocalDateTime pocetok, LocalDateTime kraj, Hotel hotel, RoomType type) {
        //TODO proverka za slobodni mesta vo hotelot proverka za dali ima slobodno mesto vo taa data vo toj hotel.
        return null;
    }

    @Override
    public boolean cancelReservation() {
        //TODO kje mozhe da se prave cancel na rezervacija mozebi lol
        return false;
    }
}
