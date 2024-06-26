package com.molveno.restaurantReservation.models;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.HashSet;
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
    private String image ;

<<<<<<< HEAD
=======
    @OneToOne(mappedBy = "menuCategory")
    private Set<MenuCategory> menuCategory;

    @OneToOne(mappedBy = "subCategory")
    private Set<SubCategory> subCategory;

    @OneToOne(mappedBy = "mealTime")
    private Set<MealTime> mealtime;
    @OneToMany(mappedBy = "menuItem")
    private Set<OrderItem> orderItems;

    @ManyToMany
    @JoinTable(
            name = "menuItemStock",
            joinColumns = @JoinColumn(name = "menuItem_id"),
            inverseJoinColumns = @JoinColumn(name = "stockItem_id")
    )
    private Set<KitchenStock> kitchenStocks;

>>>>>>> 989d5852745deb6dbc6c8aaff0bf106029d9f62a
    public Menu() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menuCategory_id")
    private MenuCategory menuCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subCategory_id")
    private SubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mealTime_id")
    private MealTime mealTime;

    @OneToMany(mappedBy = "menuItemStock")
    private MenuItemStock menuItemStocks;

    @OneToMany(mappedBy = "orderItem")
    private OrderItem orderItem;

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

    public MenuItemStock getMenuItemStocks() {
        return menuItemStocks;
    }

    public void setMenuItemStocks(MenuItemStock menuItemStocks) {
        this.menuItemStocks = menuItemStocks;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
