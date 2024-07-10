package com.molveno.restaurantReservation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name="MenuItemStock")
public class MenuItemStock {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    private double amount;
    

    

    public MenuItemStock() {
    }
}
