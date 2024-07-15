package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.MenuItemStock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemStockRepo extends CrudRepository<MenuItemStock, Long> {
}
