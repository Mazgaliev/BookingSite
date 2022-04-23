package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Exception.*;
import com.example.bookingsite.Model.*;
import com.example.bookingsite.Repository.*;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final PersonRepository personRepository;
    private final HotelRepository hotelRepository;
    private final VillaRepository villaRepository;


    public ReservationServiceImpl(ReservationRepository reservationRepository, PersonRepository personRepository, HotelRepository hotelRepository, VillaRepository villaRepository) {
        this.reservationRepository = reservationRepository;
        this.personRepository = personRepository;
        this.hotelRepository = hotelRepository;
        this.villaRepository = villaRepository;
    }


    @Override
    @Transactional
    public Optional<Reservation> createHotelReservation(LocalDateTime start, LocalDateTime finish,
                                                        Long personId, Long hotelId, RoomType roomType) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);

        int numberOfRooms = calculateNumberOdRooms(hotel, start, finish, roomType);

        Optional<Reservation> reservation = getReservation(start, finish, roomType, hotel, person, numberOfRooms);
        if (reservation != null && reservation.isPresent()) return reservation;

        if(roomType == RoomType.STANDARD) {
            throw new StandardRoomNotAvaiable();
        }
        else {
            throw new VipRoomNotAvaiable();
        }
    }

    @Override
    @Transactional
    public Optional<Reservation> createVillaReservation(LocalDateTime start, LocalDateTime finish,
                                                        Long personId, Long villaId) {
        Villa villa = this.villaRepository.findById(villaId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        List<Reservation> reservationList = villa.getReservations();

        for (Reservation reserv : reservationList) {
            if ((start.isAfter(reserv.getStart()) || start.equals(reserv.getStart())) ||
                    (finish.isBefore(reserv.getFinish()) || finish.equals(reserv.getFinish()))) {
                throw new VillaIsAlreadyReservedException();
            }
        }
        long days = ChronoUnit.DAYS.between(start, finish);

        Reservation reservation = new Reservation(start, finish, person, villa);

        reservation.setPrice((int) (villa.getPricePerNight() * days));

        this.reservationRepository.save(reservation);

        return Optional.of(reservation);
    }

    @Override
    @Transactional
    public Optional<Reservation> updateHotelReservation(Long Id, LocalDateTime start, LocalDateTime finish,
                                                        Long personId, Long hotelId, RoomType roomType) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);


        Reservation reservation_old = this.reservationRepository.findById(Id).orElseThrow(ReservationDoesNotExist::new);
        reservationRepository.delete(reservation_old);

        int numberOfRooms = calculateNumberOdRooms(hotel, start, finish, roomType);

        Optional<Reservation> reservation = getReservation(start, finish, roomType, hotel, person, numberOfRooms);
        if (reservation != null && reservation.isPresent()) return reservation;

        reservationRepository.save(reservation_old);
        if(roomType == RoomType.STANDARD) {
            throw new StandardRoomNotAvaiable();
        }
        else {
            throw new VipRoomNotAvaiable();
        }
    }


    @Override
    @Transactional
    public Optional<Reservation> updateVillaReservation(Long Id, LocalDateTime start, LocalDateTime finish,
                                                        Long personId, Long villaId) {
        Villa villa = this.villaRepository.findById(villaId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);


        Reservation reservation_old = this.reservationRepository.findById(Id).orElseThrow(ReservationDoesNotExist::new);
        reservationRepository.delete(reservation_old);

        List<Reservation> reservationList = villa.getReservations();
        for (Reservation reserv : reservationList) {
            if ((start.isAfter(reserv.getStart()) || start.equals(reserv.getStart())) ||
                    (finish.isBefore(reserv.getFinish()) || finish.equals(reserv.getFinish()))) {
                reservationRepository.save(reservation_old);
                throw new VillaRoomNotAbleToUpdate();
            }
        }
        long days = ChronoUnit.DAYS.between(start, finish);

        Reservation reservation = new Reservation(start, finish, person, villa);
        reservation.setPrice((int) (villa.getPricePerNight() * days));

        this.reservationRepository.save(reservation);
        return Optional.of(reservation);
    }

    @Override
    public Optional<Reservation> deleteReservation(Long id) {
        Reservation reservation = this.reservationRepository.findById(id).orElseThrow(ReservationDoesNotExist::new);
        reservationRepository.delete(reservation);
        return Optional.of(reservation);
    }

    Integer calculateNumberOdRooms(Hotel hotel, LocalDateTime start, LocalDateTime finish, RoomType roomType) {
        List<Reservation> reservations_1 = reservationRepository
                .findByPlaceIdAndStartGreaterThanEqualAndStartLessThanEqualAndRoomType(hotel, start, finish, roomType);

        List<Reservation> reservations_2 = reservationRepository
                .findByPlaceIdAndFinishGreaterThanEqualAndFinishLessThanEqualAndRoomType(hotel, start, finish, roomType);

        List<Reservation> reservations_3 = reservationRepository
                .findByPlaceIdAndStartLessThanEqualAndFinishGreaterThanEqualAndRoomType(hotel, start, finish, roomType);

        List<Reservation> reservations_4 = reservationRepository
                .findByPlaceIdAndStartGreaterThanEqualAndFinishLessThanEqualAndRoomType(hotel, start, finish, roomType);

        return reservations_1.size() + reservations_2.size() + reservations_3.size() - reservations_4.size();
    }

    private Optional<Reservation> getReservation(LocalDateTime start, LocalDateTime finish, RoomType roomType,
                                                 Hotel hotel, Person person, int numberOfRooms) {
        long days = ChronoUnit.DAYS.between(start, finish);
        if (roomType == RoomType.STANDARD && hotel.getStandardRooms() > numberOfRooms) {
            Reservation reservation = new Reservation(start, finish, person, hotel);
            int price = hotel.getPriceStandardRoom();
            reservation.setRoomType(roomType);
            reservation.setPrice((int) (price * days));
            this.reservationRepository.save(reservation);
            return Optional.of(reservation);
        }
        if (roomType == RoomType.VIP && hotel.getVipRooms() > numberOfRooms) {
            Reservation reservation = new Reservation(start, finish, person, hotel);
            int price = hotel.getPriceVipRoom();
            reservation.setRoomType(roomType);
            reservation.setPrice((int) (price * days));
            this.reservationRepository.save(reservation);
            return Optional.of(reservation);
        }
        return Optional.empty();
    }
}
