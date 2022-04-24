package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeControler {

    private final PlaceService placeService;
    private final PersonService personService;

    public HomeControler(PlaceService placeService, PersonService personService) {
        this.placeService = placeService;
        this.personService = personService;
    }

    @GetMapping(value = {"/home", "/"})
    public String loadPlaces(Model model, Authentication authentication) {
        UserDetails userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserDetails) authentication.getPrincipal();
            PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
            model.addAttribute("person", person);
        }
        List<Place> placeList = this.placeService.findAll();
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
}
