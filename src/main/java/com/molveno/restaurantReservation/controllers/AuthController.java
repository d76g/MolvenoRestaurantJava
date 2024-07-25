package com.molveno.restaurantReservation.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Controller
public class AuthController {

    @Autowired
    private LocaleResolver localeResolver;
    @GetMapping("/login")
    public String login(@RequestParam(value = "lang", required = false) String lang, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (lang != null) {
            Locale locale = new Locale(lang);
            localeResolver.setLocale(request, response, locale);
        }
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