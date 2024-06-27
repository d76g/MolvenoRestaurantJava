package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.TableService;
import com.molveno.restaurantReservation.utils.TableValidationException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
// set route to /TABLES
// GUYS, PLEASE CHANGE THIS IN EVERY CONTROLLER
@RequestMapping("/admin/tables")
public class TableController {

    final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // get all tables
    @GetMapping("")
    public String getTables(Model model) {
        // the first one it to show the list of tables
        model.addAttribute("tables", tableService.listTables());
        // this is to add a new table using the form
        model.addAttribute("table", new Table());
        // return the view using the template not the URL
        return "admin/tableManagement/tableList";
    }
    // Add new table
    @PostMapping("/addTable")
    public String addTable(@ModelAttribute("table") @Valid Table table, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // If there are validation errors, return to the form with error messages
            return "admin/tableManagement/tableList";
        }
        try {
            tableService.createTable(table);
        } catch (TableValidationException e) {
            // Catch any custom validation exceptions from service layer
            if ("tableNumber".equals(e.getField())) {
                result.rejectValue("tableNumber", "error.table", e.getMessage());
            } else if ("tableCapacity".equals(e.getField())) {
                result.rejectValue("tableCapacity", "error.table", e.getMessage());
            } else if ("tableNumber".equals(e.getField()) && "tableCapacity".equals(e.getField())) {
                result.rejectValue("tableNumber", "error.table", e.getMessage());
                result.rejectValue("tableCapacity", "error.table", e.getMessage());
            }
            else {
                result.rejectValue("tableNumber", "error.table", "Error occurred with table validation.");
                result.rejectValue("tableCapacity", "error.table", "Error occurred with table validation.");
            }
            return "admin/tableManagement/tableList";
        }

        // If successful, redirect to list of tables
        return "redirect:/admin/tables";
    }

    // Update table
    @PostMapping("/updateTable/{id}")
    public String updateTable(@PathVariable long id, @ModelAttribute Table table, BindingResult result, Model model) {
        try {
            tableService.updateTableCapacity(id, table.getTableCapacity());
        } catch (TableValidationException e) {
            // show error messages
            if ("tableCapacity".equals(e.getField())) {
                result.rejectValue("tableCapacity", "error.table", e.getMessage());
            } else {
                result.rejectValue("tableId", "error.table", e.getMessage());
            }
            model.addAttribute("tables", tableService.listTables());
                return "admin/tableManagement/tableList";
        }
        return "redirect:/admin/tables";
    }
    // Delete table
    @GetMapping("/deleteTable/{id}")
    public String deleteTable(@PathVariable long id) {
        tableService.deleteTable(id);
        return "redirect:/admin/tables";
    }
}
