package com.molveno.restaurantReservation.models.DTO;

public class StockCategoryDTO {
    private long id;
    private String name;

    public StockCategoryDTO() {
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public String getCategoryName() {
        return name;
    }
    public void setCategoryName(String name) {
        this.name = name;
    }


}
