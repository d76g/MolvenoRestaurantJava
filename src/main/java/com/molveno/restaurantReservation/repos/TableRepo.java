package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepo extends JpaRepository<Table, Long>{
    // check if table already exists BY TABLE NUMBER
    boolean existsByTableNumber(long tableNumber);
}
