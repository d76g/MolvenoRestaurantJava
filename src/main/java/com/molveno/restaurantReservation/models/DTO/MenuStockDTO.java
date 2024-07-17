package com.molveno.restaurantReservation.models.DTO;

import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.models.Menu;

import java.util.Set;

public class MenuStockDTO {

    private long id;
    private long menuId;
    private long kitchenStockId;
    private double amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public long getKitchenStockId() {
        return kitchenStockId;
    }

    public void setKitchenStockId(long kitchenStockId) {
        this.kitchenStockId = kitchenStockId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
