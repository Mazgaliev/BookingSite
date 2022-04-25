package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.PlaceType;
import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Exception.HotelRoomNotAvaiable;
import com.example.bookingsite.Model.Exception.VillaIsAlreadyReservedException;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/reserve")
public class ReservationController {

    private final PlaceService placeService;
    private final ReservationService reservationService;
    private final PersonService personService;

    public ReservationController(PlaceService placeService, ReservationService reservationService, PersonService personService) {
        this.placeService = placeService;
        this.reservationService = reservationService;
        this.personService = personService;
    }

    @PostMapping
    public String makeReservation(Model model,
                                  Authentication authentication,
                                  @RequestParam Long placeId,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                  @RequestParam(required = false) RoomType roomType) {
        UserDetails userPrincipal;
        Long personId = 0L;

        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            personId = person.getId();
            model.addAttribute("person", person);
        }

        Place place = this.placeService.findById(placeId).get();
        try {
            if (from.isBefore(end)) {
                if (this.placeService.placeType(place) == PlaceType.VILLA) {
                    this.reservationService.createVillaReservation(from, end, personId, place.getId());
                } else if (this.placeService.placeType(place) == PlaceType.HOTEL) {
                    this.reservationService.createHotelReservation(from, end, personId, place.getId(), roomType);
                }
            } else if (from.isAfter(LocalDate.now())) {
                String string = placeId + "?hasError=true&error=Date+from+is+before+today";
                return "redirect:/place/" + string;
            } else {
                String string = placeId + "?hasError=true&error=Date+from+is+after+date+end";
                return "redirect:/place/" + string;
            }
        } catch (HotelRoomNotAvaiable | VillaIsAlreadyReservedException e) {
            String string = placeId + "?hasError=true&error=" + e.getMessage();
            return "redirect:/place/" + string;
        }
        return "redirect:/home";
    }

    @GetMapping("/edit/{id}")
    public String editReservation(@PathVariable Long id) {
        //TODO edit na rezervacija
        return null;
    }

}
