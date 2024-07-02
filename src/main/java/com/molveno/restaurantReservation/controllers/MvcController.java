package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.ReservationServiceImp;
import com.molveno.restaurantReservation.services.TableServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MvcController {

    ReservationServiceImp reservationServiceImp;
    TableServiceImp tableServiceImp;

    @GetMapping("/reservation/form")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "frontDesk/reservation/reservationForm";
    }
    @GetMapping("/reservation/list")
    public String showReservationList() {
        return "frontDesk/reservation/reservationList";
    }
    @GetMapping("/reservation/edit/{id}")
    public String editReservation(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationServiceImp.getReservation(id));
        return "frontDesk/reservation/reservationUpdateForm";
    }
    @GetMapping("/reservation/editStatus/{id}")
    public String editReservationStatus(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationServiceImp.getReservation(id));
        return "frontDesk/reservation/reservationStatusForm";
    }

    // tables
    @GetMapping("/admin/tables")
    public String getTables(Model model) {
        model.addAttribute("table", new Table());
        return "admin/tableManagement/tableList";
    }

}
