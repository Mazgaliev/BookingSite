package com.example.bookingsite.Web;

import com.example.bookingsite.Model.CustomOAuth2User;
import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.PlaceType;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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

        getUserId(model, authentication);

        Reservation reservation;

        try {
            reservation = this.reservationService.findById(id);
        } catch (Exception exception) {
            model.addAttribute("getCause", exception.getCause());
            model.addAttribute("exceptionMessage", exception.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }


        model.addAttribute("reservation", reservation);
        model.addAttribute("bodyContent", "editReservation");

        return "Master-Template";
    }

    @PostMapping("/edit/reservation")
    public String editReservation(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end, @RequestParam Long id, @RequestParam(required = false) RoomType roomType, Model model) {

        if (roomType == null) {
            try {
                this.reservationService.updateVillaReservation(id, start, end);
            } catch (Exception e) {
                model.addAttribute("getCause", e.getCause());
                model.addAttribute("exceptionMessage", e.getMessage());
                model.addAttribute("bodyContent", "error-template");
                return "Master-Template";
            }
        } else this.reservationService.updateHotelReservation(id, start, end, roomType);

        return "redirect:/person/reservations";
    }

    @GetMapping("/reservations")
    public String viewReservations(Model model, Authentication authentication) {
        UserDetails userPrincipal = null;
        CustomOAuth2User oauth2User = null;
        PersonDto person = null;
        try {
            person = getPersonDto(model, authentication, userPrincipal, oauth2User, person);
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        model.addAttribute("person", person);
        model.addAttribute("reservations", person.getReservations());


        model.addAttribute("bodyContent", "reservations");

        return "Master-Template";
    }

    @GetMapping("/places")
    public String viewPlaces(Model model, Authentication authentication) {
        UserDetails userPrincipal = null;
        CustomOAuth2User oauth2User = null;
        PersonDto person = null;
        try {
            person = getPersonDto(model, authentication, userPrincipal, oauth2User, person);
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }

        model.addAttribute("person", person);
        model.addAttribute("places", person.getOwns());

        model.addAttribute("bodyContent", "ownedPlaces");
        return "Master-Template";
    }

    @GetMapping("/reservations/delete/{id}")
    public String cancelReservation(@PathVariable Long id, Model model) {
        try {
            this.reservationService.deleteReservation(id);
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        return "redirect:/person/reservations";
    }

    @GetMapping("/places/{id}/reservations")
    public String placeReservations(@PathVariable Long id,
                                    Model model,
                                    Authentication authentication,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer size,
                                    @RequestParam(required = false) String state) {

        getUserId(model, authentication);

        if (authentication != null) {
            try {
                Place place = this.placeService.findById(id).get();
                model.addAttribute("placeId", place.getId());
                model.addAttribute("PlaceName", place.getName());
                // model.addAttribute("person", person);
            } catch (Exception e) {
                model.addAttribute("getCause", e.getCause());
                model.addAttribute("exceptionMessage", e.getMessage());
                model.addAttribute("bodyContent", "error-template");
                return "Master-Template";
            }
            List<Reservation> reservationList;
            Page<Reservation> reservationPage;

            if (page == null) {
                reservationPage = this.reservationService.findReservationPage(id, PageRequest.of(0, 5));
                reservationList = reservationPage.toList();

                makePaginationBar(model, size, reservationPage, -1);
            } else {
                int countReservations = (int) this.reservationService.countPlaceReservations(id);

                page = getPageNumberFromState(page, size, state, countReservations);

                reservationPage = this.reservationService.findReservationPage(id, PageRequest.of(page - 1, size));
                reservationList = reservationPage.toList();

                makePaginationBar(model, size, reservationPage, countReservations);
            }
            model.addAttribute("reservations", reservationList);
        }

        model.addAttribute("bodyContent", "placeReservations");

        return "Master-Template";
    }

    @GetMapping("/settings")
    public String loadPersonSettings(Model model, Authentication authentication) {

        UserDetails userPrincipal;
        CustomOAuth2User oauth2User;
        List<String> roles = new ArrayList<>();
        try {
            if (authentication != null) {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    userPrincipal = (UserDetails) authentication.getPrincipal();
                    PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
                    roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
                    model.addAttribute("person", person);
                    model.addAttribute("googleAcc", false);
                }
                if (authentication.getPrincipal() instanceof CustomOAuth2User) {
                    oauth2User = (CustomOAuth2User) authentication.getPrincipal();
                    PersonDto person = this.personService.findByUsername(oauth2User.getEmail());
                    roles = oauth2User.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
                    model.addAttribute("person", person);
                    model.addAttribute("googleAcc", true);
                }
            }
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }

//        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
//        roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        List<String> allRoles = this.personService.getAllRoles();

        //PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());

        model.addAttribute("currentRole", roles.get(0));
        model.addAttribute("roles", allRoles);
        // model.addAttribute("person", person);
        model.addAttribute("bodyContent", "settings");

        return "Master-Template";
    }

    @PostMapping("/settings")
    public String updatePerson(@RequestParam Long id,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String phoneNumber,
                               @RequestParam String username,
                               Model model) {

        try {
            this.personService.update(id, name, surname, username, null, null, null, phoneNumber);
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }

        return "redirect:/logout";
    }

    @GetMapping("/settings/changePassword")
    public String loadchangePersonPassword(Model model, Authentication authentication) {
        getUserId(model, authentication);

        model.addAttribute("bodyContent", "changePassword");
        return "Master-Template";
    }

    @PostMapping("/settings/changePassword")
    public String changePersonPassword(@RequestParam Long id,
                                       @RequestParam String oldPassword,
                                       @RequestParam String password,
                                       @RequestParam String repeatPassword,
                                       Model model) {

        try {
            PersonDto person = this.personService.findById(id);
            this.personService.update(id, person.getName(), person.getSurname(), person.getUsername(), password, repeatPassword, oldPassword, person.getPhoneNumber());
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        return "redirect:/logout";
    }

    @GetMapping("/create")
    public String viewCreatePage(Model model, Authentication
            authentication, @RequestParam(required = false) PlaceType placeType) {

        UserDetails userPrincipal = null;
        CustomOAuth2User oauth2User = null;
        try {
            getUserId(model, authentication);
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        if (placeType == null || placeType == PlaceType.VILLA) {
            model.addAttribute("isVilla", true);
        } else {
            model.addAttribute("isVilla", false);
        }

        model.addAttribute("bodyContent", "create-place");
        return "Master-Template";

    }

    @GetMapping("/delete")
    public String deletePerson(Authentication authentication, Model model) {
        UserDetails userPrincipal = null;
        CustomOAuth2User customOAuth2User = null;
        PersonDto person = null;
        if (authentication != null) {
            try {
                person = getPersonDto(model,authentication,userPrincipal,customOAuth2User,person);
                this.personService.deleteUserById(person.getId());
            } catch (Exception e) {
                model.addAttribute("getCause", "");
                model.addAttribute("exceptionMessage", "Cannot delete user when he still has reservations");
                model.addAttribute("bodyContent", "error-template");
                return "Master-Template";
            }
        }

        return "redirect:/logout";
    }

    private Integer getPageNumberFromState(Integer page, Integer size, String state, int countReservations) {
        if (state != null) {
            if (state.equals("previous") && page > 1) {
                page = page - 1;
            } else if (state.equals("next") && page < Math.ceil(countReservations / (double)size)) {
                page = page + 1;
            }
        }
        return page;
    }

    private void makePaginationBar(Model model, Integer size, Page<Reservation> reservationPage, int countReservations) {

        if (countReservations == -1) {
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
            if (Math.ceil(countReservations / (double)size) < toPage) {
                toPage = (int) Math.ceil(countReservations / (double)size);
                fromPage = toPage - 3;
                if (fromPage < 2) {
                    makePaginationBar(model, size, reservationPage, -1);
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

    private void getUserId(Model model, Authentication authentication) {
        UserDetails userPrincipal;
        CustomOAuth2User oauth2User;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                userPrincipal = (UserDetails) authentication.getPrincipal();
                PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
                model.addAttribute("person", person);
            }
            if (authentication.getPrincipal() instanceof CustomOAuth2User) {
                oauth2User = (CustomOAuth2User) authentication.getPrincipal();
                PersonDto person = this.personService.findByUsername(oauth2User.getEmail());
                model.addAttribute("person", person);
            }
        } else {
            throw new AccessDeniedException("You are not authorized to access this page");
        }
    }

    private PersonDto getPersonDto(Model model,
                                   Authentication authentication,
                                   UserDetails userPrincipal,
                                   CustomOAuth2User oauth2User,
                                   PersonDto person) {
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                userPrincipal = (UserDetails) authentication.getPrincipal();
                person = this.personService.findByUsername(userPrincipal.getUsername());
                model.addAttribute("person", person);

            }
            if (authentication.getPrincipal() instanceof CustomOAuth2User) {
                oauth2User = (CustomOAuth2User) authentication.getPrincipal();
                person = this.personService.findByUsername(oauth2User.getEmail());
                model.addAttribute("person", person);
            }
        } else {
            throw new AccessDeniedException("You are not authorized to access this page");
        }
        return person;
    }
}
