package com.example.bookingsite.Web;

import com.example.bookingsite.Model.CustomOAuth2User;
import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.PlaceType;
import com.example.bookingsite.Model.Enum.RoomType;
import com.example.bookingsite.Model.Hotel;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Model.Villa;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/place")
public class PlaceController {
    private final PersonService personService;

    private final PlaceService placeService;

    public PlaceController(PersonService personService, PlaceService placeService) {
        this.personService = personService;
        this.placeService = placeService;
    }


    @PostMapping("/create")
    public String createPlace(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam String contactNumber,
                              @RequestParam String location,
                              @RequestParam Long id,
                              @RequestParam List<String> images,
                              @RequestParam(required = false) Integer pricePerNight,
                              @RequestParam(required = false) Integer vipRooms,
                              @RequestParam(required = false) Integer priceVipRoom,
                              @RequestParam(required = false) Integer standardRooms,
                              @RequestParam(required = false) Integer priceStandardRoom
    ) {

        if (pricePerNight == null) {
            this.placeService.createHotel(name, location, contactNumber, id, description, vipRooms, standardRooms, priceVipRoom, priceStandardRoom, images);
        } else {
            this.placeService.createVilla(name, location, contactNumber, id, description, pricePerNight, images);
        }

        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public String viewPlace(@PathVariable Long id, Authentication authentication,
                            Model model,
                            @RequestParam(required = false) boolean hasError,
                            @RequestParam(required = false) String error) {

        getUserId(model,authentication);

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

    private void getUserId(Model model, Authentication authentication) {
        UserDetails userPrincipal;
        CustomOAuth2User oauth2User;
        if (authentication != null) {
            if(authentication.getPrincipal() instanceof UserDetails) {
                userPrincipal = (UserDetails) authentication.getPrincipal();
                PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
                model.addAttribute("person", person);
            }
            if(authentication.getPrincipal() instanceof CustomOAuth2User) {
                oauth2User = (CustomOAuth2User) authentication.getPrincipal();
                PersonDto person = this.personService.findByUsername(oauth2User.getEmail());
                model.addAttribute("person", person);
            }
        }
    }
}
