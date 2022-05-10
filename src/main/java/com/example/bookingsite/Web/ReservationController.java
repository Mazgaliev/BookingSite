package com.example.bookingsite.Web;

import com.example.bookingsite.Model.CustomOAuth2User;
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
import org.springframework.security.access.AccessDeniedException;
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
        UserDetails userPrincipal = null;
        CustomOAuth2User oauth2User = null;

        Long personId;

        try {
            personId = getUserId(model, authentication);
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }

        Place place = new Place();
        try {
            place = this.placeService.findById(placeId).get();
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        this.reservationService.checkOverlappingSelf(personId, placeId);
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
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        return "redirect:/person/reservations";
    }

    private Long getUserId(Model model, Authentication authentication) {
        UserDetails userPrincipal;
        CustomOAuth2User oauth2User;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                userPrincipal = (UserDetails) authentication.getPrincipal();
                PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
                model.addAttribute("person", person);
                return person.getId();
            }
            if (authentication.getPrincipal() instanceof CustomOAuth2User) {
                oauth2User = (CustomOAuth2User) authentication.getPrincipal();
                PersonDto person = this.personService.findByUsername(oauth2User.getEmail());
                model.addAttribute("person", person);
                return person.getId();
            }
        } else {
            throw new AccessDeniedException("You are not authorized to access this page");
        }
        return 0L;
    }
}
