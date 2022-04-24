package com.example.bookingsite.Web;

import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Service.AuthService;
import com.example.bookingsite.Service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final PersonService personService;
    private final AuthService authService;

    public LoginController(PersonService personService, AuthService authService) {
        this.personService = personService;
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {

        model.addAttribute("bodyContent", "login");

        return "Master-Template";
    }


    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, Model model) {
        Person person = null;
        try {
            person = this.authService.login(request.getParameter("username"), request.getParameter("password"));
            request.getSession().setAttribute("password", person);

            return "redirect:/login";
        } catch (PersonDoesNotExistException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }

    }
}
