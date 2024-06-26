package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.services.KitchenStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StockController {
    private final KitchenStockService kitchenStockService;
    @Autowired
    public StockController(KitchenStockService kitchenStockService) {
        this.kitchenStockService = kitchenStockService;
    }
    @GetMapping("/Stock")
    public String home(Model model) {
        return "Stock";
    }

    @GetMapping("/Stock/add")
    public String add(Model model) {
        return "Stock/add";
    }
//    @PostMapping("/Stock/add")
//    public String add(@ModelAttribute KitchenStock kitchenStock, Model model) {
//        return "redirect:/Stock";
//    }
}
