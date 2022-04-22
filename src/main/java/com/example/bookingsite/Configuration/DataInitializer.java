package com.example.bookingsite.Configuration;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Villa;
import com.example.bookingsite.Service.Implementation.PersonServiceImpl;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.stereotype.Component;
import com.example.bookingsite.Model.Enum.Role;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataInitializer {

    private final PersonService personService;

    private final PlaceService placeService;

    private final ReservationService reservationService;

    private final PersonServiceImpl personServiceimpl;

    public DataInitializer(PersonService personService, PlaceService placeService, ReservationService reservationService, PersonServiceImpl personServiceimpl) {
        this.personService = personService;
        this.placeService = placeService;
        this.reservationService = reservationService;
        this.personServiceimpl = personServiceimpl;
    }

//    private BookCategory randomizeCategory(int i) {
//        if (i % 6 == 0) return BookCategory.NOVEL;
//        else if (i % 6 == 1) return BookCategory.THRILER;
//        else if (i % 6 == 2) return BookCategory.HISTORY;
//        else if (i % 6 == 3) return BookCategory.FANTASY;
//        else if (i % 6 == 4) return BookCategory.BIOGRAPHY;
//        else if (i % 6 == 5) return BookCategory.CLASSICS;
//        return BookCategory.DRAMA;
//    }

    @PostConstruct
    public void initData() {
        Optional<PersonDto> ivan = this.personService.createPerson("Ivan","Janev","Medo","Medo","072085175",Role.STANDARD);
        Optional<PersonDto> mite = this.personService.createPerson("Mite","Mazgaliev","Micosss","Micosss","072085175",Role.OWNER);
        Optional<Villa> villa = this.placeService.createVilla("OdemeJako","Slatiii,Sveti","071472957",
                this.personServiceimpl.findByUsernameTest(mite.get().getUsername()),"oahsfgouqeggfoewgo",9861);
        Long villaId = villa.get().getId();
        this.reservationService.createVillaReservation(LocalDateTime.now(),LocalDateTime.now().plusDays(10L),
                this.personServiceimpl.findByUsernameTest(ivan.get().getUsername()),villaId);
    }
}

