package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.services.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chef/menu")
public class MenuCagetoryController {
    final MenuCategoryService menuCategoryService;
    @Autowired
    public MenuCagetoryController(MenuCategoryService menuCategoryService) {
        this.menuCategoryService = menuCategoryService;
    }
}
