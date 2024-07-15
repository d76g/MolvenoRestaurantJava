package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.models.DTO.MenuDTO;

import java.util.Optional;

public interface MenuService {
    MenuDTO saveMenuItem(MenuDTO menu);
    Iterable<MenuDTO> listMenu();
    Optional<Menu> getMenuItemById(long id);
    void deleteMenuItem(long id);


}
