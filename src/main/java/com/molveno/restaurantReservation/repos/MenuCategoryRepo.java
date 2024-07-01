package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.models.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCategoryRepo extends JpaRepository<MenuCategory, Long > {
    // check if table already exists BY menuCategoryItem_id
    boolean existsByMenuCategory(long menuCategory_id);

}
