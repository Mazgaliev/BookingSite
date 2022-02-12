package project.bookingsite.Service.implementation;

import project.bookingsite.Model.Hotel;
import project.bookingsite.Model.Reservation;
import project.bookingsite.Model.RoomType;
import project.bookingsite.Model.exceptions.PlaceDoesNotExistException;
import project.bookingsite.Model.exceptions.ReservationDoesNotExistException;
import project.bookingsite.Repository.HotelRepository;
import project.bookingsite.Repository.ReservationRepository;
import project.bookingsite.Service.ReservationService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(HotelRepository hotelRepository, ReservationRepository reservationRepository) {
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation makeReservation(LocalDateTime pocetok, LocalDateTime kraj, Long hotelId, RoomType type) {
        //TODO proverka za slobodni mesta vo hotelot proverka za dali ima slobodno mesto vo taa data vo toj hotel.

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(PlaceDoesNotExistException::new);
        Reservation reservation = null;
        Duration duration = Duration.between(pocetok, kraj);
        Integer days = Math.toIntExact(duration.toDays());
        double price;

        if (type.equals(RoomType.VIP) && hotel.getVipRooms() >= 1) {
            price = days * hotel.getVipPrice();
            reservation = new Reservation(pocetok, kraj, hotel, type, price);
        } else if (type.equals(RoomType.STANDARD) && hotel.getStandardRooms() >= 1) {
            price = days * hotel.getStandardPrice();
            reservation = new Reservation(pocetok, kraj, hotel, type, price);
        }
        return reservation;
    }

    @Override
    public boolean cancelReservation(Long reservationId) {
        //TODO kje mozhe da se prave cancel na rezervacija mozebi lol
        Reservation r = reservationRepository.findById(reservationId).orElseThrow(ReservationDoesNotExistException::new);
        reservationRepository.delete(r);
        return true;
    }
}
