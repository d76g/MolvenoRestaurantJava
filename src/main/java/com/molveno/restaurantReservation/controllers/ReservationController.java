package com.molveno.restaurantReservation.controllers;


import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @PostMapping("/reservation/create")
    public String createReservation(Reservation reservation) {
        reservationService.createReservation(reservation);
        return "redirect:/reservations/list";
    }
    @GetMapping("/reservation/list")
    public ResponseEntity<Iterable<Reservation>> listReservations(Model model) {
        Iterable<Reservation> reservations = reservationService.listReservations();
        return ResponseEntity.ok(reservations);
    }
    @GetMapping("/reservation/delete/{id}")
    public String deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/reservations/list";
    }

    @PostMapping("/reservation/update")
    public String updateReservation(Reservation reservation) {
        reservationService.updateReservation(reservation);
        return "redirect:/reservations/list";
    }

    @PostMapping("/reservation/updateStatus")
    public String updateReservationStatus(@RequestParam("id") long id, @RequestParam("reservationStatus") String reservationStatus) {
        reservationService.updateReservationStatus(id, reservationStatus);
        return "redirect:/reservations/list";
    }
}
