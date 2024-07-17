package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.DTO.MenuStockDTO;
import com.molveno.restaurantReservation.models.DTO.Response.MenuStockResponseDTO;
import com.molveno.restaurantReservation.models.MenuItemStock;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MenuItemStockService {
    //create
    MenuStockResponseDTO saveMenuStockItem(MenuStockDTO menuItemStock);

    Iterable<MenuStockResponseDTO> getAllMenuItemStocks();

    Iterable<MenuStockResponseDTO> getAllByMenuItemId(long menu_id);

    // list by menu id
    //find
    MenuItemStock getMenuItemStockById(Long menuItemStock_id);
    //delete
    void deleteMenuItemStock(Long menuItemStock_id);

    MenuItemStock findById(Long id);


}
