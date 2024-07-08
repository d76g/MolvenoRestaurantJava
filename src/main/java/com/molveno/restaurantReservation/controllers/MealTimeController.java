package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.MealTime;
import com.molveno.restaurantReservation.models.SubCategory;
import com.molveno.restaurantReservation.services.MealTimeService;
import com.molveno.restaurantReservation.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MealTimeController {

    @Autowired
    private MealTimeService mealTimeService;

    // get all mealTime
    @GetMapping(value="/mealTime/all", produces = "application/json")
    public ResponseEntity<Iterable<MealTime>> getAllMealTime() {
        System.out.println("Inside getAllMealTime");
        Iterable<MealTime> allMealTime = mealTimeService.listMealTime();
        return ResponseEntity.ok(allMealTime);
    }

    // Add new mealTime
    @PostMapping(value = "/mealTime/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MealTime> addMealTime(@RequestBody MealTime mealTime) {
        System.out.println("Inside addMealTime");
        mealTime = mealTimeService.save(mealTime);
        return ResponseEntity.ok(mealTime);
    }

    // Update mealTime
    @PostMapping("/mealTime/update/{id}")
    public ResponseEntity<MealTime> updateMealTime(@PathVariable long id, @RequestBody MealTime mealTime) {
        System.out.println("Inside updateMealTime");
        MealTime updatedMealTime = mealTimeService.save(mealTime);
        if (updatedMealTime != null) {
            return new ResponseEntity<>(updatedMealTime, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete mealTime
    @DeleteMapping(value = "/mealTime/{id}")
    public void deleteMealTime(@PathVariable long id) {
        System.out.println("Inside deleteMealTime");
        mealTimeService.deleteMealTime(id);
    }

}
