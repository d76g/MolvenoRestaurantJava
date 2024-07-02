package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MenuController {
    @Autowired
    private MenuService menuService;

    // get all menu
    @GetMapping("/menu/all")
    public ResponseEntity<Iterable<Menu>> getAllMenu() {
        System.out.println("Inside getAllMenu");
        Iterable<Menu> allMenu = menuService.listMenu();
        return ResponseEntity.ok(allMenu);
    }

    // Add new menu item
    @PostMapping(value = "/menu/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Menu> addMenuItem(@RequestBody Menu menu) {
        System.out.println("Inside addMenuItem");
        menu = menuService.saveMenuItem(menu);
        return ResponseEntity.ok(menu);
    }

    // Update menuItem
    @PostMapping("/menu/update/{id}")
    public ResponseEntity<Menu> updateMenuItem(@PathVariable long id, @RequestBody Menu menu) {
        System.out.println("Inside updateMenuItem");
        Menu updatedMenuItem = menuService.saveMenuItem(menu);
        if (updatedMenuItem != null) {
            return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete menuItem
    @DeleteMapping(value = "menu/{id}")
    public void deleteMenuItem(@PathVariable long id) {
        System.out.println("Inside deleteMenuItem");
        menuService.deleteMenuItem(id);
    }
}


