package com.example.bookingsite.Web;

import com.example.bookingsite.Model.CustomOAuth2User;
import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Service.PersonService;
import com.example.bookingsite.Service.PlaceService;
import com.example.bookingsite.Service.ReviewService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private final PlaceService placeService;
    private final PersonService personService;

    private final ReviewService reviewService;

    public ReviewController(PlaceService placeService, PersonService personService, ReviewService reviewService) {
        this.placeService = placeService;
        this.personService = personService;
        this.reviewService = reviewService;
    }


    @GetMapping("/{id}")
    public String viewPage(@PathVariable Long id, Model model, Authentication authentication) {
        Place place;

        try {
            getUserId(model, authentication);
            place = this.placeService.findById(id).get();
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }
        model.addAttribute("placeName", place.getName());
        model.addAttribute("placeId", place.getId());

        model.addAttribute("bodyContent", "create-review");
        return "Master-Template";
    }

    @PostMapping
    public String review(@RequestParam String description, @RequestParam Short rating, @RequestParam Long placeId, @RequestParam Long personId, Model model) {

        try {
            this.reviewService.createReview(personId, placeId, description, rating);
        } catch (Exception e) {
            model.addAttribute("getCause", e.getCause());
            model.addAttribute("exceptionMessage", e.getMessage());
            model.addAttribute("bodyContent", "error-template");
            return "Master-Template";
        }

        return "redirect:/place/" + placeId;
    }


    private Long getUserId(Model model, Authentication authentication) {
        UserDetails userPrincipal;
        CustomOAuth2User oauth2User;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                userPrincipal = (UserDetails) authentication.getPrincipal();
                PersonDto person = this.personService.findByUsername(userPrincipal.getUsername());
                model.addAttribute("person", person);
                return person.getId();
            }
            if (authentication.getPrincipal() instanceof CustomOAuth2User) {
                oauth2User = (CustomOAuth2User) authentication.getPrincipal();
                PersonDto person = this.personService.findByUsername(oauth2User.getEmail());
                model.addAttribute("person", person);
                return person.getId();
            }
        } else {
            throw new AccessDeniedException("You are not authorized to access this page");
        }
        return 0L;
    }
}
