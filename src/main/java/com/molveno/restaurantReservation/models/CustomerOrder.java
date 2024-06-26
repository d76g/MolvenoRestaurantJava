package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Order {
    @Id
    private Long order_id;
    private String reservation_id;
    private double totalPrice;
    private String dateTime;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private  Reservation reservation;


    @OneToMany(mappedBy = "order")
    private Set<Order> order;

    public Order() {
    }

    public Order(Long order_id, String reservation_id, double totalPrice, String dateTime, Reservation reservation, Set<Order> order) {
        this.order_id = order_id;
        this.reservation_id = reservation_id;
        this.totalPrice = totalPrice;
        this.dateTime = dateTime;
        this.reservation = reservation;
        this.order = order;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
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

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }
}
