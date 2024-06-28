package com.molveno.restaurantReservation.controllers;

import ch.qos.logback.core.model.Model;
import com.molveno.restaurantReservation.services.KitchenCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
    private final KitchenCategoryService kitchenCategoryService;
    @Autowired
    public CategoryController(KitchenCategoryService kitchenCategoryService) {
        this.kitchenCategoryService = kitchenCategoryService;
    }

    @GetMapping("/Category")
    public String home(Model model) {
        return "Category";
    }


}
