package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.KitchenCategory;
import com.molveno.restaurantReservation.services.KitchenCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final KitchenCategoryService kitchenCategoryService;
    @Autowired
    public CategoryController(KitchenCategoryService kitchenCategoryService) {
        this.kitchenCategoryService = kitchenCategoryService;
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<KitchenCategory>> categoryList(Model model) {
         Iterable<KitchenCategory> categories = kitchenCategoryService.getKitchenCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/form")
    public String add(Model model) {
        model.addAttribute("kitchenCategory", new KitchenCategory());
        return "category/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute KitchenCategory kitchenCategory, Model model) {
        kitchenCategoryService.addKitchenCategory(kitchenCategory);
        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("kitchenCategory", kitchenCategoryService.getKitchenCategoryById(id));
        return "category/update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute KitchenCategory kitchenCategory, Model model) {
        kitchenCategoryService.updateKitchenCategory(kitchenCategory);
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        kitchenCategoryService.deleteKitchenCategory(id);
        return "redirect:/category";
    }






}
