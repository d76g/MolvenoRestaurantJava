package com.molveno.restaurantReservation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Roles {
    @Id
    @GeneratedValue
    private String role_id;
    private String role;

    public Roles(String role_id, String role) {
        this.role_id = role_id;
        this.role = role;
    }

    public Roles() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
