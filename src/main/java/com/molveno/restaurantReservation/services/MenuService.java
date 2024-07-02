package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.Menu;
import java.util.Optional;

public interface MenuService {
    Menu saveMenuItem(Menu menu);
    Iterable<Menu> listMenu();
    Optional<Menu> getMenuItemById(long id);
    void deleteMenuItem(long id);


}
