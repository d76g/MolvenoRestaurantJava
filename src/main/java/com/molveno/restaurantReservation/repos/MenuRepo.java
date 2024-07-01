package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long > {
    // check if table already exists BY menuItem_id
    boolean existsByMenuItem(long menuItem_id);
}
