package com.molveno.restaurantReservation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="MenuItemStock")
public class MenuItemStock {
    @Id
    @GeneratedValue
    private long menuItem_id;
    private String stock_id;
    private double amount;
}
