package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "reserved_table")
public class ReservedTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long table_id;
    private long reservation_id;

    @ManyToMany
    private Table table;
    @ManyToMany
    private Reservation reservation;
}
