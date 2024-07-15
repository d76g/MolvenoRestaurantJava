package com.molveno.restaurantReservation.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="KITCHEN_STOCK")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class KitchenStock {
    @Id
    @GeneratedValue
    private long id ;
    private String description;
    private int amount;
    private String unit;
    private String brand;
    private String supplier;
    private int articleNumber;
    private double price;
    private String tax;
    private double pricePerUnit;
    private int stock;
    private int stockValue;
    @Column(name = "stock_limit", nullable = true)
    private int limit;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private KitchenCategory category;

    @OneToMany(mappedBy = "kitchenStock", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuItemStock> menuItemStocks;

    public Set<MenuItemStock> getMenuItemStocks() {
        return menuItemStocks;
    }

    public void setMenuItemStocks(Set<MenuItemStock> menuItemStocks) {
        this.menuItemStocks = menuItemStocks;
    }

    public void setId(long id) {
        this.id = id;
    }

    public KitchenStock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(int articleNumber) {
        this.articleNumber = articleNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockValue() {
        return stockValue;
    }

    public void setStockValue(int stockValue) {
        this.stockValue = stockValue;
    }

    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public KitchenCategory getCategory() {
        return category;
    }

    public void setCategory(KitchenCategory category) {
        this.category = category;
    }
}
