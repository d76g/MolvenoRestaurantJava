package com.molveno.restaurantReservation.controllers;


import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.services.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @GetMapping("form")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "frontDesk/reservation/reservationForm";
    }

    @PostMapping("/createReservation")
    public String createReservation(Reservation reservation) {
        reservationService.createReservation(reservation);
        return "redirect:/reservations/list";
    }

    @GetMapping("/list")
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationService.listReservations());
        return "frontDesk/reservation/reservationList";
    }
    @GetMapping("/deleteReservation/{id}")
    public String deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/reservations/list";
    }

    @GetMapping("/editReservation/{id}")
    public String editReservation(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.getReservation(id));
        return "frontDesk/reservation/reservationUpdateForm";
    }

    @PostMapping("/updateReservation")
    public String updateReservation(Reservation reservation) {
        reservationService.updateReservation(reservation);
        return "redirect:/reservations/list";
    }
    @GetMapping("/editStatus/{id}")
    public String editReservationStatus(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.getReservation(id));
        return "frontDesk/reservation/reservationStatusForm";
    }
    @PostMapping("/updateStatus")
    public String updateReservationStatus(@RequestParam("id") long id, @RequestParam("reservationStatus") String reservationStatus) {
        reservationService.updateReservationStatus(id, reservationStatus);
        return "redirect:/reservations/list";
    }
}
