package com.molveno.restaurantReservation.controllers;


import com.molveno.restaurantReservation.models.SubCategory;
import com.molveno.restaurantReservation.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    // get all subCategories
    @GetMapping("/subCategory/all")
    public ResponseEntity<Iterable<SubCategory>> getAllSubCategory() {
        System.out.println("Inside getAllSubCategory");
        Iterable<SubCategory> allSubCategory = subCategoryService.listSubCategory();
        return ResponseEntity.ok(allSubCategory);
    }

    // Add new SubCategory
    @PostMapping(value = "/subCategory/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SubCategory> addSubCategory(@RequestBody SubCategory subCategory) {
        System.out.println("Inside addSubCategory");
        subCategory = subCategoryService.save(subCategory);
        return ResponseEntity.ok(subCategory);
    }

    // Update SubCategory
    @PostMapping("/subCategory/update/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable long id, @RequestBody SubCategory subCategory) {
        System.out.println("Inside updateSubCategory");
        SubCategory updatedSubCategory = subCategoryService.save(subCategory);
        if (updatedSubCategory != null) {
            return new ResponseEntity<>(updatedSubCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete SubCategory
    @DeleteMapping(value = "subCategory/{id}")
    public void deleteSubCategory(@PathVariable long id) {
        System.out.println("Inside deleteSubCategory");
        subCategoryService.deleteSubCategory(id);
    }
}
