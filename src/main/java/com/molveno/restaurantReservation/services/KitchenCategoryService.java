package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.KitchenCategory;
import com.molveno.restaurantReservation.repos.KitchenCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenCategoryService {
    final KitchenCategoryRepo kitchenCategoryRepo;
    @Autowired
    public KitchenCategoryService(KitchenCategoryRepo kitchenCategoryRepo) {
        this.kitchenCategoryRepo = kitchenCategoryRepo;
    }
//read
    public List<KitchenCategory> getKitchenCategories() {
        return kitchenCategoryRepo.findAll();
    }
//create
    public KitchenCategory addKitchenCategory(KitchenCategory kitchenCategory) {
        return kitchenCategoryRepo.save(kitchenCategory);
    }
    //FIND
    public KitchenCategory getKitchenCategoryById(Long category_id) {
        return kitchenCategoryRepo.findById(category_id).get();
    }

    //DELETE
    public void deleteKitchenCategory(Long category_id) {
        kitchenCategoryRepo.deleteById(category_id);
    }
    //UPDATE
    public KitchenCategory updateKitchenCategory(KitchenCategory kitchenCategory) {
        return kitchenCategoryRepo.save(kitchenCategory);
    }

}
