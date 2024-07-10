package com.molveno.restaurantReservation.models.DTO;

public class MenuStockDTO {

    private long id;
    private long menuItemId;
    private String menuItemName;
    private long kitchenStockId;
    private String kitchenStockName;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getKitchenStockName() {
        return kitchenStockName;
    }

    public void setKitchenStockName(String kitchenStockName) {
        this.kitchenStockName = kitchenStockName;
    }

    public long getKitchenStockId() {
        return kitchenStockId;
    }

    public void setKitchenStockId(long kitchenStockId) {
        this.kitchenStockId = kitchenStockId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
