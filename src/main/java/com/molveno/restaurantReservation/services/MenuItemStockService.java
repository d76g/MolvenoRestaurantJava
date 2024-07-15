package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.MenuItemStock;

public interface MenuItemStockService {
    //create
    MenuItemStock addMenuItemStock(MenuItemStock menuItemStock);

    //read
    Iterable<MenuItemStock> getMenuItemStocks();

    //find
    MenuItemStock getMenuItemStockById(Long menuItemStock_id);

    //delete
    void deleteMenuItemStock(Long menuItemStock_id);

    //update
    MenuItemStock updateMenuItemStock(MenuItemStock menuItemStock);

    MenuItemStock findById(Long id);

    void placeOrder(String menuItemName) throws Exception;
}
