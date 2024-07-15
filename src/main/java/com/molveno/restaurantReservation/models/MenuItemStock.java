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

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "kitchenStock_id")
    private KitchenStock kitchenStock;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Menu getMenuItem() {
        return menu;
    }

    public void setMenuItem(Menu menu) {
        this.menu = menu;
    }

    public KitchenStock getKitchenStock() {
        return kitchenStock;
    }

    public void setKitchenStock(KitchenStock kitchenStock) {
        this.kitchenStock = kitchenStock;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public MenuItemStock() {
    }
}
