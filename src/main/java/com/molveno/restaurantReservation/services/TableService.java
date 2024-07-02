package com.molveno.restaurantReservation.services;


import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.repos.TableRepo;
import com.molveno.restaurantReservation.utils.TableValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    // define repository
    private TableRepo tableRepo;
    private static final int MAX_SEATS = 8;

    // add new table
    public Table createTable(Table table) throws TableValidationException {
        validateTable(table);
        return tableRepo.save(table);
    }
    // get all tables
    public List<Table> listTables() {
        return tableRepo.findAll();
    }
    // get table by id
    public Table getTable(long id) {
        return tableRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Table not found"));
    }
    // update table
    // NOT USED IN THE CONTROLLER BECAUSE IT IS NOT NEEDED
    //    public Table updateTable(long id, Table table) throws TableValidationException {
    //        table.setId(id);
    //        return tableRepo.save(table);
    //    }
    public Table updateTableCapacity(long id, int newCapacity) {
        Optional<Table> optionalTable = tableRepo.findById(id);
        if (optionalTable.isPresent()) {
            Table table = optionalTable.get();
            validateTableCapacity(newCapacity);
            table.setTableCapacity(newCapacity);
            return tableRepo.save(table);
        } else {
            return null; // or throw an exception
        }
    }
    // delete table
    public void deleteTable(long id) {
        tableRepo.deleteById(id);
    }
    // get all tables by capacity

    // define methods to interact with repository
    // THIS METHODS ARE NOT USED IN THE CONTROLLER AND USED FOR VALIDATION PURPOSES
    private void validateTable(Table table) throws TableValidationException {
        if (table.getTableCapacity() > MAX_SEATS) {
            throw new TableValidationException("Table capacity exceeds maximum seats " + MAX_SEATS + " seats.", "tableCapacity");
        }
        // check if table already exists
        if (tableRepo.existsByTableNumber(table.getTableNumber())) {
            throw new TableValidationException("Table already exists", "tableNumber");
        }
        // table number should be greater than 0
        if (table.getTableNumber() <= 0) {
            throw new TableValidationException("Table number should be greater than 0 ", "tableNumber");
        }
        // table capacity should be greater than 0
        if (table.getTableCapacity() <= 0) {
            throw new TableValidationException("Table capacity should be greater than 0", "tableCapacity");
        }
    }
    private void validateTableCapacity(int capacity) throws TableValidationException {
        if (capacity > MAX_SEATS) {
            throw new TableValidationException("Table capacity exceeds maximum seats " + MAX_SEATS + " seats.", "tableCapacity");
        }
        // table capacity should be greater than 0
        if (capacity <= 0) {
            throw new TableValidationException("Table capacity should be greater than 0 ", "tableCapacity");
        }
    }
}
