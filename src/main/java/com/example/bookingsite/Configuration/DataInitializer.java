package com.example.bookingsite.Configuration;

import com.example.bookingsite.Repository.HotelRepository;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Repository.ReservationRepository;
import com.example.bookingsite.Repository.VillaRepository;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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


        @PostConstruct
    public void initData() {
//        this.personService.register("ivan", "surname", "Ivan", "pass","pass", "phoneNum", Role.ROLE_OWNER);
        List<String> images = new ArrayList<>();
        images.add("abc");
        this.placeService.createHotel("name", "location", "contactnum", 45L, "description", 5, 5, 500, 150, images);
//        Person person = this.personRepository.getById(29L);
//        this.villaRepository.save(new Villa("name", "location", "description", "contactNumber", person, 1500));
//        Villa villa = this.villaRepository.getById(14L);

//        this.reservationService.createVillaReservation(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(9L), 13L, 14L);
//        this.hotelRepository.save(new Hotel("hotel", "location", "description", "contactNum", person, 5, 5, 2000, 500));
//        this.reservationRepository.save(new Reservation(LocalDateTime.now(), LocalDateTime.now().plusDays(10L), person, place));
//        this.reservationService.createHotelReservation(LocalDateTime.now(), LocalDateTime.now().plusDays(4L), 13L, 16L, RoomType.VIP);
//        this.reservationService.createHotelReservation(LocalDateTime.now().plusDays(2L),LocalDateTime.now().plusDays(4L),13L,16L, RoomType.VIP);
//        this.reservationService.createHotelReservation(LocalDateTime.now().minusDays(1L),LocalDateTime.now().plusDays(1),13L,16L, RoomType.VIP);
//        this.reservationService.createHotelReservation(LocalDateTime.now().minusDays(2L),LocalDateTime.now().plusDays(5),13L,16L, RoomType.VIP);
//        this.reservationService.updateHotelReservation(25L, LocalDateTime.now(), LocalDateTime.now().plusDays(2), 13L, 16L, RoomType.STANDARD);
//        this.personService.createPerson("Mite", "Mazgaliev", "Mazgaliev123", "password", "075277544", Role.OWNER);
//        this.personService.register("name", "surname", "username", "password", "password", "1234567");
//        this.personService.update(29L, "Mite", "Mazgaliev", "Mazgaliev123", "password", "password", "075277544");
    }
}

