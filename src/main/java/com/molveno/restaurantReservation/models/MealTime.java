package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="MealTime")
public class MealTime {
    @Id
    @GeneratedValue
    private long menuCategory_id;
    private String menuCategory_name;

    @OneToMany(mappedBy = "mealTime", cascade = CascadeType.ALL)
    private List<Menu> menu= new ArrayList<>();

    public MealTime() {
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
