package com.example.bookingsite.Configuration;

import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Villa;
import com.example.bookingsite.Repository.HotelRepository;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Repository.ReservationRepository;
import com.example.bookingsite.Repository.VillaRepository;
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

    private final PlaceService placeService;


    public DataInitializer(PersonRepository personRepository, VillaRepository villaRepository, HotelRepository hotelRepository, ReservationRepository reservationRepository, ReservationService reservationService, PlaceService placeService) {


        this.personRepository = personRepository;
        this.villaRepository = villaRepository;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.placeService = placeService;
    }


    @PostConstruct
    public void initData() {
        //this.personRepository.save(new Person("name", "surname", "username", "password", "phoneNum", Role.OWNER));

//        Person person = this.personRepository.getById(12L);
//        this.villaRepository.save(new Villa("villa2", "location", "description", "contactNumber", person, 1500));
//        Villa villa = this.villaRepository.getById(14L);

//        this.reservationService.createVillaReservation(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(9L), 13L, 14L);
//        this.hotelRepository.save(new Hotel("hotel2", "location", "description", "contactNum", person, 5, 5, 2000, 500));
//        this.reservationRepository.save(new Reservation(LocalDateTime.now(), LocalDateTime.now().plusDays(10L), person, place));
//        this.reservationService.createHotelReservation(LocalDateTime.now(), LocalDateTime.now().plusDays(4L), 13L, 16L, RoomType.VIP);
//        this.reservationService.createHotelReservation(LocalDateTime.now().plusDays(2L),LocalDateTime.now().plusDays(4L),13L,16L, RoomType.VIP);
//        this.reservationService.createHotelReservation(LocalDateTime.now().minusDays(1L),LocalDateTime.now().plusDays(1),13L,16L, RoomType.VIP);
//        this.reservationService.createHotelReservation(LocalDateTime.now().minusDays(2L),LocalDateTime.now().plusDays(5),13L,16L, RoomType.VIP);
//        this.reservationService.updateHotelReservation(25L, LocalDateTime.now(), LocalDateTime.now().plusDays(2), 13L, 16L, RoomType.STANDARD);

//        Place place = this.placeService.findById(3L).get();
//        List<String> imgList = new ArrayList<>();
//        imgList.add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.wjM6AizadeJqyyvyqG77QAHaFj%26pid%3DApi&f=1");
//        this.placeService.addImages(place.getId(), imgList);
    }
}

