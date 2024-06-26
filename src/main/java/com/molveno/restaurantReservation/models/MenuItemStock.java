package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name="MenuItemStock")
public class MenuItemStock {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private KitchenStock kitchenStock;
    private double amount;

    public MenuItemStock() {
    }
}
