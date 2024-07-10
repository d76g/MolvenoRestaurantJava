package com.molveno.restaurantReservation.models.DTO;

import java.util.Set;

public class OrderDTO {
    private long reservationId;
    private Set<OrderItemDTO> orderItems;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public Set<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}


