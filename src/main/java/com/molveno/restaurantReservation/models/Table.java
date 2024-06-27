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
    @Column(name = "table_id")
    private long Id;
    @Column(name = "table_number")
    private int tableNumber;
    @Column(name = "table_capacity")
    private int tableCapacity;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableCapacity() {
        return tableCapacity;
    }

    public void setTableCapacity(int tableCapacity) {
        this.tableCapacity = tableCapacity;
    }

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
        return Id == table.Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
