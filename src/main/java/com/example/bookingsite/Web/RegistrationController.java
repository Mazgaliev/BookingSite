package com.example.bookingsite.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    //TODO implement registration
    @GetMapping
    public String loadRegisterPage() {


        return null;
    }

    @PostMapping
    public String registerUser() {

        return "redirect:/login";
    }
}
