package com.example.bookingsite.Configuration;

import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Repository.HotelRepository;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Repository.ReservationRepository;
import com.example.bookingsite.Repository.VillaRepository;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {

    private final PersonRepository personRepository;

    private final VillaRepository villaRepository;

    private final HotelRepository hotelRepository;

    private final ReservationRepository reservationRepository;

    private final ReservationService reservationService;

    private final PersonService personService;
    private final PlaceService placeService;

    public DataInitializer(PersonRepository personRepository, VillaRepository villaRepository, HotelRepository hotelRepository, ReservationRepository reservationRepository, ReservationService reservationService, PersonService personService, PlaceService placeService) {


        this.personRepository = personRepository;
        this.villaRepository = villaRepository;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.personService = personService;

        this.placeService = placeService;
    }


//    @PostConstruct
    public void initData() {

        for (int i = 0; i < 1500; i++) {
            this.reservationService.createHotelReservation(LocalDate.now(), LocalDate.now().plusDays(1L), 29L, 54L, RoomType.STANDARD);
            this.reservationService.createHotelReservation(LocalDate.now(), LocalDate.now().plusDays(1L), 29L, 54L, RoomType.VIP);

        }

    }
}

