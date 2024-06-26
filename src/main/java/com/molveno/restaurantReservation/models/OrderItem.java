package com.molveno.restaurantReservation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem {
    @Id
    private Long orderItem_id;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne
    @JoinColumn(name = "menuItem_id")
    private Menu menu;

    public Long getOrderItem_id() {
        return orderItem_id;
    }

    public void setOrderItem_id(Long orderItem_id) {
        this.orderItem_id = orderItem_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public OrderItem(Long orderItem_id, int quantity, double price, CustomerOrder order, Menu menu) {
        this.orderItem_id = orderItem_id;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.menu = menu;
    }
}
