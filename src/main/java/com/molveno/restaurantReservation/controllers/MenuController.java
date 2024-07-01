package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.services.MenuService;
import com.molveno.restaurantReservation.utils.MenuValidationException;
import com.molveno.restaurantReservation.utils.TableValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// set route to /Menu
@RequestMapping("api/chef/menu")
public class MenuController {
    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // get all Menu
    @GetMapping("/getAllMenu")
    public ResponseEntity<Iterable<Menu>> getAllMenu() {
        List<Menu> menuItems= menuService.getAllMenu();
        return ResponseEntity.ok(menuItems);
    }

    // Add new table
    @PostMapping("/addMenuItem")
    public String addMenuItem(@ModelAttribute("menu") @Valid Menu menu, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // If there are validation errors, return to the form with error messages
            return "chef/menu/menuList";
        }
        try {
            menuService.createMenuItem(menu);
        } catch (MenuValidationException e) {
            // Catch any custom validation exceptions from service layer
            if ("menuItem_id".equals(e.getField())) {
                result.rejectValue("menuItem_id", "error.menu", e.getMessage());
            }
//        } else if ("tableCapacity".equals(e.getField())) {
//            result.rejectValue("tableCapacity", "error.table", e.getMessage());
//        } else if ("tableNumber".equals(e.getField()) && "tableCapacity".equals(e.getField())) {
//            result.rejectValue("tableNumber", "error.table", e.getMessage());
//            result.rejectValue("tableCapacity", "error.table", e.getMessage());
//        }
            else {
                result.rejectValue("menuItem_id", "error.menu", "Error occurred with menu validation.");
                //result.rejectValue("tableCapacity", "error.table", "Error occurred with table validation.");
            }
            return "chef/menu/menuList";
        }
        return "redirect:/chef/menu";
    }

    // Update menu
    @PostMapping("/updateMenu/{id}")
    public String updateMenu(@PathVariable long id, @ModelAttribute Menu menu, BindingResult result, Model model) {
        try {
            menuService.updateMenuItem(id);
        } catch (TableValidationException e) {
            // show error messages
//            if ("tableCapacity".equals(e.getField())) {
//                result.rejectValue("tableCapacity", "error.table", e.getMessage());
//            } else {
//                result.rejectValue("tableId", "error.table", e.getMessage());
//            }
//            model.addAttribute("tables", tableService.listTables());
//            return "admin/tableManagement/tableList";
        }
        return "redirect:/chef/menu";
    }

    @GetMapping("/menu/{id}")
    public String deleteMenuItem(@PathVariable long id) {
        menuService.deleteMenuItem(id);
        return "redirect:/chef/menu";
    }

}


