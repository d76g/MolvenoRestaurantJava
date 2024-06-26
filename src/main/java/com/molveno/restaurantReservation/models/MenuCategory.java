package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="MenuCategory")
public class MenuCategory {
    @Id
    @GeneratedValue
    private long menuCategory_id;
    private String menuCategory_name;

    @OneToMany(mappedBy = "menuCategory", cascade = CascadeType.ALL)
    private Set<Menu> menu= new HashSet<>();

    public MenuCategory() {
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
