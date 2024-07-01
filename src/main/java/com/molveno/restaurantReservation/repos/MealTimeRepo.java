package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.MealTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealTimeRepo extends JpaRepository<MealTime, Long> {
}
