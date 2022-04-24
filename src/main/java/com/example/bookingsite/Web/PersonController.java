package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final ReservationService reservationService;
    private final PersonService personService;

    private final PlaceService placeService;

    public PersonController(ReservationService reservationService, PersonService personService, PlaceService placeService) {
        this.reservationService = reservationService;
        this.personService = personService;
        this.placeService = placeService;
    }

    @GetMapping("/reservations")
    public String viewReservations(Model model, Authentication authentication) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            model.addAttribute("person", person);
            model.addAttribute("reservations", person.getReservations());
        }

        model.addAttribute("bodyContent", "reservations");

        return "Master-Template";
    }

    @GetMapping("/places")
    public String viewPlaces(Model model, Authentication authentication) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            model.addAttribute("person", person);
            model.addAttribute("places", person.getOwns());
        }

        model.addAttribute("bodyContent", "ownedPlaces");
        return "Master-Template";
    }

    @GetMapping("/reservations/delete/{id}")

    public String cancelReservation(@PathVariable Long id) {

        this.reservationService.deleteReservation(id);

        return "redirect:/person/reservations";
    }

    @GetMapping("/places/{id}/reservations")
    public String placeReservations(@PathVariable Long id, Model model, Authentication authentication) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            Place place = this.placeService.findById(id).get();
            model.addAttribute("PlaceName", place.getName());
            model.addAttribute("person", person);
            model.addAttribute("reservations", place.getReservations());
        }

        model.addAttribute("bodyContent", "placeReservations");

        return "Master-Template";
    }
}
