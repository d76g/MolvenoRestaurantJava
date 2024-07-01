package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final ReservationService reservationService;

    public HomeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @GetMapping({"/home", "/"})
    public String home(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "index";
    }
    @PostMapping("/createReservation")
    public String createReservation(Reservation reservation, RedirectAttributes redirectAttributes) {
        reservationService.createReservation(reservation);
        redirectAttributes.addAttribute("id", reservation.getId());
        return "redirect:/reservationDetails";
    }
    @GetMapping("/reservationDetails")
    public String reservationDetails(@RequestParam long id, Model model) {
        model.addAttribute("reservation", reservationService.getReservation(id));
        return "common/reservation/reservationInfo";
    }
}
