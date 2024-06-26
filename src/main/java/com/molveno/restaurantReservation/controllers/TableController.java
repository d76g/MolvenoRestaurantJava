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
    public String addTable(@ModelAttribute("table") @Valid Table table, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // If there are validation errors, return to the form with error messages
            return "admin/tableManagement/addForm";
        }

        try {
            tableService.createTable(table);
        } catch (TableValidationException e) {
            // Catch any custom validation exceptions from service layer
            if ("table_number".equals(e.getField())) {
                result.rejectValue("table_number", "error.table", e.getMessage());
            } else if ("table_capacity".equals(e.getField())) {
                result.rejectValue("table_capacity", "error.table", e.getMessage());
            } else if ("table_number".equals(e.getField()) && "table_capacity".equals(e.getField())) {
                result.rejectValue("table_number", "error.table", e.getMessage());
                result.rejectValue("table_capacity", "error.table", e.getMessage());
            }
            else {
                result.rejectValue("table_number", "error.table", "Error occurred with table validation.");
                result.rejectValue("table_capacity", "error.table", "Error occurred with table validation.");
            }
            return "admin/tableManagement/addForm";
        }

        // If successful, redirect to list of tables
        return "redirect:/tables";
    }

    // Update table
    @GetMapping("/editTable/{id}")
    public String editTable(@PathVariable long id, Model model) {
        Table table = tableService.getTable(id);
        model.addAttribute("table", table);
        return "admin/tableManagement/updateForm";
    }

    @PostMapping("/updateTable/{id}")
    public String updateTable(@PathVariable long id, @ModelAttribute Table table, BindingResult result, Model model) {
        try {
            tableService.updateTable(id, table);
        } catch (TableValidationException e) {
            // show error messages
//            if(id != table.getTable_number()){
//                result.rejectValue("table_number", "error.table", "Table number cannot be changed.");
//                result.rejectValue("table_capacity", "error.table", e.getMessage());
//            } else {
//                if ("table_number".equals(e.getField())) {
//                    result.rejectValue("table_number", "error.table", e.getMessage());
//                    result.rejectValue("table_capacity", "error.table", "Invalid table capacity.");
//                } else if ("table_capacity".equals(e.getField())) {
//                    result.rejectValue("table_capacity", "error.table", e.getMessage());
//                    result.rejectValue("table_number", "error.table", "Invalid table number.");
//                }
//            }
            model.addAttribute("tables", tableService.listTables());
                return "admin/tableManagement/updateForm";
        }
        return "redirect:/tables";
    }

    // Delete table
    @GetMapping("/deleteTable/{id}")
    public String deleteTable(@PathVariable long id) {
        tableService.deleteTable(id);
        return "redirect:/tables";
    }
}
