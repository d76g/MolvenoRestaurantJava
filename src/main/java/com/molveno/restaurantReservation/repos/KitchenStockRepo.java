package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.KitchenStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenStockRepo extends CrudRepository<KitchenStock, Long> {
    // check if a stock is below the stock limit
    @Query("SELECT k FROM KitchenStock k WHERE k.stock < k.limit")
    Iterable<KitchenStock> findByStockLessThan();
}
