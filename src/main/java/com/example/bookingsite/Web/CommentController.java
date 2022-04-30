package com.example.bookingsite.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comment")
public class CommentController {


    @PostMapping
    public String comment() {

        return "redirect:/home";
    }

}
