package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.services.KitchenStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stock")
public class StockController {
    private final KitchenStockService kitchenStockService;
    @Autowired
    public StockController(KitchenStockService kitchenStockService) {
        this.kitchenStockService = kitchenStockService;
    }
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("kitchenStocks", kitchenStockService.getKitchenStocks());
        return "chef/stock/index";
    }

    @GetMapping("/form")
    public String add(Model model) {
        model.addAttribute("kitchenStock", new KitchenStock());
        return "chef/stock/form";
    }
  @PostMapping("/add")
    public String add(@ModelAttribute KitchenStock kitchenStock, Model model) {

        kitchenStockService.addKitchenStock(kitchenStock);
      return "redirect:/stock";
   }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("kitchenStock", kitchenStockService.addKitchenStockById(id));
        return "chef/stock/update";
    }

    @PostMapping("/update")

    public String update(@ModelAttribute KitchenStock kitchenStock, Model model) {
        kitchenStockService.updateKitchenStock(kitchenStock);
        return "redirect:/stock";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        kitchenStockService.deleteKitchenStock(id);
        return "redirect:/stock";
    }


}
