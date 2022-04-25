package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Villa;
import com.example.bookingsite.Repository.VillaRepository;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = {"/home", "/"})
public class HomeController {

    private final PlaceService placeService;
    private final PersonService personService;

    public HomeController(PlaceService placeService, PersonService personService) {
        this.placeService = placeService;
        this.personService = personService;
    }

    @GetMapping
    public String loadPlaces(Model model,
                             Authentication authentication,
                             @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer size) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            model.addAttribute("person", person);
        }

        List<Place> placeList;
        if(page == null || size == null) {
            placeList = this.placeService.findAll();
        }else {
            placeList = this.placeService.findPage(PageRequest.of(page,size)).toList();
        }

        HashMap<Place, String> placeMap = new HashMap<>();
        for (Place place : placeList) {
            if (place.getImages() == null || place.getImages().isEmpty()) {
                placeMap.put(place, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.mQSPWpQZPJ3szYgasgF40wHaFj%26pid%3DApi&f=1");
            } else {
                placeMap.put(place, place.getImages().get(0));
            }
        }
        //model.addAttribute("n",placeMap.size());
        model.addAttribute("placeMap", placeMap);
        model.addAttribute("bodyContent", "home");
        return "Master-Template";
    }

    @GetMapping("/villas")
    public String loadVillas(Model model,
                             Authentication authentication,
                             @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer size) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            model.addAttribute("person", person);
        }

        List<Villa> villasList;
        if(page == null || size == null) {
            villasList = this.placeService.findAllVillas();;
        }else {
            villasList = this.placeService.findPageVillas(PageRequest.of(page,size)).toList();
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
                             @RequestParam(required = false) Integer size) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            model.addAttribute("person", person);
        }

        List<Hotel> hotelList;
        if(page == null || size == null) {
            hotelList = this.placeService.findAllHotels();
        }else {
            hotelList = this.placeService.findPageHotels(PageRequest.of(page,size)).toList();
        }

        HashMap<Hotel, String> hotels = new HashMap<>();
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
}
