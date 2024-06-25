package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue
    private String user_id;
    private String  user_name;
    private String email ;
    private String password ;
    private String  role_id;

   // @OneToMany(mappedBy = "Users")
    //private Set< >

    public Users() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}


