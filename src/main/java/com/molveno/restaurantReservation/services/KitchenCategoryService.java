package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.KitchenCategory;

import java.util.List;

public interface KitchenCategoryService {

    //read
    Iterable<KitchenCategory> getKitchenCategories();

    //create
    KitchenCategory addKitchenCategory(KitchenCategory kitchenCategory);

    //FIND
    KitchenCategory getKitchenCategoryById(Long category_id);

    //DELETE
    void deleteKitchenCategory(Long category_id);

    //UPDATE
    KitchenCategory updateKitchenCategory(KitchenCategory kitchenCategory);

    //FIND by ID
    KitchenCategory findById(Long id);
}
