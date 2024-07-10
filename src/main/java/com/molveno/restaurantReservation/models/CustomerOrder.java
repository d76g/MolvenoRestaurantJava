package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue
    private long order_id;
    private double total_price;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private  Reservation reservation;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderItem> orderItem = new HashSet<>();

    public CustomerOrder() {
    }

    public Set<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Set<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}