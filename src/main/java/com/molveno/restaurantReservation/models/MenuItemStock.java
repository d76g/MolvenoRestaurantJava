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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menuItem_id")
    private Menu menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id")
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
        return menuItem;
    }

    public void setMenuItem(Menu menuItem) {
        this.menuItem = menuItem;
    }

    public KitchenStock getKitchenStock() {
        return kitchenStock;
    }

    public void setKitchenStock(KitchenStock kitchenStock) {
        this.kitchenStock = kitchenStock;
    }

    public MenuItemStock() {
    }
}
