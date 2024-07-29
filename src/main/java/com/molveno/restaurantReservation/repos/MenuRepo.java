package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.DTO.MenuDTO;
import com.molveno.restaurantReservation.models.MealTime;
import com.molveno.restaurantReservation.models.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepo extends CrudRepository<Menu, Long > {
    @Query("SELECT m FROM Menu m WHERE m.mealTime.mealTime_name = :mealTimeName")
    List<Menu> findByMealTimeName(@Param("mealTimeName") String mealTimeName);
    @Query("SELECT m FROM Menu m WHERE m.menuItem_id = (SELECT oi.menu.menuItem_id FROM OrderItem oi GROUP BY oi.menu.menuItem_id ORDER BY SUM(oi.quantity) DESC LIMIT 1)")
    Menu findMostOrderedMenuItem();
    List<Menu> findAll();
}
