package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.PlaceType;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Villa;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/place")
public class PlaceController {
    private final PersonService personService;

    private final PlaceService placeService;

    public PlaceController(PersonService personService, PlaceService placeService) {
        this.personService = personService;
        this.placeService = placeService;
    }


    @GetMapping("/{id}")
    public String viewPlace(@PathVariable Long id, Authentication authentication,
                            Model model,
                            @RequestParam(required = false) boolean hasError,
                            @RequestParam(required = false) String error) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            model.addAttribute("person", person);
        }
        Place place = this.placeService.findById(id).get();
        if (this.placeService.placeType(place) == PlaceType.VILLA) {
            Villa villa = (Villa) place;
            model.addAttribute("price1", villa.getPricePerNight());
            model.addAttribute("price2", 0);
        } else {
            Hotel hotel = (Hotel) place;
            model.addAttribute("price1", hotel.getPriceStandardRoom());
            model.addAttribute("price2", hotel.getPriceVipRoom());
        }
        model.addAttribute("hasError", hasError);
        model.addAttribute("error", error);
        model.addAttribute("images", place.getImages());
        model.addAttribute("place", place);
        model.addAttribute("bodyContent", "place");

        return "Master-Template";
    }
}
