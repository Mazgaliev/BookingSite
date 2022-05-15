package com.example.bookingsite.Web;

import com.example.bookingsite.Model.*;
import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.PlaceType;
import com.example.bookingsite.Service.ReviewService;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/place")
public class PlaceController {
    private final PersonService personService;

    private final PlaceService placeService;

    private final ReviewService reviewService;

    public PlaceController(PersonService personService, PlaceService placeService, ReviewService reviewService) {
        this.personService = personService;
        this.placeService = placeService;
        this.reviewService = reviewService;
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
        Place place;

        getUserId(model, authentication);

        place = this.placeService.findById(id).get();

        if (this.placeService.placeType(place) == PlaceType.VILLA) {
            Villa villa = (Villa) place;

            model.addAttribute("price1", villa.getPricePerNight());
            model.addAttribute("price2", 0);
        } else {
            Hotel hotel = (Hotel) place;
            model.addAttribute("price1", hotel.getPriceStandardRoom());
            model.addAttribute("price2", hotel.getPriceVipRoom());
        }
        List<Review> reviews = this.reviewService.findReviewsByPlaceId(id);
        model.addAttribute("hasError", hasError);
        model.addAttribute("error", error);
        model.addAttribute("images", place.getImages());
        model.addAttribute("place", place);
        model.addAttribute("ownerPhone", place.getOwner().getPhoneNumber());
        model.addAttribute("reviews", reviews.size() == 0 ? null : reviews);

        List<String> stars = new ArrayList<>();
        reviews.forEach(r -> {
            String string = "<span style='font-size:200%;color:red;'>";
            for (int star = (int) r.getRating(); star != 0; star--) {
                string = string + "&bigstar;";
            }
            string = string + "</span>";
            stars.add(string);
        });

        String placeStars = "<span style='font-size:200%;color:red;'>";
        if (place.getRating() != null) {
            for (int star = (int) Math.floor(place.getRating()); star != 0; star--) {
                placeStars = placeStars + "&bigstar;";
            }
            placeStars = placeStars + "</span>";

            model.addAttribute("reviewStars", placeStars);
            model.addAttribute("reviewsStars", stars);
        }
        model.addAttribute("bodyContent", "place");
        return "Master-Template";
    }

    @GetMapping("/edit/{id}")
    public String editPlace(@PathVariable Long id, Authentication authentication,
                            Model model,
                            @RequestParam(required = false) boolean hasError,
                            @RequestParam(required = false) String error) {

        Place place;
        try {
            getUserId(model, authentication);
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


        try {
            Place place = this.placeService.findById(id).get();

            name = Objects.equals(name, "") ? place.getName() : name;
            description = Objects.equals(description, "") ? place.getDescription() : description;
            contactNumber = Objects.equals(contactNumber, "") ? place.getContactNumber() : contactNumber;
            location = Objects.equals(location, "") ? place.getLocation() : location;

            if (this.placeService.placeType(place) == PlaceType.VILLA) {
                Villa villa = (Villa) place;
                pricePerNight = pricePerNight == null ? villa.getPricePerNight() : pricePerNight;
                this.placeService.updateVilla(id, name, location, contactNumber, place.getOwner().getId(), description, pricePerNight, images);
            } else {
                Hotel hotel = (Hotel) place;
                vipRooms = vipRooms == null ? hotel.getVipRooms() : vipRooms;
                priceVipRoom = priceVipRoom == null ? hotel.getPriceVipRoom() : priceVipRoom;
                standardRooms = standardRooms == null ? hotel.getStandardRooms() : standardRooms;
                priceStandardRoom = priceStandardRoom == null ? hotel.getPriceStandardRoom() : priceStandardRoom;
                this.placeService.updateHotel(id, name, location, contactNumber, place.getOwner().getId(), description, vipRooms, standardRooms, priceVipRoom, priceStandardRoom, images);
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
