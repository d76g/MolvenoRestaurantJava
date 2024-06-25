package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name="MenuCategory")
public class MenuCategory {
    @Id
    @GeneratedValue
    private long menuCategory_id;
    private String menuCategory_name;

    public MenuCategory() {
    }

    public MenuCategory(long menuCategory_id, String menuCategory_name) {
        this.menuCategory_id = menuCategory_id;
        this.menuCategory_name = menuCategory_name;
    }

    public long getMenuCategory_id() {
        return menuCategory_id;
    }

    public void setMenuCategory_id(long menuCategory_id) {
        this.menuCategory_id = menuCategory_id;
    }

    public String getMenuCategory_name() {
        return menuCategory_name;
    }

    public void setMenuCategory_name(String menuCategory_name) {
        this.menuCategory_name = menuCategory_name;
    }
}
