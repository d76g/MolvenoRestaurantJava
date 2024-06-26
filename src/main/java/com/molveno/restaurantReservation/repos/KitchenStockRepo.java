package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.KitchenStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenStockRepo extends JpaRepository<KitchenStock, Long> {
}
