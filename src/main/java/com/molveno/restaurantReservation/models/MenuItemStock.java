package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="MenuItemStock")
public class MenuItemStock {
    @Id
    @GeneratedValue
    private long menuItem_id;
    private String stock_id;
    private double amount;


}
