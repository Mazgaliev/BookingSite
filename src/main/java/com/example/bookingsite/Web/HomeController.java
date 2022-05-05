package com.example.bookingsite.Web;

import com.example.bookingsite.Model.*;
import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = {"/home", "/"})
public class HomeController {

    private final PlaceService placeService;
    private final PersonService personService;
    
    public HomeController(PlaceService placeService, PersonService personService) {
        this.placeService = placeService;
        this.personService = personService;
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage(Model model) {
        model.addAttribute("exceptionMessage", "Access denied");
        model.addAttribute("getCause", "");
        model.addAttribute("bodyContent", "error-template");
        return "Master-Template";
    }

    @GetMapping("/user/admin")
    public String adminView() {

        return "Master-Template";
    }

    @GetMapping
    public String loadPlaces(Model model,
                             Authentication authentication,
                             @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer size,
                             @RequestParam(required = false) String state) {

        try {
            getUserId(model, authentication);
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        List<Place> placeList;
        Page<Place> placePage;
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"rating");

        if (page == null) {
            String string;
            placePage = this.placeService.findPage(PageRequest.of(0, 5,Sort.by(order)));
            placeList = placePage.toList();

            makePaginationBar(model, size, placePage, -1);
        } else {
            int countPlaces = (int) this.placeService.countPlaces();

            page = getPageNumberFromState(page, size, state, countPlaces);

            placePage = this.placeService.findPage(PageRequest.of(page - 1, size,Sort.by(order)));

            placeList = placePage.toList();

            makePaginationBar(model, size, placePage, countPlaces);
        }

        LinkedHashMap<Place, String> placeMap = new LinkedHashMap<>();

        for (Place place : placeList) {
            if (place.getImages() == null || place.getImages().isEmpty()) {
                placeMap.put(place, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.mQSPWpQZPJ3szYgasgF40wHaFj%26pid%3DApi&f=1");
            } else {
                placeMap.put(place, place.getImages().get(0));
            }
        }

        model.addAttribute("placeMap", placeMap);
        model.addAttribute("bodyContent", "home");
        return "Master-Template";
    }

    @GetMapping("/villas")
    public String loadVillas(Model model,
                             Authentication authentication,
                             @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer size,
                             @RequestParam(required = false) String state) {
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

        List<Villa> villasList;
        Page<Villa> placePage;
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"rating");

        if (page == null) {
            placePage = this.placeService.findPageVillas(PageRequest.of(0, 5,Sort.by(order)));
            villasList = placePage.toList();

            makePaginationBar(model, size, placePage, -1);
        } else {
            int countPlaces = (int) this.placeService.countVillas();

            page = getPageNumberFromState(page, size, state, countPlaces);

            placePage = this.placeService.findPageVillas(PageRequest.of(page - 1, size,Sort.by(order)));
            villasList = placePage.toList();

            makePaginationBar(model, size, placePage, countPlaces);
        }

        HashMap<Villa, String> villas = new HashMap<>();
        for (Villa villa : villasList) {
            if (villa.getImages() == null || villa.getImages().isEmpty()) {
                villas.put(villa, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.mQSPWpQZPJ3szYgasgF40wHaFj%26pid%3DApi&f=1");
            } else {
                villas.put(villa, villa.getImages().get(0));
            }
        }

        model.addAttribute("villas", villas);
        model.addAttribute("bodyContent", "Villas");

        return "Master-Template";
    }

    @GetMapping("/hotels")
    public String loadHotels(Model model,
                             Authentication authentication,
                             @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer size,
                             @RequestParam(required = false) String state) {

        try {
            getUserId(model, authentication);
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }

        List<Hotel> hotelList;
        Page<Hotel> placePage;
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"rating");

        if (page == null) {

            placePage = this.placeService.findPageHotels(PageRequest.of(0, 5,Sort.by(order)));
            hotelList = placePage.toList();

            makePaginationBar(model, size, placePage, -1);
        } else {
            int countPlaces = (int) this.placeService.countHotels();

            page = getPageNumberFromState(page, size, state, countPlaces);

            placePage = this.placeService.findPageHotels(PageRequest.of(page - 1, size,Sort.by(order)));
            hotelList = placePage.toList();

            makePaginationBar(model, size, placePage, countPlaces);
        }

        LinkedHashMap<Hotel, String> hotels = new LinkedHashMap<>();
        for (Hotel hotel : hotelList) {
            if (hotel.getImages() == null || hotel.getImages().isEmpty()) {
                hotels.put(hotel, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.mQSPWpQZPJ3szYgasgF40wHaFj%26pid%3DApi&f=1");
            } else {
                hotels.put(hotel, hotel.getImages().get(0));
            }
        }

        model.addAttribute("hotels", hotels);
        model.addAttribute("bodyContent", "Hotels");

        return "Master-Template";
    }

    //TODO: copy paste makePaginationBar and getPageNuber

    private Integer getPageNumberFromState(Integer page, Integer size, String state, int countPlaces) {
        if (state != null) {
            if (state.equals("previous") && page > 1) {
                page = page - 1;
            } else if (state.equals("next") && page < Math.ceil(countPlaces / (double)size)) {
                page = page + 1;
            }
        }
        return page;
    }

    private void makePaginationBar(Model model, Integer size, Page<? extends Place> placePage, int countPlaces) {

        if (countPlaces == -1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, 5)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("placePageSize", placePage.getSize());
            model.addAttribute("placePageNumber", placePage.getNumber());
            return;
        }

        model.addAttribute("placePageSize", placePage.getSize());
        model.addAttribute("placePageNumber", placePage.getNumber());
        int pageNumber = placePage.getNumber();

        if (pageNumber < 3) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, 5)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        } else {
            int fromPage = pageNumber;
            int toPage = pageNumber + 3;
            if (Math.ceil(countPlaces / (double)size) < toPage) {
                toPage = (int) Math.ceil(countPlaces / (double)size);
                fromPage = toPage - 3;
                if (fromPage < 2) {
                    makePaginationBar(model, size, placePage, -1);
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
        }
    }

}
