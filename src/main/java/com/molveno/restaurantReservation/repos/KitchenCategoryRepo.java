package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.KitchenCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenCategoryRepo extends JpaRepository<KitchenCategory, Long> {
}
