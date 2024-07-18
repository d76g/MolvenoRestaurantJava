package com.molveno.restaurantReservation.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "common/login";
    }

    // http://localhost:9090/login-fail
    @GetMapping("/login-fail")
    public String loginFailPage(Model model) {

        System.out.println("Inside loginFailPage");
        model.addAttribute("loginError", true);
        return "common/login";
    }
}