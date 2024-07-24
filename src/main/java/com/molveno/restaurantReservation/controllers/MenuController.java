package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.DTO.MenuDTO;
import com.molveno.restaurantReservation.models.MealTime;
import com.molveno.restaurantReservation.services.MenuService;
import com.molveno.restaurantReservation.services.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MenuController {
    @Autowired
    private MenuServiceImpl menuService;

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

    // delete menuItem
    @DeleteMapping(value = "menu/{id}")
    public void deleteMenuItem(@PathVariable long id) {
        System.out.println("Inside deleteMenuItem");
        menuService.deleteMenuItem(id);
    }


    // get menu item base on meal time
    @GetMapping(value = "/menu/{mealTime}")
    public ResponseEntity<Iterable<MenuDTO>> getMenuByMealTime(@PathVariable String mealTime) {
        System.out.println("Inside getMenuByMealTime");
        Iterable<MenuDTO> menuByMealTime = menuService.getMenuByMealTime(mealTime);
        return ResponseEntity.ok(menuByMealTime);
    }
}


