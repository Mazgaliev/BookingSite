package project.bookingsite.Service.implementation;

import org.springframework.stereotype.Service;
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

@Service
public class ReservationServiceImpl implements ReservationService {

    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(HotelRepository hotelRepository, ReservationRepository reservationRepository) {
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation makeReservation(LocalDateTime pocetok, LocalDateTime kraj, Long hotelId, RoomType type) {


        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(PlaceDoesNotExistException::new);
        Reservation reservation = null;
        Duration duration = Duration.between(pocetok, kraj);
        Integer days = Math.toIntExact(duration.toDays());
        Integer rooms;
        double price;

        if (type.equals(RoomType.VIP) && hotel.getVipRooms() >= 1) {
            price = days * hotel.getVipPrice();
            reservation = new Reservation(pocetok, kraj, hotel, type, price);
            rooms = hotel.getVipRooms();
            hotel.setVipRooms(rooms - 1);
        } else if (type.equals(RoomType.STANDARD) && hotel.getStandardRooms() >= 1) {
            price = days * hotel.getStandardPrice();
            reservation = new Reservation(pocetok, kraj, hotel, type, price);
            rooms = hotel.getStandardRooms();
            hotel.setStandardRooms(rooms - 1);
        }
        //TODO finish ako sobite se 0 da dade error deka nema slobodni vip ili standard sobi

        //dodaj rezervacija plus
        List<Reservation> reservations = hotel.getReservationList();
        reservations.add(reservation);
        hotel.setReservationList(reservations);
        hotelRepository.save(hotel);

        return reservation;
    }

    @Override
    public boolean cancelReservation(Long reservationId) {

        Reservation r = reservationRepository.findById(reservationId).orElseThrow(ReservationDoesNotExistException::new);
        reservationRepository.delete(r);
        return true;
    }
}
