package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class KitchenStock {
    @Id
    @GeneratedValue
    private Long id ;
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
    private int limit;

@ManyToOne
    @JoinColumn(name = "category_id")
    private KitchenCategory category;
@OneToMany(mappedBy = "kitchenStock")
private Set<MenuItemStock> menuItemStocks= new HashSet<>();

    public Set<MenuItemStock> getMenuItemStocks() {
        return menuItemStocks;
    }

    public void setMenuItemStocks(Set<MenuItemStock> menuItemStocks) {
        this.menuItemStocks = menuItemStocks;
    }

    @ManyToMany(mappedBy = "kitchenStocks")
    private Set<MenuItem> menuItems;
    public KitchenStock() {
    }

    public KitchenStock(Long id, String description, int amount, String unit, String brand, String supplier, int articleNumber, double price, String tax, double pricePerUnit, int stock, int stockValue, int limit, KitchenCategory category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.unit = unit;
        this.brand = brand;
        this.supplier = supplier;
        this.articleNumber = articleNumber;
        this.price = price;
        this.tax = tax;
        this.pricePerUnit = pricePerUnit;
        this.stock = stock;
        this.stockValue = stockValue;
        this.limit = limit;
        this.category = category;
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
