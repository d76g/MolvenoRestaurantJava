package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.ReservationService;
import com.molveno.restaurantReservation.services.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MvcController {

    ReservationService reservationService;
    TableService tableService;

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
        model.addAttribute("reservation", reservationService.getReservation(id));
        return "frontDesk/reservation/reservationUpdateForm";
    }
    @GetMapping("/reservation/editStatus/{id}")
    public String editReservationStatus(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.getReservation(id));
        return "frontDesk/reservation/reservationStatusForm";
    }
    // front desk home page
    @GetMapping("/frontDesk")
    public String getFrontDesk() {
        return "frontDesk/home";
    }

    @GetMapping("/frontDesk/orders")
    public String getOrders() {
        return "common/menuOrdering/ordersList";
    }


    // tables
    @GetMapping("/admin/tables")
    public String getTables(Model model) {
        model.addAttribute("table", new Table());
        return "admin/tableManagement/tableList";
    }
    @GetMapping("/admin/users")
    public String getUsers() {
        return "admin/userManagement/usersList";
    }


    //stock
    @GetMapping("/chef")
    public String home() {
        return "chef/home";
    }
    @GetMapping("/chef/stock/form")
    public String add() {
        return "chef/stock/form";
    }
    //stock
    @GetMapping("/chef/stock/menu")
    public String manage() {
        return "chef/stock/menuStock";
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


    //menu
    @GetMapping("/chef/menu/form")
    public String showMenuForm() {
        return "chef/menuManagement/menu/menuList";
    }
    //mealtime
    @GetMapping("/chef/menu/mealtime/form")
    public String showMealTimeForm() {
        return "chef/menuManagement/mealTime/mealTimeList";
    }

    //subCategory
    @GetMapping("/chef/menu/subCategory/form")
    public String showSubCategoryForm() {
        return "chef/menuManagement/subCategory/subCategoryList";
    }

    //menuCategory
    @GetMapping("/chef/menu/menuCategory/form")
    public String showMenuCategoryForm() {
        return "chef/menuManagement/menuCategory/menuCategoryList";
    }
    @GetMapping("/chef/order")
    public String showOrdersList() {
        return "common/menuOrdering/ordersList";
    }

    //menuItemStock
    @GetMapping("/chef/menuItemStock")
    public String showMenuItemStockForm() {
        return "chef/menuItemStock/menuItemStockList";
    }


}
