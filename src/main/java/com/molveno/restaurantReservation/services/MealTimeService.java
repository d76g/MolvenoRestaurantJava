package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.MealTime;
import java.util.Optional;

public interface MealTimeService {
    MealTime save(MealTime mealTime);
    Iterable<MealTime> listMealTime();
    Optional<MealTime> getMealTimeById(long id);
    void deleteMealTime(long id);
}
