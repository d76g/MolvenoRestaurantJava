package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.MenuCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCategoryRepo extends CrudRepository<MenuCategory, Long > {

}
