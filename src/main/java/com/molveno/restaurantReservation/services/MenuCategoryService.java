package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.MenuCategory;
import java.util.Optional;

public interface MenuCategoryService {
    MenuCategory save(MenuCategory menuCategory);
    Iterable<MenuCategory> listMenuCategory();
    Optional<MenuCategory> getMenuCategoryById(long id);
    void deleteMenuCategory(long id);
}
