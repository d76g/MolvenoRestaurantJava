package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@jakarta.persistence.Table(name = "restaurant_tables")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long table_id;
    private int table_number;
    private int table_capacity;
    /*
    Table Entity Contains a set of reservations.
    Uses @ManyToMany to establish the relationship.
    Uses @JoinTable to define the join table (table_reservation) with join columns (table_id and reservation_id).
     */
    @ManyToMany
    @JoinTable(
            name = "reserved_table",
            joinColumns = @JoinColumn(name = "table_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id")
    )
    private Set<Reservation> reservations = new HashSet<>();
    public long getTable_id() {
        return table_id;
    }
    public void setTable_id(long table_id) {
        this.table_id = table_id;
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
    public Set<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    public Table() {
    }

    // equals() and hashCode() methods for Table Entity to compare table_id with other tables.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return table_id == table.table_id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(table_id);
    }

    public Table(long table_id, int table_number, int table_capacity) {
        this.table_id = table_id;
        this.table_number = table_number;
        this.table_capacity = table_capacity;
    }
}
