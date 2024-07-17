package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.MealTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealTimeRepo extends CrudRepository<MealTime, Long> {
}
