package com.molveno.restaurantReservation.models.DTO;

public class MenuDTO {
    private long menuItem_id;
    private String item_name;
    private String description ;
    private double price ;
    private String image ;
    private long menuCategoryId;
    private String menuCategoryName ;
    private long subCategoryId;
    private String subCategoryName ;
    private long mealTimeId;
    private String mealTimeName ;

    public long getMenuItem_id() {
        return menuItem_id;
    }

    public void setMenuItem_id(long menuItem_id) {
        this.menuItem_id = menuItem_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(long menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getMenuCategoryName() {
        return menuCategoryName;
    }

    public void setMenuCategoryName(String menuCategoryName) {
        this.menuCategoryName = menuCategoryName;
    }

    public long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public long getMealTimeId() {
        return mealTimeId;
    }

    public void setMealTimeId(long mealTimeId) {
        this.mealTimeId = mealTimeId;
    }

    public String getMealTimeName() {
        return mealTimeName;
    }

    public void setMealTimeName(String mealTimeName) {
        this.mealTimeName = mealTimeName;
    }
}
