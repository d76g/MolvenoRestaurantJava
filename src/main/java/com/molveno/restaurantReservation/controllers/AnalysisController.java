package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.repos.MenuRepo;
import com.molveno.restaurantReservation.repos.OrderRepo;
import com.molveno.restaurantReservation.repos.ReservationRepo;
import com.molveno.restaurantReservation.repos.TableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    @Autowired
    private ReservationRepo reservationRepo;
    @Autowired
    private TableRepo tableRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private MenuRepo menuRepo;

    // get the total number of reservations
    @GetMapping("/analysis/reservations")
    public ResponseEntity<Integer> getReservations() {
        int count = reservationRepo.findAll().size();
        return ResponseEntity.ok(count);
    }
    // get the total number of reservations for today
    @GetMapping("/analysis/reservations/today")
    public ResponseEntity<Integer> getReservationsToday() {
        LocalDate today = LocalDate.now();
        String currentDate = today.format(DateTimeFormatter.ISO_LOCAL_DATE);
        int count = reservationRepo.countReservationsForToday(currentDate);
        return ResponseEntity.ok(count);
    }
    // get total number of guests
    @GetMapping("/analysis/guests")
    public ResponseEntity<Integer> getGuests() {
        return ResponseEntity.ok(reservationRepo.countTotalGuests());
    }
    // get the total number of tables
    @GetMapping("/analysis/tables")
    public ResponseEntity<Integer> getTables() {
        int count = tableRepo.findAll().size();
        return ResponseEntity.ok(count);
    }
    // get the total number of orders
    @GetMapping("/analysis/orders")
    public ResponseEntity<Integer> getOrders() {
        int count = orderRepo.findAll().size();
        return ResponseEntity.ok(count);
    }
    // get the total revenue
    @GetMapping("/analysis/revenue")
    public ResponseEntity<Double> getRevenue() {
        double revenue = 0;
        for (int i = 0; i < orderRepo.findAll().size(); i++) {
            revenue += orderRepo.findAll().get(i).getTotal_price();
        }
        return ResponseEntity.ok(revenue);
    }
    // get the total number of menu items
    @GetMapping("/analysis/menu")
    public ResponseEntity<Integer> getMenu() {
        int count = menuRepo.findAll().size();
        return ResponseEntity.ok(count);
    }

    // get famous menu items based on ordering frequency
    @GetMapping("/analysis/menu/famous")
    public ResponseEntity<String> getFamousMenu() {
        Menu famousMenu = menuRepo.findMostOrderedMenuItem();
        String famous = famousMenu != null ? famousMenu.getItem_name() : "No orders yet";
        return ResponseEntity.ok(famous);
    }
}
