package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.MenuItemStock;
import jakarta.transaction.Transactional;

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

    @Transactional
    void placeOrder(Long orderItemId) throws Exception;
}
