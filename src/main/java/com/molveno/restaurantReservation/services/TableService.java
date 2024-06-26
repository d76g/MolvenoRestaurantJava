package com.molveno.restaurantReservation.services;


import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.repos.TableRepo;
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

    // add new table
    public Table createTable(Table table) {
        return tableRepo.save(table);
    }
    // get all tables
    public List<Table> listTables() {
        return tableRepo.findAll();
    }
    // get table by id

    // update table

    // delete table

    // get all tables by capacity

    // define methods to interact with repository
}
