package com.molveno.restaurantReservation.models.DTO;

public class OrderItemDTO {
    private long menuId;
    private int quantity;

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
