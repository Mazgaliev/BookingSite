package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Reservation;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/reservations/edit/{id}")
    public String editReservationPage(@PathVariable Long id, Model model, Authentication authentication) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            model.addAttribute("person", person);
        }
        Reservation reservation;

        try {
            reservation = this.reservationService.findById(id);
        } catch (Exception exception) {
            return "redirect:/home";
        }


        model.addAttribute("reservation", reservation);
        model.addAttribute("bodyContent", "editReservation");

        return "Master-Template";
    }

    @PostMapping("/edit/reservation")
    public String editReservation(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                  @RequestParam Long id,
                                  @RequestParam(required = false) RoomType roomType) {

        if (roomType == null)
            this.reservationService.updateVillaReservation(id, start, end);
        else
            this.reservationService.updateHotelReservation(id, start, end, roomType);

        return "redirect:/home";
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
    public String placeReservations(@PathVariable Long id,
                                    Model model,
                                    Authentication authentication,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer size,
                                    @RequestParam(required = false) String state) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            Place place = this.placeService.findById(id).get();
            model.addAttribute("PlaceName", place.getName());
            model.addAttribute("person", person);

            List<Reservation> reservationList;
            Page<Reservation> reservationPage;

            if (page == null) {
                reservationPage = this.reservationService.findReservationPage(id, PageRequest.of(0, 5));
                reservationList = reservationPage.toList();

                makePaginationBar(model,size,reservationPage,-1);
            } else {
                int countReservations = (int) this.reservationService.countPlaceReservations(id);

                page = getPageNumberFromState(page, size, state, countReservations);

                reservationPage = this.reservationService.findReservationPage(id, PageRequest.of(page - 1, size));
                reservationList = reservationPage.toList();

                makePaginationBar(model, size, reservationPage, countReservations);
            }

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

    private Integer getPageNumberFromState(Integer page, Integer size, String state, int countReservations) {
        if (state != null) {
            if (state.equals("previous") && page > 1) {
                page = page -1;
            } else if (state.equals("next") && page < countReservations / size) {
                page = page +1;
            }
        }
        return page;
    }

    private void makePaginationBar(Model model, Integer size, Page<Reservation> reservationPage, int countReservations) {

        if(countReservations == -1){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, 5)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("placePageSize", reservationPage.getSize());
            model.addAttribute("placePageNumber", reservationPage.getNumber());
            return;
        }

        model.addAttribute("placePageSize", reservationPage.getSize());
        model.addAttribute("placePageNumber", reservationPage.getNumber());
        int pageNumber = reservationPage.getNumber();

        if (pageNumber < 3) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, 5)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        } else {
            int fromPage = pageNumber;
            int toPage = pageNumber + 3;
            if (countReservations / size < toPage){
                toPage = countReservations / size;
                fromPage = toPage - 3;
                if(fromPage <2){
                    makePaginationBar(model,size,reservationPage,-1);
                    return;
                }
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(fromPage, toPage)
                    .boxed()
                    .collect(Collectors.toList());
            pageNumbers.add(0, 1);
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
