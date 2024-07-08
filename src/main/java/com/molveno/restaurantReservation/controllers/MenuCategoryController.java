package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.MenuCategory;
import com.molveno.restaurantReservation.services.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MenuCategoryController {

    @Autowired
    private MenuCategoryService menuCategoryService;

    // get all menuCategory
    @GetMapping("/menuCategory/all")
    public ResponseEntity<Iterable<MenuCategory>> getAllMenuCategory() {
        System.out.println("Inside getAllMenuCategory");
        Iterable<MenuCategory> allMenuCategory = menuCategoryService.listMenuCategory();
        return ResponseEntity.ok(allMenuCategory);
    }

    // Add new menu category
    @PostMapping(value = "/menuCategory/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MenuCategory> addMenuCategory(@RequestBody MenuCategory menuCategory) {
        System.out.println("Inside addMenuCategory");
        menuCategory = menuCategoryService.save(menuCategory);
        return ResponseEntity.ok(menuCategory);
    }

    // Update menuCategory
    @PostMapping("/menuCategory/update/{id}")
    public ResponseEntity<MenuCategory> updateMenuCategory(@PathVariable long id, @RequestBody MenuCategory menuCategory) {
        System.out.println("Inside updateMenuCategory");
        MenuCategory updatedMenuCategory = menuCategoryService.save(menuCategory);
        if (updatedMenuCategory != null) {
            return new ResponseEntity<>(updatedMenuCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "menuCategory/{id}")
    public void deleteMenuCategory(@PathVariable long id) {
        System.out.println("Inside deleteMenuCategory");
        menuCategoryService.deleteMenuCategory(id);
    }
}
