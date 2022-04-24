package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.PlaceType;
import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        LocalDateTime for1 = from.atStartOfDay();
        LocalDateTime end1 = end.atStartOfDay();
        if (for1.isBefore(end1)) {
            if (this.placeService.placeType(place) == PlaceType.VILLA) {
                this.reservationService.createVillaReservation(for1, end1, personId, place.getId());
            } else if (this.placeService.placeType(place) == PlaceType.HOTEL) {
                this.reservationService.createHotelReservation(for1, end1, personId, place.getId(), roomType);
            }
        } else {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Date from is after date end");
            return "/place/" + placeId;
        }
        return "redirect:/home";
    }

}
