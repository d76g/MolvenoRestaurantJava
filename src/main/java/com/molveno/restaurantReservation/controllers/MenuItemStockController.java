package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.MenuItemStock;
import com.molveno.restaurantReservation.services.MenuItemStockService;
import com.molveno.restaurantReservation.services.MenuItemStockServiceImpl;
import com.molveno.restaurantReservation.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MenuItemStockController {
    @Autowired
    private MenuItemStockService menuItemStockService;

    @GetMapping(value = "/menu-item-stock/list", produces = "application/json")
    public ResponseEntity<Iterable<MenuItemStock>> menuItemStockList() {
        Iterable<MenuItemStock> menuItemStocks = menuItemStockService.getMenuItemStocks();
        return ResponseEntity.ok(menuItemStocks);
    }

    @PostMapping(value = "/menu-item-stock/save", produces = "application/json", consumes = "application/json")
    public ResponseEntity<MenuItemStock> add(@RequestBody MenuItemStock menuItemStock) {
        MenuItemStock newMenuItemStock = menuItemStockService.addMenuItemStock(menuItemStock);
        return new ResponseEntity<>(newMenuItemStock, HttpStatus.CREATED);
    }

    @DeleteMapping("/menu-item-stock/delete/{id}")
    public ResponseEntity<MenuItemStock> delete(@PathVariable Long id) {
        menuItemStockService.deleteMenuItemStock(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/menu-item-stock/{menuItemName}")
    public String placeOrder(@PathVariable String menuItemName) {
        try {
            menuItemStockService.placeOrder(menuItemName);
            return menuItemName + " order placed successfully.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
