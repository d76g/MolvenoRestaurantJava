package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.KitchenCategory;
import com.molveno.restaurantReservation.services.KitchenCategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final KitchenCategoryServiceImp kitchenCategoryServiceImp;
    @Autowired
    public CategoryController(KitchenCategoryServiceImp kitchenCategoryServiceImp) {
        this.kitchenCategoryServiceImp = kitchenCategoryServiceImp;
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<KitchenCategory>> categoryList(Model model) {
         Iterable<KitchenCategory> categories = kitchenCategoryServiceImp.getKitchenCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/form")
    public String add(Model model) {
        model.addAttribute("kitchenCategory", new KitchenCategory());
        return "category/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute KitchenCategory kitchenCategory, Model model) {
        kitchenCategoryServiceImp.addKitchenCategory(kitchenCategory);
        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("kitchenCategory", kitchenCategoryServiceImp.getKitchenCategoryById(id));
        return "category/update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute KitchenCategory kitchenCategory, Model model) {
        kitchenCategoryServiceImp.updateKitchenCategory(kitchenCategory);
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        kitchenCategoryServiceImp.deleteKitchenCategory(id);
        return "redirect:/category";
    }






}
