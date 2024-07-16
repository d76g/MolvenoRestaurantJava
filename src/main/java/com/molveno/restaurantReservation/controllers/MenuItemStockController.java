package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.DTO.MenuStockDTO;
import com.molveno.restaurantReservation.models.DTO.Response.MenuStockResponseDTO;
import com.molveno.restaurantReservation.models.MenuItemStock;
import com.molveno.restaurantReservation.services.MenuItemStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuItemStockController {
    @Autowired
    private MenuItemStockService menuItemStockService;

    @PostMapping(value = "/menuItemStock", produces = "application/json", consumes = "application/json")
        public ResponseEntity<MenuStockResponseDTO> add(@RequestBody MenuStockDTO menuItemStock) {
        MenuStockResponseDTO savedMenuItemStock = menuItemStockService.saveMenuStockItem(menuItemStock);
        return new ResponseEntity<>(savedMenuItemStock, HttpStatus.CREATED);
    }

    @GetMapping("/menu-item-stock")
        public ResponseEntity<Iterable<MenuStockResponseDTO>> getAll() {
        Iterable<MenuStockResponseDTO> menuItemStocks = menuItemStockService.getAllMenuItemStocks();
        return ResponseEntity.ok(menuItemStocks);
    }
    // get by menu id
    @GetMapping("/menu-item-stock/menu/{id}")
        public ResponseEntity<Iterable<MenuStockResponseDTO>> getByMenuItemId(@PathVariable long id) {
        Iterable<MenuStockResponseDTO> menuItemStocks = menuItemStockService.getAllByMenuItemId(id);
        return ResponseEntity.ok(menuItemStocks);
    }
    @DeleteMapping("/menu-item-stock/delete/{id}")
        public ResponseEntity<Void> delete(@PathVariable long id) {
        menuItemStockService.deleteMenuItemStock(id);
        return ResponseEntity.noContent().build();
    }
}
