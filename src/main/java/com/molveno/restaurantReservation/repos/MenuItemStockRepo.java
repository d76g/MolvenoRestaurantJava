package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.MenuItemStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemStockRepo extends CrudRepository<MenuItemStock, Long> {
    // find all by menu id
    @Query("SELECT m FROM MenuItemStock m WHERE m.menu.menuItem_id = ?1")
    Iterable<MenuItemStock> findAllByMenu_MenuItem_id(long menu_id);


}
