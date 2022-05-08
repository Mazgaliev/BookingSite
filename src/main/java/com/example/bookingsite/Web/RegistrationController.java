package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Exception.InvalidUsernameOrPasswordException;
import com.example.bookingsite.Model.Exception.PasswordsDoNotMatchException;
import com.example.bookingsite.Model.Exception.UsernameAlreadyExistsException;
import com.example.bookingsite.Service.PersonService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final PersonService personService;

    public RegistrationController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String loadRegisterPage(Model model) {
        model.addAttribute("bodyContent", "registration");
        return "Master-Template";
    }
//TODO roles

    @PostMapping
    public String registerUser(@RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String repeatPassword,
                               @RequestParam String phoneNumber,
                               @RequestParam String role) {
        try {
            Role user_role = Role.getRoleFromString(role);
            System.out.println(user_role);
            this.personService.register(name, surname, username, password, repeatPassword, phoneNumber, user_role);
        } catch (InvalidUsernameOrPasswordException | PasswordsDoNotMatchException | UsernameAlreadyExistsException e) {
            String query = "?hasError=true&error=" + e.getMessage().replaceAll(" ", "+");
            return "redirect:/register" + query;
        }
        return "redirect:/login";
    }
}
