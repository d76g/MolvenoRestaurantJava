package com.molveno.restaurantReservation.controllers;


import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @PostMapping(value = "/reservation/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Reservation> createReservation(@RequestBody  Reservation reservation) {
        Reservation newReservation = reservationService.createReservation(reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }
    @GetMapping(value = "/reservation/all", produces = "application/json")
    public ResponseEntity<Iterable<Reservation>> listReservations(Model model) {
        Iterable<Reservation> reservations = reservationService.listReservations();
        return ResponseEntity.ok(reservations);
    }
    @DeleteMapping("/reservation/delete/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reservation/update/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable long id,@RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservation);
        if (updatedReservation != null) {
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reservation/{id}/updateStatus")
    public ResponseEntity<Table> updateReservationStatus(@PathVariable long id, @RequestBody String reservationStatus) {
        Reservation updatedReservation = reservationService.updateReservationStatus(id, reservationStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
