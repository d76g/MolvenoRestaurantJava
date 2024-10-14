package com.molveno.restaurantReservation.models;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name="MenuItem")
public class Menu {
    @Id
    @GeneratedValue
    private long menuItem_id;
    private String item_name;
    private String description ;
    private double price ;
    private String imageUrl;
    @Lob // This annotation specifies that the image is a large object
    private byte[] image;

    @ManyToOne
    @JoinColumn(name="menuCategory_id")
    private MenuCategory menuCategory;

    @ManyToOne
    @JoinColumn(name="subCategory_id")
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name="mealTime_id")
    private MealTime mealTime;

    @OneToMany(mappedBy = "menu")
    private Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private Set<MenuItemStock> menuItemStocks;


    public Menu() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MenuCategory getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(MenuCategory menuCategory) {
        this.menuCategory = menuCategory;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public MealTime getMealTime() {
        return mealTime;
    }

    public void setMealTime(MealTime mealTime) {
        this.mealTime = mealTime;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

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

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<MenuItemStock> getMenuItemStocks() {
        return menuItemStocks;
    }
    public void setMenuItemStocks(Set<MenuItemStock> menuItemStocks) {
        this.menuItemStocks = menuItemStocks;
    }

}
