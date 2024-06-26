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
    private String image ;

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

    public Menu() {
    }

    public Set<MenuCategory> getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(Set<MenuCategory> menuCategory) {
        this.menuCategory = menuCategory;
    }

    public Set<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Set<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

    public Set<MealTime> getMealtime() {
        return mealtime;
    }

    public void setMealtime(Set<MealTime> mealtime) {
        this.mealtime = mealtime;
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
