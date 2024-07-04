package com.molveno.restaurantReservation.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "category_id")
public class KitchenCategory {
    @Id
    @GeneratedValue
    private long category_id;
    private String categoryName;

    @OneToMany (mappedBy = "category", cascade = CascadeType.ALL)
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
