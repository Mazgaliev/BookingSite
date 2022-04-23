package com.example.bookingsite.Configuration;

import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Reservation;
import com.example.bookingsite.Repository.HotelRepository;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Repository.ReservationRepository;
import com.example.bookingsite.Repository.VillaRepository;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class DataInitializer {

    private final PersonRepository personRepository;

    private final VillaRepository villaRepository;

    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    private final ReservationService reservationService;


    public DataInitializer(PersonRepository personRepository, VillaRepository villaRepository, HotelRepository hotelRepository, ReservationRepository reservationRepository, ReservationService reservationService) {


        this.personRepository = personRepository;
        this.villaRepository = villaRepository;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }


    @PostConstruct
    public void initData() {
//        this.personRepository.save(new Person("name", "surname", "username", "password", "phoneNum", Role.OWNER));

//        Person person = this.personRepository.getById(13L);
//        this.villaRepository.save(new Villa("name", "location", "description", "contactNumber", person, 1500));
//        Villa villa = this.villaRepository.getById(14L);

//        this.reservationService.createVillaReservation(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(9L), 13L, 14L);
//        this.hotelRepository.save(new Hotel("hotel", "location", "description", "contactNum", person, 5, 5, 2000, 500));
//        this.reservationRepository.save(new Reservation(LocalDateTime.now(), LocalDateTime.now().plusDays(10L), person, place));
//        this.reservationService.createHotelReservation(LocalDateTime.now(), LocalDateTime.now().plusDays(4L), 13L, 16L, RoomType.VIP);
//        this.reservationService.createHotelReservation(LocalDateTime.now().plusDays(2L),LocalDateTime.now().plusDays(4L),13L,16L, RoomType.VIP);
//        this.reservationService.createHotelReservation(LocalDateTime.now().minusDays(1L),LocalDateTime.now().plusDays(1),13L,16L, RoomType.VIP);
//        this.reservationService.createHotelReservation(LocalDateTime.now().minusDays(2L),LocalDateTime.now().plusDays(5),13L,16L, RoomType.VIP);
        this.reservationService.updateHotelReservation(25L, LocalDateTime.now(), LocalDateTime.now().plusDays(2), 13L, 16L, RoomType.STANDARD);

    }
}

