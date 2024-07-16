package com.molveno.restaurantReservation.models.DTO.Response;

import com.molveno.restaurantReservation.models.DTO.MenuDTO;
import com.molveno.restaurantReservation.models.DTO.StockDTO;

public class MenuStockResponseDTO {

    private long id;
    private double amount;
    private MenuDTO menu;
    private StockDTO kitchenStock;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public MenuDTO getMenu() {
        return menu;
    }

    public void setMenu(MenuDTO menu) {
        this.menu = menu;
    }

    public StockDTO getKitchenStock() {
        return kitchenStock;
    }

    public void setKitchenStock(StockDTO kitchenStock) {
        this.kitchenStock = kitchenStock;
    }
}
