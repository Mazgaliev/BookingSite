package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Exception.*;
import com.example.bookingsite.Model.*;
import com.example.bookingsite.Repository.*;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final PersonRepository personRepository;
    private final HotelRepository hotelRepository;
    private final VillaRepository villaRepository;
    private final PlaceService placeService;


    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  PersonRepository personRepository,
                                  HotelRepository hotelRepository,
                                  VillaRepository villaRepository,
                                  PlaceService placeService) {
        this.reservationRepository = reservationRepository;
        this.personRepository = personRepository;
        this.hotelRepository = hotelRepository;
        this.villaRepository = villaRepository;
        this.placeService = placeService;
    }


    @Override
    @Transactional
    public Optional<Reservation> createHotelReservation(LocalDate start, LocalDate finish,
                                                        Long personId, Long hotelId, RoomType roomType) {

        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        long days = ChronoUnit.DAYS.between(start, finish);
        int numberOfRooms = calculateNumberOdRooms(hotel, start, finish, roomType);

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
        throw new HotelRoomNotAvaiable();
    }

    @Override
    @Transactional
    public Optional<Reservation> createVillaReservation(LocalDate start, LocalDate finish,
                                                        Long personId, Long villaId) {
        Villa villa = this.villaRepository.findById(villaId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        List<Reservation> reservationList = villa.getReservations();

        for (Reservation reserv : reservationList) {
            if (isBetween(start, finish, reserv)) {
                throw new VillaIsAlreadyReservedException();
            }
        }
        long days = ChronoUnit.DAYS.between(start, finish);

        Reservation reservation = new Reservation(start, finish, person, villa);
        reservation.setPrice((int) (villa.getPricePerNight() * days));

        this.reservationRepository.save(reservation);

        return Optional.of(reservation);
    }

    public boolean isBetween(LocalDate start, LocalDate end, Reservation reservation) {
        if ((start.isAfter(reservation.getStart()) || start.isEqual(reservation.getStart())) && (start.isEqual(reservation.getFinish()) || start.isBefore(reservation.getFinish()))) {
            return true;
        }
        return false;

    }

    @Override
    @Transactional
    public Optional<Reservation> updateHotelReservation(Long Id, LocalDate start, LocalDate finish, RoomType roomType) {
        Reservation reservation_old = this.reservationRepository.findById(Id).orElseThrow(ReservationDoesNotExist::new);
        Hotel hotel = this.hotelRepository.findById(reservation_old.getPlaceId().getId()).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(reservation_old.getPersonId().getId()).orElseThrow(PersonDoesNotExistException::new);


        reservationRepository.delete(reservation_old);

        int numberOfRooms = calculateNumberOdRooms(hotel, start, finish, roomType);
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

        reservationRepository.save(reservation_old);
        throw new HotelRoomNotAvaiable();
    }


    @Override
    @Transactional
    public Optional<Reservation> updateVillaReservation(Long Id, LocalDate start, LocalDate finish) {

        Reservation reservation_old = this.reservationRepository.findById(Id).orElseThrow(ReservationDoesNotExist::new);
        Villa villa = this.villaRepository.findById(reservation_old.getPlaceId().getId()).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(reservation_old.getPersonId().getId()).orElseThrow(PersonDoesNotExistException::new);


        List<Reservation> reservationList = villa.getReservations();
        for (Reservation reserv : reservationList) {
            if (isBetween(start, finish, reserv) && Id.equals(reserv.getId())) {
                reservationRepository.save(reservation_old);
                throw new VillaRoomNotAbleToUpdate();
            }
        }
        long days = ChronoUnit.DAYS.between(start, finish);

        reservation_old.setStart(start);
        reservation_old.setFinish(finish);
        reservation_old.setPrice((int) (days * villa.getPricePerNight()));

        this.reservationRepository.save(reservation_old);
        return Optional.of(reservation_old);
    }

    @Override
    public Optional<Reservation> deleteReservation(Long id) {
        Reservation reservation = this.reservationRepository.findById(id).orElseThrow(ReservationDoesNotExist::new);
        reservationRepository.delete(reservation);
        return Optional.of(reservation);
    }

    @Override
    public Page<Reservation> findReservationPage(Long placeId, Pageable pageable) {
        return this.reservationRepository.findByPlaceId(placeId,pageable);
    }

    @Override
    public long countPlaceReservations(Long placeId) {
        Optional<Place> placeOptional = this.placeService.findById(placeId);
        Place place;
        if (placeOptional.isPresent()){
            place = placeOptional.get();
        }else {
            throw new PlaceDoesNotExistException();
        }
        return this.reservationRepository.countByPlaceId(place);
    }

    @Override
    public Reservation findById(Long id) {
        return this.reservationRepository.findById(id).orElseThrow(ReservationDoesNotExist::new);
    }

    Integer calculateNumberOdRooms(Hotel hotel, LocalDate start, LocalDate finish, RoomType roomType) {
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
}
