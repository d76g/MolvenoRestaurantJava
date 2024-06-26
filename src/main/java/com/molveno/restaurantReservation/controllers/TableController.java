package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
// set route to /reservation
@RequestMapping("/tables")
public class TableController {

    final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // get all tables
    @GetMapping("")
    public String getTables(Model model) {
        model.addAttribute("tables", tableService.listTables());
        return "admin/tableManagement/tableList";
    }

    @GetMapping("/form")
    public String getTableForm(Model model) {
        model.addAttribute("table", new Table());
        return "admin/tableManagement/addForm";
    }
    // Add new table
    @PostMapping("/addTable")
    public String addTable(@ModelAttribute Table table, Model model) {
        model.addAttribute("table", tableService.createTable(table));
        return "redirect:/tables";
    }
}
