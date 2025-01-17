package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.TableService;
import com.molveno.restaurantReservation.services.TableServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TableController {

    @Autowired
    private TableService tableService;

    // get all tables
    @GetMapping("/table/all")
    public ResponseEntity<Iterable<Table>> getTables() {
        Iterable<Table> tables = tableService.listTables();
        return ResponseEntity.ok(tables);
    }
    // Add new table
    @PostMapping("/table/add")
    public ResponseEntity<Table> addTable(@Valid @RequestBody Table table) {
        System.out.println(table.getTableCapacity());
        Table newTable = tableService.createTable(table);
        return new ResponseEntity<>(newTable, HttpStatus.CREATED);
    }
    // Update table
    @PostMapping("/table/update/{id}")
    public ResponseEntity<Table> updateTable(@PathVariable long id, @RequestBody Table table) {
        Table updatedTable = tableService.updateTableCapacity(id, table.getTableCapacity());
        if (updatedTable != null) {
            return new ResponseEntity<>(updatedTable, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Delete table
    @DeleteMapping("/table/delete/{id}")
    public ResponseEntity<Table> deleteTable(@PathVariable long id) {
        tableService.deleteTable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
