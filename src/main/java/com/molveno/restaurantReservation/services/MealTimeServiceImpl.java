package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.MealTime;
import com.molveno.restaurantReservation.repos.MealTimeRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MealTimeServiceImpl implements MealTimeService{

    private MealTimeRepo mealTimeRepo;

    @Override
    public MealTime save(MealTime mealTime) {
        if( mealTime.getMealTime_id() == 0){
            System.out.println("Creating mealTime");
        }else{
            System.out.println("updating mealTime");
        }
        return mealTimeRepo.save(mealTime);
    }

    @Override
    public Iterable<MealTime> listMealTime() {
        return mealTimeRepo.findAll();
    }

    @Override
    public Optional<MealTime> getMealTimeById(long id) {
        return mealTimeRepo.findById(id);
    }

    @Override
    public void deleteMealTime(long id) {
        mealTimeRepo.deleteById(id);
    }
}
