package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.models.MenuDTO;
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
    public ResponseEntity<Iterable<MenuDTO>> getAllMenu() {
        System.out.println("Inside getAllMenu");
        Iterable<MenuDTO> allMenu = menuService.listMenu();
        return ResponseEntity.ok(allMenu);
    }

    // Add new menu item
    @PostMapping(value = "/menu/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MenuDTO> addMenuItem(@RequestBody MenuDTO menuDto) {
        System.out.println("Inside addMenuItem");
        MenuDTO savedMenu = menuService.saveMenuItem(menuDto);
        return ResponseEntity.ok(savedMenu);
    }

    // Update menu item
    @PostMapping("/menu/update/{id}")
    public ResponseEntity<MenuDTO> updateMenuItem(@PathVariable long id, @RequestBody MenuDTO menuDto) {
        System.out.println("Inside updateMenuItem");
        menuDto.setMenuItem_id(id); // Ensure the ID is set
        MenuDTO updatedMenuItem = menuService.saveMenuItem(menuDto);
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


