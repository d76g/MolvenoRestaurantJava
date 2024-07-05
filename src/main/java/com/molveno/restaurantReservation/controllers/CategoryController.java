package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.KitchenCategory;
import com.molveno.restaurantReservation.services.KitchenCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    KitchenCategoryService kitchenCategoryService;

    @GetMapping(value = "/category/list", produces = "application/json")
    public ResponseEntity<Iterable<KitchenCategory>> categoryList() {
         Iterable<KitchenCategory> categories = kitchenCategoryService.getKitchenCategories();
        return ResponseEntity.ok(categories);
    }
    
    @PostMapping(value = "/category/save", produces = "application/json", consumes = "application/json")
    public ResponseEntity<KitchenCategory> add(@RequestBody KitchenCategory kitchenCategory) {
        KitchenCategory newKitchenCategory = kitchenCategoryService.addKitchenCategory(kitchenCategory);
        return  new ResponseEntity<>(newKitchenCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<KitchenCategory> delete(@PathVariable Long id) {
        kitchenCategoryService.deleteKitchenCategory(id);
        return ResponseEntity.noContent().build();
    }
}
