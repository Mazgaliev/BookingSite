package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.*;
import com.example.bookingsite.Model.CompositeKey.ReservationId;
import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Exception.PlaceDoesNotExistException;
import com.example.bookingsite.Model.Exception.ReservationDoesNotExist;
import com.example.bookingsite.Model.Exception.VillaIsAlreadyReservedException;
import com.example.bookingsite.Repository.*;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.stereotype.Service;

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
    public Optional<Reservation> createHotelReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long hotelId) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> createVillaReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long villaId) {
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
    public Optional<Reservation> updateHotelReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long hotelId) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> updateVillaReservation(LocalDateTime start, LocalDateTime finish, Long personId, Long hotelId) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> deleteReservation(Long personId, Long placeId) {
        Place place = this.placeRepository.findById(placeId).orElseThrow(PlaceDoesNotExistException::new);
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        ReservationId reservationId = new ReservationId(place, person);
        Reservation reservation = this.reservationRepository.findById(reservationId).orElseThrow(ReservationDoesNotExist::new);
        return Optional.of(reservation);
    }
}
