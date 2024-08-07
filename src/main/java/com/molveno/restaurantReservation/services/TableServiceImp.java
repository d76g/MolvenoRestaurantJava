package com.molveno.restaurantReservation.services;


import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.repos.TableRepo;
import com.molveno.restaurantReservation.utils.TableValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableServiceImp implements TableService {

    @Autowired
    // define repository
    private TableRepo tableRepo;
    private static final int MAX_SEATS = 8;

    @Override
    // add new table
    public Table createTable(Table table) throws TableValidationException {
        validateTable(table);
        return tableRepo.save(table);
    }
    @Override
    // get all tables
    public List<Table> listTables() {
        return tableRepo.findAll();
    }
    @Override
    // get table by id
    public Table getTable(long id) {
        return tableRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Table not found"));
    }
    @Override
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
    @Override
    // delete table
    public void deleteTable(long id) {
        tableRepo.deleteById(id);
    }
    // get all tables by capacity

    // define methods to interact with repository
    // THIS METHODS ARE NOT USED IN THE CONTROLLER AND USED FOR VALIDATION PURPOSES
    private void validateTable(Table table) throws TableValidationException {
        if (table.getTableCapacity() > MAX_SEATS) {
            throw new TableValidationException("table-capacity-exceeded-max-seats-number" , "tableCapacity");
        }
        // check if table already exists
        if (tableRepo.existsByTableNumber(table.getTableNumber())) {
            throw new TableValidationException("table-already-exists", "tableNumber");
        }
        // table number should be greater than 0
        if (table.getTableNumber() <= 0) {
            throw new TableValidationException("table-number-should-be-greater-than-zero ", "tableNumber");
        }
        // table capacity should be greater than 0
        if (table.getTableCapacity() <= 0) {
            throw new TableValidationException("table-capacity-should-be-greater-than-zero", "tableCapacity");
        }
    }
    private void validateTableCapacity(int capacity) throws TableValidationException {
        if (capacity > MAX_SEATS) {
            throw new TableValidationException("table-capacity-exceeded-max-seats-number" , "tableCapacity");
        }
        // table capacity should be greater than 0
        if (capacity <= 0) {
            throw new TableValidationException("table-number-should-be-greater-than-zero ", "tableCapacity");
        }
    }
    // update table
    // NOT USED IN THE CONTROLLER BECAUSE IT IS NOT NEEDED
    //    public Table updateTable(long id, Table table) throws TableValidationException {
    //        table.setId(id);
    //        return tableRepo.save(table);
    //    }
}
