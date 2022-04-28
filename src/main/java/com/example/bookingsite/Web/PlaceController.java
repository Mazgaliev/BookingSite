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
import org.springframework.security.core.parameters.P;
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
                              @RequestParam(required = false) Integer priceStandardRoom,
                              Model model) {
        try {
            if (pricePerNight == null) {
                this.placeService.createHotel(name, location, contactNumber, id, description, vipRooms, standardRooms, priceVipRoom, priceStandardRoom, images);
            } else {
                this.placeService.createVilla(name, location, contactNumber, id, description, pricePerNight, images);
            }
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }

        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public String viewPlace(@PathVariable Long id, Authentication authentication,
                            Model model,
                            @RequestParam(required = false) boolean hasError,
                            @RequestParam(required = false) String error) {

        getUserId(model, authentication);
        Place place;
        try {
            place = this.placeService.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
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
        model.addAttribute("ownerPhone", place.getOwner().getPhoneNumber());

        model.addAttribute("bodyContent", "place");

        return "Master-Template";
    }

    @GetMapping("/edit/{id}")
    public String editPlace(@PathVariable Long id, Authentication authentication,
                            Model model,
                            @RequestParam(required = false) boolean hasError,
                            @RequestParam(required = false) String error) {


        getUserId(model, authentication);
        Place place = new Place();
        try {
            place = this.placeService.findById(id).get();

            if (this.placeService.placeType(place) == PlaceType.VILLA) {
                Villa villa = (Villa) place;
                model.addAttribute("isVilla", true);
            } else {
                Hotel hotel = (Hotel) place;
                model.addAttribute("isVilla", false);
            }
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        model.addAttribute("hasError", hasError);
        model.addAttribute("error", error);
        model.addAttribute("images", place.getImages());
        model.addAttribute("place", place);

        model.addAttribute("bodyContent", "edit-place");

        return "Master-Template";
    }

    @PostMapping("/edit/{id}")
    public String updatePlace(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) String contactNumber,
                              @RequestParam(required = false) String location,
                              @PathVariable Long id,
                              @RequestParam(required = false) List<String> images,
                              @RequestParam(required = false) Integer pricePerNight,
                              @RequestParam(required = false) Integer vipRooms,
                              @RequestParam(required = false) Integer priceVipRoom,
                              @RequestParam(required = false) Integer standardRooms,
                              @RequestParam(required = false) Integer priceStandardRoom,
                              Model model) {


        Place place = this.placeService.findById(id).get();
        name = name == "" ? place.getName() : name;
        description = description == "" ? place.getDescription() : description;
        contactNumber = contactNumber == "" ? place.getContactNumber() : contactNumber;
        location = location == "" ? place.getLocation() : location;
        images = images.size() == 0 ? place.getImages() : images;
        try {
            if (this.placeService.placeType(place) == PlaceType.VILLA) {
                Villa villa = (Villa) place;
                pricePerNight = pricePerNight == null ? villa.getPricePerNight() : pricePerNight;
                this.placeService.updateVilla(id, name, location, contactNumber, place.getOwner().getId(), description, pricePerNight);
            } else {
                Hotel hotel = (Hotel) place;
                vipRooms = vipRooms == null ? hotel.getVipRooms() : vipRooms;
                priceVipRoom = priceVipRoom == null ? hotel.getPriceVipRoom() : priceVipRoom;
                standardRooms = standardRooms == null ? hotel.getStandardRooms() : standardRooms;
                priceStandardRoom = priceStandardRoom == null ? hotel.getPriceStandardRoom() : priceStandardRoom;
                this.placeService.updateHotel(id, name, location, contactNumber, place.getOwner().getId(), description, vipRooms, standardRooms, priceVipRoom, priceStandardRoom);
            }
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }

        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deletePlace(@PathVariable Long id, Model model) {
        try {
            this.placeService.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("getCause", "");
            model.addAttribute("exceptionMessage", "Cannot delete place when it still has reservations");
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        return "redirect:/person/places";
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
