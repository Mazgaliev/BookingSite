package com.example.bookingsite.Configuration;

import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Person;
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

        Person person = this.personRepository.getById(29L);
//        this.villaRepository.save(new Villa("name", "location", "description", "contactNumber", person, 1500));
//        Villa villa = this.villaRepository.getById(14L);

//        this.reservationService.createVillaReservation(LocalDate.now().plusDays(1L), LocalDate.now().plusDays(9L), 13L, 14L);
//        for(int i = 0;i<20;i++)
//            this.placeService.createHotel("hotel", "location", "description",  person.getId(), "aksgvfjhwvefkwhercvwekivgcwuivr", 5, 5,2000, 500);
//        this.reservationRepository.save(new Reservation(LocalDate.now(), LocalDate.now().plusDays(10L), person, place));
//        for(long i = 0 ; i<10; i++) {
//            this.reservationService.createHotelReservation(LocalDate.now().plusDays(i*20L), LocalDate.now().plusDays(4L+(i*20L)), person.getId(), 259L, RoomType.VIP);
//            this.reservationService.createHotelReservation(LocalDate.now().plusDays(2L+(i*20L)), LocalDate.now().plusDays(4L+(i*20L)), person.getId(), 259L, RoomType.VIP);
//            this.reservationService.createHotelReservation(LocalDate.now().plusDays(i*20L), LocalDate.now().plusDays(1+(i*20L)), person.getId(), 259L, RoomType.STANDARD);
//            this.reservationService.createHotelReservation(LocalDate.now().plusDays(i*20L), LocalDate.now().plusDays(5+(i*20L)), person.getId(), 259L, RoomType.STANDARD);
//        }
//        this.personService.createPerson("Mite", "Mazgaliev", "Mazgaliev123", "password", "075277544", Role.OWNER);
//        this.personService.register("name", "surname", "username", "password", "password", "1234567");
//        this.personService.update(29L, "Mite", "Mazgaliev", "Mazgaliev123", "password", "password", "075277544");
    }
}

