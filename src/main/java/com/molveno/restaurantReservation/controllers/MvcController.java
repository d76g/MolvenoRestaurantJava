package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.ReservationService;
import com.molveno.restaurantReservation.services.TableService;
import com.molveno.restaurantReservation.utils.TableValidationException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcController {

    ReservationService reservationService;
    TableService tableService;

    @GetMapping("/reservation/form")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "frontDesk/reservation/reservationForm";
    }
    @GetMapping("/reservation/edit/{id}")
    public String editReservation(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.getReservation(id));
        return "frontDesk/reservation/reservationUpdateForm";
    }
    @GetMapping("/reservation/editStatus/{id}")
    public String editReservationStatus(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.getReservation(id));
        return "frontDesk/reservation/reservationStatusForm";
    }

    // tables
    @GetMapping("/admin/tables")
    public String getTables(Model model) {
        model.addAttribute("table", new Table());
        return "admin/tableManagement/tableList";
    }
    //stock
    @GetMapping("/chef/stock/form")
    public String add() {
        return "chef/stock/form";
    }

    @GetMapping("/chef/stocks")
    public String getStocks() {
        return "chef/stock/list";
    }
    @GetMapping("/chef/stock/edit/{id}")
    public String edit(@PathVariable Long id) {
        return "chef/stock/update";
    }

    // kitchen categories URL
    @GetMapping("/chef/categories")
    public String getCategories() {
        return "chef/category/list";
    }
    @GetMapping("/chef/category/form")
    public String addCategory() {
        return "chef/category/form";
    }

}
