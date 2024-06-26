package com.molveno.restaurantReservation.services;


import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.repos.TableRepo;
import com.molveno.restaurantReservation.utils.TableValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    // define repository
    private TableRepo tableRepo;
    @Autowired
    public TableService(TableRepo tableRepo) {
        this.tableRepo = tableRepo;
    }
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
    public Table updateTable(long id, Table table) throws TableValidationException {
        validateTable(table);
        table.setTable_id(id);
        return tableRepo.save(table);
    }
    // delete table
    public void deleteTable(long id) {
        tableRepo.deleteById(id);
    }
    // get all tables by capacity

    // define methods to interact with repository
    private void validateTable(Table table) throws TableValidationException {
        if (table.getTable_capacity() > MAX_SEATS) {
            throw new TableValidationException("Table capacity exceeds maximum seats" + MAX_SEATS + " seats.", "table_capacity");
        }
        // check if table already exists
        if (tableRepo.existsById(table.getTable_number())) {
            throw new TableValidationException("Table already exists", "table_number");
        }
        // table number should be greater than 0
        if (table.getTable_number() <= 0) {
            throw new TableValidationException("Table number should be greater than 0", "table_number");
        }

        // table capacity should be greater than 0
        if (table.getTable_capacity() <= 0) {
            throw new TableValidationException("Table capacity should be greater than 0", "table_capacity");
        }
    }
}
