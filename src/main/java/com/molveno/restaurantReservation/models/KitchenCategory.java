package com.molveno.restaurantReservation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class KitchenCategory {
    @Id
    @GeneratedValue
    private Long category_id;
    private String categoryName;

    @OneToMany (mappedBy = "category")
    private Set<KitchenStock> kitchenStock =new HashSet<>();

    public KitchenCategory() {
    }

    public KitchenCategory(Long category_id, String categoryName, Set<KitchenStock> kitchenStock) {
        this.category_id = category_id;
        this.categoryName = categoryName;
        this.kitchenStock = kitchenStock;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
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
