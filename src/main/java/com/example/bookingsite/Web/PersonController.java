package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/settings")
    public String loadPersonSettings(Model model, Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        List<String> allRoles = this.personService.getAllRoles();

        PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());

        model.addAttribute("currentRole", roles.get(0));
        model.addAttribute("roles", allRoles);
        model.addAttribute("person", person);
        model.addAttribute("bodyContent", "settings");

        return "Master-Template";
    }

    @PostMapping("/settings")
    public String updatePerson(@RequestParam Long id,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String oldPassword,
                               @RequestParam String password,
                               @RequestParam String repeatPassword,
                               @RequestParam String phoneNumber,
                               @RequestParam String username) {

        try {
            this.personService.update(id, name, surname, username, password, repeatPassword, oldPassword, phoneNumber);
        } catch (Exception exception) {
            return "redirect:/home";
        }
        return "redirect:/logout";
    }
}
