package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.*;
import com.example.bookingsite.Model.CompositeKey.ReservationId;
import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Exception.*;
import com.example.bookingsite.Repository.*;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final PersonRepository personRepository;
    private final HotelRepository hotelRepository;
    private final VillaRepository villaRepository;

    private final PlaceRepository placeRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, PersonRepository personRepository, HotelRepository hotelRepository, VillaRepository villaRepository, PlaceRepository placeRepository) {
        this.reservationRepository = reservationRepository;
        this.personRepository = personRepository;
        this.hotelRepository = hotelRepository;
        this.villaRepository = villaRepository;
        this.placeRepository = placeRepository;
    }


    @Override
    @Transactional
    public Optional<Reservation> createHotelReservation(LocalDateTime start, LocalDateTime finish,
                                                        Long personId, Long hotelId, RoomType roomType) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);

        int numberOfRooms = calculateNumberOdRooms(hotel,start,finish,roomType);

        if (roomType == RoomType.STANDARD && hotel.getStandardRooms()>numberOfRooms) {
            Reservation reservation = new Reservation(start, finish, person, hotel);
            int price = hotel.getPriceStandardRoom();
            reservation.setRoomType(roomType);
            reservation.setPrice(price);
            return Optional.of(reservation);
        }
        if (roomType == RoomType.VIP && hotel.getVipRooms()>numberOfRooms) {
            Reservation reservation = new Reservation(start, finish, person, hotel);
            int price = hotel.getPriceVipRoom();
            reservation.setRoomType(roomType);
            reservation.setPrice(price);
            return Optional.of(reservation);
        }
        throw new HotelRoomNotAvaiable();
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
        Reservation reservation = new Reservation(start, finish, person, villa);
        reservationList.add(reservation);
        villa.setReservations(reservationList);
        this.reservationRepository.save(reservation);
        this.villaRepository.save(villa);
        return Optional.of(reservation);
    }

    @Override
    @Transactional
    public Optional<Reservation> updateHotelReservation(Long Id, LocalDateTime start, LocalDateTime finish,
                                                        Long personId, Long hotelId, RoomType roomType) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);

        ReservationId reservationId = new ReservationId(Id, hotel, person);
        Reservation reservation_old = this.reservationRepository.findById(reservationId).orElseThrow(ReservationDoesNotExist::new);
        reservationRepository.delete(reservation_old);

        int numberOfRooms = calculateNumberOdRooms(hotel,start,finish,roomType);

        if (roomType == RoomType.STANDARD && hotel.getStandardRooms()>numberOfRooms) {
            Reservation reservation = new Reservation(start, finish, person, hotel);
            int price = hotel.getPriceStandardRoom();
            reservation.setRoomType(roomType);
            reservation.setPrice(price);
            return Optional.of(reservation);
        }
        if (roomType == RoomType.VIP && hotel.getVipRooms()>numberOfRooms) {
            Reservation reservation = new Reservation(start, finish, person, hotel);
            int price = hotel.getPriceVipRoom();
            reservation.setRoomType(roomType);
            reservation.setPrice(price);
            return Optional.of(reservation);
        }

        reservationRepository.save(reservation_old);
        throw new HotelRoomNotAvaiable();
    }


    @Override
    @Transactional
    public Optional<Reservation> updateVillaReservation(Long Id, LocalDateTime start, LocalDateTime finish,
                                                        Long personId, Long villaId) {
        Villa villa = this.villaRepository.findById(villaId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);

        ReservationId reservationId = new ReservationId(Id, villa, person);
        Reservation reservation_old = this.reservationRepository.findById(reservationId).orElseThrow(ReservationDoesNotExist::new);
        reservationRepository.delete(reservation_old);

        List<Reservation> reservationList = villa.getReservations();
        for (Reservation reserv : reservationList) {
            if ((start.isAfter(reserv.getStart()) || start.equals(reserv.getStart())) ||
                    (finish.isBefore(reserv.getFinish()) || finish.equals(reserv.getFinish()))) {
                reservationRepository.save(reservation_old);
                throw new VillaRoomNotAbleToUpdate();
            }
        }
        Reservation reservation = new Reservation(start, finish, person, villa);
        reservationList.add(reservation);
        villa.setReservations(reservationList);
        this.reservationRepository.save(reservation);
        this.villaRepository.save(villa);
        return Optional.of(reservation);
    }

    @Override
    public Optional<Reservation> deleteReservation(Long Id, Long personId, Long placeId) {
        Place place = this.placeRepository.findById(placeId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        ReservationId reservationId = new ReservationId(Id, place, person);
        Reservation reservation = this.reservationRepository.findById(reservationId).orElseThrow(ReservationDoesNotExist::new);
        reservationRepository.delete(reservation);
        return Optional.of(reservation);
    }

    Integer calculateNumberOdRooms(Hotel hotel,LocalDateTime start,LocalDateTime finish,RoomType roomType){
        List<Reservation> reservations_1 = reservationRepository
                .findByPlaceIdAndStartGreaterThanEqualAndStartLessThanEqualAndRoomType(hotel,start,finish,roomType);

        List<Reservation> reservations_2 = reservationRepository
                .findByPlaceIdAndFinishGreaterThanEqualAndFinishLessThanEqualAndRoomType(hotel,start,finish,roomType);

        List<Reservation> reservations_3 = reservationRepository
                .findByPlaceIdAndStartLessThanEqualAndFinishGreaterThanEqualAndRoomType(hotel,start,finish,roomType);

        List<Reservation> reservations_4 = reservationRepository
                .findByPlaceIdAndStartGreaterThanEqualAndFinishLessThanEqualAndRoomType(hotel,start,finish,roomType);

        return reservations_1.size()+reservations_2.size()+reservations_3.size()-reservations_4.size();
    }
}
