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

<<<<<<< HEAD
    @OneToMany(mappedBy = "order")
    private Set<CustomerOrder> order;

    public CustomerOrder() {
    }

    public CustomerOrder(Long order_id, String reservation_id, double totalPrice, String dateTime, Reservation reservation, Set<CustomerOrder> order) {
        this.order_id = order_id;
        this.reservation_id = reservation_id;
        this.totalPrice = totalPrice;
        this.dateTime = dateTime;
        this.reservation = reservation;
        this.order = order;
=======
    public CustomerOrder() {
>>>>>>> 989d5852745deb6dbc6c8aaff0bf106029d9f62a
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
<<<<<<< HEAD

    public Set<CustomerOrder> getOrder() {
        return order;
    }

    public void setOrder(Set<CustomerOrder> order) {
        this.order = order;
    }
=======
>>>>>>> 989d5852745deb6dbc6c8aaff0bf106029d9f62a
}
