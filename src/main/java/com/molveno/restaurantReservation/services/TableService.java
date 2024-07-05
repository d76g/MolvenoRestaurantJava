package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.utils.TableValidationException;

import java.util.List;

public interface TableService {

    // add new table
    Table createTable(Table table) throws TableValidationException;

    // get all tables
    List<Table> listTables();

    // get table by id
    Table getTable(long id);

    Table updateTableCapacity(long id, int newCapacity);

    // delete table
    void deleteTable(long id);
}
