package com.molveno.restaurantReservation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class KitchenCategory {
    @Id
    @GeneratedValue
    private long category_id;
    private String categoryName;


    @JsonIgnore
    @OneToMany (mappedBy = "category")
    private Set<KitchenStock> kitchenStock =new HashSet<>();

    public KitchenCategory() {
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<KitchenStock> getKitchenStock() {
        return kitchenStock;
    }

    public void setKitchenStock(Set<KitchenStock> kitchenStock) {
        this.kitchenStock = kitchenStock;
    }
}
