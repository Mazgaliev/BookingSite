package com.example.bookingsite.Configuration;

import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Villa;
import com.example.bookingsite.Repository.HotelRepository;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Repository.ReservationRepository;
import com.example.bookingsite.Repository.VillaRepository;
import com.example.bookingsite.Service.PersonService;
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

    private final PersonService personService;

    public DataInitializer(PersonRepository personRepository, VillaRepository villaRepository, HotelRepository hotelRepository, ReservationRepository reservationRepository, ReservationService reservationService, PersonService personService) {


        this.personRepository = personRepository;
        this.villaRepository = villaRepository;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.personService = personService;
    }


    @PostConstruct
    public void initData() {
//        this.personRepository.save(new Person("name", "surname", "username", "password", "phoneNum", Role.OWNER));


        Person person = this.personRepository.getById(29L);
//        this.villaRepository.save(new Villa("name", "location", "description", "contactNumber", person, 1500));
//        Villa villa = this.villaRepository.getById(14L);
//        this.reservationService.createVillaReservation(LocalDateTime.now().plusDays(12L), LocalDateTime.now().plusDays(16L), person.getId(), 30L);

    }
}

