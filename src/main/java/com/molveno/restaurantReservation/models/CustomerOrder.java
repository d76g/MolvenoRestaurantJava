package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue
    private Long order_id;
    private double totalPrice;
    private String dateTime;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private  Reservation reservation;

    @OneToMany(mappedBy = "customerOrder")
    private Set<OrderItem> orderItem = new HashSet<>();

    public CustomerOrder() {
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }
  
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
