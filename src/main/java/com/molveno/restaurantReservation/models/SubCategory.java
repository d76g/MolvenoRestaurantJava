package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name="SubCategory")
public class SubCategory {
    @Id
    @GeneratedValue
    private long subCategory_id;
    private String subCategory_name ;

    public SubCategory() {
    }

    public SubCategory(long subCategory_id, String subCategory_name) {
        this.subCategory_id = subCategory_id;
        this.subCategory_name = subCategory_name;
    }

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
