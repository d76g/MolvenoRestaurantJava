package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.services.KitchenStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StockController {
    @Autowired
    KitchenStockService kitchenStockService;
    
    @GetMapping( value = "/stock", produces = "application/json")
    public ResponseEntity<Iterable<KitchenStock>> getKitchenStock() {
        Iterable<KitchenStock> kitchenStocks = kitchenStockService.getKitchenStocks();
        return ResponseEntity.ok(kitchenStocks);
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
