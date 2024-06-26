package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="SubCategory")
public class SubCategory {
    @Id
    @GeneratedValue
    private long subCategory_id;
    private String subCategory_name ;

    public SubCategory() {
    }

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
    private Set<Menu> menu= new HashSet<>();

    public long getSubCategory_id() {
        return subCategory_id;
    }

    public void setSubCategory_id(long subCategory_id) {
        this.subCategory_id = subCategory_id;
    }

    public String getSubCategory_name() {
        return subCategory_name;
    }

    public void setSubCategory_name(String subCategory_name) {
        this.subCategory_name = subCategory_name;
    }
}
