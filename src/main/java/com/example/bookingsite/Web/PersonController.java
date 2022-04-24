package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Service.PersonService;
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

    public PersonController(ReservationService reservationService, PersonService personService) {
        this.reservationService = reservationService;
        this.personService = personService;
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
}
