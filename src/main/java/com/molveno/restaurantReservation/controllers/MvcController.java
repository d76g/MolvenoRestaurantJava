package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.ReservationService;
import com.molveno.restaurantReservation.services.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MvcController {

    ReservationService reservationService;
    TableService tableService;

    // home page
    @GetMapping("/")
    public String index() {
        return "index";
    }
    // home page
    @GetMapping("/home")
    public String home() {
        return "home";
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

    // front desk
    @GetMapping("/reservations")
    public String showReservationList() {
        return "frontDesk/reservation/reservationList";
    }

    @GetMapping("/orders")
    public String getOrders() {
        return "common/menuOrdering/ordersList";
    }

    // admin
    @GetMapping("/tables")
    public String getTables(Model model) {
        model.addAttribute("table", new Table());
        return "admin/tableManagement/tableList";
    }
    @GetMapping("/users")
    public String getUsers() {
        return "admin/userManagement/usersList";
    }


    @GetMapping("/stock/form")
    public String add() {
        return "chef/stock/form";
    }

    @GetMapping("/stock/menu")
    public String manage() {
        return "chef/stock/menuStock";
    }

    @GetMapping("/stock")
    public String getStocks() {
        return "chef/stock/list";
    }

    @GetMapping("/stock/edit/{id}")
    public String edit(@PathVariable Long id) {
        return "chef/stock/update";
    }

    // kitchen categories URL
    @GetMapping("/stock/category")
    public String getCategories() {
        return "chef/category/list";
    }

    @GetMapping("/category/form")
    public String addCategory() {
        return "chef/category/form";
    }

    //menu
    @GetMapping("/menu")
    public String showMenuForm() {
        return "chef/menuManagement/menu/menuList";
    }
    //mealtime
    @GetMapping("/menu/mealtime")
    public String showMealTimeForm() {
        return "chef/menuManagement/mealTime/mealTimeList";
    }

    //subCategory
    @GetMapping("/menu/subCategory")
    public String showSubCategoryForm() {
        return "chef/menuManagement/subCategory/subCategoryList";
    }

    //menuCategory
    @GetMapping("/menu/menuCategory")
    public String showMenuCategoryForm() {
        return "chef/menuManagement/menuCategory/menuCategoryList";
    }
    //menuItemStock
    @GetMapping("/menuItemStock")
    public String showMenuItemStockForm() {
        return "chef/menuItemStock/menuItemStockList";
    }

}
