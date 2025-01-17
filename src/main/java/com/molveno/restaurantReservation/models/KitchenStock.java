package com.molveno.restaurantReservation.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
    private double amount;
    private String unit;
    private String brand;
    private String supplier;
    private int articleNumber;
    private double price;
    private String tax;
    private double pricePerUnit;
    private double stock;
    private double stockValue;
    @Column(name = "stock_limit", nullable = true)
    private double limit;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private KitchenCategory category;

    @OneToMany(mappedBy = "kitchenStock")
    private Set<MenuItemStock> menuItemStocks= new HashSet<>();

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
    }

    public double getLimit() {
        return limit;
    }
    public void setLimit(double limit) {
        this.limit = limit;
    }

    public KitchenCategory getCategory() {
        return category;
    }

    public void setCategory(KitchenCategory category) {
        this.category = category;
    }

    public Set<MenuItemStock> getMenuItemStocks() {
        return menuItemStocks;
    }
    public void setMenuItemStocks(Set<MenuItemStock> menuItemStocks) {
        this.menuItemStocks = menuItemStocks;
    }
}
