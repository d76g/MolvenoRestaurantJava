package com.molveno.restaurantReservation.models.DTO.Response;

import java.util.Set;

public class OrderResponseDTO {

    private long orderId;
    private long reservationId;
    private double totalPrice;
    private String status;
    private Set<OrderItemResponseDTO> orderItems;
    private ReservationResponseDTO reservation;

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

    public ReservationResponseDTO getReservation() {
        return reservation;
    }
    public void setReservation(ReservationResponseDTO reservation) {
        this.reservation = reservation;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
