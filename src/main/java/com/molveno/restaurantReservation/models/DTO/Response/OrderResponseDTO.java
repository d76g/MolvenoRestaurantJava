package com.molveno.restaurantReservation.models.DTO.Response;

import java.math.BigDecimal;
import java.util.Set;

public class OrderResponseDTO {

    private long orderId;
    private long reservationId;
    private double totalPrice;
    private Set<OrderItemResponseDTO> orderItems;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<OrderItemResponseDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemResponseDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
