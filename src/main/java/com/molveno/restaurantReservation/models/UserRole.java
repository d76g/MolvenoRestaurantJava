package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table
public class UserRole {
    @Id
    @GeneratedValue
    private long role_id;
    private String role;

    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
