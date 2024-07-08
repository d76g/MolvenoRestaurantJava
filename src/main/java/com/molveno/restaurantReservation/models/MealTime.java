package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="MealTime")
public class MealTime {
    @Id
    @GeneratedValue
    private long mealTime_id;
    private String mealTime_name;

    @OneToMany(mappedBy = "mealTime", cascade = CascadeType.ALL)
    private Set<Menu> menu= new HashSet<>();

    public MealTime() {
    }

    public long getMealTime_id() {
        return mealTime_id;
    }

    public void setMealTime_id(long mealTime_id) {
        this.mealTime_id = mealTime_id;
    }

    public String getMealTime_name() {
        return mealTime_name;
    }

    public void setMealTime_name(String mealTime_name) {
        this.mealTime_name = mealTime_name;
    }
}
