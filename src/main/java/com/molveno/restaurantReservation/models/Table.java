package com.molveno.restaurantReservation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@jakarta.persistence.Table(name = "table")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long table_id;
    private int table_number;
    private int table_capacity;


    public long getTable_id() {
        return table_id;
    }

    public int getTable_number() {
        return table_number;
    }

    public void setTable_number(int table_number) {
        this.table_number = table_number;
    }
    public int getTable_capacity() {
        return table_capacity;
    }
    public void setTable_capacity(int table_capacity) {
        this.table_capacity = table_capacity;
    }

    public Table() {
    }

    public Table(long table_id, int table_number, int table_capacity) {
        this.table_id = table_id;
        this.table_number = table_number;
        this.table_capacity = table_capacity;
    }
}
